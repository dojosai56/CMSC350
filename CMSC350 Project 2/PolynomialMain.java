/**
 * Name: Sairam Soundararajan
 * Date: 2-9-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 2
 * Description: The PolynomialMain class allows the user to select the input file from the default directory by using an object of the JFileChooser class. It then
populates an ArrayList of objects of type Polynomial as it reads in the lines of the file.
 */
import javafx.scene.shape.Box;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class PolynomialMain {

    private JButton stronglyOrderedBtn, weaklyOrderedBtn, loadFileBtn;
    private JTextArea textArea;
    private JScrollPane textAreaScrollPane;
    private JFrame frame;
    private JPanel mainPanel, buttonPanel;
    private JTextField resultField;


    //internal variable
    private ArrayList<Polynomial> polynomialList;

    public PolynomialMain()
    {
        frame = new JFrame("Polynomial File Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(20,20);
        textArea.setEditable(false);
        textAreaScrollPane = new JScrollPane(textArea);

        resultField = new JTextField(30);
        //create buttons
        stronglyOrderedBtn = new JButton("Strongly Ordered");
        stronglyOrderedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = isStronglyOrdered(polynomialList);
                if(polynomialList == null)
                    return;
                if(result)
                    resultField.setText("File is Strongly Ordered.");
                else
                    resultField.setText("File is NOT Strongly Ordered.");
                //System.out.println("--- STRONGLY ORDER TEST ----");
                //System.out.println(isStronglyOrdered(polynomials));


            } //actionperformed
        }); // stronglyOrdered btn listener

        weaklyOrderedBtn = new JButton("Weakly Ordered");
        weaklyOrderedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean result = isWeaklyOrdered(polynomialList);
                if(polynomialList == null)
                    return;
                if(result)
                    resultField.setText("File is weakly Ordered");
                else
                    resultField.setText("File is NOT weakly Ordered");
                ///System.out.println("--- WEAKLY ORDER TEST ----");
                //System.out.println(isWeaklyOrdered(polynomials));

            } //actionperformed
        }); // stronglyOrdered btn listener

        loadFileBtn = new JButton("Load File (.txt)");
        loadFileBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(polynomialList != null)
                    polynomialList.clear();
                textArea.setText("");

                File file = openTextFile();
                polynomialList = readTextFile(file);
            }
        });{

    }

        buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(loadFileBtn);
        buttonPanel.add(stronglyOrderedBtn);
        buttonPanel.add(weaklyOrderedBtn);
        buttonPanel.add(resultField);


        mainPanel = new JPanel();

        mainPanel.add(textAreaScrollPane);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new PolynomialMain();


    } // main

    /*
     * Opens a text file from directory
     * return the file if successful Otherwise
     * return null
     */
    private static File openTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES","txt", "text");
        fileChooser.setFileFilter(filter);

        int chooserResult = fileChooser.showOpenDialog(null);
        if(chooserResult == JFileChooser.APPROVE_OPTION)
        {
            return fileChooser.getSelectedFile();
        }
        return null;
    } // openTextFile
    /*
     * read file line by line and create
     *  ArrayList(Polynomial)
     */
    private ArrayList<Polynomial> readTextFile(File file) {
        ArrayList<Polynomial> polynomials;
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            showErrorWindow("Error! Text file not loaded!!!");
            return null;
        } catch (NullPointerException ex) {
            showErrorWindow("Error! Text file not loaded!!!");
            return null;
        }

        polynomials = new ArrayList<Polynomial>();

        while (fileScanner.hasNextLine()) {
            try {
                polynomials.add(new Polynomial(fileScanner.nextLine())); // throws InvalidPolynomialSyntax
            } catch (InvalidPolynomialSyntax ex) {
                // show a JOptionPAne message with the error
                showErrorWindow(ex.getMessage());
                return null;
            }
            textArea.append(polynomials.get(polynomials.size() - 1).toString() + "\n");
        } // loop through each line of the file

        System.out.println("--- TEST COMPARE TO----");
        testCompareTo(polynomials);

        return polynomials;
    } // readTextFile

    private static boolean isStronglyOrdered(ArrayList<Polynomial> list)
    // use of compareTo polynomial
    {
        if(list == null || list.size() == 0) {
            showErrorWindow("Error! Text file not loaded!!!");
            return false;
        }
        return OrderedList.checkSorted(list);
    } //isStrongOrdered

    private static boolean isWeaklyOrdered(ArrayList<Polynomial> list)
    {
        if(list == null || list.size() == 0) {
            showErrorWindow("Error! Text file not loaded!!!");
            return false;
        }

        return OrderedList.checkSorted(list, new Comparator<Polynomial>() {


            @Override
            public int compare(Polynomial o1, Polynomial o2) {
                Polynomial.PolynomialIterator iter = o1.iterator();
                Polynomial.PolynomialIterator iterOther = o2.iterator();
                Polynomial.Vector2D dataThis, dataOther;

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
                    //otherwise they're equal continue to next iteration of loop
                } // loop
                return 0;

            }
        });
    }

    private static void testCompareTo(ArrayList<Polynomial> list) {
        for(int i =0; i < list.size() - 1;i++) {
            System.out.println(list.get(i).compareTo(list.get(i+1)));
        }
    } // testCompareTo
    private static void showErrorWindow(String error)
    {
        JOptionPane.showMessageDialog(null, error, "PolynomialSyntaxError", JOptionPane.ERROR_MESSAGE);
    }

} // PolynomialMain
