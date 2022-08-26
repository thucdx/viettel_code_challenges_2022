import java.io.*;
import java.util.*;

class Result {

    /*
     * Complete the 'calculateTime' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER_ARRAY taskTimeCosts
     *  4. 2D_INTEGER_ARRAY edges
     */

    static int INF = (int) 1e9 + 7;
    static int n;
    static int[] cost;
    static int[] finish;

    static int[] depend;
    static List<List<Integer>> dependOn = new ArrayList<>();

    static int findTime(int v) {
        if (finish[v] != INF) {
            return finish[v];
        }

        int before = 0;
        for (int task: dependOn.get(v)) {
           before = Math.max(before, findTime(task));
        }

        finish[v] = before + cost[v];
        return finish[v];
    }

    public static int calculateTime(int n, int k, List<Integer> taskTimeCosts, List<List<Integer>> edges) {
        cost = new int[n];
        finish = new int[n];
        depend = new int[n];
        for (int i = 0; i < n; ++i) {
            cost[i] = taskTimeCosts.get(i);
            finish[i] = INF;
            dependOn.add(new ArrayList<Integer>());
        }

        for (List<Integer> e: edges) {
            int a = e.get(0) - 1, b = e.get(1) - 1;
            dependOn.get(a).add(b);
        }

        int res = -1;
        for (int i = 0; i < n; ++i) {
            res = Math.max(res, findTime(i));
        }

        return res;
    }

}

public class PC {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("inp.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] taskTimeCostsTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> taskTimeCosts = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int taskTimeCostsItem = Integer.parseInt(taskTimeCostsTemp[i]);
            taskTimeCosts.add(taskTimeCostsItem);
        }

        List<List<Integer>> edges = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            String[] edgesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> edgesRowItems = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                int edgesItem = Integer.parseInt(edgesRowTempItems[j]);
                edgesRowItems.add(edgesItem);
            }

            edges.add(edgesRowItems);
        }

        int result = Result.calculateTime(n, k, taskTimeCosts, edges);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
