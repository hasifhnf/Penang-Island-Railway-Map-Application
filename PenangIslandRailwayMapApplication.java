/* CPT212 ASSIGNMENT 2

   GROUP MEMBERS :
   - Sofea Binti Taufik (159072)
   - Rabiatul Adawiyah Binti Nordin (160079)
   - Muhammad Hasif Bin Mohd Hanif (159795)
   - Chusnul Mariyah Binti Muhammad (159354)

    SYSTEM PURPOSE :
    The application that we developed is an application that is made
    from a path-finding algorithm method which is Depth-First Search.
    Our application is a Railway Map application for users who commute using a Railway.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PenangIslandRailwayMapApplication{
    private JFrame frame; // The main GUI frame
    private Graph graph; // The graph representing the railway map
    private JButton[] stationButtons; // Buttons representing each station
    private int sourceIndex = -1; // Index of the selected source station
    private int destinationIndex = -1; // Index of the selected destination station

    public PenangIslandRailwayMapApplication() {
        int numCities = 8; // Number of cities in the transportation system

        graph = new Graph(numCities); // Create a new graph

        // Add city names to the graph
        graph.addCityName("USM");
        graph.addCityName("GeorgeTown");
        graph.addCityName("Penang Hill");
        graph.addCityName("Batu Maung");
        graph.addCityName("Bayan Lepas");
        graph.addCityName("Jelutong");
        graph.addCityName("Batu Feringghi");
        graph.addCityName("ESCAPE");

        // Add connections between cities using addEdge() method
        graph.addEdge(0, 1); // City USM connected to City GeorgeTown
        graph.addEdge(0, 2); // City USM connected to City Penang Hill
        graph.addEdge(1, 3); // City GeorgeTown connected to City Batu Maung
        graph.addEdge(2, 3); // City Penang Hill connected to City Batu Maung
        graph.addEdge(3, 4); // City Batu Maung connected to City Bayan Lepas
        graph.addEdge(3, 5); // City Batu Maung connected to City Jelutong
        graph.addEdge(4, 6); // City Bayan Lepas connected to City Batu Feringghi
        graph.addEdge(5, 7); // City Jelutong connected to City ESCAPE

        frame = new JFrame("Railway Map"); // Create the main GUI frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setTitle("Penang Island Railway Map");

        JTextField titleTextField = new JTextField("Penang Island Railway Map");
        titleTextField.setEditable(false);
        titleTextField.setHorizontalAlignment(JTextField.CENTER);
        frame.add(titleTextField, BorderLayout.NORTH);

        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));

                // Iterate over the graph's cities and their connections to draw arrows on the map
                for (int i = 0; i < graph.getNumCities(); i++) {
                    for (int j = 0; j < graph.getNumCities(); j++) {
                        if (graph.hasConnection(i, j)) {
                            Point startPoint = getButtonCenter(stationButtons[i]);
                            Point endPoint = getButtonCenter(stationButtons[j]);
                            drawArrow(g2d, startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                        }
                    }
                }
            }
        };
        mapPanel.setLayout(null);

        // Create buttons for each station
        stationButtons = new JButton[graph.getNumCities()];
        for (int i = 0; i < graph.getNumCities(); i++) {
            JButton button = new JButton(graph.getCityName(i));
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(index);
                }
            });
            button.setBounds(getButtonPosition(i));
            mapPanel.add(button);
            stationButtons[i] = button;
        }

        // Add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setBounds(500, 300, 100, 30);
        mapPanel.add(exitButton);

        // Add reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSelection();
            }
        });
        resetButton.setBounds(400, 300, 100, 30);
        mapPanel.add(resetButton);

        // Add map panel to the frame
        frame.getContentPane().add(mapPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    // Handle button click event
    private void buttonClicked(int index) {
        if (sourceIndex == -1) {
            sourceIndex = index;
            stationButtons[index].setBackground(Color.GREEN);
            JOptionPane.showMessageDialog(frame, "Source station selected: " + graph.getCityName(index));
        } else if (destinationIndex == -1) {
            destinationIndex = index;
            stationButtons[index].setBackground(Color.RED);
            JOptionPane.showMessageDialog(frame, "Destination station selected: " + graph.getCityName(index));
            calculatePath();
        } else {
            JOptionPane.showMessageDialog(frame, "Both source and destination stations have been selected.");
        }
    }

    // Calculate and display the path between the source and destination stations
    private void calculatePath() {
        if (graph.hasConnection(sourceIndex, destinationIndex)) {
            JOptionPane.showMessageDialog(frame, "You're travelling directly from " + graph.getCityName(sourceIndex) +
                    " to " + graph.getCityName(destinationIndex) + ".");
        } else {
            List<Integer> path = graph.findPathDFS(sourceIndex, destinationIndex);

            // Display the path
            StringBuilder pathBuilder = new StringBuilder();
            String sourceCity = graph.getCityName(sourceIndex);
            String destinationCity = graph.getCityName(destinationIndex);
            pathBuilder.append("You're travelling from ").append(sourceCity).append(" to ").append(destinationCity).append("\n");
            if (path.isEmpty()) {
                pathBuilder.append("No path found from ").append(sourceCity).append(" to ").append(destinationCity);
            } else {
                pathBuilder.append("Path from ").append(sourceCity).append(" to ").append(destinationCity).append(":\n");

                // Iterate over the path and append city names to the pathBuilder
                for (int i = path.size() - 1; i > 0; i--) {
                    int city = path.get(i);
                    pathBuilder.append(graph.getCityName(city)).append(" -> ");
                }
                pathBuilder.append(destinationCity);
            }

            JOptionPane.showMessageDialog(frame, pathBuilder.toString());
        }
    }

    // Calculate the position of a button based on its index
    private Rectangle getButtonPosition(int index) {
        int[] xCoords = {200, 400, 400, 600, 800, 800, 1000, 1000};
        int[] yCoords = {300, 250, 350, 300, 250, 350, 250, 350};

        int x = xCoords[index] - 180;
        int y = yCoords[index] - 150;
        int width = 120;
        int height = 30;

        return new Rectangle(x, y, width, height);
    }

    // Calculate the center position of a button
    private Point getButtonCenter(JButton button) {
        Point position = button.getLocation();
        Dimension size = button.getSize();
        int x = position.x + size.width / 2;
        int y = position.y + size.height / 2;
        return new Point(x, y);
    }

    // Draw an arrow between two points
    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        // Create a polygon for the arrowhead
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(x2, y2);
        arrowHead.addPoint((int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6)), (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6)));
        arrowHead.addPoint((int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6)),(int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6)));

        g.fillPolygon(arrowHead);
    }

    // Reset the selection of source and destination stations
    private void resetSelection() {
        sourceIndex = -1;
        destinationIndex = -1;

        // Reset the background color of all station buttons
        for (JButton button : stationButtons) {
            button.setBackground(null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PenangIslandRailwayMapApplication();
            }
        });
    }
}



