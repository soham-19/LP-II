import java.util.*;
import java.io.*;

public class Assignment01{

    static class Edge{
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }
    }

    public static ArrayList<Edge>[] createGraph(String fileName)
    throws FileNotFoundException{
        Scanner file = new Scanner(new File(fileName));
        int V = Integer.parseInt(file.nextLine().trim());
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[V];
        for(int i=0; i<V; i++) {
            graph[i] = new ArrayList<>();

            String[] data = file.nextLine().split(" ");

            for(int j=0; j<V; j++) {
                int wt = Integer.parseInt(data[j]);
                if(wt != 0) {
                    graph[i].add(new Edge(i, j, wt));
                }
            }
        }
        System.out.println("Graph Fetched from File");
        file.close();
        return graph;
    }

    public static void BFS(ArrayList<Edge>[] graph) {
        System.out.println("BFS");

        boolean[] visited = new boolean[graph.length];

        for(int i=0; i<graph.length; i++) {
            if(!visited[i]){
                bfs(graph, visited, i);
                System.out.println("");
            }
        }
        System.out.println();
    }
    
    public static void bfs(ArrayList<Edge>[] graph, boolean[] visited, int start) {
        
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = true;
        
        while(!q.isEmpty()) {
            
            int curr = q.remove();
            visited[curr] = true;
            System.out.print(curr + " ");
            
            for(Edge e : graph[curr]){
                if(!visited[e.dest]) {
                    q.add(e.dest);
                    visited[e.dest] = true;
                }
            }
        }
    }
    
    public static void DFS(ArrayList<Edge>[] graph) {
        System.out.println("DFS");

        boolean[] visited = new boolean[graph.length];

        for(int i=0; i<graph.length; i++) {
            if(!visited[i]){
                dfs(graph, visited, i);
                System.out.println("");
            }
        }
    }

    public static void dfs(ArrayList<Edge>[] graph, boolean[] visited, int start) {
        Stack<Integer> s = new Stack<>();
        s.push(start);
        visited[start] = true;

        while(!s.isEmpty()) {

            int curr = s.pop();
            System.out.print(curr + " ");
            visited[curr] = true;

            for(Edge e : graph[curr]) {
                if(!visited[e.dest]) {
                    s.add(e.dest);
                    visited[e.dest] = true;
                }
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