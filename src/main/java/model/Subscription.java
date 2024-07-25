package model;

public class Subscription {

    private int consumerId;
    private String city;
    private String province;
    private String method;
    private boolean status;

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
                "customerId=" + consumerId +
                ", city='" + city + "'" +
                ", province='" + province + "'" +
                ", method='" + method + "'" +
                ", status=" + status +
                '}';
    }
}
