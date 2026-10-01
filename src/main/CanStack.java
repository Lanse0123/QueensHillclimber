package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class CanStack {

    public static void main(String[] args){
        calculateMinStackArea();
    }

    static class Can{
        public int height;
        public boolean isUsed;

        public Can(int height, boolean isUsed){
            this.height = height;
            this.isUsed = isUsed;
        }
    }

    record CanPair(Can can1, Can can2){}

    public static void calculateMinStackArea(){
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();

        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = scanner.nextInt();
        }

        int average = 0;
        for (int num : numbers) {
            average += num;
        }

        average /= numbers.length;

        List<Can> canList = new ArrayList<>();
        List<CanPair> canPairList = new ArrayList<>();
        for (int can : numbers) {
            canList.add(new Can(can, false));
        }

        for (Can can : canList) {
            if (can.isUsed) continue;

            int wantedCan2Height = average - can.height;
            Can currentUsedCan = null;

            for (Can can2 : canList) {
                if (can2.isUsed) continue;
                if (can == can2) continue;

                if (currentUsedCan == null || Math.abs(can2.height - wantedCan2Height) < Math.abs(currentUsedCan.height - wantedCan2Height)) {
                    currentUsedCan = can2;
                }
            }

            if (currentUsedCan != null) {
                can.isUsed = true;
                currentUsedCan.isUsed = true;
                canPairList.add(new CanPair(can, currentUsedCan));
            } else {
                can.isUsed = true;
                canPairList.add(new CanPair(can, null));
            }
        }

        int maxCanPairHeight = 0;
        for (CanPair canPair : canPairList){
            if (canPair.can2 == null){
                if (canPair.can1.height > maxCanPairHeight){
                    maxCanPairHeight = canPair.can1.height;
                }
            } else {
                if (canPair.can1.height + canPair.can2.height > maxCanPairHeight) {
                    maxCanPairHeight = canPair.can1.height + canPair.can2.height;
                }
            }
        }

        System.out.println(maxCanPairHeight * (66 * canPairList.toArray().length));
        System.out.println();
        for (CanPair canPair : canPairList){
            if (canPair.can2 == null){
                System.out.println(canPair.can1.height);
            } else {
                System.out.println(canPair.can1.height + canPair.can2.height);
            }
        }
    }
}
