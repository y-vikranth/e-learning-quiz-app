package service;

import model.Question;

import java.util.List;

public class QuizManager {

    private final List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizManager(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = -1;  // will increment on nextQuestion()
        this.score = 0;
    }

    // Checks if more questions remain
    public boolean hasNextQuestion() {
        return currentQuestionIndex + 1 < questions.size();
    }

    // Returns next question and increments index
    public Question nextQuestion() {
        if (!hasNextQuestion()) {
            return null;
        }
        currentQuestionIndex++;
        return questions.get(currentQuestionIndex);
    }

    // Called when user answers the current question
    // selectedIndex = option index chosen by user
    public void answerCurrentQuestion(int selectedIndex) {
        Question current = questions.get(currentQuestionIndex);
        if (current.getCorrectIndex() == selectedIndex) {
            score++;
        }
    }

    // Total number of questions in the quiz
    public int getTotalQuestions() {
        return questions.size();
    }

    // Number of correct answers so far
    public int getScore() {
        return score;
    }
}
