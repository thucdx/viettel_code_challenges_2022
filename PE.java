import java.io.*;
import java.util.*;

class Result {

    /*
     * Complete the 'changeType' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY matrix
     */

    static int[][] board;
    static int[][] type2;
    static boolean isFound = false;
    static int n;
    static int curState;


    static int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

    private static int cnt(int r, int c) {
        int cnt = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!(i == 0 && j == 0)) {
                    int nr = r + i;
                    int nc = c + j;
                    if (0 <= nr && nr < n && 0 <= nc && nc < n) {
                        cnt += board[nr][nc];
                    }
                }
            }
        }

        return cnt;
    }

    // Check if isOK if we place board[r][c] a mine
    // r >= 1
    private static boolean isOK(int r, int c) {
        // Check value of type2[r-1][c-1]
        boolean isOk = true;
        if (c > 0 && r > 0) {
            if (!(cnt(r - 1, c - 1) == type2[r - 1][c - 1])) {
                isOk = false;
            }
        }

        // Check for type2[r-1][c]
        if (c == n - 1 && r > 0) {
            if (!(cnt(r - 1, c) == type2[r - 1][c])) {
                isOk = false;
            }
        }

        if (r == n - 1) {
            if (c > 0 && !(cnt(r, c - 1) == type2[r][c - 1])) {
                isOk = false;
            }

            if (c == n - 1) {
                if (!(cnt(r, c) == type2[r][c])) {
                    isOk = false;
                }
            }
        }

        return isOk;
    }

    static boolean isRowOk = true;

    private static void goCell(int row, int col) {
        if (isFound) {
            return;
        }

        if (col == n) {
            isRowOk = true;
            return;
        }

        for (int val = 0; val < 2; ++val) {
            if (!isFound && !isRowOk) {
                board[row][col] = val;
//                System.out.println("Set Cell " + row + " " + col + " " + board[row][col]);
                if (isOK(row, col)) {
//                    System.out.println("Cell OK  " + row + " " + col + " " + val);
                    goCell(row, col + 1);
                }
            }
        }
    }

    private static void go(int row) {
//        System.out.println("GO " + row + " firstLIne State "  + curState);
        isRowOk = false;
        if (isFound) {
            return;
        }

        if (row >= n) {
            isFound = true;

//            System.out.println("FOUND!");
//            printBoard();
            return;
        }

        goCell(row, 0);
        if (isRowOk) {
            go(row + 1);
        }
//        System.out.println("ROW OK ? " + row + " " + isRowOk);
//        printBoard();
    }

    private static void printBoard() {

//        boolean isOK = true;
//        for (int i = 0; i < n; ++i) {
//            for (int j = 0; j < n; ++j) {
//                if (!isOK(i, j)) {
//                    isOK = false;
//                    System.out.println("NOT OK at" + i + " " + j + " " + cnt(i, j));
//                    break;
//                }
//            }
//        }
//
//        System.out.println("ISOK ? " + isOK);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("END");
    }

    public static List<List<Integer>> changeType(int n, List<List<Integer>> matrix) {
        // Write your code here
        Result.n = n;
        type2 = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                type2[i][j] = matrix.get(i).get(j);
            }
        }

        board = new int[n][n];

        // First line
        isFound = false;
        for (int state = 0; state < (1 << n) && !isFound; ++state) {
            curState = state;
            // set first line
            for (int i = 0; i < n; ++i) {
                if ((state & (1 << i)) > 0) {
                    board[0][i] = 1;
                } else {
                    board[0][i] = 0;
                }
            }
            go(1);
//            System.out.println("state " + state + " " + isFound);
        }

//        System.out.println("ISFOUND? " + isFound);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; ++i) {
            List<Integer> cur = new ArrayList<Integer>();

            for (int j = 0; j < n; ++j) {
                cur.add(board[i][j]);
            }
            res.add(cur);
        }

//        System.out.println("Print board");
//        printBoard();

        return res;
    }

}

public class PE {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("inp.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> matrix = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] matrixRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> matrixRowItems = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int matrixItem = Integer.parseInt(matrixRowTempItems[j]);
                matrixRowItems.add(matrixItem);
            }

            matrix.add(matrixRowItems);
        }

        List<List<Integer>> result = Result.changeType(n, matrix);

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                bufferedWriter.write(String.valueOf(result.get(i).get(j)));

                if (j != result.get(i).size() - 1) {
                    bufferedWriter.write(" ");
                }
            }

            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
