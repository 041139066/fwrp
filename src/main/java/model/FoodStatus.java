package model;

public enum FoodStatus {
    SALE("sale"),
    DONATION("donation");
    private final String status;

    FoodStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
