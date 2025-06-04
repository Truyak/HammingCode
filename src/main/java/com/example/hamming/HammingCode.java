package com.example.hamming;

import java.util.Arrays;

public class HammingCode {
    public static int[] calculateHammingCode(int[] data, int dataLength) {
        int r = 1;
        while (Math.pow(2, r) < dataLength + r + 1) {
            r++;
        }

        int totalLength = dataLength + r;
        int[] hammingCode = new int[totalLength];

        for (int i = 0; i < totalLength; i++) {
            hammingCode[i] = 0;
        }

        int dataIndex = 0;
        int currentPos = totalLength - 1;
        int parityCount = r;
        while (dataIndex < dataLength || parityCount > 0) {
            if (parityCount > 0 && isPowerOfTwo(totalLength - currentPos)) {

                parityCount--;
            } else if (dataIndex < dataLength) {

                hammingCode[currentPos] = data[dataIndex++];
            }
            currentPos--;
        }
        return hammingCode;
    }

    private static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    private static int calculateParity(int[] code, int parityPos, int totalLength) {
        int parity = 0;
        for (int i = 0; i < totalLength; i++) {
            if (code[i] != 0 && ((totalLength - i) & (totalLength - parityPos)) == (totalLength - parityPos)) {
                parity ^= 1;
            }
        }
        return parity;
    }

    public static int[] correctError(int[] received, int syndrome) {
        if (syndrome > 0 && syndrome <= received.length) {
            received[received.length - syndrome] ^= 1; // Sağdan hata pozisyonu
        }
        return received;
    }
}