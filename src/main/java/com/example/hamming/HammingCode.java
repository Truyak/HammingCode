package com.example.hamming;

public class HammingCode {
    public static int[] calculateHammingCode(int[] data, int dataLength) {
        int r = 1;
        while (Math.pow(2, r) < dataLength + r + 1) {
            r++;
        }
        int totalLength = dataLength + r;
        int[] hammingCode = new int[totalLength];

        int dataIndex = 0;
        for (int i = 0; i < totalLength; i++) {
            if (isPowerOfTwo(i + 1)) {
                hammingCode[i] = 0;
            } else if (dataIndex < dataLength) {
                hammingCode[i] = data[dataIndex++];
            }
        }

        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            hammingCode[parityPos] = calculateParity(hammingCode, parityPos, totalLength);
        }
        return hammingCode;
    }

    private static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    private static int calculateParity(int[] code, int parityPos, int totalLength) {
        int parity = 0;
        for (int i = 0; i < totalLength; i++) {
            if (code[i] != 0 && ((i + 1) & (parityPos + 1)) == (parityPos + 1)) {
                parity ^= 1;
            }
        }
        return parity;
    }

    public static int[] correctError(int[] received, int syndrome) {
        if (syndrome > 0 && syndrome <= received.length) {
            received[syndrome - 1] ^= 1;
        }
        return received;
    }
}