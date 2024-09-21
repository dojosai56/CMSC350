/**
 * Name: Sairam Soundararajan
 * Date: 1-24-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 1
 * Description: The SyntaxErrorException class is a checked exception that displays an error message
 * when incorrect format is inputted while attempting to covert.
 */
public class SyntaxErrorException extends RuntimeException{
    /*
     * Default Constructor
     */

    public SyntaxErrorException()
    {
        super("Syntax Error: Incorrect Expression Format!");
    }
}
