package Models;

import java.sql.Date;

public class Orders {
    String id;
    String customer_id;
    String status;
    String voucher_id;
    Date date;
    Integer price;

    public Orders(String id, String customer_id, String status, String voucher_id, Date date, Integer price) {
        this.id = id;
        this.customer_id = customer_id;
        this.status = status;
        this.voucher_id = voucher_id;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
