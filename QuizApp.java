// QuizApp.java
import java.util.*;

public class QuizApp {
    static class Question {
        String question;
        String[] options;
        String answer;

        Question(String question, String[] options, String answer) {
            this.question = question;
            this.options = options;
            this.answer = answer;
        }
    }

    static List<Question> questions = new ArrayList<>();
    static int score = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadQuestions();
        for (int i = 0; i < questions.size(); i++) {
            askQuestion(i);
        }
        showResults();
    }

    static void loadQuestions() {
        questions.add(new Question("What is the capital of France?",
                new String[]{"Berlin", "Paris", "Rome", "Madrid"}, "Paris"));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Venus", "Jupiter"}, "Mars"));
        questions.add(new Question("Who wrote 'To be, or not to be'?",
                new String[]{"Dickens", "Shakespeare", "Tolstoy", "Hemingway"}, "Shakespeare"));
    }

    static void askQuestion(int index) {
        Question q = questions.get(index);
        System.out.println("\nQuestion " + (index + 1) + ": " + q.question);
        for (int i = 0; i < q.options.length; i++) {
            System.out.println((i + 1) + ". " + q.options[i]);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\nTime's up!");
                showResults();
                System.exit(0);
            }
        };
        timer.schedule(task, 10000); // 10 seconds

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
            timer.cancel();
        } catch (Exception e) {
            System.out.println("Invalid input or timeout.");
            timer.cancel();
            return;
        }

        if (choice >= 1 && choice <= q.options.length) {
            if (q.options[choice - 1].equals(q.answer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect! Correct answer: " + q.answer);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void showResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your score: " + score + "/" + questions.size());
    }
}
