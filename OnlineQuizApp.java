import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


class Question
{
    private String questionText;
    private String[] options;
    private int correctAnswer;

    
    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() 
    {
        System.out.println(questionText);
        for (int i = 0; i < options.length; i++)
        {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }


    public boolean isCorrect(int userAnswer) 
    {
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
                    scanner.next(); 
                }
            }
            
            if (question.isCorrect(userAnswer)) 
            {
                System.out.println("Correct!\n");
                score++;
            }
            else 
            {
                System.out.println("Wrong answer.\n");
            }
        }
        
        System.out.println("Quiz completed! Your score: " + score + "/" + questions.size());
        scanner.close();
    }
}


public class OnlineQuizApp
{
    public static void main(String[] args) 
    {
        Quiz quiz = new Quiz();

        String[] options1 = {"Java", "Python", "C++", "JavaScript"};
        String[] options2 = {"2", "3", "4", "5"};
        
        Question q1 = new Question("Which programming language is platform-independent?", options1, 0);
        Question q2 = new Question("What is 2 + 2?", options2, 2);
        
        quiz.addQuestion(q1);
        quiz.addQuestion(q2);
        quiz.start();
    }
}
