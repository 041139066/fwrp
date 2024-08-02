package model;

public class City {
    private String city;
    private String provinceId;

    // Getter for city
    public String getCity() {
        return city;
    }

    // Setter for city
    public void setCity(String city) {
        this.city = city;
    }

    // Getter for provinceId
    public String getProvinceId() {
        return provinceId;
    }

    // Setter for provinceId
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City{" +
                "city='" + city + "'" +
                ", provinceId='" + provinceId + "'" +
                '}';
    }
}

