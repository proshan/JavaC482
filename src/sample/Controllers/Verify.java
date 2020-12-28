package sample.Controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class Verify {
    public boolean minLessThanMax(int min, int max){
        if(min < max){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean invBetweenMinMax(int min, int max, int inv){
        if(inv <= max && inv >= min){
            return true;
        }
        else{
            return false;
        }
    }

    static public boolean confirmDeletion(String message){
        AtomicBoolean result = new AtomicBoolean(false);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Delete!");

        Label l = new Label();
        l.setText(message);
        l.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("Confirm");
        confirmButton.setMinWidth(100);
        confirmButton.setPadding(new Insets(5, 5,5,5));
        confirmButton.setOnAction(e-> {
           result.set(true);
            window.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setMinWidth(100);
        cancelButton.setPadding(new Insets(5, 5, 5, 5));
        cancelButton.setOnAction(e-> {
            result.set(false);
            window.close();
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(confirmButton, cancelButton);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(l, hBox);
        vBox.setMinWidth(200);
        vBox.setMinHeight(100);

        Scene s = new Scene(vBox);
        window.setScene(s);

        window.showAndWait();
        return result.get();
    }

    static public boolean isValidString(String str){
        boolean result = true;
        str = str.toLowerCase();
        for(int i = 0; i < str.length(); i++){
            if(!(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')){
                result = false;
            }
        }
        return result;
    }

    static public boolean isValidDouble(String str){
        try{
            double d = Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e){
           return false;
        }
    }
    static public boolean isValidInt(String str){
        try{
            int i = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

}
