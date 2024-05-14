package GraphProject;

import java.io.*;
import java.util.*;

public class MainMethod {

    static matrixHashingGraph g = new matrixHashingGraph();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = 14;

        while (choice != 0) {
            System.out.println("Options:\n"
                    + "1) Load your file into graph\n"
                    + "2) Check if there is a path between two cities\n"
                    + "3) Breadth First Search of your cities\n"
                    + "4) Depth First Search of your cities\n"
                    + "5) Shortest path between two cities\n"
                    + "6) Number of simple paths between two cities\n"
                    + "7) Neighboring cities\n"
                    + "8) City with highest degree\n"
                    + "9) Is your graph directed\n"
                    + "10) Are two cities adjacent\n"
                    + "11) Check cycle\n"
                    + "12) Number of vertices in component\n"
                    + "0) Exit");
            System.out.println("");
            System.out.print("Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter file name: ");
                    g.readFromFile(input.next());
                    break;
                case 2:
                    System.out.print("Starting City: ");
                    String startCity = input.next();
                    System.out.print("Ending City: ");
                    String endCity = input.next();
                    System.out.println(g.isThereAPath(startCity, endCity));
                    break;
                case 3:
                    System.out.print("Starting City: ");
                    startCity = input.next();
                    System.out.print("Ending City: ");
                    endCity = input.next();
                    g.BFSfromTo(startCity, endCity);
                    break;
                case 4:
                    System.out.print("Starting City: ");
                    startCity = input.next();
                    System.out.print("Ending City: ");
                    endCity = input.next();
                    g.DFSfromTo(startCity, endCity);
                    break;
                case 5:
                    System.out.print("Starting City: ");
                    startCity = input.next();
                    System.out.print("Ending City: ");
                    endCity = input.next();
                    System.out.println(g.shortestPath(startCity, endCity));
                    break;
                case 6:
                    System.out.print("Starting City: ");
                    startCity = input.next();
                    System.out.print("Ending City: ");
                    endCity = input.next();
                    System.out.println(g.NumberOfSimplePaths(startCity, endCity));
                    break;
                case 7:
                    System.out.print("City: ");
                    String city = input.next();
                    System.out.println(g.neighbors(city));
                    break;
                case 8:
                    System.out.println(g.highestDegree());
                    break;
                case 9:
                    System.out.println(g.isDIrected());
                    break;
                case 10:
                    System.out.print("Starting City: ");
                    startCity = input.next();
                    System.out.print("Ending City: ");
                    endCity = input.next();
                    System.out.println(g.areTheyAdjacent(startCity, endCity));
                    break;
                case 11:
                    System.out.print("City: ");
                    city = input.next();
                    System.out.println(g.isThereACycle(city));
                    break;
                case 12:
                    System.out.print("City: ");
                    city = input.next();
                    System.out.println(g.NumberOfVerticesInComponent(city));
                    break;
            }
        }
        input.close();
    }
}