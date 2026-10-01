package main;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class rockPaperScissors {

    public static List<String> inputList = List.of(new String[]{"rock", "paper", "scissors"});
    public static String playerInput;
    public static String computerInput;
    public static Random random = new Random();

    public static void main(String[] args) {

        int tCount = 0;
        int cCount = 0;
        int pCount = 0;
        int total = 0;
        String result;

        while (total <= 10){

            total++;
            result = rockPaperScissors();

            if (result.equals("t")) {
                tCount++;
                System.out.println("tie");
            }
            else if (result.equals("c")) {
                cCount++;
                System.out.println("computer wins");
            }
            else if (result.equals("p")) {
                pCount++;
                System.out.println("player wins");
            }
        }

        System.out.println("tCount = " + tCount);
        System.out.println("cCount = " + cCount);
        System.out.println("pCount = " + pCount);
    }

    public static String rockPaperScissors(){

        Scanner console = new Scanner(System.in);

        System.out.println("Rock Paper Scissors! (input one)");
        playerInput = console.nextLine();

        playerInput = playerInput.toLowerCase();
        computerInput = inputList.get(random.nextInt(3)).toLowerCase();

        System.out.println("Player input: " + playerInput);
        System.out.println("Computer input: " + computerInput);

        /////////////////////////////////////////////////////////////////////////////

        if (playerInput.equals(computerInput)) return "t";

        if (playerInput.equals("rock")){
            if (computerInput.equals("paper")) return "c";
            if (computerInput.equals("scissors")) return  "p";
        }
        if (playerInput.equals("paper")){
            if (computerInput.equals("scissors")) return "c";
            if (computerInput.equals("rock")) return "p";
        }
        if (playerInput.equals("scissors")){
            if (computerInput.equals("rock")) return "c";
            if (computerInput.equals("paper")) return "p";
        }
        return "hi this is impossible to reach but it requires a string so here's a sentence. HI!!!";
    }
}
