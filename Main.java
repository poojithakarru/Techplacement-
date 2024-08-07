import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        parser.start();
    }
}

class Quiz {
    private String topic;
    private List<Question> questions;

    public Quiz(String topic) {
        this.topic = topic;
        this.questions = new ArrayList<>();
    }

    public String getTopic() {
        return topic;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}


  class CommandParser {
    private Scanner scanner;
    private HashMap<String, Quiz> quizzes;

    public CommandParser() {
        this.scanner = new Scanner(System.in);
        this.quizzes = new HashMap<>();
    }

    public void start() {
        String command;
        System.out.println("Welcome to the Quiz Generator!");
        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            parseCommand(command);
        }
    }

    private void parseCommand(String command) {
        String[] parts = command.split(" ");
        String mainCommand = parts[0];

        switch (mainCommand) {
            case "createQuiz":
                createQuiz(parts);
                break;
            case "addQuestion":
                addQuestion(parts);
                break;
            case "takeQuiz":
                takeQuiz(parts);
                break;
            default:
                System.out.println("Unknown command. Please try again.");
        }
    }

    private void createQuiz(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: createQuiz <topic>");
            return;
        }
        String topic = parts[1];
        quizzes.put(topic, new Quiz(topic));
        System.out.println("Quiz on topic \"" + topic + "\" created.");
    }

    private void addQuestion(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: addQuestion <topic>");
            return;
        }
        String topic = parts[1];
        if (!quizzes.containsKey(topic)) {
            System.out.println("Quiz on topic \"" + topic + "\" does not exist.");
            return;
        }

        System.out.println("Enter the question text:");
        String questionText = scanner.nextLine();

        List<String> options = new ArrayList<>();
        System.out.println("Enter the options (type 'done' to finish):");
        while (true) {
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("done")) {
                break;
            }
            options.add(option);
        }

        System.out.println("Enter the index of the correct answer (starting from 0):");
        int correctAnswerIndex = Integer.parseInt(scanner.nextLine());

        Question question = new Question(questionText, options, correctAnswerIndex);
        quizzes.get(topic).addQuestion(question);

        System.out.println("Question added to the quiz on topic \"" + topic + "\".");
    }

    private void takeQuiz(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: takeQuiz <topic>");
            return;
        }
        String topic = parts[1];
        if (!quizzes.containsKey(topic)) {
            System.out.println("Quiz on topic \"" + topic + "\" does not exist.");
            return;
        }

        Quiz quiz = quizzes.get(topic);
        int score = 0;
        List<Question> questions = quiz.getQuestions();

        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            int answer = Integer.parseInt(scanner.nextLine()) - 1;
            if (answer == question.getCorrectAnswerIndex()) {
                score++;
            }
        }

        System.out.println("You scored " + score + " out of " + questions.size());
    }
}


