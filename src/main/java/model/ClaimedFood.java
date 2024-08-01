package model;

import java.time.LocalDateTime;

public class ClaimedFood {
    private int id;
    private String description;
    private Double price;
    private LocalDateTime claimDate;

    public ClaimedFood() {
    }

    public ClaimedFood(int id, String description, Double price, LocalDateTime claimDate) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.claimDate = claimDate;
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
     * @return claimDate
     */
    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    /**
     * 设置
     * @param claimDate
     */
    public void setClaimDate(LocalDateTime claimDate) {
        this.claimDate = claimDate;
    }

    public String toString() {
        return "ClaimedFood{id = " + id + ", description = " + description + ", price = " + price + ", claimDate = " + claimDate + "}";
    }
}
