import java.util.*;
import java.io.*;

public class D {

    private static Scanner sc = new Scanner(System.in);

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

    static class Edge {
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Edge>[] graph = createGraph("graph.txt");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("\t1. Calculate minimum distance for all nodes by selecting a source");
            System.out.println("\t2. Enter source and destination to find the shortest path between them");
            System.out.println("\t3. Exit");

            int option = sc.nextInt();

            switch (option) {
                case 1:
                    calculateDistances(graph);
                    break;
                case 2:
                    findShortestPath(graph);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    public static void calculateDistances(ArrayList<Edge>[] graph) {
        System.out.println("Enter source node:");
        int src = sc.nextInt();
        int[][] result = dijkastra(graph, graph.length, src);
        int[] dist = result[0];
        int[] parent = result[1];
        System.out.println("Minimum distances from source node " + src + ":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Node " + i + ": " + dist[i]);
        }
    }

    public static void findShortestPath(ArrayList<Edge>[] graph) {
        System.out.println("Enter source node:");
        int src = sc.nextInt();
        System.out.println("Enter destination node:");
        int dest = sc.nextInt();
        int[] parent = dijkastra(graph, graph.length, src)[1]; // Running Dijkstra's algorithm to get the shortest path tree
        System.out.println("Shortest path from " + src + " to " + dest + ":");
        printPath(graph, dest, parent);
    }

    public static int[][] dijkastra(ArrayList<Edge>[] graph, int n, int src) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] vis = new boolean[n];
        int[] dist = new int[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            if (i != src) {
                dist[i] = Integer.MAX_VALUE;
            }
            parent[i] = -1;
        }

        pq.add(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.remove();
            if (!vis[curr.v]) {
                vis[curr.v] = true;
                for (Edge e : graph[curr.v]) {
                    int u = e.src;
                    int v = e.dest;
                    int w = e.wt;
                    if (dist[u] + w < dist[v]) {
                        dist[v] = w + dist[u];
                        pq.add(new Pair(v, dist[v]));
                        parent[v] = u;
                    }
                }
            }
        }
        return new int[][] { dist, parent };
    }

    public static void printPath(ArrayList<Edge>[] graph, int dest, int[] parent) {
        if (parent[dest] == -1) {
            System.out.print(dest);
            return;
        }
        printPath(graph, parent[dest], parent);
        System.out.print(" -> " + dest);
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
