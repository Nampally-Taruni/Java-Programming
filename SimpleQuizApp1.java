import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleQuizApp1 extends JFrame {
    private static final int TIMER_DURATION = 15;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLeft = TIMER_DURATION;
    private Timer timer;

    String[] questions = {
        "What is the default value of a boolean variable in Java?",
        "Which method is the entry point of a Java program?",
        "Which keyword is used to inherit a class in Java?",
        "Which of the following is not a Java Keyword?",
        "Which class is the parent of all classes in Java?"
    };

    String[][] options = {
        {"true", "false", "null", "0"},
        {"main()", "start()", "run()", "init()"},
        {"implements", "extends", "super", "this"},
        {"static", "Boolean", "void", "private"},
        {"System", "Object", "Class", "ClassLoader"}
    };

    String[] correctAnswers = {
        "false",
        "main()",
        "extends",
        "Boolean",
        "Object"
    };

    // GUI Components
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel timerLabel;

    public SimpleQuizApp1() {
        setTitle("Simple Quiz Application");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Question Label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Option Buttons
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new OptionButtonListener());
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        // Timer Label
        timerLabel = new JLabel("Time Left: " + timeLeft, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(timerLabel, BorderLayout.SOUTH);

        // Show the first question
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            // Update question text and options
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[currentQuestionIndex][i]);
                optionButtons[i].setBackground(null); // Reset color
            }

            // Reset and start the timer
            timeLeft = TIMER_DURATION;
            timerLabel.setText("Time Left: " + timeLeft);
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    submitAnswer(); // Auto-submit if time runs out
                }
            }
        });
        timer.start();
    }

    private void submitAnswer() {
        for (JButton button : optionButtons) {
            if (button.getBackground() == Color.YELLOW) {
                if (button.getText().equals(correctAnswers[currentQuestionIndex])) {
                    score++;
                }
                break;
            }
        }
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this,
            "Your Score: " + score + " out of " + questions.length);
        System.exit(0);
    }

    // Listen for option selection (highlight selected option)
    private class OptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            for (JButton button : optionButtons) {
                button.setBackground(null);
            }
            selectedButton.setBackground(Color.YELLOW);
        }
    }

    public static void main(String[] args) {
        SimpleQuizApp1 app = new SimpleQuizApp1();
        app.setVisible(true);
    }
}
