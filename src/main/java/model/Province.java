package model;

public class Province {
    private String id;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Provinces{" +
                "provinceId=" + id +
                ", provinceName='" + province + '\'' +
                '}';
    }
}
