package com.company;

import java.util.*;

public class Main {
    private static final int NO_PARENT = -1;

    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    private static void dijkstra(int[][] adjacencyMatrix,
                                 int startVertex, int end , String[] bus, Route route)
    {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];

                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;

                }

            }
        }

        printSolution(startVertex, shortestDistances, parents,end,bus,route);
    }

    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
                                      int[] distances,
                                      int[] parents,int end,String[] bus,Route route)
    {
        int nVertices = distances.length;
        //System.out.print("Vertex\t Distance\tPath");

        for (int vertexIndex = 0;
             vertexIndex < nVertices;
             vertexIndex++)
        {
            if (vertexIndex == end)
            {
                //System.out.print("\n" + startVertex + " -> ");
                //System.out.print(vertexIndex + " \t\t ");
                //System.out.print(distances[vertexIndex] + "\t\t");
                route.distant = distances[vertexIndex];

                printPath(vertexIndex, parents,bus,route);
            }
        }
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex,
                                  int[] parents, String[] bus,Route route)
    {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents,bus,route);
        route.path.add(bus[currentVertex]);
        //System.out.print(bus[currentVertex] + " ");
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] adjacencyMatrix = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
                { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
                { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        int [][] busLine = { { 0, 515, 0, 0, 0, 0, 0, 509, 0 },
                { 515, 0, 36, 0, 0, 0, 0, 516, 0 },
                { 0, 36, 0, 256, 0, 198, 0, 0, 124 },
                { 0, 0, 256, 0, 34, 189, 0, 0, 0 },
                { 0, 0, 0, 34, 0, 10, 0, 0, 0 },
                { 0, 0, 198, 0, 10, 0, 80, 0, 0 },
                { 0, 0, 0, 189, 0, 80, 0, 70, 101 },
                { 509, 516, 0, 0, 0, 0, 70, 0, 123 },
                { 0, 0, 124, 0, 0, 0, 101, 123, 0 } };

        String[] bus = {"อนุสาวรีย์ชัยสมรภูมิ" , "สะพานควาย" , "จตุจักร" , "ห้าแยกลาดพร้าว" , "มหาวิทยาลัยสวนดุสิต" , "พระราชวังสวนดุสิต" , "ตั้งหั้วเส็ง" , "มหาวิทยาลัยเกษตรศาสตร์" , "กรมป่าไม้"};
        Route route = new Route();
        route.start = in.nextInt();
        route.dest = in.nextInt();
        dijkstra(adjacencyMatrix, route.start,route.dest , bus, route);
        System.out.println("Start : "+bus[route.start]);
        System.out.println("Destination : "+ bus[route.dest]);
        System.out.println("Distant :" +route.distant);
        for(int i=0;i<route.path.size();i++){
            System.out.print(route.path.get(i));
            if(i!=0) {
                int indexDest = -1;
                int indexStart = -1;
                for (int j = 0; j < bus.length; j++) {
                    if (route.path.get(i).equals(bus[j])) {
                        indexDest = j;
                    }
                    if (route.path.get(i-1).equals(bus[j])) {
                        indexStart = j;
                    }
                }
                route.busLine.add(busLine[indexStart][indexDest]);
            }
            if(i+1<route.path.size()){
                System.out.print(" -> ");
            }
            else{
                System.out.println();
            }
        }
        route.busLine.forEach(x-> System.out.print("รถเมล์สาย "+x+" "));
    }
}
