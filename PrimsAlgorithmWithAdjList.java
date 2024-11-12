import java.util.ArrayList;
import java.util.PriorityQueue;

class PrimsAlgorithmWithAdjList {
    static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int V;
        ArrayList<ArrayList<Edge>> adj;

        Graph(int v) {
            V = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) {
                adj.add(new ArrayList<>());
            }
        }


        void addEdge(int src, int dest, int weight) {
            adj.get(src).add(new Edge(dest, weight));
            adj.get(dest).add(new Edge(src, weight));
        }
    }

    static class Node implements Comparable<Node> {
        int vertex;
        int key;

        Node(int vertex, int key) {
            this.vertex = vertex;
            this.key = key;
        }

        @Override
        public int compareTo(Node other) {
            return this.key - other.key;
        }
    }

    public void primMST(Graph graph) {
        int V = graph.V;
        int[] parent = new int[V];
        int[] key = new int[V];
        boolean[] inMST = new boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(V);

        key[0] = 0;
        parent[0] = -1;
        pq.offer(new Node(0, key[0]));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            inMST[u] = true;

            for (Edge edge : graph.adj.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;

                if (!inMST[v] && weight < key[v]) {
                    parent[v] = u;
                    key[v] = weight;
                    pq.offer(new Node(v, key[v]));
                }
            }
        }

        printMST(parent, key, V);
    }

    private void printMST(int[] parent, int[] key, int V) {
        int totalWeight = 0;
        System.out.println("Edge \tWeight");

        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + key[i]);
            totalWeight += key[i]; // Add the weight of each edge to the total weight
        }

        System.out.println("Total Weight of MST: " + totalWeight);
    }

    public static void main(String[] args) {
        int V = 9;
        Graph graph = new Graph(V);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 8, 2);
        graph.addEdge(2, 5, 4);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);

        PrimsAlgorithmWithAdjList prim = new PrimsAlgorithmWithAdjList();
        System.out.println("Edges in the constructed Minimum Spanning Tree:");
        prim.primMST(graph);
    }
}
