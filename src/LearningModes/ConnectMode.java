package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectMode implements LearningMode {

    private final List<Word> left;
    private final List<String> right;

    public ConnectMode(WordSet wordSet) {
        List<Word> ws = wordSet.getWords();
        left = new ArrayList<>();
        right = new ArrayList<>();

        Collections.shuffle(ws);
        for (int i = 0; i < Math.min(8, ws.size()); i++) {
            left.add(ws.get(i));
            right.add(ws.get(i).getTarget());
        }
        Collections.shuffle(right);
    }

    public boolean check(int leftIndex, int rightIndex) {
        return left.get(leftIndex).getTarget().equals(right.get(rightIndex));
    }

    public Word removePair(int leftIndex, int rightIndex) {
        Word w = left.remove(leftIndex);
        right.remove(rightIndex);
        return w;
    }

    public boolean isFinished() {
        return left.isEmpty();
    }

    public List<Word> getLeftSources() {
        return left.stream().toList();
    }

    public List<String> getRightTargets() {
        return new ArrayList<>(right);
    }

    @Override
    public void start(WordSet wordSet, LearningSession learningSession) {

    }
}

