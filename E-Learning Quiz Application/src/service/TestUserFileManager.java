package service;

import model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Test class to verify reading and writing of User objects to users.json
 * using the addUser() method only.
 */
public class TestUserFileManager {

    public static void main(String[] args) {
        // Create individual users
        User u1 = new User("Alice", Arrays.asList(70, 80, 90));
        User u2 = new User("Bob", Arrays.asList(65, 75));
        User u3 = new User("Charlie", List.of(85));

        // Try to add them one by one
        System.out.println("➕ Adding users using addUser()...");

        for (User user : Arrays.asList(u1, u2, u3)) {
            boolean added = FileManager.addUser(user);
            System.out.println(added
                    ? "✅ Added: " + user.getUsername()
                    : "⚠️ Already exists: " + user.getUsername());
        }

        // Show all users from file
        System.out.println("\n📄 Users in File:");
        List<User> allUsers = FileManager.getUsers();
        for (User user : allUsers) {
            System.out.println("👤 Username: " + user.getUsername());
            System.out.println("   Attempts: " + user.getNumAttempts());
            System.out.println("   Scores: " + user.getScores());
        }
    }
}
