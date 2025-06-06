package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Question;
import model.User;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static List<User> getUsers() {
        try (
                InputStream inputStream = FileManager.class.getResourceAsStream("/data/users.json");
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        ) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return new Gson().fromJson(reader, userListType);
        } catch (Exception e) {
            System.err.println("Failed to read users: " + e.getMessage());
            return new ArrayList<>(); // Return empty list if file missing or unreadable
        }
    }

    public static void setUsers(List<User> users) {
        try {
            Path outputPath = Paths.get("resources/data/users.json"); // writable location
            Files.createDirectories(outputPath.getParent());

            try (Writer writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                new Gson().toJson(users, writer);
            }
        } catch (Exception e) {
            System.err.println("Failed to write users: " + e.getMessage());
        }
    }

    /**
     * Adds a single user to users.json if they don’t already exist.
     * @param newUser The User to be added
     * @return true if added, false if user already exists
     */
    public static boolean addUser(User newUser) {
        List<User> users = getUsers();

        // Check for existing user by username (case-insensitive)
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                return false; // User already exists
            }
        }

        users.add(newUser);
        setUsers(users);
        return true; // Successfully added
    }




    public static void main(String[] args) {
        List<Question> questions = FileManager.readQuestions();
        System.out.println(questions.size());
        for (int i=0; i< questions.size(); i++) {
            System.out.println("question at index(" + i + ") is: " + questions.get(i));
        }
        System.out.println(questions);
    }

}
