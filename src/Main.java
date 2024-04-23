import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


public class Main {
    private final static Scanner input = new Scanner(System.in);
    private static String fileName;
    private static File getFile;    // to save the input file
    private static Parser parser;   //parser object


    public static void main(String[] args) {

        System.out.println("SLIDING PUZZLE\n");

        loadNewFile();


    }

    private static void totalDistance(){    //to display the shortest path

        int[][] puzzleArray = parser.getPuzzle();
        int[] startingNodeArray = parser.getStartingNode();
        int[] endingNodeArray = parser.getEndingNode();

        ShortestPathAlgorithm shortestPathObject = new ShortestPathAlgorithm();

        System.out.println("\nFINDING THE SHORTEST PATH");
        System.out.println(shortestPathObject.shortestPathDistance(puzzleArray, startingNodeArray, endingNodeArray));
    }

    private static void loadNewFile(){

        System.out.println("Please put the relevant text file into the 'MazeFolder' \n");
        try {
            String newInputFileName;
            System.out.println("Note: Please enter a valid text file name below");
            System.out.print("New Input File Name:  ");
            newInputFileName = input.nextLine();  //getting the file name

            Parser fileParser = new Parser();
            fileParser.fileRead("src/MazeFolder/" + newInputFileName);
            fileParser.retrievingLines();
            fileParser.retrieveValues();
            if (!fileParser.asFileRead()) {
                throw new Exception("File not available !");
            }
            fileName = fileParser.retrievingFileName();
            getFile = fileParser.getInputFile();
            parser = fileParser;

            Instant start = Instant.now();  //timer to count
            totalDistance();
            Instant end = Instant.now();
            System.out.println(Duration.between(start, end));

            System.out.println("Shortest path founded.\n"+"DONE!");

        } catch (Exception e) {

            System.out.println("\nOccurred exception: "+e);
            System.out.println("\nUnable to read the text file. Please try again later!");
        }
    }
}