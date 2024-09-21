/**
 * Name: Sairam Soundararajan
 * Date: 3-8-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 4
 * Description: The DirectedGraph class is a a generic class, whose generic parameter
 * specifies the type of the labels that are associated with the vertices of the graph.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * - Generic type represents the type of labels
 * associated with the vertices of the graph
 * - Internal representation should be: alternate adjacency
 * list (Fig 10.7)
 * - should contain a method: allows edges to be added to the graph
 * - Another method: performs depth-first search according to pseudocode
 * 		- 1st init all verts to undiscovered
 * 		- begin search at vert corresponding to 1st name in txt file
 * - Another method: display any unreachable classes (remaining undiscovered
 * 		verts after depth-first search is run)
 */
public class DirectedGraph <T>{
    private ArrayList<ArrayList<T>> directedGraph;
    Hierarchy hierarchyRep;
    ParenthesizedList paraRep;
    private T firstVertexInFile;

    private enum Status {
        UNDISCOVERED, DISCOVERED, FINISHED;
    }
    private Status vertsStatus[];//T-> discovered, F->Undiscovered
    /*
     * constructor
     */
    public DirectedGraph(File inputFile) throws FileNotFoundException{
        directedGraph = new ArrayList<ArrayList<T>>();
        hierarchyRep = new Hierarchy<T>();
        paraRep = new ParenthesizedList<T>();
        createDirectedGraph(inputFile);

    } // constructor

    public T getFirstVertexInFile() {
        return firstVertexInFile;
    } // getFirstVertexInFile

    /*
     * loop through the file
     * add verts to graph and/or add edges between verts
     */
    private void createDirectedGraph(File inputFile) throws FileNotFoundException{
        Scanner fileReader;
        String tokens[];
        T destVert, srcVert;
        int srcVertIndex, destVertIndex;

        fileReader = new Scanner(inputFile); // throws FNF Exp

        while(fileReader.hasNextLine()) {
            tokens = cleanLine(fileReader.nextLine()).split(" "); //tokenize

            srcVert = (T) tokens[0]; //get src vert from current line



            srcVertIndex = graphContains(srcVert);//check if in graph

            if(directedGraph.size() == 0)
                firstVertexInFile = srcVert;

            if(srcVertIndex == -1) {
                directedGraph.add(new ArrayList<T>());
                directedGraph.get(directedGraph.size()-1).add(srcVert);
                srcVertIndex = directedGraph.size() -1;
            } // if vert not in graph add it

            for(int i = 1; i < tokens.length;i++) {
                destVert = (T) tokens[i];

                destVertIndex = graphContains(destVert);

                if(destVertIndex == -1) {
                    directedGraph.add(new ArrayList<T>());
                    directedGraph.get(directedGraph.size()-1).add(destVert);
                    //srcVertIndex = directedGraph.size() -1;
                } // if vert not in graph add it

                // add edge from srcVert to destVert
                addEdge(destVert, srcVertIndex);
            } // loop through each token add to graph/create edge

        } // loop each line of file

    } // createDirectedGraph

    /*
     * return -1 if vert not found in graph
     * or return index of vert
     */
    private int graphContains(T vert) {

        for(int i = 0; i < directedGraph.size();i++) {
            if(directedGraph.get(i).get(0).equals(vert))
                return i;
        } // loop through verts of graph

        return -1;
    } // graphContains


    /*
     * Gets rid of multiple consecutive spaces
     * in between words and spaces at the beginning
     * and end of the line
     */
    private String cleanLine(String line) {
        return line.replaceAll("//s+", " ").trim();
    } // cleanString

    /*
     * add edges to the graph
     */
    public void addEdge(T destVert, int srcVertIndex) {
        directedGraph.get(srcVertIndex).add(destVert);
    } // addEdge

    /*
     * performs depth-first search according to pseudocode
     * 	- 1st init all verts to undiscovered
     * 	- begin search at vert corresponding to 1st name in txt file
     */
    public void depthFirstSearch(T vertex) {
        int vertexIndex;

        if(vertsStatus == null) {
            vertsStatus = new Status[directedGraph.size()];
            for(int i = 0; i < vertsStatus.length;i++) {
                vertsStatus[i] = Status.UNDISCOVERED;
            }
        } // init vertsStatus on first call

        vertexIndex = graphContains(vertex);

        if(vertexIndex == -1)
            return;// error vertex not in graph

        if(vertsStatus[vertexIndex] == Status.DISCOVERED) {
            //perform cycle detected action
            hierarchyRep.cycleDetected(vertex);
            paraRep.cycleDetected(vertex);
            return;
        }
        // perform add vertex action
        hierarchyRep.processVertex(vertex);
        paraRep.processVertex(vertex);
        // mark vertex as discovered
        vertsStatus[vertexIndex] = Status.DISCOVERED;
        // perform descend action
        hierarchyRep.descend();
        paraRep.descend();

        for(int i = 1; i < directedGraph.get(vertexIndex).size();i++) {
            depthFirstSearch(directedGraph.get(vertexIndex).get(i));
        }//loop through adjacent verts

        vertsStatus[vertexIndex] = Status.FINISHED;

        // perform ascend action
        hierarchyRep.ascend();
        paraRep.ascend();
        // mark vertex as finished ??

    } // depthFirstSearch

    public String printGraph() {
        String result = "";
        System.out.println(directedGraph.size());
        for(int i = 0; i < directedGraph.size();i++) {
            result += directedGraph.get(i).get(0) + "\n";
            for(int j = 1; j < directedGraph.get(i).size();j++) {
                result += "\t" + directedGraph.get(i).get(j) + " ";
            }// loop through the edges of the ith vert
            result += "\n";
        }// loop through the verts

        return result;
    } // printGraphDS


    /*
     * Assuming depthFirstSearch was already called
     */
    public String getUnreachableVerts() {
        String unreachableVerts = "";
        if(vertsStatus == null)
            return "";

        for(int i = 0; i < vertsStatus.length;i++) {
            if(vertsStatus[i] == Status.UNDISCOVERED) {
                unreachableVerts += directedGraph.get(i).get(0) + ", ";
            } // if not reached
        }// loop

        if(unreachableVerts.length() == 0)
            return "";

        unreachableVerts = unreachableVerts.substring(0,unreachableVerts.length()-2);
        unreachableVerts += " is unreachable";

        return unreachableVerts;
    } // getUnreachableVerts

} // DirectedGraph
