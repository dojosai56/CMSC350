/**
 * Name: Sairam Soundararajan
 * Date: 2-9-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 2
 * Description: The Polynomial class defines an individual polynomial. Polynomial objects are represented internally by a singly linked list.
 */
import java.util.Iterator;

public class Polynomial implements Comparable<Polynomial>, Iterable{
    private Node head; // first in the linked list

    public Polynomial(String polynomial) throws InvalidPolynomialSyntax{
        createLinkedList(polynomial);

    } // constructor
    static class Node {
        private Node next;
        private double coefficient;
        private int exponent;

        public Node(double coeff, int exp){
            this.coefficient = coeff;
            this.exponent = exp;
        } // constructor

        public String toString()
        {
            if(coefficient == 0.0)
                return "";
            if (exponent == 1.0)
                return coefficient + "x";
            if (exponent == 0.0)
                return coefficient + "";

            return coefficient + "x^" + exponent;
        }


    } // static class Node

    public int compareTo(Polynomial other)
    {
        PolynomialIterator iter = iterator();
        PolynomialIterator iterOther = other.iterator();
        Vector2D dataThis, dataOther;

        while(iter.hasNext() || iterOther.hasNext())
        {
            dataThis = iter.next(); //might be null
            dataOther = iterOther.next(); //might be null

            if(dataThis == null)
                return -1;
            else if(dataOther == null)
                return 1;

            if(dataThis.exponent > dataOther.exponent)
                return 1;
            else if(dataThis.exponent < dataOther.exponent)
                return -1;
            else {
                if(dataThis.coefficient > dataOther.coefficient)
                    return 1;
                else if(dataThis.coefficient < dataOther.coefficient)
                    return -1;
                // otherwise the coefficients are equal
            }   // check the coefficient
            //otherwise they're equal continue to next iteration of loop
        } // loop
        return 0;

    }

    public String toString()
    {
        String output = "";
        Vector2D tempData;
        PolynomialIterator iter = iterator();
        while(iter.hasNext())
        {
            tempData = iter.next();

            if(tempData.coefficient == 0.0)
                output += "";
            else if(tempData.exponent == 1.0)
                output += tempData.coefficient + "x";
            else if (tempData.exponent == 0.0)
                output += tempData.coefficient + "";
            else
                output += tempData.coefficient + "x^" + tempData.exponent;

            if(iter.hasNext())
                output += " + ";
        }
        return output;
    } // toString of Polynomial

    @Override
    public PolynomialIterator iterator()
    {
        return new PolynomialIterator(this.head);
    }

    static class PolynomialIterator implements Iterator<Vector2D>
    {
        private Node currentNode;
        PolynomialIterator(Node node)
        {
            currentNode = node;
        } // constructor

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Vector2D next() {
            if(!this.hasNext())
                return null;
            Vector2D data = new Vector2D(currentNode.coefficient, currentNode.exponent);
            currentNode = currentNode.next;


            return data;
        } // next
    }

    public static class Vector2D
    {
        public double coefficient;
        public int exponent;

        public Vector2D(double c, int exp)
        {
            coefficient = c;
            exponent = exp;
        }}


    private String cleanString(String str)
    {
        return str.replaceAll("\\s+", " ").trim();
        // remove trailing/leading spaces
    }   // cleanString

    private void createLinkedList(String polynomial) throws InvalidPolynomialSyntax{
        String [] stringTokens; // hold coeff & exp
        polynomial = cleanString(polynomial);
        stringTokens = polynomial.split(" ");
        Node currentNode;

        if(stringTokens.length == 0 || stringTokens.length % 2 != 0)
            throw new InvalidPolynomialSyntax("Syntax Error: Disproportionate Number of Values");

        try {
            head = new Node(Double.parseDouble(stringTokens[0]), Integer.parseInt(stringTokens[1]));

        } catch(NumberFormatException ex) {
            throw new InvalidPolynomialSyntax("Syntax Error: Coefficient should be double, Exponents should be integers!");
        }

        currentNode = head;

        for(int counter = 2; counter < stringTokens.length - 1; counter+=2)
        {
            try {
                currentNode.next = new Node((Double.parseDouble(stringTokens[counter])), Integer.parseInt(stringTokens[counter+1]));
            } catch (NumberFormatException ex) {
                throw new InvalidPolynomialSyntax("Syntax Error: Coefficient should be double, Exponents should be integers!");
            }

            if(currentNode.exponent < currentNode.next.exponent)
            {
                throw new InvalidPolynomialSyntax("Syntax Error: Exponents should be in descending order!");
            }
            currentNode = currentNode.next;
        } // loop through tokens creating a new node each time
    } // create LinkedList


    /*
     * Example string: 5, 6, 3, 4, 2, 3, 1, 2, 0
     * FORMAT coeffl expl coeff2 exp2 coeff3 exp3
     * should be in descending order otherwise throw InvalidPolynomialException
     */
} // Polynomial

