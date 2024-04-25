import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathAlgorithm {
    static class Nodes implements Comparable<Nodes> {
        int axisX;
        int axisY;
        String directionPath;
        int lengthDistance;  //the length of the path from the initial node to the final node.


        Nodes(int axisX, int axisY, int lengthDistance, String path) {
            this.axisX = axisX;
            this.axisY = axisY;
            this.lengthDistance = lengthDistance;
            this.directionPath = path  + " (" + (axisY + 1) + ", " + (axisX + 1) +")\n" ;     //x and y axis starts from 1
        }

        @Override
        public String toString() {
            return "TOTAL DISTANCE : " + lengthDistance +"\n"+ " \nSTART: " + directionPath;
        }

        @Override
        public int compareTo(Nodes coordinate) {
            return this.lengthDistance == coordinate.lengthDistance ? this.directionPath.compareTo(coordinate.directionPath) : this.lengthDistance - coordinate.lengthDistance;
        }
    }

    public String shortestPathDistance(int[][] puzzle, int[] startNode, int[] endNode) {  //passing the 2D array puzzle and the start point x,y and the end point x,y
        // to check whether the nodes are checked
        int rows = puzzle.length;
        int columns = puzzle.length;
        boolean[][] checkedNodes = new boolean[rows][columns];     //for the easiness to check visited and not visited nodes (true,false)

        Queue<Nodes> queue = new LinkedList<>();        //initializing the queue
        queue.add(new Nodes(startNode[0], startNode[1], 0, ""));       //initializing the starting coordinate

        // accessible 4 ways which are available
        String[] reachableWays = {"Move to UP", "Move to DOWN", "Move to LEFT", "Move to RIGHT"};
        int[][] reachableCorrdinates = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  //possible  4 directions that can move

        while (!queue.isEmpty()) {

            Nodes nodePosition = queue.poll();    //removing the first element of the queue
            if (nodePosition.axisX == endNode[0] && nodePosition.axisY == endNode[1]) {
                return nodePosition.toString();
            }

            for (int i = 0; i < reachableCorrdinates.length; i++) {
                int row = nodePosition.axisX;
                int column = nodePosition.axisY;
                int fromStartDistance = nodePosition.lengthDistance;
                String path = nodePosition.directionPath;

                // Searching for the directions until a rock or wall is hit
                while (row >= 0 && row < rows &&            //checking whether the x axis hit on a wall
                        column >= 0 && column < columns &&     //checking whether the y axis hit on a wall
                        puzzle[row][column] == 0 &&           //checking whether it hits on a rock
                        (row != endNode[0] || column != endNode[1])) {    //checking whether it has reached the end point

                    row = row + reachableCorrdinates[i][0];
                    column =column + reachableCorrdinates[i][1];
                    fromStartDistance += 1;
                }

                // if the goal is not found, need to roll back one step to get the correct place the player can go
                if (row != endNode[0] || column != endNode[1]) {
                    row -= reachableCorrdinates[i][0];
                    column -= reachableCorrdinates[i][1];
                    fromStartDistance -= 1;
                }

                if (!checkedNodes[row][column]) {        //checking whether the the node has visited or not if not visited adding to the queue
                    checkedNodes[nodePosition.axisX][nodePosition.axisY] = true;
                    queue.add(new Nodes(row, column, fromStartDistance, path + reachableWays[i]));
                }
            }
        }
        return "Path not found";
    }
}