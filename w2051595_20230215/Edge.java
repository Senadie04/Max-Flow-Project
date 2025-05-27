class Edge {
    private final int from; // The node from which the edge originates
    private final int to; // The node to which the edge leads
    private final int capacity; // The capacity of the edge (maximum possible flow through it)
    private int flow; // The current flow through the edge

    // Constructor to initialize an edge with a source node, target node, and capacity
    public Edge(int from, int to, int capacity) {
        this.from = from; // Set the source node
        this.to = to; // Set the target node
        this.capacity = capacity; // Set the capacity of the edge
        this.flow = 0; // Initially, there is no flow through the edge
    }

    // Getter method for the source node
    public int getFrom() {
        return from;
    }

    // Getter method for the target node
    public int getTo() {
        return to;
    }

    // Getter method for the capacity of the edge
    public int getCapacity() {
        return capacity;
    }

    // Getter method for the current flow through the edge
    public int getFlow() {
        return flow;
    }

    // Method to calculate the residual capacity of the edge (capacity - current flow)
    public int getResidualCapacity() {
        return capacity - flow;
    }

    // Method to add a certain amount of flow to the edge (forward direction)
    public void addFlow(int amount) {
        flow += amount; // Increase or decrease the flow depending on the amount
    }

    // Override the toString method to provide a readable representation of the edge
    @Override
    public String toString() {
        return from + " -> " + to + " (" + flow + "/" + capacity + ")"; // Display in the format: "from -> to (current flow/ capacity)"
    }
}
