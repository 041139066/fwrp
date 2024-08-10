package model;

/**
 * The Province class represents a province with an ID and name.
 * It includes methods to get and set the province's ID and name, as well as a method to provide
 * a string representation of the Province object.
 */
public class Province {
    private String id;
    private String province;

    /**
     * Gets the ID of the province.
     *
     * @return The ID of the province.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the province.
     *
     * @param id The ID of the province.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the province.
     *
     * @return The name of the province.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the name of the province.
     *
     * @param province The name of the province.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Provides a string representation of the Province object.
     *
     * @return A string representation of the Province object, including the ID and name.
     */
    @Override
    public String toString() {
        return "Provinces{" +
                "provinceId=" + id +
                ", provinceName='" + province + '\'' +
                '}';
    }
}
