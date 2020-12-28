package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    //Parts ObservableList
    private ObservableList<Part> parts = FXCollections.observableArrayList();

    //Products ObservableList
    private ObservableList<Product> products =  FXCollections.observableArrayList();

    public void addPart(Part newPart){
        if(newPart != null){
            parts.add(newPart);
        }
    }

    public void addProduct(Product newProduct){
        if(newProduct != null){
            products.add(newProduct);
        }
    }
    public Part lookupPart(int partId){
        for(int i = 0; i<parts.size(); i++){
            if(parts.get(i).getId() == partId){
                return parts.get(i);
            }
        }
        return null;
    }
    public Product lookupProduct(int productId){
        for(int i = 0; i<products.size(); i++){
            if(getAllProducts().get(i).getId() == productId){
                return products.get(i);
            }
        }
        return null;
    }
    public ObservableList<Part> lookupPart(String partName){
        //using .contains() to find the matching values.
        partName = partName.toLowerCase();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for(int i = 0; i<getAllParts().size(); i++){
            if(getAllParts().get(i).getName().toLowerCase().contains(partName)){
                matchingParts.add(this.getAllParts().get(i));
            }
        }
        return matchingParts;
    }

    public ObservableList<Product> lookupProduct(String productName){
        productName = productName.toLowerCase();
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for(int i = 0; i<getAllProducts().size(); i++){
            if(getAllProducts().get(i).getName().toLowerCase().contains(productName)){
                matchingProducts.add(this.getAllProducts().get(i));
            }
        }
        return matchingProducts;
    }
    public void updatePart(int index, Part selectedPart){
        for(int i = 0; i<getAllParts().size(); i++){
            if(parts.get(i).getId() == index){
                parts.set(index, selectedPart);
            }
        }
    }
    public void updateProduct(int index, Product newProduct){
        for(int i = 0; i<getAllProducts().size(); i++){
            if(products.get(i).getId() == index){
                products.set(index, newProduct);
            }
        }
    }
    public boolean deletePart(Part selectedPart){
        if(parts.remove(selectedPart)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deleteProduct(Product selectedProduct){
        if(products.remove(selectedProduct)){
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<Part> getAllParts(){
        return parts;
    }
    public ObservableList<Product> getAllProducts(){
        return products;
    }
}
