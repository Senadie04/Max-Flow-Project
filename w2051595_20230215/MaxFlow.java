import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MaxFlow {
    // Main class to test the implementation
    public static void main(String[] args) {
        // Initialize Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter the filename containing the graph data
        System.out.print("Enter filename (with extension): ");
        String filename = scanner.nextLine();

        // Display the entered filename for confirmation
        System.out.println("File Parsed: " + filename);

        // Start the timer to measure the time taken for the max flow calculation
        long startTime = System.currentTimeMillis();

        // Parse the network data from the given file
        FlowNetwork network = parseNetworkFromFile(filename);

        if (network != null) {
            System.out.println("Calculating maximum flow..");

            // Create a FordFulkerson instance to calculate the max flow using the parsed network
            FordFulkerson fordFulkerson = new FordFulkerson(network);

            // Find the maximum flow from source (0) to sink (last node)
            int maxFlow = fordFulkerson.findMaxFlow(0, network.getNumNodes() - 1);

            // End the timing and calculate the total time taken
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            // Output the maximum flow and the time taken for the calculation
            System.out.println("Maximum Flow: " + maxFlow);
            System.out.println("Time taken: " + timeTaken + " ms");

            // Provide a summary of the network, indicating if detailed steps are omitted for large graphs
            System.out.println("\nSummary: Graph has " +
                    (network.getNumNodes() > 250 ? "more than 250 nodes. Detailed steps omitted for large graphs." : network.getNumNodes() + " nodes."));
            System.out.println("Total Augmentations: " + fordFulkerson.getIterations());
        }

        // Close the scanner to free resources
        scanner.close();
    }

    // Method to parse the network data from the given file
    public static FlowNetwork parseNetworkFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the first line which contains the number of nodes
            int numNodes = Integer.parseInt(reader.readLine().trim());
            FlowNetwork network = new FlowNetwork(numNodes); // Initialize the network with the given number of nodes

            String line;
            // Read the rest of the lines to parse edges (from, to, capacity)
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");

                // If the line contains at least 3 parts (from, to, capacity)
                if (parts.length >= 3) {
                    // Parse the from, to, and capacity values
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);

                    // Add the parsed edge to the network
                    network.addEdge(from, to, capacity);
                }
            }

            // Return the parsed network object
            return network;
        } catch (IOException e) {
            // Handle IO exceptions such as file reading errors
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            // Handle errors during number parsing (e.g., non-numeric values)
            System.err.println("Error parsing number: " + e.getMessage());
            return null;
        }
    }
}
