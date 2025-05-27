import java.util.*;

class FordFulkerson {
    private final FlowNetwork network; // Flow network representing the graph
    private int iterations; // Count of iterations for the algorithm

    // Constructor to initialize the FordFulkerson algorithm with a given network
    public FordFulkerson(FlowNetwork network) {
        this.network = network;
        this.iterations = 0;
    }

    // Getter to retrieve the number of iterations the algorithm ran
    public int getIterations() {
        return iterations;
    }

    // Method to compute the maximum flow from the source to the sink
    public int findMaxFlow(int source, int sink) {
        int maxFlow = 0; // To keep track of the total flow
        iterations = 0; // Reset the iterations count
        boolean printDetails = true; // Flag to control if algorithm details are printed

        // Continue finding augmenting paths while there is one
        while (true) {
            iterations++; // Increment the iteration count

            // If printDetails is true, print the current iteration
            if (printDetails) {
                System.out.println("\nIteration " + iterations + ":");
            }

            // Find an augmenting path (a path from source to sink with available capacity)
            List<Edge> path = findAugmentingPath(source, sink);
            if (path == null) {
                break; // No more augmenting paths found, exit the loop
            }

            // Find the minimum residual capacity of the path (bottleneck capacity)
            int minResidualCapacity = findMinResidualCapacity(path);
            maxFlow += minResidualCapacity; // Add the flow from the found path to the total flow

            // Update the flow in the network for each edge in the path
            for (Edge edge : path) {
                int from = edge.getFrom();
                int to = edge.getTo();

                // Add flow to the forward edge and subtract flow from the reverse edge (for residual graph)
                network.getEdge(from, to).addFlow(minResidualCapacity);
                network.getEdge(to, from).addFlow(-minResidualCapacity);

                // If printDetails is true, print the updated edge and the added flow
                if (printDetails) {
                    System.out.println("  Updated edge " + from + " -> " + to +
                            ", added flow: " + minResidualCapacity);
                }
            }

            // Print the current total max flow if printDetails is true
            if (printDetails) {
                System.out.println("  Current max flow: " + maxFlow);
            }
        }

        // If printDetails is true, print the total number of iterations after the algorithm completes
        if (printDetails) {
            System.out.println("\nAlgorithm completed in " + iterations + " iterations.");
        }
        return maxFlow; // Return the computed maximum flow
    }

    // Method to find an augmenting path from source to sink using BFS
    private List<Edge> findAugmentingPath(int source, int sink) {
        Edge[] edgeTo = new Edge[network.getNumNodes()]; // To store the path (edge leading to node)
        boolean[] visited = new boolean[network.getNumNodes()]; // To track visited nodes
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS

        visited[source] = true; // Mark the source node as visited
        queue.add(source); // Add the source node to the queue

        // Perform BFS to find an augmenting path
        while (!queue.isEmpty() && !visited[sink]) {
            int v = queue.poll(); // Get the next node from the queue

            // Explore each outgoing edge from node v
            for (Edge edge : network.getEdges(v)) {
                int w = edge.getTo(); // Get the destination node of the edge

                // If there’s residual capacity and the destination node hasn't been visited
                if (edge.getResidualCapacity() > 0 && !visited[w]) {
                    edgeTo[w] = edge; // Set the edge leading to node w
                    visited[w] = true; // Mark node w as visited
                    queue.add(w); // Add node w to the queue for further exploration
                }
            }
        }

        // If the sink node has not been reached, return null (no augmenting path found)
        if (!visited[sink]) {
            return null;
        }

        // Reconstruct the augmenting path from sink to source using edgeTo array
        List<Edge> path = new ArrayList<>();
        for (int v = sink; v != source; v = edgeTo[v].getFrom()) {
            path.add(edgeTo[v]); // Add each edge to the path
        }
        Collections.reverse(path); // Reverse the path to get it from source to sink

        return path; // Return the augmenting path
    }

    // Method to find the minimum residual capacity along the path
    private int findMinResidualCapacity(List<Edge> path) {
        int min = Integer.MAX_VALUE; // Initialize to a large number
        // Loop through all the edges in the path and find the minimum residual capacity
        for (Edge edge : path) {
            min = Math.min(min, edge.getResidualCapacity());
        }
        return min; // Return the minimum residual capacity
    }

    // Helper method to convert the path to a string representation (for debugging purposes)
    private String pathToString(List<Edge> path) {
        StringBuilder sb = new StringBuilder();
        sb.append(path.get(0).getFrom()); // Append the source node of the first edge
        for (Edge edge : path) {
            sb.append(" -> ").append(edge.getTo()); // Append each node in the path
        }
        return sb.toString(); // Return the string representation of the path
    }
}
