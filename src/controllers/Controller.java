package controllers;

/**
 * Wspólny interfejs dla wszystkich kontrolerów aplikacji.
 *
 * Definiuje kontrakt uruchamiania kontrolera w architekturze MVC.
 * Wywołanie metody run() oznacza rozpoczęcie pracy kontrolera,
 * w szczególności:
 * - inicjalizację widoku,
 * - rejestrację obsługi zdarzeń,
 * - ustawienie odpowiedniego panelu w routerze aplikacji.
 *
 * Konkretne implementacje decydują o szczegółowym przebiegu
 * uruchamiania danego fragmentu aplikacji.
 */
public interface Controller {
    /**
     * Uruchamia kontroler i rozpoczyna obsługę danego
     * fragmentu aplikacji.
     */
    void run();
}
