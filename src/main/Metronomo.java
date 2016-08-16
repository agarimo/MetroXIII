/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.mtm.BeasMetronome;
import main.mtm.Compas;

/**
 *
 * @author Agarimo
 */
public class Metronomo extends Application {
    
    public static Label label;

    @Override
    public void start(Stage primaryStage) {
        BeasMetronome metronomo = new BeasMetronome(200,Compas.c6by8);
        Button btn1 = new Button();
        btn1.setText("Start");
        btn1.setOnAction((ActionEvent event) -> {
            System.out.println("STARTING");
            metronomo.run();
        });

        Button btn2 = new Button();
        btn2.setText("Stop");
        btn2.setOnAction((ActionEvent event) -> {
            System.out.println("STOPING");
            metronomo.stop();
        });
        
        label = new Label();
        label.setText("WAITING");

        VBox root = new VBox();

        root.getChildren().add(btn1);
        root.getChildren().add(btn2);
        root.getChildren().add(label);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
