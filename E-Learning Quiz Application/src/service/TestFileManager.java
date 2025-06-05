package service;

import model.Question;
import service.FileManager;

import java.util.List;

public class TestFileManager {
    public static void main(String[] args) {
        // Attempt to read the questions from the JSON file
        List<Question> questions = FileManager.readQuestions();

        if (questions == null) {
            System.out.println("❌ Failed to load questions.");
            return;
        }

        // Print out each question and its options
        System.out.println("✅ Questions loaded successfully:");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.getText());

            List<String> options = q.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println("  " + (j + 1) + ". " + options.get(j));
            }

            System.out.println("Correct Answer Index: " + q.getCorrectIndex());
        }
    }
}
