package Models;

public class Order_Items {
    String id;
    String product_id;
    Integer quantity;
    Integer price;
    String size;
    String product_name;

    public Order_Items(String id, String product_id, Integer quantity, Integer price, String size, String product_name) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}