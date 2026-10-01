package main;

import java.math.BigInteger;

public class BigIntTesting {

    public static void main(String[] args){
        BigInteger bigInteger = BigInteger.ONE;

        int count = 0;
        while (count < 2_000_000_000){
            count++;
            bigInteger = bigInteger.multiply(BigInteger.valueOf(16));
            if (count % 10000 == 0) {
                System.out.println("level" + count + ": " + bigInteger.bitLength());
            }
        }
    }

}
