package Models;

public class Shopping_Cart {
    String productid;
    int amount;
    int price;
    String sizeproduct;

    public Shopping_Cart(String productid, int amount, int price, String sizeproduct) {
        this.productid = productid;
        this.amount = amount;
        this.price = price;
        this.sizeproduct = sizeproduct;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSizeproduct() {
        return sizeproduct;
    }

    public void setSizeproduct(String sizeproduct) {
        this.sizeproduct = sizeproduct;
    }
}
