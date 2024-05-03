package Assignment02;

import java.util.Arrays;
import java.util.Scanner;

public class Assignment02 {

    static Scanner sc = new Scanner(System.in);
    static int n = 5;
    static int countWays = 0;
    static int UB = Integer.MAX_VALUE;

    public static void main(String[] args) {

        int choice = 1;
        while(choice != 0) {

            System.out.print("Enter value of n : ");
            n = sc.nextInt();

            char[][] board = setBoard(n);
    
            nQueens(board, 0);
    
            System.out.println("Number of possible solutions : " + countWays);

            System.out.println("Continue ? : ");
            choice = sc.nextInt();
            countWays = 0;
        }
    }

    public static char[][] setBoard(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], 'x');
        }
        return board;
    }

    public static void printBoard(char[][] board) {
        System.out.println("----- * Chess Board * -----");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void nQueens(char[][] board, int col) {

        if (col == n) {
            printBoard(board);
            countWays++;
            return;
        }

        // Prioritize columns based on the heuristic values
        Integer[] cols = new Integer[n];
        for (int i = 0; i < n; i++) {
            cols[i] = i;
        }

        Arrays.sort(cols, (a, b) -> heuristic(board, a) - heuristic(board, b));

        for (int i : cols) {

            if (heuristic(board, i) >= UB) {
                continue; // this branch won't lead to better solution
            }

            if (isSafe(board, i, col)) {

                board[i][col] = 'Q';

                if (col + 1 < n) {
                    int prevUB = UB;
                    UB = Math.min(UB, UB - heuristic(board, i));
                    nQueens(board, col + 1);
                    UB = prevUB;
                } else {
                    nQueens(board, col + 1);
                }
                // backtracking
                board[i][col] = 'x';
            }
        }
    }

    public static boolean isSafe(char[][] board, int row, int col) {
        int i, j;

        // Check this row on the left side
        for (i = 0; i < col; i++)
            if (board[row][i] == 'Q')
                return false;

        // Check this column above
        for (i = row - 1; i >= 0; i--)
            if (board[i][col] == 'Q')
                return false;

        // Check upper diagonal on left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q')
                return false;

        // Check lower diagonal on left side
        for (i = row, j = col; j >= 0 && i < n; i++, j--)
            if (board[i][j] == 'Q')
                return false;

        return true;
    }

    public static int heuristic(char[][] board, int col) {

        int conflicts = 0;  // no. of conflicts in column with index==col
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q') {
                conflicts++;
            }
        }
        return conflicts;
    }

}