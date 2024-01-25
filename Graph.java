import java.util.ArrayList;
import java.util.List;

class Graph {
    private int numVertices; // Number of vertices in the graph
    private List<String> cityNames; // List of city names
    private boolean[][] adjacencyMatrix; // Adjacency matrix to represent connections between vertices

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.cityNames = new ArrayList<>(); // Initialize the list of city names
        adjacencyMatrix = new boolean[numVertices][numVertices]; // Initialize the adjacency matrix
    }

    // Add an edge between two vertices (cities)
    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = true;
        adjacencyMatrix[destination][source] = true;
    }

    // Add a city name to the list
    public void addCityName(String name) {
        cityNames.add(name);
    }

    // Get the list of city names
    public List<String> getCityNames() {
        return cityNames;
    }

    // Get the number of cities in the graph
    public int getNumCities() {
        return numVertices;
    }

    // Get the name of a city based on its index
    public String getCityName(int index) {
        return cityNames.get(index);
    }

    // Check if there is a connection between two cities (vertices)
    public boolean hasConnection(int source, int destination) {
        return adjacencyMatrix[source][destination] || adjacencyMatrix[destination][source];
    }

    // Find a path between two vertices (cities) using Depth-First Search (DFS)
    public List<Integer> findPathDFS(int source, int destination) {
        PathFinderDFS pathFinder = new PathFinderDFS(numVertices);
        pathFinder.setAdjacencyMatrix(adjacencyMatrix);
        return pathFinder.findPathDFS(source, destination);
    }
}
