/**
 * Name: Sairam Soundararajan
 * Date: 3-8-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 4
 * Description: The DFSActions interface is an interface whose generic parameter again
 * specifies the type of the labels that are associated with the vertices of the graph.
 */
public interface DFSActions <T> {

    public void cycleDetected(T vertex);

    public void processVertex(T vertex);

    public void descend();

    public void ascend();
}
