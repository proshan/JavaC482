package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Controllers.Controller;
import sample.View.MainView;

public class Main extends Application {

    Stage window;

    MainView mainView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Inventory");

        Label header = new Label("Inventory Management System");

        HBox mainHBox = new HBox();
        mainHBox.setSpacing(10);
        mainHBox.setPadding(new Insets(10, 10, 10, 10));

        mainView = new MainView();
        mainHBox.getChildren().addAll(mainView.getPartsView(), mainView.getProductsView());
        //add parts and products to mainHBox;

        Button exitButton = new Button("Exit");

        //exit functionality
        exitButton.setOnAction(e -> { Controller.exitProgram(window); });

        VBox mainVBox = new VBox();
        mainVBox.setSpacing(50);
        mainVBox.setPadding(new Insets(10, 10, 10, 10));

        mainVBox.getChildren().addAll(header, mainHBox, exitButton);

        Scene scene = new Scene(mainVBox);
        window.setScene(scene);
        window.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
