/**
 * Name: Sairam Soundararajan
 * Date: 1-24-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 1
 * Description: The Main class allows a user to input an expression
 * in either prefix to postfix and convert it to either postfix or prefix, correspondingly.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.geometry.Insets;

public class Main extends Application{

    private Button button, button1;
    private Label enterExpression_label, result_label;
    private TextField expression_textField, result_textField;
    private BorderPane borderpaneLayout;
    private HBox top_hbox, center_hbox, bottom_hbox;

    public static void main(String[] args) {
        launch(args);
    } // main

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Expression Converter");

        // initialize GUI elements for top hbox
        enterExpression_label = new Label("Enter Expression:");

        expression_textField = new TextField();

        // initialize GUI elements for center hbox
        // initialize prefix to postfix button
        button = new Button();
        button.setText("Prefix to Postfix");



        // initialize postfix to prefix
         button1 = new Button();
        button1.setText("Postfix to Prefix");

        // initialize GUI elements for bottom hbox
        result_label = new Label("Result:");

        result_textField = new TextField();
        result_textField.setEditable(false);
        result_textField.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY)));

        // initialize hbox's
        top_hbox = new HBox();
        center_hbox = new HBox();
        bottom_hbox = new HBox();

        // add GUI elements to the appropriate Hbox
        top_hbox.getChildren().addAll(enterExpression_label, expression_textField);
        center_hbox.getChildren().addAll(button, button1);
        bottom_hbox.getChildren().addAll(result_label, result_textField);

        // initialize the BorderPane
        borderpaneLayout = new BorderPane();
        borderpaneLayout.setTop(top_hbox);
        borderpaneLayout.setCenter(center_hbox);
        borderpaneLayout.setBottom(bottom_hbox);


        addButtonHandlers(); // method that adds event handlers to each


        Scene scene = new Scene(borderpaneLayout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();




    } // start

    private void addButtonHandlers() {
        // add event handler
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    String result = ExpressionConverter.preToPostfix(expression_textField.getText());
                    result_textField.setText(result);
                } catch (SyntaxErrorException ex) {
                    // display error message on window
                    showErrorAlertBox(ex);
                }
            } // handle
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String result = ExpressionConverter.postToPrefix(expression_textField.getText());
                    result_textField.setText(result);
                }catch (SyntaxErrorException ex) {
                    // display window showing error message
                    showErrorAlertBox(ex);
                }
            }
        });
    } // addButtonHandlers

    private static void showErrorAlertBox(SyntaxErrorException ex)
    {
       Alert errorAlert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
       errorAlert.showAndWait();
    }// show error alert box
}
