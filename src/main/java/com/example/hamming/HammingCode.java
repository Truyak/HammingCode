package com.example.hamming;

public class HammingCode {
    public static int[] calculateHammingCode(int[] data, int dataLength) {
        // Determine number of parity bits
        int r = 1;
        while (Math.pow(2, r) < dataLength + r + 1) {
            r++;
        }
        int totalLength = dataLength + r;
        int[] hammingCode = new int[totalLength];

        // Place data bits
        int dataIndex = 0;
        for (int i = 0; i < totalLength; i++) {
            if (isPowerOfTwo(i + 1)) {
                hammingCode[i] = 0; // Reserve for parity
            } else if (dataIndex < dataLength) {
                hammingCode[i] = data[dataIndex++];
            }
        }

        // Calculate parity bits
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
    public static int detectError(int[] received, int dataLength) {
        int r = 1;
        while (Math.pow(2, r) < dataLength + r + 1) {
            r++;
        }
        int syndrome = 0;
        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parity = calculateParity(received, parityPos, received.length);
            if (parity != received[parityPos]) {
                syndrome += (int) Math.pow(2, i);
            }
        }
        return syndrome; // 0: no error, >0: error at position syndrome, -1: double error (simplified)
    }

    public static int[] correctError(int[] received, int syndrome) {
        if (syndrome > 0 && syndrome <= received.length) {
            received[syndrome - 1] ^= 1; // Flip the erroneous bit
        }
        return received;
    }
}