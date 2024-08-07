package dataaccesslayer;

import model.City;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CityDAOTest {

    private CityDAO cityDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        cityDAO = new CityDAO();
        closeable = MockitoAnnotations.openMocks(this);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock the Database.getConnection() to return our mock connection
        mockedDatabase = mockStatic(Database.class);
        mockedDatabase.when(Database::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mockedDatabase != null) {
            mockedDatabase.close();
        }
        closeable.close();
    }

    @Test
    void testGetAllCities() throws SQLException {
        City city = new City();
        city.setCity("TestCity");
        city.setProvinceId("TestProvince");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("city")).thenReturn(city.getCity());
        when(mockResultSet.getString("province_id")).thenReturn(city.getProvinceId());

        List<City> cities = cityDAO.getAllCities();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("TestCity", cities.get(0).getCity());
        assertEquals("TestProvince", cities.get(0).getProvinceId());
    }

    @Test
    void testGetCitiesByProvinceId() throws SQLException {
        City city = new City();
        city.setCity("TestCity");
        city.setProvinceId("TestProvince");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("city")).thenReturn(city.getCity());
        when(mockResultSet.getString("province_id")).thenReturn(city.getProvinceId());

        List<City> cities = cityDAO.getCitiesByProvinceId("TestProvince");

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("TestCity", cities.get(0).getCity());
        assertEquals("TestProvince", cities.get(0).getProvinceId());
    }

    @Test
    void testGetCityByIds() throws SQLException {
        City city = new City();
        city.setCity("TestCity");
        city.setProvinceId("TestProvince");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("city")).thenReturn(city.getCity());
        when(mockResultSet.getString("province_id")).thenReturn(city.getProvinceId());

        City resultCity = cityDAO.getCityByIds("TestCity", "TestProvince");

        assertNotNull(resultCity);
        assertEquals("TestCity", resultCity.getCity());
        assertEquals("TestProvince", resultCity.getProvinceId());
    }

    @Test
    void testGetCityByIdsNotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        City resultCity = cityDAO.getCityByIds("NonExistentCity", "NonExistentProvince");

        assertNull(resultCity);
    }
}
