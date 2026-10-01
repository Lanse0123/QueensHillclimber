package main;

class Solution {
    public int rob(int[] nums) {
        int maxIndex = -1;
        int max = Integer.MIN_VALUE + 1;
        int editCount = 0;
        int sum = 0;

        while (nums.length >= editCount) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > max && !checkIfNeighborsAreIllegal(i, nums)) {
                    max = nums[i];
                    maxIndex = i;
                }
            }

            sum += nums[maxIndex];

            nums[maxIndex - 1] = -404;
            nums[maxIndex] = -404;
            nums[maxIndex + 1] = -404;

            editCount += 3;
            maxIndex = -1;
            max = Integer.MIN_VALUE + 1;
        }

        return sum;
    }

    public boolean checkIfNeighborsAreIllegal(int index, int[] nums){
        return (nums[index] == -404 || nums[index - 1] == -404 || nums[index + 1] == -404);
    }
}