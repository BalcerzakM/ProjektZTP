package models;

/**
 * Poziomy biegłości językowej zgodne ze skalą CEFR (A1–C2).
 *
 * Enum definiuje progi punktowe wykorzystywane do:
 * - określania aktualnego poziomu użytkownika,
 * - wyliczania postępu do kolejnego poziomu,
 * - kontroli dostępu do zestawów słów.
 */
public enum LanguageCERFLevel {
    A1(0, 100),
    A2(100, 200),
    B1(200, 300),
    B2(300, 400),
    C1(400, 500),
    C2(500, 600);

    /**
     * Minimalna liczba punktów wymagana do osiągnięcia danego poziomu.
     */
    private final int minPoints;

    /**
     * Maksymalna liczba punktów przypisana do danego poziomu.
     */
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

    /**
     * Zwraca zakres punktów przypisany do danego poziomu.
     *
     * @return różnica pomiędzy maksymalną a minimalną liczbą punktów
     */
    public int getPointsRange() {
        return maxPoints - minPoints;
    }

    /**
     * Sprawdza, czy użytkownik ma dostęp do zestawu słów
     * o określonym poziomie trudności.
     *
     * Dostęp jest przyznawany, jeśli poziom użytkownika
     * jest równy lub wyższy od poziomu zestawu.
     *
     * @param wordSetLevel poziom zestawu słów
     * @param userLevel poziom użytkownika
     * @return true, jeśli dostęp jest dozwolony
     */
    public static boolean isAccessAllowed(LanguageCERFLevel wordSetLevel, LanguageCERFLevel userLevel) {
        return (userLevel.ordinal()) >= wordSetLevel.ordinal();
    }
}
