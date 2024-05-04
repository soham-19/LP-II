package Prims;
import java.io.*;
import java.util.*;

public class Prims {

    static class Edge{
        int src, dest, wt;

        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            wt = w;
        }
    }

    public static ArrayList<Edge>[] createGraph(String fileName)
    throws FileNotFoundException {

        Scanner file = new Scanner(new File(fileName));
        
        int v = Integer.parseInt(file.nextLine().trim());

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[v];

        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
            String[] data = file.nextLine().split(" ");
            for(int j=0; j<v; j++) {
                int wt = Integer.parseInt(data[j]);

                if(wt != 0) {
                    graph[i].add(new Edge(i, j, wt));
                }
            }
        }
        System.out.println("Graph loaded from file.");
        file.close();
        return graph;
    }

    public static void main(String[] args)
    throws FileNotFoundException {

        ArrayList<Edge>[] graph = createGraph("graph.txt");

        PrimsMST(graph);
    }

    public static void PrimsMST(ArrayList<Edge>[] graph) {

        int MSTCost = 0;
        ArrayList<Pair> MST = new ArrayList<>();
        
        boolean[] vis = new boolean[graph.length];
        
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, 0));

        while(!pq.isEmpty()) {

            Pair curr = pq.remove();

            if(!vis[curr.v]) {

                // Add to MST
                MSTCost += curr.cost;
                MST.add(curr);
                vis[curr.v] = true;

                for(Edge e : graph[curr.v]) {
                    pq.add(new Pair(e.dest, e.wt));
                }
            }
        }

        System.out.println("Vi\tCi");
        for(Pair p : MST) {
            System.out.println(p.v + "\t" +p.cost);
        }
        System.out.println("\nCost of MST : " + MSTCost);
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