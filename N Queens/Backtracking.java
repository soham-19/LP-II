import java.util.*;

public class Backtracking {

    static Scanner sc = new Scanner(System.in);
    static int n = 5;
    static int countWays = 0;

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

    public static void nQueens(char[][] board, int row) {

        if (row == n) {
            printBoard(board);
            countWays++;
            return;
        }

        for(int j=0; j<n; j++) {
            if(isSafe(board, row, j)) {
                board[row][j] = 'Q';
                nQueens(board, row+1);
                board[row][j] = 'x';
            }
        }
    }

    public static boolean isSafe(char[][] board, int row, int col) {
        int i, j;

        //  check up straight
        for(i=0; i<row; i++) {
            if(board[i][col] == 'Q') {
                return false;
            }
        }

        //  check up left diagonally
        for(i=row, j=col; i>=0 && j>=0; i--,j--) {
            if(board[i][j] == 'Q') {
                return false;
            }
        }

        //  check up right diagonally
        for(i=row, j=col; i>=0 && j<n; i--,j++) {
            if(board[i][j] == 'Q') {
                return false;
            }
        }

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