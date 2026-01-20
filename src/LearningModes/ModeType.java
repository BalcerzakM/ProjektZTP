package LearningModes;

/**
 * Typ wyliczeniowy identyfikujący dostępne tryby nauki.
 *
 * Enum wykorzystywany jest jako klucz w mechanizmie Memento
 * do zapisu i przywracania stanu poszczególnych trybów
 * w ramach jednej sesji nauki.
 */
public enum ModeType {
    FLASHCARD,
    MILLIONAIRE,
    CONNECT,
    TYPING
}
