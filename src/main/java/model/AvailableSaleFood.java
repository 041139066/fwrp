package model;

public class AvailableSaleFood {

    private int id;
    private String description;
    private Double price;
    private int quantity;
    private String status;

    public AvailableSaleFood() {
    }

    public AvailableSaleFood(int id, String description, Double price, int quantity, String status) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 设置
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "AvailableSaleFood{id = " + id + ", description = " + description + ", price = " + price + ", quantity = " + quantity + ", status = " + status + "}";
    }
}
