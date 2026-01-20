package LearningModes;
/**
 * Wspólny interfejs dla wszystkich trybów nauki.
 *
 * Definiuje minimalny kontrakt, który musi spełniać każdy tryb,
 * aby mógł zostać uruchomiony w ramach sesji nauki.
 *
 * Interfejs umożliwia jednolite zarządzanie trybami
 * oraz integrację z mechanizmem Memento poprzez kontrolowane
 * inicjalizowanie generatorów losowych.
 */
public interface LearningMode {
    /**
     * Rozpoczyna nową instancję trybu nauki.
     *
     * Metoda wywoływana jest przy pierwszym uruchomieniu trybu
     * i służy do inicjalizacji jego stanu na podstawie seedu sesji.
     *
     * @param seed seed generatora losowego sesji nauki
     */
    void startNew(long seed);
}
