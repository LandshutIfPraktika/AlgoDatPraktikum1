import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {


    // read ascii file linewise and fill ArrayList edgeList
// file structure: each line contains two integers, separated by blanks = two vertices = edge
    static ArrayList<Point> readFile(String filename) {
        ArrayList<Point> edgeList = new ArrayList<Point>();

        try {
            FileReader file_in = new FileReader(filename);
            BufferedReader file_line = new BufferedReader(file_in);

            for (String line = file_line.readLine(); line != null && !line.trim().equals(""); line = file_line.readLine()) {
                String[] lineItems = line.split(" ");  // entries in file are separated by blanks
                Point newEdge = new Point(Integer.parseInt(lineItems[0]), Integer.parseInt(lineItems[1]));
                edgeList.add(newEdge);
            }
            file_in.close();
        } catch (IOException e) {
            System.out.println("Couldn't read data file: " + filename);
        }
        return edgeList;
    }
}