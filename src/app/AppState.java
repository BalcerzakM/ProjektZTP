package app;

/**
 * Enum reprezentujący główne stany aplikacji.
 *
 * Każda wartość odpowiada logicznemu etapowi działania aplikacji
 * oraz jest mapowana na dedykowany kontroler w {@link AppRouter}.
 *
 * Wykorzystanie typu wyliczeniowego pozwala:
 * - jednoznacznie definiować możliwe stany aplikacji,
 * - uprościć nawigację pomiędzy widokami,
 * - zapobiec przełączaniu się do nieobsługiwanych ekranów.
 */
public enum AppState {
    /** Stan uwierzytelniania użytkownika (logowanie / rejestracja). */
    Authentication,
    /** Główne menu aplikacji po zalogowaniu użytkownika. */
    MainMenu,
    /** Stan wyboru lub ładowania zestawu słów. */
    LoadWordSet,
    /** Aktywna sesja nauki wraz z trybami ćwiczeń. */
    LearningSession,
    /** Stan związany z danymi użytkownika. */
    User,
    /** Widok statystyk użytkownika. */
    Statistics
}
