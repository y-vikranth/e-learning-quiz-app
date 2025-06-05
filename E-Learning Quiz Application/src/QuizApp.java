package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class QuizApp {
    public static void main(String[] args) {
        new Login();
    }
}

class Login extends JFrame implements ActionListener {
    JTextField nameField, rollField;
    JButton nextButton, backButton;

    Login() {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setBounds(50, 50, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 50, 150, 25);
        add(nameField);

        JLabel rollLabel = new JLabel("Enter your Roll No:");
        rollLabel.setBounds(50, 100, 150, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(200, 100, 150, 25);
        add(rollField);

        nextButton = new JButton("Next");
        nextButton.setBounds(50, 160, 100, 30);
        nextButton.addActionListener(this);
        add(nextButton);

        backButton = new JButton("Back");
        backButton.setBounds(250, 160, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();

            if (name.isEmpty() || roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and roll number.");
            } else {
                new Quiz(name, roll);
                dispose();
            }
        } else if (e.getSource() == backButton) {
            System.exit(0);
        }
    }
}

class Quiz extends JFrame implements ActionListener {
    String[][] questions = new String[10][5];
    String[] answers = new String[10];
    String[] userAnswers = new String[10];
    JLabel qnoLabel, questionLabel, timerLabel, statsLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup optionsGroup;
    JButton nextButton, submitButton;
    Timer timer;
    JProgressBar timeProgressBar;
    int timeLeft = 15;
    int currentQuestion = 0;
    int score = 0;
    String name, roll;

    Quiz(String name, String roll) {
        this.name = name;
        this.roll = roll;

        setTitle("Quiz");
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        qnoLabel = new JLabel();
        qnoLabel.setBounds(50, 50, 50, 30);
        qnoLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(qnoLabel);

        questionLabel = new JLabel();
        questionLabel.setBounds(100, 50, 600, 30);
        questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(questionLabel);

        opt1 = new JRadioButton();
        opt1.setBounds(100, 100, 600, 30);
        add(opt1);

        opt2 = new JRadioButton();
        opt2.setBounds(100, 150, 600, 30);
        add(opt2);

        opt3 = new JRadioButton();
        opt3.setBounds(100, 200, 600, 30);
        add(opt3);

        opt4 = new JRadioButton();
        opt4.setBounds(100, 250, 600, 30);
        add(opt4);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(opt1);
        optionsGroup.add(opt2);
        optionsGroup.add(opt3);
        optionsGroup.add(opt4);

        timerLabel = new JLabel("Time left: 15");
        timerLabel.setBounds(600, 10, 150, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        timeProgressBar = new JProgressBar(0, 15);
        timeProgressBar.setValue(15);
        timeProgressBar.setBounds(100, 300, 300, 20);
        timeProgressBar.setForeground(Color.BLUE);
        add(timeProgressBar);

        nextButton = new JButton("Next");
        nextButton.setBounds(100, 350, 100, 30);
        nextButton.addActionListener(this);
        add(nextButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 350, 100, 30);
        submitButton.addActionListener(this);
        submitButton.setEnabled(false);
        add(submitButton);

        statsLabel = new JLabel();
        statsLabel.setBounds(100, 400, 500, 30);
        add(statsLabel);

        loadQuestions();
        displayQuestion();

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                timeProgressBar.setValue(timeLeft);
                if (timeLeft <= 0) {
                    recordAnswer();
                    playSound("beep.wav");
                    moveToNextQuestion();
                }
            }
        });
        timer.start();

        setVisible(true);
    }

    void playSound(String soundFile) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e);
        }
    }

    void loadQuestions() {
        questions[0][0] = "Number of primitive data types in Java are?";
        questions[0][1] = "6";
        questions[0][2] = "7";
        questions[0][3] = "8";
        questions[0][4] = "9";
        answers[0] = "8";

        questions[1][0] = "What is the size of float and double in Java?";
        questions[1][1] = "32 and 64";
        questions[1][2] = "32 and 32";
        questions[1][3] = "64 and 64";
        questions[1][4] = "64 and 32";
        answers[1] = "32 and 64";

        questions[2][0] = "Automatic type conversion is possible in which case?";
        questions[2][1] = "Byte to int";
        questions[2][2] = "Int to Long";
        questions[2][3] = "Long to int";
        questions[2][4] = "Short to int";
        answers[2] = "Int to Long";

        questions[3][0] = "When an array is passed to a method, what does the method receive?";
        questions[3][1] = "The reference of the array";
        questions[3][2] = "A copy of the array";
        questions[3][3] = "Length of the array";
        questions[3][4] = "Address of the first element";
        answers[3] = "The reference of the array";

        questions[4][0] = "Which of the following is not a Java feature?";
        questions[4][1] = "Object-oriented";
        questions[4][2] = "Use of pointers";
        questions[4][3] = "Portable";
        questions[4][4] = "Dynamic and Extensible";
        answers[4] = "Use of pointers";

        questions[5][0] = "Which keyword is used for accessing the features of a package?";
        questions[5][1] = "package";
        questions[5][2] = "import";
        questions[5][3] = "extends";
        questions[5][4] = "export";
        answers[5] = "import";

        questions[6][0] = "What is the default value of byte variable?";
        questions[6][1] = "0";
        questions[6][2] = "null";
        questions[6][3] = "0.0";
        questions[6][4] = "undefined";
        answers[6] = "0";

        questions[7][0] = "What is the return type of Constructors?";
        questions[7][1] = "int";
        questions[7][2] = "void";
        questions[7][3] = "float";
        questions[7][4] = "None of the above";
        answers[7] = "None of the above";

        questions[8][0] = "Which of the following is a reserved keyword in Java?";
        questions[8][1] = "object";
        questions[8][2] = "strictfp";
        questions[8][3] = "main";
        questions[8][4] = "system";
        answers[8] = "strictfp";

        questions[9][0] = "Which exception is thrown when Java is out of memory?";
        questions[9][1] = "MemoryError";
        questions[9][2] = "OutOfMemoryError";
        questions[9][3] = "MemoryFullException";
        questions[9][4] = "MemoryOutOfBoundsException";
        answers[9] = "OutOfMemoryError";
    }

    void displayQuestion() {
        qnoLabel.setText("Q" + (currentQuestion + 1) + ":");
        questionLabel.setText(questions[currentQuestion][0]);
        opt1.setText(questions[currentQuestion][1]);
        opt2.setText(questions[currentQuestion][2]);
        opt3.setText(questions[currentQuestion][3]);
        opt4.setText(questions[currentQuestion][4]);
        optionsGroup.clearSelection();
        timeLeft = 15;
        timeProgressBar.setValue(15);
    }

    void recordAnswer() {
        if (opt1.isSelected()) userAnswers[currentQuestion] = opt1.getText();
        else if (opt2.isSelected()) userAnswers[currentQuestion] = opt2.getText();
        else if (opt3.isSelected()) userAnswers[currentQuestion] = opt3.getText();
        else if (opt4.isSelected()) userAnswers[currentQuestion] = opt4.getText();
        else userAnswers[currentQuestion] = "";
    }

    void moveToNextQuestion() {
        recordAnswer();
        currentQuestion++;
        if (currentQuestion >= questions.length || questions[currentQuestion][0] == null) {
            timer.stop();
            calculateScore();
        } else {
            displayQuestion();
        }
        if (currentQuestion == questions.length - 1) {
            nextButton.setEnabled(false);
            submitButton.setEnabled(true);
        }
    }

    void calculateScore() {
        int correct = 0, incorrect = 0;
        for (int i = 0; i < questions.length; i++) {
            if (answers[i] != null && answers[i].equals(userAnswers[i])) {
                correct++;
            } else {
                incorrect++;
            }
        }
        score = correct;

        String summary = "Quiz Completed, " + name + "!\n" +
                "Roll No: " + roll + "\n" +
                "Correct: " + correct + ", Incorrect: " + incorrect + "\n" +
                "Score: " + score + "/" + questions.length;
        statsLabel.setText("Correct: " + correct + ", Incorrect: " + incorrect);
        saveScore(summary);
        JOptionPane.showMessageDialog(this, summary);
        System.exit(0);
    }

    void saveScore(String content) {
        try (FileWriter fw = new FileWriter("quiz_scores.txt", true)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            fw.write(date + "\n" + content + "\n-------------------------\n");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            moveToNextQuestion();
        } else if (e.getSource() == submitButton) {
            timer.stop();
            recordAnswer();
            calculateScore();
        }
    }
}
