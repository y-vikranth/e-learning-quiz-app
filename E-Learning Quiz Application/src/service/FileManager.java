package service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import model.Question;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String QUESTIONS_PATH = "/data/questions.json";
    private static final String USERS_RESOURCE_PATH = "/data/users.json";
    private static final Path USERS_WRITABLE_PATH = Paths.get("resources/data/users.json");

    public static List<Question> readQuestions() {
        try (
                InputStream inputStream = FileManager.class.getResourceAsStream(QUESTIONS_PATH);
                InputStreamReader reader = inputStream == null ? null : new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        ) {
            if (inputStream == null) {
                System.err.println("Questions file not found at: " + QUESTIONS_PATH);
                return new ArrayList<>();
            }
            if (reader == null) {
                System.err.println("Questions file not found in resources.");
                return new ArrayList<>();
            }

            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return new Gson().fromJson(reader, questionListType);

        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON syntax in questions file: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("File was not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error while reading questions: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Permission denied: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while reading questions: " + e.getMessage());
        }

        return new ArrayList<>();
    }

//    public static List<User> getUsers() {
//        try (
//                InputStream inputStream = FileManager.class.getResourceAsStream(USERS_RESOURCE_PATH);
//                InputStreamReader reader = inputStream == null ? null : new InputStreamReader(inputStream, StandardCharsets.UTF_8)
//        ) {
//            if (reader == null) {
//                System.err.println("Users file not found in resources.");
//                return new ArrayList<>();
//            }
//            if (inputStream == null) {
//                System.err.println("Questions file not found at: " + QUESTIONS_PATH);
//                return new ArrayList<>();
//            }
//
//            Type userListType = new TypeToken<List<User>>() {}.getType();
//            return new Gson().fromJson(reader, userListType);
//
//        } catch (JsonSyntaxException e) {
//            System.err.println("Invalid JSON syntax in users file: " + e.getMessage());
//        } catch (FileNotFoundException e) {
//            System.err.println("File was not found: " + e.getMessage());
//        } catch (IOException e) {
//            System.err.println("IO error while reading users: " + e.getMessage());
//        } catch (SecurityException e) {
//            System.err.println("Permission denied: " + e.getMessage());
//        } catch (Exception e) {
//            System.err.println("Unexpected error while reading users: " + e.getMessage());
//        }
//        return new ArrayList<>();
//    }

    public static List<User> getUsers() {
        // Define the path where the users.json file is located on disk
        Path inputPath = Paths.get("resources/data/users.json");
        // Prepare the list to return
        List<User> users = new ArrayList<>();

        try (
                // Open a reader with UTF-8 encoding
                Reader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)
        ) {
            // Define the generic type for Gson to deserialize JSON into List<User>
            Type userListType = new TypeToken<List<User>>() {
            }.getType();

            // Deserialize the JSON content into a list of User objects
            users = new Gson().fromJson(reader, userListType);

            // Return the list if successfully parsed
            return users;

        } catch (FileNotFoundException e) {
            System.err.println("❌ File not found: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("🚫 Access denied to read users file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("⚠️ Path is null or unreadable: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("📂 I/O error while reading users file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("💥 Unexpected error reading users: " + e.getMessage());
        }

        // If something goes wrong, return an empty list instead of crashing
        return users;
    }



    public static void setUsers(List<User> users) {
        try {
            // Ensure the parent directory (resources/data) exists, otherwise create it.
            Files.createDirectories(USERS_WRITABLE_PATH.getParent());

            // Try to open a buffered writer to the users.json file using UTF-8 encoding
            try (Writer writer = Files.newBufferedWriter(USERS_WRITABLE_PATH, StandardCharsets.UTF_8)) {
                // Use Gson to convert the list of User objects to JSON format and write it to the file
                new Gson().toJson(users, writer);
            }

        } catch (FileNotFoundException e) {
            // This block is rarely reached here because FileNotFoundException is a subclass of IOException,
            // and Files.newBufferedWriter usually throws IOException instead.
            System.err.println("File not found: " + e.getMessage());

        } catch (IOException e) {
            // Catches I/O errors such as problems with disk access or failure to create/write the file
            System.err.println("Failed to write users file due to IO error: " + e.getMessage());

        } catch (SecurityException e) {
            // Catches issues where the Java process doesn’t have permission to write to the location
            System.err.println("Permission denied: " + e.getMessage());

        } catch (Exception e) {
            // Catches any other unexpected exceptions
            System.err.println("Unexpected error while writing users file: " + e.getMessage());
        }
    }

    public static boolean addOrUpdateUser(User newUser) {
        try {
            // Step 1: Load existing users from the file
            List<User> users = getUsers();
            boolean userFound = false;

            // Step 2: Search for the user in the existing list
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equalsIgnoreCase(newUser.getUsername())) {
                    // If the user exists, update the entry
                    users.set(i, newUser);
                    userFound = true;
                    break;
                }
            }

            // Step 3: If the user was not found, add them as a new user
            if (!userFound) {
                users.add(newUser);
            }

            // Step 4: Ensure the directory exists
            Files.createDirectories(USERS_WRITABLE_PATH.getParent());

            // Step 5: Write the updated user list back to the JSON file
            try (Writer writer = Files.newBufferedWriter(USERS_WRITABLE_PATH, StandardCharsets.UTF_8)) {
                new Gson().toJson(users, writer);

            }

            return true; // Success

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to write users file due to IO error: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Permission denied: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Attempted to write to a null location: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while writing users file: " + e.getMessage());
        }

        return false; // Failure due to some exception

    }

    public static void main(String[] args) {
        List<Question> questions = readQuestions();
        System.out.println("Total questions: " + questions.size());
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ": " + questions.get(i));
        }
    }
}
