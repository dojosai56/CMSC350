/**
 * Name: Sairam Soundararajan
 * Date: 3-8-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 4
 * Description: The MainGUI class contains all the graphical components using Swing
 * and components from the DirectedGraph class in order to perform the necessary operations of the Directed Graph
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainGUI {

    private JButton hierarchyBtn, parenthesizedBtn, loadFileBtn;
    private JTextArea textArea;
    private JScrollPane textAreaScrollPane;
    private JFrame frame;
    private JPanel mainPanel, buttonPanel;

    private DirectedGraph<String> directedGraph;

    public MainGUI() {
        frame = new JFrame("Class Dependency Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create textArea
        textArea = new JTextArea(50, 80);
        textArea.setEditable(false);

        // create ScrollPane
        textAreaScrollPane = new JScrollPane(textArea);

        // create Buttons
        // loadFileButton
        loadFileBtn = new JButton("Load File");
        loadFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                textArea.setText("");

                File file = openTextFile();


                try {
                    if(file == null)
                        throw new FileNotFoundException();
                    directedGraph = new DirectedGraph<String>(file);
                    directedGraph.depthFirstSearch("ClassA");
                } catch (FileNotFoundException e) {
                    showErrorWindow("File Not Found.");
                }
            } // actionPerformed
        }); // laodFileButton

        // hierarchyBtn
        hierarchyBtn = new JButton("Hierarchy");
        hierarchyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                textArea.setText(directedGraph.hierarchyRep.toString()
                        + "\n" + directedGraph.getUnreachableVerts());
            } // actionPerformed

        }); // hierarchyBtn

        // parenthesizedBtn

        parenthesizedBtn = new JButton("Parenthesized");
        parenthesizedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                textArea.setText(directedGraph.paraRep.toString()
                        + "\n" + directedGraph.getUnreachableVerts());
            } // actionPerformed
        }); // hierarchyBtn

        buttonPanel = new JPanel();

        buttonPanel.add(loadFileBtn);
        buttonPanel.add(hierarchyBtn);
        buttonPanel.add(parenthesizedBtn);

        mainPanel = new JPanel();

        mainPanel.add(textAreaScrollPane);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);

        frame.pack();

        frame.setVisible(true);
    } // constructor

    /*
     * Opens a text file from directory return the File if successful Otherwise
     * return null
     *
     */
    private static File openTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);

        int chooserResult = fileChooser.showOpenDialog(null);

        if (chooserResult == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    } // openTextFile

    /*
     * Display JOptionPane for the given error message
     */
    private static void showErrorWindow(String error) {
        // JOptionPane errorPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE);
        // errorPane.setVisible(true);
        JOptionPane.showMessageDialog(null, error, "PolynomialSyntaxError", JOptionPane.ERROR_MESSAGE);
    } // showErrorWindow

    public static void main(String[] args) {
        new MainGUI();
    } // main

    /**
     * public static void main(String[] args) { DirectedGraph<String> graph1;
     *
     *
     * try { graph1 = new DirectedGraph<String>(new
     * File("C:\\Users\\Aleck\\Desktop\\sampleFile.txt"));
     * //System.out.println(graph1.printGraph()); graph1.depthFirstSearch("ClassA");
     * System.out.println(graph1.hierarchyRep); System.out.println(graph1.paraRep);
     * } catch (FileNotFoundException e) { System.out.println("File not found"); }
     *
     * } // main
     **/

} // mainGUI
