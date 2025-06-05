package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Question;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileManager {

    // Path to the questions.json file inside the resources/data folder
    private static final String QUESTIONS_PATH = "/data/questions.json";

    /**
     * Reads questions.json from resources and returns a List of Question objects.
     * @return List<Question> if successful, otherwise null
     */
    public static List<Question> readQuestions() {
        try {
            // Load the file from the classpath (resources/data/questions.json)
            InputStream inputStream = FileManager.class.getResourceAsStream(QUESTIONS_PATH);

            // Convert the byte stream into a character stream using UTF-8 encoding
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            // Define the type for Gson to correctly convert the JSON array into List<Question>
            Type questionListType = new TypeToken<List<Question>>() {}.getType();

            // Use Gson to parse the JSON content into Java objects
            return new Gson().fromJson(reader, questionListType);

        } catch (Exception e) {
            // Print an error message if anything goes wrong (e.g., file not found or invalid JSON)
            System.err.println("Failed to read questions: " + e.getMessage());
            return null;
        }
    }

//    public static List<User> readUsers() {
//        try (
//                InputStream inputStream = FileManager.class.getResourceAsStream("/data/users.json");
//                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)
//        ) {
//            Type userListType = new TypeToken<List<User>>() {}.getType();
//            return new Gson().fromJson(reader, userListType);
//        } catch (Exception e) {
//            System.err.println("Failed to read users: " + e.getMessage());
//            return null;
//        }
//    }

    public static void main(String[] args) {
        List<Question> questions = FileManager.readQuestions();
        System.out.println(questions.size());
        for (int i=0; i< questions.size(); i++) {
            System.out.println("question at index(" + i + ") is: " + questions.get(i));
        }
        System.out.println(questions);
    }

}
