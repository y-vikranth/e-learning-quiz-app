package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user who takes quizzes.
 * Stores the username and a list of their quiz scores.
 */
public class User {
    private String username;
    private List<Integer> scores;

    public User() {
        // Required for Gson
        this.scores = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
        this.scores = new ArrayList<>();

    }
    public User(String username, List<Integer> scores) {
        this.username = username;
        this.scores = scores;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void addScore(int score) {
        this.scores.add(score);
    }

    public int getNumAttempts() {
        return scores.size();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", scores=" + scores +
                '}';
    }
}
