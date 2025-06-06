package ui;

import model.User;
import model.Question;
import service.FileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LoginAndStart {

    private JFrame frame;
    private JTextField usernameField;
    private JButton startButton;
    private JLabel statusLabel;

    public LoginAndStart() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login to Start Quiz");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null); // Center the frame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Enter your name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(usernameField, gbc);

        startButton = new JButton("Start Quiz");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(startButton, gbc);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 2;
        frame.add(statusLabel, gbc);

        startButton.addActionListener(this::handleStart);
    }

    private void handleStart(ActionEvent e) {
        String name = usernameField.getText().trim();
        if (name.isEmpty()) {
            statusLabel.setText("Please enter your name.");
            return;
        }

        List<User> users = FileManager.getUsers();
        User currentUser = null;

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            // User does not exist, create new and save
            currentUser = new User(name, new java.util.ArrayList<>());
            FileManager.addUser(currentUser);
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("New user created. Starting quiz...");
        } else {
            statusLabel.setForeground(Color.BLUE);
            statusLabel.setText("Welcome back, " + currentUser.getUsername() + "!");
        }

        // Load questions and open quiz
        List<Question> questions = FileManager.readQuestions();
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No questions found. Exiting.");
            return;
        }

        frame.dispose(); // Close login screen
        QuizScreen quizScreen = new QuizScreen(questions);
        quizScreen.display();
    }

    //@Override
    public void display() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
