/**
 * Name: Sairam Soundararajan
 * Date: 1-24-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 1
 * Description: The ExpressionConverter class is designed to contain the code necessary to perform the conversions
 */
import java.util.ArrayList;
import java.util.Stack;

public class ExpressionConverter {
    /**
     * input exp:
     * - does NOT need spaces unless they are consecutive operands
     *
     * output exp:
     * - has spaces between all symbols
     *
     * Throw SyntaxError exception if stack in not empty once the conversion is complete
     *
     */
    public static String postToPrefix(String exp)  throws SyntaxErrorException{
       ArrayList<String> tokens = tokenizeString(exp);
       Stack<String> operandStack = new Stack<String>();
       int index = 0;

       while(index < tokens.size())
       {
           if(isOperand(tokens.get(index)))
           {
               operandStack.push(tokens.get(index));
           } // if isOperand push onto operand stack
           else
           {
               if(operandStack.size() < 2)
                   throw new SyntaxErrorException();
               String op1 = operandStack.pop();
               String op2 = operandStack.pop();
               operandStack.push(tokens.get(index) + " " + op2 + " " + op1);
           }
           index++;
       }
        if(operandStack.size() != 1)
            throw new SyntaxErrorException();
        return operandStack.pop();
    } // postToPrefix

    /*
     *
     *
     */
    public static String preToPostfix(String exp) throws SyntaxErrorException{
        ArrayList<String> tokens = tokenizeString(exp);
        Stack<String> reversalStack = new Stack<String>(), operandStack = new Stack<String>();
        int index = tokens.size()-1;
        String poppedToken;
        while(index >= 0) {
            System.out.println(index);
            reversalStack.push(tokens.get(index));
            while(reversalStack.size() > 0)
            {

                poppedToken = reversalStack.pop();

                if(isOperand(poppedToken))
                    operandStack.push(poppedToken);
                else
                {
                  if(operandStack.size() < 2)
                      throw new SyntaxErrorException();
                  operandStack.push(operandStack.pop() + " " + operandStack.pop() + " " + poppedToken);
                }

            }
            index--;
        }
        if(operandStack.size() != 1)
            throw new SyntaxErrorException();
        exp = operandStack.pop();

        return exp;
    }

    private static boolean isOperand(String token)
    {
        return !token.equals(" ") && !(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%"));
    }

    private static boolean isOperator(String token)
    {
        return (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%"));
    }

    /**
     *
     * remove consecutive spaces between symbols
     * Trim spaces from before and after String
     * @param str
     * @return
     */
    private static ArrayList<String> tokenizeString(String str) {
       ArrayList<String> tokens = new ArrayList<String>();

        str = str.trim().replaceAll("\\s+", " ");
        String operand = "";
        int j = 0;

        for(int counter = 0; counter < str.length(); counter++) {
            if (str.charAt(counter) != ' ') {
                if(isOperator(str.substring(counter, counter+1)))
                {
                    tokens.add(str.substring(counter, counter+1));
                    continue; // go to next iteration of loop
                } // if is operator add to tokens list
                else {
                    //j = counter;
                    operand = "";
                    while(counter < str.length() && isOperand(str.substring(counter, counter+1)))
                    {
                        operand += str.substring(counter, counter+1);
                        counter++;
                    }
                    counter--;
                    tokens.add(operand);
                } // must be operand to add consecutive chars that are ' ' or +
                //tokens.add(str.substring(counter, counter+1));
            }
        }

        return tokens;
    } // cleanString
}
