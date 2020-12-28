package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.Controller;
import sample.Controllers.PartController;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.util.Random;

public class ProductController {
    Stage window;
    Inventory inventory;

    TableView<Part> partTable;
    TableView<Part> associatedPartsTable;

    Label l;
    VBox v1;

    TextField inputName, inputStock, inputPrice, inputMax, inputMin;
    TextField inputId;

    Button addAssociatedPartButton, removeAssociatedButton, saveButton, cancelButton;

    ObservableList<Part> associatedPartsList;

    static Label err;
    String errors;

    public ProductController(){
        inventory = PartController.getInventory();
        associatedPartsList = FXCollections.observableArrayList();

        errors = "";
        err = new Label();
        err.setText("");
    }

    public void addModifyProduct(int paramId) {
        Product productToModify = inventory.lookupProduct(paramId);

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        TextField t = new TextField();

        Label l;
        if (paramId == -1) {
            window.setTitle("Add Product");
            l = new Label("Add Product");
        } else {
            window.setTitle("Modify Product");
            l = new Label("Modify Product");
        }

        partTable = new TableView<>();
        associatedPartsTable = new TableView<>();

        GridPane gri = new GridPane();

        gri.setPadding(new Insets(10, 10, 10, 10));

        gri.setVgap(10);
        gri.setHgap(10);

        Label id = new Label("ID");
        GridPane.setConstraints(id, 0, 0);


        inputId = new TextField();
        if (paramId == -1) {
            inputId.setPromptText("Auto-Generated");
        } else {
            inputId.setPromptText(String.valueOf(paramId));
        }
        inputId.setDisable(true);
        GridPane.setConstraints(inputId, 1, 0);

        Label name = new Label("Name");
        GridPane.setConstraints(name, 0, 1);

        inputName = new TextField();
        if (paramId != -1) {
            inputName.setText(productToModify.getName());
        }
        GridPane.setConstraints(inputName, 1, 1);

        Label stock = new Label("Inventory");
        GridPane.setConstraints(stock, 0, 2);

        inputStock = new TextField();
        if (paramId != -1) {
            inputStock.setText(String.valueOf(productToModify.getStock()));
        }
        GridPane.setConstraints(inputStock, 1, 2);

        Label price = new Label("Price/Cost");
        GridPane.setConstraints(price, 0, 3);

        inputPrice = new TextField();
        if (paramId != -1) {
            inputPrice.setText(String.valueOf(productToModify.getPrice()));
        }
        GridPane.setConstraints(inputPrice, 1, 3);

        Label max = new Label("Max");
        GridPane.setConstraints(max, 0, 4);

        inputMax = new TextField();
        if (paramId != -1) {
            inputMax.setText(String.valueOf(productToModify.getMax()));
        }
        GridPane.setConstraints(inputMax, 1, 4);

        Label min = new Label("Min");
        GridPane.setConstraints(min, 2, 4);

        inputMin = new TextField();
        if (paramId != -1) {
            inputMin.setText(String.valueOf(productToModify.getMin()));
        }
        GridPane.setConstraints(inputMin, 3, 4);

        gri.getChildren().addAll(id, inputId, name, inputName, stock, inputStock, price, inputPrice, max, inputMax, min, inputMin);

        gri.setPadding(new Insets(200, 10, 10, 10));

        v1 = new VBox();
        v1.setSpacing(10);
        v1.setPadding(new Insets(10, 10, 10, 10));
        v1.getChildren().addAll(l, gri, err);

        //right pane

        t.setMaxWidth(200);
        t.setPromptText("Search by Part ID or Name");

        t.setOnAction(e -> {
            if (t.getText().isEmpty()){
                partTable.setItems(inventory.getAllParts());
            } else if (isNumber(t.getText())) {
                ObservableList<Part> pa = FXCollections.observableArrayList();
                pa.add(inventory.lookupPart(Integer.parseInt(t.getText())));
                if(pa.get(0) == null){
                    Controller.alert("Error!", "Part not found!");
                }
                else{
                    partTable.setItems(pa);
                }
            } else{
                partTable.refresh();
                ObservableList<Part> par;
                par = inventory.lookupPart(t.getText());
                if(par.isEmpty()){
                    Controller.alert("Error!", "Part not found!");
                }
                else{
                    partTable.setItems(par);
                }
            }
        });

        TableColumn<Part, Integer> partIdColumn = new TableColumn<>("Part ID");
        partIdColumn.setMinWidth(120);
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Part, String> partNameColumn = new TableColumn<>("Part Name");
        partNameColumn.setMinWidth(120);
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> partStockColumn = new TableColumn<>("Inventory Level");
        partStockColumn.setMinWidth(120);
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Part, Double> partPriceColumn = new TableColumn<>("Price/Cost per Unit");
        partPriceColumn.setMinWidth(150);
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.getColumns().addAll(partIdColumn, partNameColumn, partStockColumn, partPriceColumn);
        partTable.setItems(inventory.getAllParts());

        TableColumn<Part, Integer> associatedPartIdColumn = new TableColumn<>("Part ID");
        associatedPartIdColumn.setMinWidth(120);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Part, String> associatedPartNameColumn = new TableColumn<>("Part Name");
        associatedPartNameColumn.setMinWidth(120);
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> associatedPartStockColumn = new TableColumn<>("Inventory Level");
        associatedPartStockColumn.setMinWidth(120);
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Part, Double> associatedPartPriceColumn = new TableColumn<>("Price/Cost per Unit");
        associatedPartPriceColumn.setMinWidth(150);
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        if(paramId != -1){
            associatedPartsList = productToModify.getAllAssociatedParts();
        }
        associatedPartsTable.getColumns().addAll(associatedPartIdColumn, associatedPartNameColumn, associatedPartStockColumn, associatedPartPriceColumn);
        if(paramId == -1){
            associatedPartsTable.setItems(null);
        }
        else{
            associatedPartsTable.setItems(productToModify.getAllAssociatedParts());
        }
        addAssociatedPartButton = new Button("Add");
        addAssociatedPartButton.setOnAction(e -> {
            boolean repeated = false;
            Part p = partTable.getSelectionModel().getSelectedItem();
            for(int i = 0; i<associatedPartsList.size(); i++){
                if(p == associatedPartsList.get(i)){
                    repeated = true;
                }
            }
            if(repeated == false){
                associatedPartsList.add(p);
                associatedPartsTable.setItems(associatedPartsList);
                associatedPartsTable.refresh();
            }
            else{
                Controller.alert("Part Addition Error", "Part Already Repeated!");
            }
        });

        removeAssociatedButton = new Button("Remove Associated Part");
        removeAssociatedButton.setOnAction(e -> {
           Part part = associatedPartsTable.getSelectionModel().getSelectedItem();
           if(Verify.confirmDeletion("Are you sure you want to remove this Part?")){
               associatedPartsList.remove(part);
               associatedPartsTable.refresh();
               Controller.alert("Success!", "Part removed Successfully!");
           }
           else{
               Controller.alert("Error!", "Part couldn't be removed successfully!");
           }
        });

        saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
           errors = "";
           validateInputFields();
           if(errors.length() > 1){
               err.setText(errors);
           }
           else{
               if(paramId == -1){
                   addProduct(associatedPartsList);
                   Controller.alert("Product Addition", "New Product Added Successfully!");
               }
               else{
                   modifyProduct(paramId, associatedPartsList);
                   Controller.alert("Product Modification", "Selected Product Modified Successfully!");
               }
               window.close();
           }
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            window.close();
        });

        HBox bottom = new HBox();
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.getChildren().addAll(saveButton, cancelButton);
        bottom.setAlignment(Pos.CENTER_RIGHT);

        VBox v2 = new VBox();
        v2.setSpacing(10);
        v2.setPadding(new Insets(10, 10, 10, 10));
        v2.getChildren().addAll(t, partTable, addAssociatedPartButton, associatedPartsTable,
                removeAssociatedButton, bottom);

        HBox mainPane = new HBox();
        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.getChildren().addAll(v1, v2);

        Scene s = new Scene(mainPane);
        window.setScene(s);
        window.showAndWait();

    }
    public boolean isNumber(String s){
        boolean result = true;
        for(int i = 0; i<s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                result = false;
            }
        }
        return result;
    }

    public void addProduct(ObservableList<Part> associatedParts){
        Product p = new Product(
                getAutoId(),
                inputName.getText(),
                Double.parseDouble(inputPrice.getText()),
                Integer.parseInt(inputStock.getText()),
                Integer.parseInt(inputMin.getText()),
                Integer.parseInt(inputMax.getText())
                );
        for(int i = 0; i<associatedParts.size(); i++){
            p.addAssociatedPart(associatedParts.get(i));
        }
        inventory.addProduct(p);
    }
    public void modifyProduct(int productId, ObservableList<Part> associatedParts){
        for(int i = 0; i<inventory.getAllProducts().size(); i++){
            if(productId == inventory.getAllProducts().get(i).getId()){
                Product p = inventory.getAllProducts().get(i);
                p.setName(inputName.getText());
                p.setPrice(Double.parseDouble(inputPrice.getText()));
                p.setStock(Integer.parseInt(inputStock.getText()));
                p.setMin(Integer.parseInt(inputMin.getText()));
                p.setMax(Integer.parseInt(inputMax.getText()));
                if(associatedParts != p.getAllAssociatedParts()){
                    for(int j = 0; j<associatedParts.size(); j++){
                        p.addAssociatedPart(associatedParts.get(j));
                    }
                }
            }
        }
    }
    public int getAutoId(){
        int i = getRandomNumber();
        while(isUniqueProductId(i) == false){
            i = getRandomNumber();
        }
        return i;
    }

    public boolean isUniqueProductId(int randomNumber){
        boolean result = true;
        for(int i = 0; i<inventory.getAllProducts().size(); i++){
            if(inventory.getAllProducts().get(i).getId() == randomNumber){
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
    public void checkEmptyField() {
        if (inputName.getText() == null || inputName.getText().isEmpty() || inputName.getText().equals("") ||
                inputPrice.getText() == null || inputPrice.getText().isEmpty() ||
                inputStock.getText() == null || inputStock.getText().isEmpty() ||
                inputMax.getText() == null || inputMax.getText().isEmpty() ||
                inputMin.getText() == null || inputMin.getText().isEmpty()){
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
    }
    static public void deleteErrors(){
        err.setText("");
    }
}
