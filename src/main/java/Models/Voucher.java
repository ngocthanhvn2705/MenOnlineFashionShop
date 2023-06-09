package Models;

public class Voucher {
    String id;
    String type;
    Double value;
    Integer amount;
    Integer used;
    String status;

    public Voucher(String id, String type, Double value, Integer amount, Integer used, String status) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.amount = amount;
        this.used = used;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
