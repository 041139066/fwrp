package model;

/**
 * The City class represents a city within a province.
 * It includes the name of the city and the ID of the province to which it belongs.
 */
public class City {
    private String city;
    private String provinceId;

    /**
     * Gets the name of the city.
     *
     * @return The name of the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the name of the city.
     *
     * @param city The name of the city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the ID of the province to which the city belongs.
     *
     * @return The ID of the province.
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * Sets the ID of the province to which the city belongs.
     *
     * @param provinceId The ID of the province.
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * Returns a string representation of the City instance.
     *
     * @return A string representation of the City instance.
     */
    @Override
    public String toString() {
        return "City{" +
                "city='" + city + "'" +
                ", provinceId='" + provinceId + "'" +
                '}';
    }
}
