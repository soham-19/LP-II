import java.io.*;
import java.util.*;

class GraphNode implements Comparable<GraphNode> {
    int id;
    double h, cost;

    public GraphNode(int id, double h, double cost) {
        this.id = id;
        this.h = h;
        this.cost = cost;
    }

    @Override
    public int compareTo(GraphNode gn) {
        return Double.compare(this.h + this.cost, gn.h + gn.cost);
    }
}

public class Final {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {

        List<List<GraphNode>> graph = new ArrayList<>();

        List<Double> heuristic = new ArrayList<>();

        String[] cities = new String[100];

        Initialize("romonia.txt", graph, heuristic, cities);

        for(int i=0; i<cities.length; i++) {
            if(cities[i] != null) {
                System.out.print(cities[i] + " ");
            }
        }
        System.out.println();

        System.out.print("Enter start city : ");
        String startCity = sc.next();

        System.out.print("Enter goal city : ");
        String goalCity = sc.next();

        int start = -1, goal = -1;

        for(int i=0; i<cities.length; i++) {
            if(cities[i] != null) {
                if(cities[i].equalsIgnoreCase((startCity))){
                    start = i;
                }
                if(cities[i].equalsIgnoreCase((goalCity))){
                    goal = i;
                }
            }
        }

        if(start == -1 || goal == -1) {
            System.out.println("Enter valid cities!");
            return;
        }

        List<Integer> path = aStarAlgo(graph, start, goal, heuristic);
        if(!path.isEmpty()) {

            System.out.println("Path has been found");

            double cost = 0.0;

            for(int i=0; i<path.size()-1; i++) {
                int curr = path.get(i);
                int next = path.get(i+1);
                double edgeCost = 0.0;
                for(GraphNode node : graph.get(curr)) {
                    if(node.id == next) {
                        edgeCost = node.cost;
                        break;
                    }
                }

                cost += edgeCost;

                System.out.println(
                    cities[curr] + " -> " +
                    cities[next] + 
                    " (Cost : " + edgeCost +
                    ", Huristic : " + heuristic.get(next) + 
                    ")"
                );
            }

            System.out.println("Total Cost = " + cost);
        } else {
            System.out.println("Path does not exist");
        }
    }

    public static void Initialize(String fileName, List<List<GraphNode>> graph, 
    List<Double> heuristic, String[] cities)
            throws FileNotFoundException {

        Scanner file = new Scanner(new File(fileName));


        int n = Integer.parseInt(file.nextLine().trim());
        int m = Integer.parseInt(file.nextLine().trim());

        String[] cityNames = file.nextLine().split(" ");
        System.arraycopy(cityNames, 0, cities, 0, n);

        String[] hVals = file.nextLine().split(" ");
        for(int i=0; i<hVals.length; i++) {
            double h = Double.parseDouble(hVals[i]);
            heuristic.add(h);
        }

        for(int i=0; i<n; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i=0; i<m; i++) {
            String[] data = file.nextLine().split(" ");
            int idx = Integer.parseInt(data[0]);
            int id = Integer.parseInt(data[1]);
            double h = Double.parseDouble(data[2]);
            double cost = Double.parseDouble(data[3]);

            graph.get(idx).add(new GraphNode(id, h, cost));
        }
        file.close();
        System.out.println("Graph stored successfully");
    }

    public static List<Integer> aStarAlgo(List<List<GraphNode>> graph, int start, int goal, List<Double> hList) {

        PriorityQueue<GraphNode> pq = new PriorityQueue<>();

        Map<Integer, Double> costSoFar = new HashMap<>();

        Map<Integer, Integer> cameFrom = new HashMap<>();

        pq.add(new GraphNode(start, hList.get(start), 0.0));

        costSoFar.put(start, 0.0);

        while (!pq.isEmpty()) {

            GraphNode curr = pq.remove();

            if(curr.id == goal) {

                List<Integer> path = new ArrayList<>();

                int node = goal;
                while(node !=start) {
                    path.add(node);
                    node = cameFrom.get(node);
                }
                path.add(start);
                Collections.reverse(path);
                return path;
            }

            for(GraphNode neigh : graph.get(curr.id)) {

                double newCost = costSoFar.getOrDefault(curr.id, 0.0) + neigh.cost;

                if(
                    !costSoFar.containsKey(neigh.id) ||
                    newCost < costSoFar.get(neigh.id)
                ) {
                    costSoFar.put(neigh.id, newCost);
                    pq.add(new GraphNode(neigh.id, hList.get(neigh.id), newCost));
                    cameFrom.put(neigh.id, curr.id);
                }
            }
        }
        return new ArrayList<>();
    }

}