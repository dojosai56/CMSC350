/**
 * Name: Sairam Soundararajan
 * Date: 2-9-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 2
 * Description: The InvalidPolynomialSyntax class is an Unchecked Exception that displays an error 
 */
public class InvalidPolynomialSyntax extends RuntimeException {
    /*
     * Default constructor
     */
    public InvalidPolynomialSyntax()
    {
        super("Syntax Error Incorrect Expression format!");
    } // constructor

    public InvalidPolynomialSyntax(String message) {
        super(message);

    }

}
