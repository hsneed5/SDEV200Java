/**M1Assignment3-8.29
 * 
 * 
 * This program takes two user inputted 3x3 array
 * and checks to see if they are identical or not.
 * 
 * IdenticalArrays.V01.001
 * Hailey Sneed
 * SDEV200
 * 11/01/2025
 */


import java.util.Scanner;

public class IdenticalArrays {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[][] m1 = new int[3][3];
        int[][] m2 = new int[3][3];


        System.out.println("Enter the first 3x3 array:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m1[i][j] = input.nextInt();
            }
        }


        System.out.println("Enter the second 3x3 array:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m2[i][j] = input.nextInt();
            }
        }


        if (equals(m1, m2)) {
            System.out.println("The two arrays are identical.");
        } else {
            System.out.println("The two arrays are not identical.");
        }
    }


    public static boolean equals(int[][] m1, int[][] m2) {

        if (m1.length != m2.length) {
            return false;
        }

        for (int i = 0; i < m1.length; i++) {
  
            if (m1[i].length != m2[i].length) {
                return false;
            }

            for (int j = 0; j < m1[i].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false; 
                }
            }
        }

        return true;
    }
}
