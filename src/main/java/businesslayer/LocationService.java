package businesslayer;

import dataaccesslayer.CityDAO;
import dataaccesslayer.ProvinceDAO;
import model.City;
import model.Province;

import java.util.List;

public class LocationService {
    private final CityDAO cityDao;
    private final ProvinceDAO provinceDao;

    public LocationService() {
        cityDao = new CityDAO();
        provinceDao = new ProvinceDAO();
    }
    public List<City> getAllCities(){
        return cityDao.getAllCities();
    }
    public List<Province> getAllProvinces(){
        return provinceDao.getAllProvinces();
    }
}
