import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MillionaireTypeQuestionFactory implements QuestionFactory {
    @Override
    public Question createQuestion(Word w, WordSet wordSet) {
        //WordSet powinien byc przekazywany aby te inne odpowiedzi miały sens, ale też aby brało słówka z odpowiedniego działu
        //i losowanie odpowiedzi albo shuffle za każdym razem tylko to liniówka pewnie, albo math rand z wykluczeniem opcji co były, aby nie powtarzało odpowiedzi.
        List<Word> ws = wordSet.getWords();
        List<String> options = new ArrayList<String>();
        options.add(w.getTarget());
        //tutaj testy powinny być czy wordSet ma wystarczająco słów i czy nie są puste itd.
        options.add(ws.get(0).getTarget());
        options.add(ws.get(1).getTarget());
        options.add(ws.get(2).getTarget());

        Collections.shuffle(options);

        return new Question("Jakie jest poprawne tłumaczenie słowa "+w.getSource(), w.getTarget(), options);
    }
}
