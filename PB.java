import java.io.*;
import java.util.*;

class Result {

    /*
     * Complete the 'findLargestSpace' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER m
     *  2. INTEGER n
     *  3. INTEGER_ARRAY positions
     */

    public static int findLargestSpace(int m, int n, List<Integer> positions) {
        // Write your code here

        Collections.sort(positions);

        int res = positions.get(0);
        for (int i = 0; i < positions.size() - 1; ++i) {
            int dist = positions.get(i + 1) - positions.get(i);
            if (dist > res) {
                res = dist;
            }
        }

        res = Math.max(res, m - positions.get(positions.size() - 1));
        return res;
    }

}

public class PB {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("inp.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int positionsItem = Integer.parseInt(bufferedReader.readLine().trim());
            positions.add(positionsItem);
        }

        int result = Result.findLargestSpace(m, n, positions);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
