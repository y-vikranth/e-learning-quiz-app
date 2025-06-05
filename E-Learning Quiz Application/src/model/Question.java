package model;

import java.util.List;

public class Question {
    private String text;
    private List<String> options;
    private int correctIndex;

    public Question() {
        // Default constructor required for JSON deserialization
    }

    public Question(String text, List<String> options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", options=" + options +
                ", correctIndex=" + correctIndex +
                '}';
    }
}
