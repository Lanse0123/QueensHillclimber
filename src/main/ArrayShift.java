package main;//Cleaned by Lance

import java.util.Arrays;
import java.util.Scanner;

public class ArrayShift {

    //TODO - move main function to main class
    public static void main(String[] args) {
        new ArrayShift().arrayShift();
    }

    public int arrayLength = 5;
    public int startIndex = 0;

    public void arrayShift(){
        int[] nums = new int[arrayLength];
        int[] ans = new int[arrayLength];

        Scanner console = new Scanner(System.in);
        System.out.println("Enter " + arrayLength + " integer numbers with spaces between.");

        for (int i = startIndex; i < arrayLength; i++){
            nums[i] = console.nextInt();
        }

        System.out.println("Enter a number from " + -arrayLength + " to " + arrayLength);
        int shiftAmt = console.nextInt();

        shiftArray(shiftAmt, ans, nums);

        System.out.println(Arrays.toString(ans));
        console.close();
    }

    private void shiftArray(int shiftAmt, int[] ans, int[] nums){
        // For the first part, start at the beginning of the array.
        // This will wrap depending on if the shift amount is positive or negative,
        int index = startIndex;
        int wrapShiftAmount = (shiftAmt < startIndex) ? arrayLength + shiftAmt : shiftAmt;

        // copy the first part into the new array
        copyPartToNewArray(ans, nums, index, wrapShiftAmount, arrayLength);

        // copy the second part into the new array
        index = (shiftAmt > startIndex) ? arrayLength - shiftAmt : -1 * shiftAmt;
        copyPartToNewArray(ans, nums, index, startIndex, wrapShiftAmount);
    }

    private void copyPartToNewArray(int[] ans, int[] nums, int index, int startIndex, int endIndex){
        for(int i = startIndex; i < endIndex; i++) {
            ans[i] = nums[index];
            index++;
        }
    }
}