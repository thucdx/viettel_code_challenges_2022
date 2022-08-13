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

    static int parent[];
    static int cnt[];

    static void makePair(int v) {
        parent[v] = v;
        cnt[v] = 1;
    }

    static int findParent(int v) {
        int pv = parent[v];
        if (pv != v) {
            parent[v] = findParent(pv);
        }
        return parent[v];
    }

    static void merge(int u, int v) {
        int pu = findParent(u), pv = findParent(v);

        if (pu != pv) {
            if (cnt[pu] < cnt[pv]) {
                // swap u, v
                int tmp = pv;
                pv = pu;
                pu = tmp;
            }

            // merge small chunk into large chunk
            parent[pv] = pu;
            cnt[pu] += cnt[pv];
        }
    }

    private static boolean inCircle(int cx, int cy, int px, int py, int r) {
        long dd = (cx - px) * (long) (cx - px) + (cy - py) * (long) (cy - py);
        return dd < r * (long) r;
    }

    static int TOP = 1, RIGHT = 1 << 1, BOTTOM = 1 << 2, LEFT = 1 << 3;
    static int TOP_LEFT = 1, TOP_RIGHT = 1<<1, BOTTOM_RIGHT = 1 << 2, BOTTOM_LEFT = 1 << 3;
    private static int check(int x, int y, int a, int b) {
        if (x == 0 && y == 0) {
            return BOTTOM_LEFT;
        }
        if (x == 0 && y == b) {
            return TOP_LEFT;
        }

        if (x == a && y == 0) {
            return BOTTOM_RIGHT;
        }

        if (x == a && y == b) {
            return TOP_RIGHT;
        }

        return 0;
    }
    public static String isValidPath(int a, int b, int r, int n, int x1, int y1, int x2, int y2, List<List<Integer>> watchPositions) {
        cnt = new int[n];
        parent = new int[n];

        // Write your code here
        for (int i = 0; i < n ; ++i) {
            makePair(i);
        }

        boolean isOK = true;

        for (int i = 0; i < n; ++i) {
            int cxi = watchPositions.get(i).get(0), cyi = watchPositions.get(i).get(1);
            // Check if (x1, y1), (x2, y2) is in circle
            if (inCircle(cxi, cyi, x1, y1, r) || inCircle(cxi, cyi, x2, y2, r)) {
                isOK = false;
                break;
            }

            for (int j = i + 1; j < n; ++j) {
                int cxj = watchPositions.get(j).get(0), cyj = watchPositions.get(j).get(1);
                if (inCircle(cxi, cyi, cxj, cyj, 2*r)) {
                    merge(i, j);
                }
            }
        }


        int ALL = TOP | RIGHT | BOTTOM | LEFT;

        Map<Integer, List<Integer>> strategies = new HashMap<>();
        strategies.put(TOP_LEFT | TOP_RIGHT, Arrays.asList(TOP | LEFT, TOP | RIGHT, TOP | BOTTOM));
        strategies.put(TOP_LEFT | BOTTOM_RIGHT, Arrays.asList(TOP | LEFT, TOP | BOTTOM, LEFT | RIGHT, RIGHT | BOTTOM));
        strategies.put(TOP_LEFT | BOTTOM_LEFT, Arrays.asList(TOP | LEFT, LEFT | BOTTOM, LEFT | RIGHT));

        strategies.put(TOP_RIGHT | BOTTOM_RIGHT, Arrays.asList(TOP | RIGHT, RIGHT | BOTTOM, LEFT | RIGHT));
        strategies.put(TOP_RIGHT | BOTTOM_LEFT, Arrays.asList(TOP | RIGHT, TOP | BOTTOM, LEFT | BOTTOM, LEFT | RIGHT)); // <==== MAIN
        strategies.put(BOTTOM_LEFT | BOTTOM_RIGHT, Arrays.asList(LEFT | BOTTOM, RIGHT | BOTTOM, TOP | BOTTOM));

        int checkStrategy = check(x1, y1, a, b) | check(x2, y2, a, b);
//        System.out.println("CHECK STRATEGY " + checkStrategy);
        if (checkStrategy == 0 || !strategies.containsKey(checkStrategy)) {
            checkStrategy = TOP_RIGHT | BOTTOM_LEFT;
        }

        // Check for intersection between cluster and rectangle
        for (int i = 0; i < n && isOK; ++i) {
            if (parent[i] == i) {
//                System.out.println("Cluster " + i);
                // i is a root of its cluster
                // intersect with top, bottom, left, right side of rectangle
                boolean top = false, bottom = false, left = false, right = false;
                int state = 0; // 4 bit: top:0, right: 1, bottom: 2, left: 3

                for (int j = 0; j < n; ++j) {
                    if (parent[j] == i) {
                        int xLeft = watchPositions.get(j).get(0) - r, xRight = watchPositions.get(j).get(0) + r;
                        if (xLeft < 0) {
                            state |= LEFT;
                        }
                        if (xRight > a) {
                            state |= RIGHT;
                        }

                        int yBottom = watchPositions.get(j).get(1) - r, yTop = watchPositions.get(j).get(1) + r;
                        if (yBottom < 0) {
                            state |= BOTTOM;
                        }
                        if (yTop > b) {
                            state |= TOP;
                        }
                    }
                }


                // Cannot move from (x1, y1) to (x2, y2)
                List<Integer> allPossibleCoverage = strategies.get(checkStrategy);

                for (int coverage: allPossibleCoverage) {
                    // check if state has 'coverage' (all on bit in coverage is also in state)
                    boolean containAll = true;
                    for (int j = 0; j < 4; ++j) {
                        if ((coverage & (1 << j)) > 0) {
                            if ((state & (1 << j)) == 0) {
                                containAll = false;
                                break;
                            }
                        }
                    }
                    if (containAll) {
                        isOK = false;
                    }
                }
            }
        }

        return isOK ? "YES" : "NO";
    }


}

public class PD_DSU {
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
