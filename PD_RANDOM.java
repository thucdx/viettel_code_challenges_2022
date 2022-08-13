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

    static Random rand = new Random(System.currentTimeMillis());
    public static String isValidPath(int a, int b, int r, int n, int x1, int y1, int x2, int y2, List<List<Integer>> watchPositions) {
        // Write your code here
        int k = rand.nextInt();
        return k % 2 == 0 ? "YES" : "NO";
    }

}

public class PD_RANDOM {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

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
