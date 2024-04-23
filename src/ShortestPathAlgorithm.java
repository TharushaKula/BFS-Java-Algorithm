import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathAlgorithm {
    static class Nodes implements Comparable<Nodes> {
        int xAxis;
        int yAxis;
        String path;  // directions
        int distanceLength;  //distance length from starting node to the end node


        Nodes(int xAxis, int yAxis, int distanceLength, String path) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.distanceLength = distanceLength;
            this.path = path  + " (" + (yAxis + 1) + ", " + (xAxis + 1) +")\n" ;     //x and y axis starts from 1
        }

        @Override
        public String toString() {
            return "TOTAL DISTANCE : " + distanceLength +"\n"+ " \nSTART: " + path;
        }

        @Override
        public int compareTo(Nodes coordinate) {
            return this.distanceLength == coordinate.distanceLength ? this.path.compareTo(coordinate.path) : this.distanceLength - coordinate.distanceLength;
        }
    }

    public String shortestPathDistance(int[][] puzzle, int[] startingNode, int[] endingNode) {    //passing the 2D array puzzle and the start point x,y and the end point x,y
        // to check whether the nodes are checked
        int rows = puzzle.length;
        int cols = puzzle.length;
        boolean[][] checkedNodes = new boolean[rows][cols];      //for the easiness to check visited and not visited nodes (true,false)

        Queue<Nodes> queue = new LinkedList<>();        //initializing the queue
        queue.add(new Nodes(startingNode[0], startingNode[1], 0, ""));       //initializing the starting coordinate

        // accessible 4 ways which are available
        String[] accessibleWays = {"Move to UP", "Move to DOWN", "Move to LEFT", "Move to RIGHT"};
        int[][] accessibleCoordinates = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  //possible  4 directions that can move

        while (!queue.isEmpty()) {

            Nodes nodePosition = queue.poll();    //removing the first element of the queue
            if (nodePosition.xAxis == endingNode[0] && nodePosition.yAxis == endingNode[1]) {
                return nodePosition.toString();
            }

            for (int i = 0; i < accessibleCoordinates.length; i++) {
                int row = nodePosition.xAxis;
                int column = nodePosition.yAxis;
                int distanceFromStart = nodePosition.distanceLength;
                String path = nodePosition.path;

                // Searching for the directions until a rock or wall is hit
                while (row >= 0 && row < rows &&            //checking whether the x axis hit on a wall
                        column >= 0 && column < cols &&     //checking whether the y axis hit on a wall
                        puzzle[row][column] == 0 &&           //checking whether it hits on a rock
                        (row != endingNode[0] || column != endingNode[1])) {    //checking whether it has reached the end point

                    row = row + accessibleCoordinates[i][0];
                    column =column + accessibleCoordinates[i][1];
                    distanceFromStart += 1;
                }

                // if the goal is not found, need to roll back one step to get the correct place the player can go
                if (row != endingNode[0] || column != endingNode[1]) {
                    row -= accessibleCoordinates[i][0];
                    column -= accessibleCoordinates[i][1];
                    distanceFromStart -= 1;
                }

                if (!checkedNodes[row][column]) {        //checking whether the the node has visited or not if not visited adding to the queue
                    checkedNodes[nodePosition.xAxis][nodePosition.yAxis] = true;
                    queue.add(new Nodes(row, column, distanceFromStart, path + accessibleWays[i]));
                }
            }
        }
        return "No path found!";
    }
}