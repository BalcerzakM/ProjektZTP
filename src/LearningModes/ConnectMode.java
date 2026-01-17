package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConnectMode implements LearningMode {

    private final List<Word> baseWords;
    private final List<Word> left = new ArrayList<>();
    private final List<String> right = new ArrayList<>();

    private Random rand;

    public ConnectMode(WordSet wordSet) {
        this.baseWords = new ArrayList<>(wordSet.getWords());
    }

    public void startNew(long seed) {
        this.rand = new Random(seed);
        generateInitialState();
    }

    public void restore( long seed) {
        this.rand = new Random(seed);
        generateInitialState();

    }



    private void generateInitialState() {
        left.clear();
        right.clear();

        List<Word> shuffled = new ArrayList<>(baseWords);
        Collections.shuffle(shuffled, rand);

        for (int i = 0; i < Math.min(8, shuffled.size()); i++) {
            left.add(shuffled.get(i));
            right.add(shuffled.get(i).getTarget());
        }

        Collections.shuffle(right, rand);
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

