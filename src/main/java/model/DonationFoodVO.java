package model;

public class DonationFoodVO {
    private int id;
    private String description;
    private Double price;
    private int quantity;
    private String status;

    public DonationFoodVO() {
    }

    public DonationFoodVO(int id, String description, Double price, int quantity, String status) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DonationFoodVO{id = " + id + ", description = " + description + ", price = " + price + ", quantity = " + quantity + ", status = " + status + "}";
    }
}
