package LearningModes;

import models.LearningSession;
import models.WordSet;

public interface LearningMode {
    void start(LearningSession learningSession);
}
