package main;

public class CharTesting {

    public static void main(String[] args){
        String string = "println(Arrays.toString(initialNumbers));selectionSort()";

        int seed = 0;
        for (char character : string.toCharArray()){
            seed += character;
        }

        System.out.println(seed);
    }

}
