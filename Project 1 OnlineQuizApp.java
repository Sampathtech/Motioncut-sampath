import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }
}

class Quiz {
    private ArrayList<Question> questions;
    private int score;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            question.displayQuestion();

            int userAnswer = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Your answer (choose a number): ");
                try {
                    userAnswer = scanner.nextInt() - 1;

                    if (userAnswer < 0 || userAnswer >= question.getOptions().length) {
                        System.out.println("Invalid option. Please choose a number between 1 and " + question.getOptions().length);
                    } else {
                        validInput = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear invalid input
                }
            }

            if (question.isCorrect(userAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong answer.\n");
            }
        }

        System.out.println("Quiz completed! Your score: " + score + "/" + questions.size());
        scanner.close();
    }

    public void loadQuestionsFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                // Read the question
                String questionText = fileScanner.nextLine();

                // Read the options (comma-separated)
                String optionsLine = fileScanner.nextLine();
                String[] options = optionsLine.split(", ");

                // Read the correct answer (as an index starting from 1)
                int correctAnswer = Integer.parseInt(fileScanner.nextLine()) - 1;

                // Add the question to the quiz
                Question question = new Question(questionText, options, correctAnswer);
                addQuestion(question);

                // Skip any empty line between questions
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file '" + filename + "' was not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();

        // Load questions from file
        String filename = "questions.txt";  // Name of the file containing questions
        quiz.loadQuestionsFromFile(filename);

        // Start the quiz
        quiz.start();
    }
}
