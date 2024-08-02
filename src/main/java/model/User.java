package model;

import utilities.MyGson;

public class User {

    private int id;
    private String name;
    private String email;
    private UserType type;
    private boolean subscription;
    private String city;
    private String province;
    private MethodType method;// Using the UserType enum
    private String contactEmail;
    private String contactPhone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public String getTypeName() {
        return (type != null) ? type.name() : "";
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void setType(String type) throws IllegalArgumentException {
        try {
            this.type = UserType.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.method = null;
        }
    }

    public boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
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

    public MethodType getMethod() {
        return method;
    }

    public String getMethodName() {
        return (method != null) ? method.name() : "";
    }

    public void setMethod(String method) throws IllegalArgumentException {
        try {
            this.method = MethodType.valueOf(method);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.method = null;
        }
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + type +
                ", subscription=" + subscription +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", methodType=" + method +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }

    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}

