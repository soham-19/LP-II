import java.util.*;
import java.io.*;

public class D1 {

    static Scanner sc = new Scanner(System.in);

    static class Edge {
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }
    }

    public static ArrayList<Edge>[] createGraph(String fileName) throws FileNotFoundException {
        Scanner file = new Scanner(new File(fileName));
        int n = Integer.parseInt(file.nextLine().trim());

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            String[] data = file.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                int wt = Integer.parseInt(data[j]);
                if (wt != 0) {
                    graph[i].add(new Edge(i, j, wt));
                }
            }
        }
        file.close();
        return graph;
    }

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Edge>[] graph = createGraph("graph.txt");
        System.out.println("Graph fetched!");

        int option = 0;
        while (option != 3) {
            System.out.println("Select");
            System.out.println("1-Print Shortest Distances from A source");
            System.out.println("2-Print Shortest Path b/w 2 nodes");
            System.out.println("3-exit\n");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    shortestDistances(graph);
                    break;
                case 2:
                    shortestPath(graph);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice!!");
                    break;
            }
        }
    }

    public static void shortestDistances(ArrayList<Edge>[] graph) {
        System.out.print("Enter the source : ");
        int src = sc.nextInt();

        int[][] res = dijkastra(graph, graph.length, src);
        int[] dist = res[0];

        System.out.println("Distances from Source " + src);
        for (int i = 0; i < graph.length; i++) {
            System.out.println("dist[" + i + "] = " + dist[i]);
        }
    }

    public static void shortestPath(ArrayList<Edge>[] graph) {
        System.out.print("Enter the source : ");
        int src = sc.nextInt();
        System.out.print("Enter the destination : ");
        int dest = sc.nextInt();

        int[][] res = dijkastra(graph, graph.length, src);
        int[] par = res[1];

        System.out.println("Shortest path between " + src + " and " + dest + " is ");
        printPath(par, dest);
        System.out.println();
    }

    public static void printPath(int[] par, int dest) {
        if (par[dest] == -1) {
            System.out.print(dest);
            return;
        }
        printPath(par, par[dest]);
        System.out.print("->" + dest);
    }

    public static int[][] dijkastra(ArrayList<Edge>[] graph, int n, int src) {

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        int[] dist = new int[n];
        boolean[] vis = new boolean[n];

        int[] par = new int[n];

        // Initialize
        for (int i = 0; i < n; i++) {
            if (i != src)
                dist[i] = Integer.MAX_VALUE;
            par[i] = -1;
        }

        pq.add(new Pair(src, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();

            if (!vis[curr.v]) {

                vis[curr.v] = true;

                for (Edge e : graph[curr.v]) {
                    int u = e.src, v = e.dest, w = e.wt;

                    // Relaxing the edge
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        pq.add(new Pair(v, dist[v]));
                        par[v] = u;
                    }
                }
            }
        }

        return new int[][] { dist, par };
    }

    static class Pair implements Comparable<Pair> {

        int v, cost;

        public Pair(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Pair p) {
            return this.cost - p.cost;
        }
    }
}
