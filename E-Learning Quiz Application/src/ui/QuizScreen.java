package ui;

import model.Question;
import service.QuizManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizScreen {

    private JFrame frame;
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private QuizManager quizManager;

    public QuizScreen(List<Question> questions) {
        quizManager = new QuizManager(questions);
        initialize();
        loadNextQuestion();
    }

    private void initialize() {
        frame = new JFrame("E-Learning Quiz - Take the Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // center the window
        frame.setLayout(new BorderLayout(10, 10));

        questionLabel = new JLabel("Question will appear here", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton("Option " + (i + 1));
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.addActionListener(new OptionButtonListener(i));
            optionButtons[i] = btn;
            optionsPanel.add(btn);
        }

        frame.add(optionsPanel, BorderLayout.CENTER);
    }

    private void loadNextQuestion() {
        if (!quizManager.hasNextQuestion()) {
            // Quiz finished, show results and close or navigate elsewhere
            JOptionPane.showMessageDialog(frame,
                    "Quiz finished!\nYour score: " + quizManager.getScore() + " out of " + quizManager.getTotalQuestions(),
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return;
        }

        Question q = quizManager.nextQuestion();

        questionLabel.setText("<html><body style='width: 500px'>" + q.getText() + "</body></html>");
        List<String> options = q.getOptions();

        for (int i = 0; i < optionButtons.length; i++) {
            if (i < options.size()) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setEnabled(true);
                optionButtons[i].setVisible(true);
            } else {
                optionButtons[i].setVisible(false);  // Hide buttons if less than 4 options
            }
        }
    }

    private class OptionButtonListener implements ActionListener {
        private final int index;

        public OptionButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Submit answer to QuizManager
            quizManager.answerCurrentQuestion(index);

            // Load next question
            loadNextQuestion();
        }
    }

    //Override
    public void display() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
