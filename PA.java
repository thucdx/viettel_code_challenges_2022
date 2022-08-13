import java.io.*;

class Result {

    /*
     * Complete the 'calculateSlogan' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING x
     *  2. STRING s
     */

    public static int calculateSlogan(String x, String s) {
        // Write your code here
        int res = 0;
        int lx = x.length(), ls = s.length();
        int idx = 0, ids = 0;
        while (ids < ls) {
            if (s.charAt(ids) != x.charAt(idx))  {
                ++ids;
            } else {
                ++ids;
                idx++;
                if (idx == lx) {
                    idx = 0;
                    res++;
                }
            }
        }

        return res;
    }

}

public class PA {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("inp.txt")));
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String x = bufferedReader.readLine();

        String s = bufferedReader.readLine();

        int result = Result.calculateSlogan(x, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}