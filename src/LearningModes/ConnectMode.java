package LearningModes;

import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Model trybu nauki typu „łączenie”.
 *
 * Odpowiada za logikę trybu, w którym użytkownik łączy słowa źródłowe
 * z ich poprawnymi tłumaczeniami. Klasa nie posiada wiedzy o interfejsie
 * użytkownika ani o sterowaniu aplikacją.
 *
 * ConnectMode jest elementem warstwy Model we wzorcu MVC.
 * Wspiera również mechanizm Memento poprzez możliwość deterministycznego
 * odtwarzania stanu na podstawie seedu generatora losowego.
 */
public class ConnectMode implements LearningMode {

    /**
     * Bazowy zestaw słów wykorzystywany do generowania kolejnych stanów trybu.
     * Lista ta nie jest modyfikowana w trakcie działania trybu.
     */
    private final List<Word> baseWords;
    private final List<Word> left = new ArrayList<>();
    private final List<String> right = new ArrayList<>();

    /**
     * Generator losowości kontrolowany przez sesję nauki.
     * Użycie seedu umożliwia odtworzenie stanu trybu po jego wznowieniu.
     */
    private Random rand;

    /**
     * Tworzy model trybu łączenia na podstawie podanego zbioru słów.
     *
     * @param wordSet zbiór słów używany w danej sesji nauki
     */
    public ConnectMode(WordSet wordSet) {
        this.baseWords = new ArrayList<>(wordSet.getWords());
    }


    /**
     * Inicjalizuje nowy stan trybu.
     *
     * Metoda wywoływana przy pierwszym uruchomieniu trybu w sesji.
     * Odpowiada za ustawienie generatora losowego oraz wygenerowanie
     * początkowego układu słów.
     *
     * @param seed seed generatora losowego zapisane w sesji
     */
    @Override
    public void startNew(long seed) {
        this.rand = new Random(seed);
        generateInitialState();
    }

    /**
     * Odtwarza stan trybu na podstawie zapisanego seedu.
     *
     * Metoda jest wykorzystywana przy powrocie do trybu
     * i stanowi element implementacji wzorca Memento.
     *
     * @param seed seed zapisane w obiekcie sesji (LearningSession)
     */
    public void restore( long seed) {
        this.rand = new Random(seed);
        generateInitialState();

    }


    /**
     * Generuje początkowy stan list słów i tłumaczeń.
     *
     * Wybierana jest ograniczona liczba słów, które następnie
     * są losowo rozmieszczane po obu stronach.
     */
    private void generateInitialState() {
        left.clear();
        right.clear();

        List<Word> shuffled = new ArrayList<>(baseWords);
        Collections.shuffle(shuffled, rand);

        for (int i = 0; i < Math.min(8, shuffled.size()); i++) {
            left.add(shuffled.get(i));
            right.add(shuffled.get(i).target());
        }

        Collections.shuffle(right, rand);
    }

    /**
     * Sprawdza poprawność wybranego połączenia.
     *
     * @param leftIndex indeks słowa źródłowego
     * @param rightIndex indeks tłumaczenia
     * @return true jeśli połączenie jest poprawne
     */
    public boolean check(int leftIndex, int rightIndex) {
        return left.get(leftIndex).target().equals(right.get(rightIndex));
    }

    /**
     * Usuwa poprawnie połączoną parę ze stanu trybu.
     *
     * Zwrócone słowo może zostać wykorzystane przez kontroler
     * do powiadomienia obserwatorów o poprawnej odpowiedzi.
     *
     * @param leftIndex indeks słowa
     * @param rightIndex indeks tłumaczenia
     * @return usunięte słowo
     */
    public Word removePair(int leftIndex, int rightIndex) {
        Word w = left.remove(leftIndex);
        right.remove(rightIndex);
        return w;
    }

    /**
     * Informuje, czy tryb został ukończony.
     *
     * @return true jeśli wszystkie pary zostały połączone
     */
    public boolean isFinished() {
        return left.isEmpty();
    }

    /**
     * Zwraca aktualny stan listy słów źródłowych.
     */
    public List<Word> getLeftSources() {
        return left.stream().toList();
    }

    /**
     * Zwraca aktualny stan listy tłumaczeń.
     */
    public List<String> getRightTargets() {
        return new ArrayList<>(right);
    }

}

