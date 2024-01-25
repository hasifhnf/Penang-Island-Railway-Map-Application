import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PathFinderDFS {
    private int numVertices; // Number of vertices in the graph
    private boolean[][] adjacencyMatrix; // Adjacency matrix representing connections between vertices
    private int[] num; // Array to keep track of visited vertices during DFS
    private List<Integer> path; // List to store the path from source to destination

    public PathFinderDFS(int numVertices) {
        this.numVertices = numVertices;
        num = new int[numVertices]; // Initialize the array to store visited vertices
        path = new ArrayList<>(); // Initialize the list to store the path
    }

    // Set the adjacency matrix for the graph
    public void setAdjacencyMatrix(boolean[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    // Find a path between two vertices (cities) using Depth-First Search (DFS)
    public List<Integer> findPathDFS(int source, int destination) {
        for (int i = 0; i < numVertices; i++) {
            num[i] = 0; // Reset the visited array for each search
        }
        path.clear(); // Clear the path list for each search

        // If source and destination are the same, add the source to the path and return
        if (source == destination) {
            path.add(source);
            return path;
        }

        DFS(source, destination); // Perform Depth-First Search

        // Reverse the path to get it from source to destination
        if (!path.isEmpty()) {
            Collections.reverse(path);
        }

        return path;
    }

    // Recursive helper method for Depth-First Search (DFS)
    private void DFS(int vertex, int destination) {
        num[vertex]++; // Mark the vertex as visited

        // If the destination is reached, add it to the path and return
        if (vertex == destination) {
            path.add(vertex);
            return;
        }

        // Traverse all neighbors of the current vertex
        for (int i = 0; i < numVertices; i++) {
            if (adjacencyMatrix[vertex][i] && num[i] == 0 && vertex != destination) {
                path.add(vertex); // Add the current vertex to the path
                DFS(i, destination); // Recursively explore the next vertex

                // If the destination is not reached, remove the current vertex from the path
                if (!path.isEmpty() && path.get(path.size() - 1) != destination) {
                    path.remove(path.size() - 1);
                }
            }
        }
    }
}
