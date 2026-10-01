//import java.util.Arrays;
//import java.util.Random;
//
//public class Main {
//
//    public static Random random = new Random();
//    public static int[] initialNumbers = {
//            1, 5, 7, 3, 25, 75, 53, 88, -3, 9999
//    };
//
//    public static int[] currentNumbers;
//
//    public static void main(String[] args) {
//        SecretMessageFinder.main(args);
//        if (true){
//            return;
//        }
//
//        //for using the initial numbers, uncomment this
////        System.out.println(Arrays.toString(initialNumbers));
////        selectionSort();
////        System.out.println(Arrays.toString(currentNumbers));
//
//        //for using random set of 10000 numbers ranging from 0 to 10000
//        initialNumbers = new int[10000];
//
//        for (int i = 0; i < 10000; i++){
//            initialNumbers[i] = random.nextInt(10000);
//        }
//
//        //prints original list and sorted list
//        System.out.println(Arrays.toString(initialNumbers));
//        selectionSort();
//        System.out.println(Arrays.toString(currentNumbers));
//    }
//
//    public static void selectionSort(){
//        currentNumbers = initialNumbers;
//        int pointer = 0;
//        int length = initialNumbers.length;
//        int smallestNumber = 0XABCDE;
//        int smallestNumberLocation = 0XABCDE;
//
//        while (pointer < length){
//
//            //find smallest number from pointer to end
//            for (int i = pointer; i < length; i++){
//                if (smallestNumber > currentNumbers[i]){
//                    smallestNumber = currentNumbers[i];
//                    smallestNumberLocation = i;
//                }
//            }
//
//            //swap it with the pointer and reset to try again
//            swap(smallestNumberLocation, pointer);
//            pointer++;
//            smallestNumber = 0XABCDE;
//            smallestNumberLocation = 0XABCDE;
//        }
//    }
//
//    //optimal way to swap (inlined b)
//    public static void swap(int locationA, int locationB){
//        int valueA = currentNumbers[locationA];
//        currentNumbers[locationA] = currentNumbers[locationB];
//        currentNumbers[locationB] = valueA;
//    }
//}