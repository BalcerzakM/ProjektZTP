package models;

public enum LanguageCERFLevel {
    A1(0, 100),
    A2(100, 200),
    B1(200, 300),
    B2(300, 400),
    C1(400, 500),
    C2(500, 600);

    private final int minPoints;
    private final int maxPoints;

    LanguageCERFLevel(int min, int max) {
        this.minPoints = min;
        this.maxPoints = max;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getPointsRange() {
        return maxPoints - minPoints;
    }

    public static boolean isAccessAllowed(LanguageCERFLevel wordSetLevel, LanguageCERFLevel userLevel) {
        return (userLevel.ordinal()) >= wordSetLevel.ordinal();
    }
}
