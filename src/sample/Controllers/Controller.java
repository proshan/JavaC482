package sample.Controllers;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.awt.*;

public class Controller {
    //exits the whole application
    public static void exitProgram(Stage window){
        window.close();
    }


    static public void alert(String alertTitle, String alertMessage){
        Stage window;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(alertTitle);

        Label l = new Label();
        l.setText(alertMessage);
        l.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().add(l);
        vBox.setMinWidth(200);
        vBox.setMinHeight(100);

        Scene s = new Scene(vBox);
        window.setScene(s);
        window.showAndWait();
    }
}
