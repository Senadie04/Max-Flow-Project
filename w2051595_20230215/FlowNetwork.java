import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FlowNetwork {
    private final int numNodes; // Number of nodes in the network
    private final List<Edge>[] adjList; // Adjacency list to store the edges for each node
    private final Map<Integer, Map<Integer, Edge>> edgeMap; // Map for fast access to edges

    // Constructor to initialize the flow network with a given number of nodes
    @SuppressWarnings("unchecked")
    public FlowNetwork(int numNodes) {
        this.numNodes = numNodes;
        this.adjList = new ArrayList[numNodes]; // Create an array to store adjacency lists for each node
        this.edgeMap = new HashMap<>(); // Create a map to store edges for quick lookup

        // Initialize adjacency lists and edge maps for each node
        for (int i = 0; i < numNodes; i++) {
            adjList[i] = new ArrayList<>(); // Initialize the adjacency list for each node
            edgeMap.put(i, new HashMap<>()); // Initialize the edge map for each node
        }
    }

    // Getter for the number of nodes in the network
    public int getNumNodes() {
        return numNodes;
    }

    // Method to add an edge from one node to another with a given capacity
    public void addEdge(int from, int to, int capacity) {
        // Create a new edge from 'from' to 'to' with the specified capacity
        Edge edge = new Edge(from, to, capacity);
        adjList[from].add(edge); // Add the edge to the adjacency list of the 'from' node

        // Create a reverse edge with 0 capacity for residual graph purposes
        Edge reverseEdge = new Edge(to, from, 0);
        adjList[to].add(reverseEdge); // Add the reverse edge to the adjacency list of the 'to' node

        // Store the edges in the map for fast lookup by both nodes (from, to) and (to, from)
        edgeMap.get(from).put(to, edge);
        edgeMap.get(to).put(from, reverseEdge);
    }

    // Method to get the list of edges connected to a specific node
    public List<Edge> getEdges(int node) {
        return adjList[node]; // Return the adjacency list for the given node
    }

    // Method to get a specific edge between two nodes
    public Edge getEdge(int from, int to) {
        return edgeMap.get(from).get(to); // Return the edge from the 'from' node to the 'to' node
    }

    // Method to print the details of all edges in the network (excluding residual edges)
    public void printFlowDetails() {
        // Iterate over all nodes
        for (int i = 0; i < numNodes; i++) {
            // Iterate over the edges of the current node
            for (Edge edge : adjList[i]) {
                // Print the edge if its capacity is greater than 0 (indicating it is an original edge)
                if (edge.getCapacity() > 0) {
                    System.out.println("Edge " + edge);
                }
            }
        }
    }
}
