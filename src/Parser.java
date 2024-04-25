import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    public Scanner lineScan = null;
    private final ArrayList<String> linesArrayList = new ArrayList<>();
    private boolean isScanned;        //to verify the file reading process is successful
    private boolean isLoaded;      //to verify whether the file is loaded successfully
    private File fileInput;        //to save the relevant txt file
    public int[] endNode; 
    public int[] startNode;
    public int[][] puzzle;
    
    
    public Boolean asFileRead() //for the easiness of the exception
    {
        return this.isScanned;
    }

    public int[][] getPuzzle() {
        if (checkFile())
        {
            return this.puzzle;
        }
        return null;
    }

    public int[] getStartingNode()
    {
        if (checkFile())
        {
            return this.startNode;
        }
        return null;
    }

    public int[] getEndingNode()
    {
        if (checkFile())
        {
            return this.endNode;
        }
        return null;
    }

    
    public void retrievingLines() throws IOException
    {
        if (this.isScanned)
        {
            linesArrayList.addAll(Files.readAllLines(fileInput.toPath(), Charset.defaultCharset()));
            this.isLoaded = true;
        }
    }
    public String retrievingFileName()
    {
        if (isLoaded)   //validating whether the file is successfully loaded
        {
            return fileInput.getName();
        }
        return null;
    }

    public ArrayList<String> getLinesArrayList()
    {
        if (this.isScanned)
        {
            return this.linesArrayList;
        }
        return null;
    }

    public void fileRead(String path) throws FileNotFoundException
    {
        File fileInput;
        fileInput = new File(path); //retrieving the file name and accessing the relevant txt file
        
        if (fileInput.length() == 0) //checks whether the path of the file is given or not (the file path will be denoted as a long value)
        {
            throw new FileNotFoundException("File " + path + " does not exist");
        }

        this.fileInput = fileInput;
        this.isScanned = true;
    }


    public File getInputFile()
    {
        if (this.isScanned)
        {
            return fileInput;
        }
        return null;

    }

    public Boolean checkFile()
    {
        if (this.asFileRead())
        {
            return this.isLoaded;
        }
        return null;
    }

    public boolean retrieveValues(){        //adding nodes into the 2D array and searching for the starting node and the ending node
        ArrayList<String> linesArrayList1 = this.getLinesArrayList();

        int horizontalSize = this.getLinesArrayList().get(0).trim().length();    //floorSize
        this.puzzle = new int[horizontalSize][linesArrayList1.size()];
        int counterOfLines = 0;

        for (int i = 0; i < linesArrayList1.size(); i++){
            String line = linesArrayList1.get(i);
            this.lineScan = new Scanner(line);
            int[] floor = new int[horizontalSize];
            int count = 0;

            while (lineScan.hasNext()) {
                if (count < horizontalSize){
                    String node = lineScan.nextLine();
                    node = node.replace("0", "1");  //restoring zeros with ones and dots with zeros
                    node = node.replace(".", "0");

                    if(node.contains("S")){
                        this.startNode = new int[]{counterOfLines, node.indexOf("S")};    //starting point S is restoring with 0
                        node = node.replace("S", "0");
                    }
                    if(node.contains("F")){
                        this.endNode = new int[]{counterOfLines, node.indexOf("F")};
                        node = node.replace("F", "0");                  //finishing point S is restoring with 0
                    }
                    String[] string = node.split("");

                    for (int j = 0; j < string.length; j++) {
                        floor[j] = Integer.valueOf(string[j]);              //storing each index into an integer array
                    }
                    counterOfLines++;
                }
                count++;
            }
            puzzle[i] = floor;
        }

        return true;
    }
}