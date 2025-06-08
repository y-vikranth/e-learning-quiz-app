package model;

import java.util.List;

/**
 * Represents a quiz consisting of multiple questions.
 */
public class Quiz {
    private List<Question> questions;
    private int currentIndex;
    private int score;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentIndex = 0;
        this.score = 0;
    }

    public Question getCurrentQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex);
        } else {
            return null;
        }
    }

    public boolean hasNextQuestion() {
        return currentIndex < questions.size() - 1;
    }

    public void moveToNextQuestion() {
        if (hasNextQuestion()) {
            currentIndex++;
        }
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public void reset() {
        currentIndex = 0;
        score = 0;
    }
}
