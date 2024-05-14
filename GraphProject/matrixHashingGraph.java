package GraphProject;

import java.io.*;
import java.util.*;

public class matrixHashingGraph {

    int[][] edges;
    HashMap map;
    HashMap<String, Integer> componentSize = new HashMap<String, Integer>(1);
    HashMap<String, Integer> paths;
    HashMap<String, String> currentPath;
    int numV;
    int numE;

    public matrixHashingGraph() {
        map = new HashMap<String, String>(1);
    }

    public void addEdge(int from, int to, int weight) {
        if (edges[from][to] == 0) {
            numE++;
            edges[from][to] = weight;
        }

    }

    public int V() {
        return numV;
    }

    public int E() {
        return numE;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                s.append(edges[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public String highestDegree() {
        StringBuilder s = new StringBuilder("");
        int maxDegree = 0;
        for (int i = 0; i < edges.length; i++) {
            int degree = degree(i);
            if (degree == maxDegree && map.keyAtIndex(i) != null) {
                maxDegree = degree;
                s.append(map.keyAtIndex(i)).append(" ");
            } else if (degree > maxDegree && map.keyAtIndex(i) != null) {
                maxDegree = degree;
                s.replace(0, s.length(), map.keyAtIndex(i)).append(" ");
            }
        }
        return s.toString();
    }

    public int degree(int v) {
        int degree = 0;
        for (int i = 0; i < edges[v].length; i++) {
            if (edges[v][i] != 0) {
                degree++;
            }
        }
        return degree;
    }

    public void readFromFile(String f) {
        readIntoHashMap(f);
        try {
            Scanner sc = new Scanner(new File(f));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] start = line.split("-> ");
                String[] ends = start[1].split(", ");
                for (String end : ends) {
                    String[] nums = end.split(": ");
                    addEdge(map.searchIndex(start[0].trim()), map.searchIndex(nums[0].trim()), Integer.parseInt(nums[1].trim()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public void readIntoHashMap(String file) { //hashing strings
        try {
            Scanner sc = new Scanner(new File(file));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] cities = line.split("\\s*->\\s*|\\s*:\\s*|\\s*,\\s*");
                for (String city : cities) {
                    if (map.search(city) == null) {
                        System.out.println(city);
                        map.put(city, city);
                        numV++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        edges = new int[map.size()][map.size()];
    }

    public boolean areTheyAdjacent(String v1, String v2) {
        return edges[map.searchIndex(v1)][map.searchIndex(v2)] != 0;
    }

    public String neighbors(String v) {
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < edges[0].length; i++) {
            if (edges[map.searchIndex(v)][i] != 0) {
                s.append(map.keyAtIndex(i)).append(" ");
            }
        }
        return s.toString();
    }

    public ArrayList<String> DFStoFindComponent(String v1) {
        ArrayList<String> discovered = new ArrayList<String>();
        GenericStack<String> s = new GenericStack<String>(1);
        int count = 0;
        s.push(v1);
        while (!s.isEmpty()) {
            String v = s.pop();
            System.out.print(v + " ");
            if (!discovered.contains(v)) {
                discovered.add(v);
                count++;
                for (String vertex : (neighbors(v)).split(" ")) {
                    if (!vertex.equals("")) {
                        s.push(vertex);
                    }
                }
            }
        }
        for (int i = 0; i < discovered.size(); i++) {
            componentSize.put(discovered.get(i), count);
        }
        return discovered;
    }

    public void DFSfromTo(String v1, String v2) {
        DFSfromTo(v1, v2, false);
    }

    public int DFSfromTo(String v1, String v2, boolean isCycleCheck) {
        ArrayList<String> discovered = new ArrayList<String>();
        GenericStack<String> s = new GenericStack<String>(1);
        s.push(v1);
        boolean isFirstVisit = true;

        while (!s.isEmpty()) {
            String v = s.pop();
            System.out.print(v + " ");
            if (v.equals(v2)) {
                if (isCycleCheck && isFirstVisit) {
                    isFirstVisit = false;
                } else {
                    return 1;
                }
            }
            if (!discovered.contains(v)) {
                discovered.add(v);
                for (String vertex : (neighbors(v)).split(" ")) {
                    if (!vertex.equals("")) {
                        s.push(vertex);
                    }
                }
            }
        }
        return 0;
    }

    public boolean isThereAPath(String v1, String v2) {
        return DFSfromTo(v1, v2, false) == 1;
    }

    public boolean isThereACycle(String v1) {
        return DFSfromTo(v1, v1, true) == 1;
    }

    public int BFSfromTo(String v1, String v2) {
        ArrayList<String> explored = new ArrayList<String>();
        GenericLinkedQueue<String> q = new GenericLinkedQueue<String>();
        q.enqueue(v1);
        explored.add(v1);
        while (!q.isEmpty()) {
            String v = q.dequeue();
            System.out.print(v + " ");
            if (v.equals(v2)) {
                return 1;
            }
            for (String vertex : (neighbors(v)).split(" ")) {
                if (!vertex.equals("")) {
                    if (!explored.contains(vertex)) {
                        explored.add(vertex);
                        q.enqueue(vertex);
                    }
                }
            }
        }
        return 0;
    }

    public boolean isDIrected() {
        for (int i = 0; i < edges.length; i++) {
            for (int j = i; j < edges.length; j++) {
                if (map.keyAtIndex(i) != null && map.keyAtIndex(j) != null && areTheyAdjacent(map.keyAtIndex(i), map.keyAtIndex(j))) {
                    if (!areTheyAdjacent(map.keyAtIndex(j), map.keyAtIndex(i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int NumberOfVerticesInComponent(String v1) {
        if (componentSize.size() <= 1) { // To Initialize the values to store in hasmap for future
            ArrayList<String> marked = new ArrayList<String>();
            for (int i = 0; i < edges[0].length; i++) {
                if (edges[map.searchIndex(v1)][i] != 0 && !marked.contains(v1)) {
                    marked.addAll(DFStoFindComponent(v1));
                }
            }
        }
        return (Integer) componentSize.search(v1).getValue(); //Constant time!!
    }

    public Integer NumberOfSimplePaths(String v1, String v2) { // 2 methods for recursion
        paths = new HashMap<String, Integer>(1);
        currentPath = new HashMap<String, String>(1);
        return dfsCountPaths(v1, v2);
    }

    public Integer dfsCountPaths(String v1, String v2) { //Recursive DFS implementation
        if (v1.equals(v2)) {
            return 1;
        }
        if (currentPath.search(v1) != null) {
            return 0;
        }
        if (paths.search(v1) != null) {
            return (Integer) (paths.search(v1).getValue());
        }
        currentPath.put(v1, v1); //to avoid cycles
        int pathCount = 0;
        for (int i = 0; i < edges[map.searchIndex(v1)].length; i++) {
            if (edges[map.searchIndex(v1)][i] != 0) {
                String neighbor = map.keyAtIndex(i);
                pathCount += dfsCountPaths(neighbor, v2);
            }
        }
        currentPath.remove(v1);
        paths.put(v1, pathCount);
        return pathCount;
    }

    public int shortestPath(String v1, String v2) {
        int startVertex = map.searchIndex(v1);
        int endVertex = map.searchIndex(v2);

        int[] distances = new int[edges.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        GenericLinkedQueue<Integer> queue = new GenericLinkedQueue<Integer>();
        queue.enqueue(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int i = 0; i < edges.length; i++) {
                if (edges[current][i] != 0 && distances[current] + edges[current][i] < distances[i]) {
                    distances[i] = distances[current] + edges[current][i];
                    queue.enqueue(i);
                }
            }
        }
        if (distances[endVertex] == Integer.MAX_VALUE) {
            System.out.println("v1 --x-- v2");
            return 0;
        }
        return distances[endVertex];
    }
}
