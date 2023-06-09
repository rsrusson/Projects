import java.util.Scanner;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        //introduction to purpose of the program
        System.out.println("Let's find out your grade!");
        System.out.println("Put in the score you received for each quiz and hit enter.");

        new Main();
    }

    Main() {

        int[] quizList = new int[10];
        int sum = 0;

        //For loop that states the quiz number, and requests/scans the score input
        for (int i = 0; i < quizList.length; i++) {
            int number = i + 1;
            System.out.println("Quiz " + number + "?");
            Scanner scanner = new Scanner(System.in);
            quizList[i] = scanner.nextInt();

            //if else declaration to cancel the loop
            if (quizList[i] == 999) {
                break;
            } else {
                continue;
            }
        }

        //for loop that prints out all the scores held in the array
        for(int i = 0; i < quizList.length; i++){
            System.out.println(quizList[i]);
            sum = sum + quizList[i];
        }

        //find and prints our desired number calculations
        System.out.println("Sum of the quiz scores: " + sum);
        int numQuizes = quizList.length;
        System.out.println("Number of quizes: " + numQuizes);
        int aveQuiz = sum/quizList.length;
        System.out.println("Average quiz grade: " + aveQuiz);

        //converts the average quiz grade into a letter grade
        if (90 <= aveQuiz && aveQuiz <=100)
            System.out.println("Letter grade: A");
        else if (80 <= aveQuiz && aveQuiz <= 89)
            System.out.println("Letter grade: B");
        else if (70 <= aveQuiz && aveQuiz <= 79)
            System.out.println("Letter grade: C");
        else if (60 <= aveQuiz && aveQuiz <=69)
            System.out.println("Letter grade: D");
        else if (0 <= aveQuiz && aveQuiz <= 59)
            System.out.println("Letter grade: F");
    }
}