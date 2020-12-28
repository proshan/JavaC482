package sample.Model;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return this.companyName;
    }
}
