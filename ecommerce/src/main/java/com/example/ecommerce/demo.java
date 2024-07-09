package com.example.ecommerce;

public class demo {
        public int countAlternatingGroups(int[] colors, int k) {
            int n = colors.length;
            if (k == n) {
                return isAlternating(colors, 0, k, n) ? 1 : 0;
            }

            int count = 0;
            int[] diffCount = new int[n];

            // Precompute differences
            for (int i = 0; i < n; i++) {
                diffCount[i] = (colors[i] != colors[(i + 1) % n]) ? 1 : 0;
            }

            // Calculate initial sum for the first window
            int windowSum = 0;
            for (int i = 0; i < k - 1; i++) {
                windowSum += diffCount[i];
            }

            // Sliding window
            for (int i = 0; i < n; i++) {
                windowSum += diffCount[(i + k - 1) % n];
                if (windowSum == k - 1) {
                    count++;
                }
                windowSum -= diffCount[i];
            }

            return count;
        }

        private boolean isAlternating(int[] colors, int start, int k, int n) {
            for (int i = start; i < start + k - 1; i++) {
                if (colors[i % n] == colors[(i + 1) % n]) {
                    return false;
                }
            }
            return true;
        }

        public static void main(String[] args) {
            demo solution = new demo();

            // Example 1
            int[] colors1 = {0, 1, 0, 1, 0};
            int k1 = 3;
            System.out.println("Example 1: " + solution.countAlternatingGroups(colors1, k1));  // Expected output: 3

            // Example 2
            int[] colors2 = {0, 1, 0, 0, 1, 0, 1};
            int k2 = 6;
            System.out.println("Example 2: " + solution.countAlternatingGroups(colors2, k2));  // Expected output: 2

            // Example 3
            int[] colors3 = {1, 1, 0, 1};
            int k3 = 4;
            System.out.println("Example 3: " + solution.countAlternatingGroups(colors3, k3));  // Expected output: 0

            String s ="hello;";
            var ar = new boolean[1];
            System.out.println(ar[0]);
        }
    }


