import java.util.*;

class Question {
    String questionText;
    List<String> options;
    int correctOption;

    Question(String questionText, List<String> options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Question> questions = new ArrayList<>();
        questions.add(new Question(
                "Which programming language is used for Android development?",
                Arrays.asList("Python", "Java", "C#", "Ruby"),
                1
        ));
        questions.add(new Question(
                "Which planet is known as the Red Planet?",
                Arrays.asList("Earth", "Mars", "Jupiter", "Venus"),
                1
        ));
        questions.add(new Question(
                "Who is known as the father of computers?",
                Arrays.asList("Albert Einstein", "Charles Babbage", "Alan Turing", "Isaac Newton"),
                1
        ));

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.questionText);

            for (int j = 0; j < q.options.size(); j++) {
                System.out.println((j + 1) + ". " + q.options.get(j));
            }

            System.out.print("Your answer (1-" + q.options.size() + "): ");
            int answer = scanner.nextInt() - 1;

            if (answer == q.correctOption) {
                System.out.println(" Correct!");
                score++;
            } else {
                System.out.println(" Wrong! Correct answer: " + q.options.get(q.correctOption));
            }
        }
        System.out.println("\n Quiz finished! Your score: " + score + "/" + questions.size());
        if(score<1){
            System.out.println("Very poor");
        }
        if(score==2){
            System.out.println("Good!");
        }
        if(score>=3){
            System.out.println("Very Good!");
        }

        scanner.close();
    }
}
