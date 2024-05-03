public class EightPuzzleSolver {
    static int g = 0;
    static int[][] moves = new int[100][9];

    public static void print(int[] puzzle) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.println();
            if (puzzle[i] == -1) System.out.print("_ ");
            else System.out.print(puzzle[i] + " ");
        }
        System.out.println("\n");
    }

    public static void moveLeft(int[] start, int position) {
        swap(start, position, position - 1);
    }

    public static void moveRight(int[] start, int position) {
        swap(start, position, position + 1);
    }

    public static void moveTop(int[] start, int position) {
        swap(start, position, position - 3);
    }

    public static void moveBottom(int[] start, int position) {
        swap(start, position, position + 3);
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void copy(int[] temp, int[] real) {
        System.arraycopy(real, 0, temp, 0, 9);
    }

    public static int heuristic(int[] start, int[] goal) {
        int h = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (start[i] == goal[j] && start[i] != -1) {
                    h += Math.abs((j - i) / 3) + Math.abs((j - i) % 3);
                }
            }
        }
        return h + g;
    }

    public static void movetile(int[] start, int[] goal) {
        int emptyAt = 0;
        for (int i = 0; i < 9; i++) {
            if (start[i] == -1) {
                emptyAt = i;
                break;
            }
        }

        int[] t1 = new int[9];
        int[] t2 = new int[9];
        int[] t3 = new int[9];
        int[] t4 = new int[9];
        int f1 = Integer.MAX_VALUE, f2 = Integer.MAX_VALUE, f3 = Integer.MAX_VALUE, f4 = Integer.MAX_VALUE;

        copy(t1, start);
        copy(t2, start);
        copy(t3, start);
        copy(t4, start);

        int row = emptyAt / 3;
        int col = emptyAt % 3;

        if (col - 1 >= 0) {
            moveLeft(t1, emptyAt);
            f1 = heuristic(t1, goal);
        }
        if (col + 1 <= 2) {
            moveRight(t2, emptyAt);
            f2 = heuristic(t2, goal);
        }
        if (row - 1 >= 0) {
            moveTop(t3, emptyAt);
            f3 = heuristic(t3, goal);
        }
        if (row + 1 <= 2) {
            moveBottom(t4, emptyAt);
            f4 = heuristic(t4, goal);
        }

        if (f1 < f2 && f1 < f3 && f1 < f4) {
            moveLeft(start, emptyAt);
            moves[g][emptyAt] = start[emptyAt];
            g++;
        } else if (f2 < f1 && f2 < f3 && f2 < f4) {
            moveRight(start, emptyAt);
            moves[g][emptyAt] = start[emptyAt];
            g++;
        } else if (f3 < f1 && f3 < f2 && f3 < f4) {
            moveTop(start, emptyAt);
            moves[g][emptyAt] = start[emptyAt];
            g++;
        } else if (f4 < f1 && f4 < f2 && f4 < f3) {
            moveBottom(start, emptyAt);
            moves[g][emptyAt] = start[emptyAt];
            g++;
        }
    }

    public static void solveEight(int[] start, int[] goal) {
        g = 0;
        movetile(start, goal);
        print(start);
        int f = heuristic(start, goal);
        while (f != g) {
            movetile(start, goal);
            print(start);
            f = heuristic(start, goal);
        }
        System.out.println("moves: " + g + "\n");
        printSolution();
    }

    public static void printSolution() {
        for (int i = 0; i < g; i++) {
            System.out.println("Step " + (i + 1) + ":");
            print(moves[i]);
        }
    }

    public static boolean solvable(int[] start) {
        int invrs = 0;
        for (int i = 0; i < 9; i++) {
            if (start[i] <= 1) continue;
            for (int j = i + 1; j < 9; j++) {
                if (start[j] == -1) continue;
                if (start[i] > start[j]) invrs++;
            }
        }
        return (invrs & 1) == 1;
    }

    public static void main(String[] args) {
        int[] start = new int[9];
        int[] goal = new int[9];

        System.out.println("Enter the current matrix:");
        for (int i = 0; i < 9; i++) {
            start[i] = Integer.parseInt(System.console().readLine());
        }

        System.out.println("Enter the goal matrix:");
        for (int i = 0; i < 9; i++) {
            goal[i] = Integer.parseInt(System.console().readLine());
        }

        if (solvable(start)) {
            solveEight(start, goal);
        } else {
            System.out.println("The puzzle is unsolvable.");
        }
    }
}