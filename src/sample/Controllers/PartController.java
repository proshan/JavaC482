package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;
import sample.Model.Part;
import sample.View.MainView;
import javafx.event.ActionEvent;

import java.beans.EventHandler;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.lang.*;
import java.util.*;


public class PartController {
    Stage window;

    int auto_id = 0;

    TextField inputName, inputStock, inputPrice, inputMax, inputMin, inputLastTextField;
    RadioButton inHouseButton, outsourcedButton;

    static Label err;
    String errors;

    static private Inventory inventory;

    public PartController(){
        inventory = new Inventory();

        inputName = new TextField();
        inputPrice = new TextField();
        inputMax = new TextField();
        inputMin = new TextField();
        inputStock  =new TextField();
        inputLastTextField = new TextField();

        errors = "";
        err = new Label();
        err.setText("");
    }
    public void addPartView(Integer parameterId, String parameterName, Integer parameterStock, Double parameterPrice, Integer parameterMax, Integer parameterMin,
                            Integer parameterMachineId, String parameterCompanyName){
        window = new Stage();
        window.setTitle("Add/Modify Part");

        window.initModality(Modality.APPLICATION_MODAL);
        Label top = new Label();
        if(parameterName == null){
            top.setText("Add Part");
        }
        else{
            top.setText("Modify Part");
        }
        top.setMinWidth(150);

        ToggleGroup tg = new ToggleGroup();
        inHouseButton = new RadioButton("In-House");
        inHouseButton.setToggleGroup(tg);
        inHouseButton.setPrefWidth(200);

        outsourcedButton = new RadioButton("Outsourced");
        outsourcedButton.setToggleGroup(tg);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(top, inHouseButton, outsourcedButton);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.setVgap(10);
        grid.setHgap(10);

        Label id = new Label("ID");
        GridPane.setConstraints(id, 0, 0);


        TextField inputId = new TextField();
        if(parameterId == null){
            inputId.setPromptText("Auto Gen- Disabled");
        }
        else{
            inputId.setPromptText(parameterId.toString());
        }
        inputId.setDisable(true);
        GridPane.setConstraints(inputId, 1, 0);

        Label name = new Label("Name");
        GridPane.setConstraints(name, 0, 1);


        inputName.setText(parameterName);
        GridPane.setConstraints(inputName, 1, 1);

        Label stock = new Label("Inventory");
        GridPane.setConstraints(stock, 0, 2);

        inputStock.setText((parameterStock == null) ? "" : String.valueOf(parameterStock));
        GridPane.setConstraints(inputStock, 1, 2);

        Label price = new Label("Price/Cost");
        GridPane.setConstraints(price, 0, 3);

        inputPrice.setText((parameterPrice == null) ? "" : String.valueOf(parameterPrice));
        GridPane.setConstraints(inputPrice, 1, 3);

        Label max = new Label("Max");
        GridPane.setConstraints(max, 0, 4);

        inputMax.setText((parameterMax == null) ? "" : String.valueOf(parameterMax));
        GridPane.setConstraints(inputMax, 1, 4);

        Label min = new Label("Min");
        GridPane.setConstraints(min, 2, 4);

        inputMin.setText((parameterMin == null) ? "" : String.valueOf(parameterMin));
        GridPane.setConstraints(inputMin, 3, 4);

        Label lastTextField = new Label();
        GridPane.setConstraints(lastTextField, 0, 5);

        GridPane.setConstraints(inputLastTextField, 1, 5);

        if(parameterCompanyName == "" && parameterMachineId != null){
            //machineId
            inHouseButton.setSelected(true);
            lastTextField.setText("Machine ID");
        }
        else if(parameterCompanyName != null && parameterMachineId == null){
            //companyName
            outsourcedButton.setSelected(true);
            lastTextField.setText("Company Name");
        }

        if(parameterMachineId != null){
            inputLastTextField.setText(String.valueOf(parameterMachineId));
        }
        else if(parameterCompanyName.equals("")){
            inputLastTextField.setText(parameterCompanyName);
        }

        inHouseButton.setOnAction(e -> {
            lastTextField.setText("Machine ID");
        });

        outsourcedButton.setOnAction(e -> {
           lastTextField.setText("Company Name");
        });

        grid.getChildren().addAll(id, inputId, name, inputName, stock, inputStock, price, inputPrice, max, inputMax, min, inputMin, lastTextField, inputLastTextField);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e ->{
            errors = "";
            validateInputFields();
            if(errors.length() > 1){
                err.setText(errors);
            }
            else{
                if (parameterId == null) {
                    this.addPart();
                } else {
                    this.updatePart(parameterId);
                    MainView.getPartTable().refresh();
                }
                window.close();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
           window.close();
        });

        HBox buttonBar = new HBox();
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setSpacing(10);

        buttonBar.getChildren().addAll(saveButton, cancelButton);

        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        mainBox.setSpacing(10);
        mainBox.getChildren().addAll(hBox, grid, buttonBar, err);
        Scene scene = new Scene(mainBox, 600, 600);
        window.setScene(scene);
        window.showAndWait();
    }
    public void addPart(){
        if(inHouseButton.isSelected()){
                inventory.addPart(new InHouse(
                        getAutoId(),
                        inputName.getText(),
                        Double.parseDouble(inputPrice.getText()),
                        Integer.parseInt(inputStock.getText()),
                        Integer.parseInt(inputMin.getText()),
                        Integer.parseInt(inputMax.getText()),
                        Integer.parseInt(inputLastTextField.getText())));

        }
        else if (outsourcedButton.isSelected()){
            inventory.addPart(new Outsourced(
                    getAutoId(),
                    inputName.getText(),
                    Double.parseDouble(inputPrice.getText()),
                    Integer.parseInt(inputStock.getText()),
                    Integer.parseInt(inputMin.getText()),
                    Integer.parseInt(inputMax.getText()),
                    inputLastTextField.getText()
            ));
        }
        Controller.alert("Success!", "Part added successfully!");
    }

    static public Inventory getInventory(){
        return inventory;
    }


    public int getAutoId(){
        int i = getRandomNumber();
        while(isUniquePartId(i) == false){
            i = getRandomNumber();
        }
        return i;
    }

    public boolean isUniquePartId(int randomNumber){
        boolean result = true;
        for(int i = 0; i<inventory.getAllParts().size(); i++){
            if(inventory.getAllParts().get(i).getId() == randomNumber){
                result = false;
            }
        }
        return result;
    }

    public int getRandomNumber(){
        int randomId = 0;
        Random rand = new Random();
        randomId = rand.nextInt(500);
        return randomId;
    }

    public void updatePart(Integer paramId){
        Part p = new Part();
        for(int i = 0; i<inventory.getAllParts().size(); i++){
            if(paramId == inventory.getAllParts().get(i).getId()){
                p = inventory.getAllParts().get(i);
                p.setName(inputName.getText());
                p.setPrice(Double.parseDouble(inputPrice.getText()));
                p.setStock(Integer.parseInt(inputStock.getText()));
                p.setMin(Integer.parseInt(inputMin.getText()));
                p.setMax(Integer.parseInt(inputMax.getText()));
                if(inHouseButton.isSelected()){
                    ((InHouse) p).setMachineId(Integer.parseInt(inputLastTextField.getText()));
                }
                else if(outsourcedButton.isSelected()){
                    ((Outsourced) p).setCompanyName(inputLastTextField.getText());
                }
            }
        }
    }

/*    static public void deleteErrors(){
        emptyFieldErrror.setText("");
        inputNameError.setText("");
        inputPriceError.setText("");
        inputStockError.setText("");
        inputMaxError.setText("");
        inputMinError.setText("");
        inputLastTextFieldError.setText("");
    }*/

    public void checkInputName(){
        if(!Verify.isValidString(inputName.getText())){
            errors = errors.concat("* Entered Name is not a valid name!\n");
        }
    }
    public void checkInputPrice(){
        if(!Verify.isValidDouble(inputPrice.getText())) {
            errors = errors.concat("* The Price should be of double data type!\n");
        }
    }
    public void checkStock(){
        if(!Verify.isValidInt(inputStock.getText())) {
            errors = errors.concat("* The inventory is not valid. Please enter an Integer.\n");
        }
    }
    public void checkMax(){
        if(!Verify.isValidInt(inputMax.getText())) {
            errors = errors.concat("* Max is not a valid number.\n");
        }
    }
    public void checkMin(){
        if(!Verify.isValidInt(inputMin.getText())) {
            errors = errors.concat("* Min is not a valid number.\n");
        }
    }
    public void checkLastField(){
        if(inHouseButton.isSelected()){
            if(!Verify.isValidInt(inputLastTextField.getText())){
                errors = errors.concat("* The machine Id should be an integer!\n");
            }
        }
        else if(outsourcedButton.isSelected()){
            if(!Verify.isValidString(inputLastTextField.getText())){
                errors = errors.concat("* The company name should be string type!\n");
            }
        }
    }

    public void checkEmptyField() {
        if (inputName.getText() == null || inputName.getText().isEmpty() || inputName.getText().equals("") ||
                inputPrice.getText() == null || inputPrice.getText().isEmpty() ||
                inputStock.getText() == null || inputStock.getText().isEmpty() ||
                inputMax.getText() == null || inputMax.getText().isEmpty() ||
                inputMin.getText() == null || inputMin.getText().isEmpty() ||
                inputLastTextField.getText() == null || inputLastTextField.getText().isEmpty()) {
            errors = errors.concat("* Fields cannot be left empty!\n");
        }
    }

    public void maxGreaterThanMin(){
        if(Verify.isValidInt(inputMax.getText()) && Verify.isValidInt(inputMin.getText())){
            if(Integer.parseInt(inputMax.getText()) < Integer.parseInt(inputMin.getText())){
                errors = errors.concat("* Max value must be greater than min value.\n");
            }
        }
    }

    public void inventoryValid(){
        if(Verify.isValidInt(inputMax.getText()) && Verify.isValidInt(inputMin.getText()) &&
        Verify.isValidInt(inputStock.getText())){
            if(Integer.parseInt(inputStock.getText()) < Integer.parseInt(inputMin.getText()) ||
            Integer.parseInt(inputStock.getText()) > Integer.parseInt(inputMax.getText())){
                errors = errors.concat("* Inventory value must be between max and min. \n");
            }
        }
    }

    public void validateInputFields(){
        checkEmptyField();
        checkInputName();
        checkStock();
        checkInputPrice();
        checkMax();
        checkMin();
        maxGreaterThanMin();
        inventoryValid();
        checkLastField();
    }

    static public void deleteErrors(){
        err.setText("");
    }

}
