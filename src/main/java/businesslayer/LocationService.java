package businesslayer;

import dataaccesslayer.CityDAO;
import dataaccesslayer.ProvinceDAO;
import model.City;
import model.Province;

import java.util.List;

/**
 * Provides services related to location management, including retrieving cities and provinces.
 */
public class LocationService {
    private final CityDAO cityDao;
    private final ProvinceDAO provinceDao;

    /**
     * Initializes a new instance of the LocationService class.
     * Creates instances of CityDAO and ProvinceDAO for data access operations.
     */
    public LocationService() {
        cityDao = new CityDAO();
        provinceDao = new ProvinceDAO();
    }

    /**
     * Retrieves a list of all cities.
     *
     * @return A list of {@link City} objects representing all cities.
     */
    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }

    /**
     * Retrieves a list of all provinces.
     *
     * @return A list of {@link Province} objects representing all provinces.
     */
    public List<Province> getAllProvinces() {
        return provinceDao.getAllProvinces();
    }
}
