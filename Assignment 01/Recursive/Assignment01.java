import java.util.*;
import java.io.*;

public class Assignment01 {

    static class Edge {
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }
    }

    public static ArrayList<Edge>[] createGraph(String filelName)
            throws FileNotFoundException {

        Scanner file = new Scanner(new File(filelName));

        int v = Integer.parseInt(file.nextLine().trim());

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[v];

        for (int i = 0; i < v; i++) {

            graph[i] = new ArrayList<>();

            String[] data = file.nextLine().split(" ");

            for (int j = 0; j < v; j++) {

                int wt = Integer.parseInt(data[j]);

                if (wt != 0) {
                    graph[i].add(new Edge(i, j, wt));
                }
            }
        }

        file.close();
        return graph;
    }

    public static void BFS(ArrayList<Edge>[] graph) {

        System.out.println("Breadth First Search : ");

        boolean[] vis = new boolean[graph.length];
        Queue<Integer> que = new LinkedList<>();

        for (int i = 0; i < graph.length; i++) {
            if (!vis[i]) {
                que.add(i);
                bfs(graph, vis, que);
            }
        }
        System.out.println();
    }

    public static void bfs(ArrayList<Edge>[] graph, boolean[] vis, Queue<Integer> que) {
        
        if (que.isEmpty()) {
            return;
        }
        int curr = que.remove();

        if(!vis[curr]) {
            vis[curr] = true;
            System.out.print(curr + " ");

            for(Edge e : graph[curr]) {
                if(!vis[e.dest])
                    que.add(e.dest);
            }
        }
        bfs(graph, vis, que);
    }

    public static void DFS(ArrayList<Edge>[] graph) {
        System.out.println("Depth First Search : ");

        boolean[] vis = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            if (!vis[i]) {
                dfs(graph, vis, i);
            }
        }
        System.out.println();
    }

    public static void dfs(ArrayList<Edge>[] graph, boolean[] vis, int curr) {
        
        System.out.print(curr + " ");
        vis[curr] = true;

        for(Edge e : graph[curr]) {
            if(!vis[e.dest]){
                dfs(graph, vis, e.dest);
            }
        }
    }

    public static void main(String[] args)
            throws FileNotFoundException {

        ArrayList<Edge>[] graph = createGraph("graph.txt");
        BFS(graph);
        DFS(graph);
    }
}
