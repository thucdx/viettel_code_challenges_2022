import java.io.*;
import java.util.*;

class Result {

    /*
     * Complete the 'isValidPath' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER a
     *  2. INTEGER b
     *  3. INTEGER r
     *  4. INTEGER n
     *  5. INTEGER x1
     *  6. INTEGER y1
     *  7. INTEGER x2
     *  8. INTEGER y2
     *  9. 2D_INTEGER_ARRAY watchPositions
     */

    static boolean vs[][];
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    static int cx[], cy[];

    static int n, r, a, b, c;
    static boolean isInCircle(int x, int y) {
        for (int i = 0; i < c; ++i) {
            long dd = (x - cx[i]) * (long) (x - cx[i]) + (y - cy[i]) * (long) (y - cy[i]);
            if (dd < r*r) {
                return true;
            }
        }
        return false;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static String isValidPath(int a, int b, int r, int n, int x1, int y1, int x2, int y2, List<List<Integer>> watchPositions) {
        // Write your code here
        Result.n = n;
        Result.a = a;
        Result.b = b;
        Result.r = r;

        vs = new boolean[a + 1][b + 1];
        c = watchPositions.size();
        cx = new int[c];
        cy = new int[c];
        for (int i = 0; i < c; ++i) {
            cx[i] = watchPositions.get(i).get(0);
            cy[i] = watchPositions.get(i).get(1);
        }


        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(x1, y1));
        vs[x1][y1] = true;

        boolean isFound = false;
        while (!q.isEmpty() && !isFound) {
            Pair cur = q.poll();

            for (int i = 0; i < dx.length; ++i) {
                int nx = cur.x + dx[i], ny = cur.y + dy[i];
                if (0 <= nx && nx <= a && 0 <= ny && ny <= b && !vs[nx][ny]) {
                    if (!isInCircle(nx, ny)) {
                        vs[nx][ny] = true;
                        q.add(new Pair(nx, ny));

                        if (cur.x == x2 && cur.y == y2) {
                            isFound = true;
                            break;
                        }
                    }
                }
            }
        }

        return vs[x2][y2] ? "YES" : "NO";
    }

}

public class PD_100 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("inp.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int a = Integer.parseInt(firstMultipleInput[0]);

        int b = Integer.parseInt(firstMultipleInput[1]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        int n = Integer.parseInt(firstMultipleInput[3]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int x1 = Integer.parseInt(secondMultipleInput[0]);

        int y1 = Integer.parseInt(secondMultipleInput[1]);

        int x2 = Integer.parseInt(secondMultipleInput[2]);

        int y2 = Integer.parseInt(secondMultipleInput[3]);

        List<List<Integer>> watchPositions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] watchPositionsRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> watchPositionsRowItems = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                int watchPositionsItem = Integer.parseInt(watchPositionsRowTempItems[j]);
                watchPositionsRowItems.add(watchPositionsItem);
            }

            watchPositions.add(watchPositionsRowItems);
        }

        String result = Result.isValidPath(a, b, r, n, x1, y1, x2, y2, watchPositions);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
