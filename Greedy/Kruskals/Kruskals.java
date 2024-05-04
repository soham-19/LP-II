import java.io.*;
import java.util.*;

public class Kruskals {

    static int n = 0;

    static class Edge implements Comparable<Edge> {
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }

        @Override
        public int compareTo(Edge e) {
            return this.wt - e.wt;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Edge> edges = createGraph("graph.txt");
        KruskalsMST(edges);
    }

    public static void KruskalsMST(ArrayList<Edge> edges) {

        ArrayList<Edge> mst = new ArrayList<>();

        Collections.sort(edges);
        init(); // Initialize par and rank after n is determined

        int MSTCost = 0;
        int count = 0;

        for (int i = 0; count < n - 1 && i < edges.size(); i++) { // Added condition i < edges.size() to prevent
                                                                  // IndexOutOfBoundsException

            Edge e = edges.get(i);

            int parA = find(e.src);
            int parB = find(e.dest);

            if (parA != parB) {
                union(e.src, e.dest);
                count++;
                MSTCost += e.wt;
                mst.add(e);
            }
        }
        System.out.println("MST Cost = " + MSTCost);
        for (Edge e : mst) {
            System.out.println("<" + e.src + "," + e.dest + "," + e.wt + ">");
        }
    }

    public static ArrayList<Edge> createGraph(String fileName) throws FileNotFoundException {
        ArrayList<Edge> edges = new ArrayList<>();

        Scanner file = new Scanner(new File(fileName));

        n = Integer.parseInt(file.nextLine().trim());

        // Initialize par and rank arrays after n is determined
        par = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            String[] data = file.nextLine().split(" ");
            for (int j = i + 1; j < n; j++) {
                int weight = Integer.parseInt(data[j]);
                if (weight != 0) {
                    edges.add(new Edge(i, j, weight));
                }
            }
        }

        file.close();
        return edges;
    }

    // Union Find
    static int[] par;
    static int[] rank;

    public static void init() {
        for (int i = 0; i < n; i++) {
            par[i] = i;
        }
    }

    public static int find(int x) {
        if (par[x] == x)
            return x;
        return par[x] = find(par[x]);
    }

    public static void union(int a, int b) {
        int parA = find(a);
        int parB = find(b);

        if (rank[parA] == rank[parB]) {
            par[parB] = parA;
            rank[parA]++;
        } else if (rank[parA] < rank[parB]) {
            par[parA] = parB;
        } else {
            par[parB] = parA;
        }
    }
}