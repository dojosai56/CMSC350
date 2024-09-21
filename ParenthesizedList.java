import java.util.Stack;

/**
 * implement DFSActions
 * override toString --> return class structure
 * as parenthesizedList
 */
public class ParenthesizedList<T> implements DFSActions <T>{
    private Stack<Integer> prevCursorPos;
    private String parentStr;

    public ParenthesizedList()
    {
        prevCursorPos = new Stack<Integer>();
        parentStr = "( ";
    }
    @Override
    public void cycleDetected(T vertex) {
        System.out.println("cycle -> " + vertex);
        int lastCursor = prevCursorPos.pop();
        parentStr = parentStr.substring(0,lastCursor+1) + "*" + parentStr.substring(lastCursor+1,parentStr.length());
    }

    @Override
    public void processVertex(T vertex) {
        parentStr += vertex + " ";
        prevCursorPos.push(parentStr.length()-2);

    }

    @Override
    public void descend() {
        parentStr += "( ";
    }

    @Override
    public void ascend() {
        if(parentStr.substring(parentStr.length()-2).equals("( "))
        {
            parentStr = parentStr.substring(0, parentStr.length()-2);
            return;
        }
        parentStr += ") ";
    }

    public String toString()
    {
        return parentStr + ")";
    }

}
