public class FlashCardQuestionFactory implements QuestionFactory {
    @Override
    public Question createQuestion(Word w, WordSet wordSet) {
        return new Question(w.getSource(), w.getTarget(), null);
    }
}
