package model;

import java.io.Serializable;
import utilities.MyGson;
import model.constants.MethodType;
import model.constants.UserType;

/**
 * The User class represents a user with various attributes such as ID, name, email,
 * user type, subscription status, city, province, and contact information.
 * It also includes methods to serialize the user object to JSON.
 */
public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private UserType type;
    private String password;
    private boolean subscription;
    private String city;
    private String province;
    private MethodType method;
    private String contactEmail;
    private String contactPhone;

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    /**
     * Returns the ID of the user.
     *
     * @return The user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The user ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the user.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the type of the user.
     *
     * @return The user's type.
     */
    public UserType getType() {
        return type;
    }

    /**
     * Returns the name of the user type.
     *
     * @return The name of the user type, or an empty string if the type is null.
     */
    public String getTypeName() {
        return (type != null) ? type.name() : "";
    }

    /**
     * Sets the type of the user.
     *
     * @param type The user type to set.
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Sets the user type based on a string value.
     *
     * @param type The user type as a string.
     * @throws IllegalArgumentException if the string does not match any UserType.
     */
    public void setType(String type) throws IllegalArgumentException {
        try {
            this.type = UserType.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.method = null;
        }
    }

    /**
     * Returns the subscription status of the user.
     *
     * @return true if the user is subscribed, false otherwise.
     */
    public boolean getSubscription() {
        return subscription;
    }

    /**
     * Sets the subscription status of the user.
     *
     * @param subscription The subscription status to set.
     */
    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    /**
     * Returns the city of the user.
     *
     * @return The user's city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     *
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the province of the user.
     *
     * @return The user's province.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province of the user.
     *
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Returns the contact method of the user.
     *
     * @return The contact method.
     */
    public MethodType getMethod() {
        return method;
    }

    /**
     * Returns the name of the contact method.
     *
     * @return The name of the contact method, or an empty string if the method is null.
     */
    public String getMethodName() {
        return (method != null) ? method.name() : "";
    }

    /**
     * Sets the contact method based on a string value.
     *
     * @param method The contact method as a string.
     * @throws IllegalArgumentException if the string does not match any MethodType.
     */
    public void setMethod(String method) throws IllegalArgumentException {
        try {
            this.method = MethodType.valueOf(method);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.method = null;
        }
    }

    /**
     * Sets the contact method of the user.
     *
     * @param method The contact method to set.
     */
    public void setMethod(MethodType method) {
        this.method = method;
    }

    /**
     * Returns the contact email of the user.
     *
     * @return The contact email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact email of the user.
     *
     * @param contactEmail The contact email to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Returns the contact phone number of the user.
     *
     * @return The contact phone number.
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Sets the contact phone number of the user.
     *
     * @param contactPhone The contact phone number to set.
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User.
     */
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

    /**
     * Converts the User object to a JSON string representation.
     *
     * @return A JSON string representation of the User.
     */
    public String toJson() {
        return MyGson.getMyGson().toJson(this);
    }
}
