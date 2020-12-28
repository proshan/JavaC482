package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Controllers.Controller;
import sample.Controllers.PartController;
import sample.Controllers.ProductController;
import sample.Controllers.Verify;
import sample.Model.InHouse;
import sample.Model.Outsourced;
import sample.Model.Part;
import sample.Model.Product;

public class MainView {

    PartController addPartView;

    ProductController productController;


    static TableView<Part> partTable;
    TableView<Product> productTable;

    public MainView(){
        addPartView = new PartController();
        productController = new ProductController();
    }

    public VBox getPartsView(){
        //creating hBox for parts and search
        Label partTitle = new Label("Parts");
        partTitle.setPadding(new Insets(10, 200, 10, 10));

        TextField partSearch = new TextField();
        partSearch.setMinWidth(150);
        partSearch.setPromptText("Search by Part ID or Name");

        partSearch.setOnAction((e -> {
                if (partSearch.getText().isEmpty()) {
                    partTable.setItems(addPartView.getInventory().getAllParts());
                }
                else if (isNumber(partSearch.getText())) {
                    ObservableList<Part> p = FXCollections.observableArrayList();
                    p.add(addPartView.getInventory().lookupPart(Integer.parseInt(partSearch.getText())));
                    if(p.isEmpty()){
                        Controller.alert("Error!", "Part not found!");
                    }
                    else{
                        partTable.setItems(p);
                    }
                } else{
                    partTable.refresh();
                    ObservableList<Part> par;
                    par = addPartView.getInventory().lookupPart(partSearch.getText());
                    if(par.isEmpty()){
                        Controller.alert("Error!", "Part not found!");
                    }
                    else{
                        partTable.setItems(par);
                    }
                }
        }));

        HBox partAndSearch = new HBox();
        partAndSearch.getChildren().addAll(partTitle, partSearch);

        //creating TableView
        partTable = new TableView<>();

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

        //adding dummy data
        addPartView.getInventory().addPart(new InHouse(2, "Chocolate", 2.3, 23, 10, 20, 550));
        addPartView.getInventory().addPart(new InHouse(56, "Chocolate", 29.99, 20, 1, 5, 50));
        addPartView.getInventory().addPart(new Outsourced(32, "Pingpong", 23.4, 45, 1, 2, "Whatsapp"));

        partTable.refresh();
        partTable.setItems(addPartView.getInventory().getAllParts());
        partTable.getColumns().addAll(partIdColumn, partNameColumn, partStockColumn, partPriceColumn);

        Button partAddButton = new Button("Add");
        partAddButton.setOnAction(e -> {
           addPartView.addPartView(null, null, null, null, null, null,
                   null, "");
           partTable.refresh();
        });

        Button partModifyButton = new Button("Modify");
        partModifyButton.setOnAction(e -> {
            PartController.deleteErrors();
           modifyPart();
           partTable.refresh();
        });
        Button partDeleteButton = new Button("Delete");
        partDeleteButton.setOnAction(e -> {
           deletePart(Verify.confirmDeletion("Are you sure you want to delete the Part?"));
        });

        HBox partButtonsBox = new HBox();
        partButtonsBox.setSpacing(10);
        partButtonsBox.setPadding(new Insets(10, 10, 10, 10));

        partButtonsBox.getChildren().addAll(partAddButton, partModifyButton, partDeleteButton);

        //main vBox
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        vBox.getChildren().addAll(partAndSearch, partTable, partButtonsBox);
        return vBox;
    }
    public VBox getProductsView(){
        //creating hBox for parts and search
        Label productTitle = new Label("Product");
        productTitle.setPadding(new Insets(10, 200, 10, 10));

        TextField productSearch = new TextField();
        productSearch.setOnAction(e -> {
            if (productSearch.getText().isEmpty()) {
                productTable.setItems(addPartView.getInventory().getAllProducts());
            }
            else if (isNumber(productSearch.getText())) {
                ObservableList<Product> pr = FXCollections.observableArrayList();
                pr.add(addPartView.getInventory().lookupProduct(Integer.parseInt(productSearch.getText())));
                if(pr.get(0) == null){
                    Controller.alert("Product Search Error!", "Product Not Found!");
                }
                else{
                    productTable.setItems(pr);
                }
            } else{
                productTable.refresh();
                ObservableList<Product> p;
                p = addPartView.getInventory().lookupProduct(productSearch.getText());
                if(p.isEmpty()){
                    Controller.alert("Product Search Error!", "Product Not Found!");
                }
                else{
                    productTable.setItems(p);
                }
            }
        });
        productSearch.setMinWidth(200);
        productSearch.setPromptText("Search by Product ID or Name");

        HBox productAndSearch = new HBox();
        productAndSearch.getChildren().addAll(productTitle, productSearch);

        //creating TableView
        productTable = new TableView<>();

        TableColumn<Product, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setMinWidth(120);
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setMinWidth(120);
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> productStockColumn = new TableColumn<>("Inventory Level");
        productStockColumn.setMinWidth(120);
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Double> productPriceColumn = new TableColumn<>("Price/Cost per Unit");
        productPriceColumn.setMinWidth(150);
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        Product p1 = new Product(1, "Whas", 2.3, 34, 2, 1);
        Product p2 = new Product(2, "Chilli", 43.4, 54, 6, 1);
        Product p3 = new Product(3, "Rumpum", 23.1, 5, 60, 11);
        p1.addAssociatedPart(addPartView.getInventory().getAllParts().get(0));
        p1.addAssociatedPart(addPartView.getInventory().getAllParts().get(1));

        addPartView.getInventory().addProduct(p1);
        addPartView.getInventory().addProduct(p2);
        addPartView.getInventory().addProduct(p3);


        productTable.getColumns().addAll(productIdColumn, productNameColumn, productStockColumn, productPriceColumn);
        productTable.setItems(addPartView.getInventory().getAllProducts());

        Button productAddButton = new Button("Add");
        productAddButton.setOnAction(e -> {
            productController.addModifyProduct(-1);
            productTable.refresh();
        });

        Button productModifyButton = new Button("Modify");
        productModifyButton.setOnAction(e -> {
            Product p = productTable.getSelectionModel().getSelectedItem();
            productController.addModifyProduct(p.getId());
            productTable.refresh();
        });

        Button productDeleteButton = new Button("Delete");
        productDeleteButton.setOnAction(e -> {
            deleteProduct(Verify.confirmDeletion("Are you sure you want to delete the Product?"));
        });

        HBox productButtonsBox = new HBox();
        productButtonsBox.setSpacing(10);
        productButtonsBox.setPadding(new Insets(10, 10, 10, 10));

        productButtonsBox.getChildren().addAll(productAddButton, productModifyButton, productDeleteButton);

        //main vBox
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        vBox.getChildren().addAll(productAndSearch, productTable, productButtonsBox);
        return vBox;
    }

    public void deletePart(boolean b){
        Part p;
        p = partTable.getSelectionModel().getSelectedItem();
        if(b){
            if(addPartView.getInventory().deletePart(p)){
                Controller.alert("Success!", "The selected Part was deleted successfully!");
            }
        }
        else{
            Controller.alert("Error!", "The selected Part was not deleted successfully!");
        }
    }

    static public boolean isNumber(String s){
        boolean result = true;
        for(int i = 0; i<s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                result = false;
            }
        }
        return result;
    }


    public void modifyPart(){
        Part p;
        p = partTable.getSelectionModel().getSelectedItem();
        if(p instanceof InHouse){
            addPartView.addPartView(p.getId(), p.getName(), p.getStock(), p.getPrice(), p.getMax(), p.getMin(), ((InHouse) p).getMachineId(), "");
        }
        else if(p instanceof Outsourced){
            addPartView.addPartView(p.getId(), p.getName(), p.getStock(), p.getPrice(), p.getMax(), p.getMin(), null, ((Outsourced) p).getCompanyName());
        }
    }

    static public TableView<Part> getPartTable(){
        return partTable;
    }

    public void deleteProduct(boolean b){
        Product p = productTable.getSelectionModel().getSelectedItem();
        for(int i = 0; i<addPartView.getInventory().getAllProducts().size(); i++){
            if(p.getId() == addPartView.getInventory().getAllProducts().get(i).getId() && b){
                //checking if the product has associated parts to it.
                if(p.getAllAssociatedParts().isEmpty()) {
                    if (addPartView.getInventory().deleteProduct(p)) {
                        Controller.alert("Product Deleteion", "Successfully Deleted the Product!");
                        productTable.refresh();
                    } else {
                        Controller.alert("Product Deletion", "Couldn't delete the Product Successfully!");
                    }
                }
                else{
                    Controller.alert("Exception", "Couldn't delete the product!\nThe product has associated Parts.");
                }
            }
        }
    }


}


