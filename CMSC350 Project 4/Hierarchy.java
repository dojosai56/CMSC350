/**
 * Name: Sairam Soundararajan
 * Date: 3-8-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 4
 * Description: The Hierarchy class is a generic class that produces a hierarchical representation of the class dependencies.
 */
import java.util.Stack;

/**
 * must implement DFSActions
 * must override toString  -> return hierarchy of
 * the classes
 *
 */
public class Hierarchy<T> implements DFSActions <T>{
    Stack<Integer> tabs, cursorPos; // hold prev Class num tabs and cursor position
    String hierarchyStr;
    int numTabs, currentCursorPos; // current num Tabs
    /**
     * ClassA
     *      ClassC
     *
     *
     */
    public Hierarchy()
    {
        hierarchyStr = "";
        cursorPos = new Stack<Integer>();
        tabs = new Stack<Integer>();
        numTabs = 0;
        currentCursorPos = 0;

    } // constructor

    @Override
    public void cycleDetected(T vertex) {
        System.out.println("cycle -> " + vertex);
        int lastCursor = cursorPos.pop();
        hierarchyStr = hierarchyStr.substring(0,lastCursor+1) + "*" + hierarchyStr.substring(lastCursor+1,hierarchyStr.length()); //cursorPos.pop();
        //hierarchyStr += writeTabs(numTabs) + vertex + "*";
        //tabs.push(numTabs); // push old num of Tabs
        //numTabs++; // update current num of tabs
    }

    @Override
    public void processVertex(T vertex) {
        hierarchyStr += writeTabs(numTabs) + vertex;
        //tabs.push(numTabs); // push old num of Tabs
        //numTabs++; // update current num of tabs
        //currentCursorPos = hierarchyStr.length()-1;
        cursorPos.push(hierarchyStr.length()-1);

    } // processVertex


    @Override
    public void descend() {
        hierarchyStr += "\n";
        tabs.push(numTabs);
        numTabs++;
    }

    @Override
    public void ascend() {
        if(tabs.isEmpty())
            return;

        if(tabs.isEmpty())
            return;

        numTabs = tabs.pop();
    }

    public String toString()
    {
        return hierarchyStr;
    }

    private String writeTabs(int n)
    {
        String result = "";
        for(int counter = 0; counter < n; counter++)
        {
            result += "\t";
        }

        return result;
     } //writeTabs

}
