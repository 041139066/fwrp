package dataaccesslayer;

import model.Province;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProvinceDAOTest {

    private ProvinceDAO provinceDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        provinceDAO = new ProvinceDAO();
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
    void testGetAllProvinces() throws SQLException {
        Province province = new Province();
        province.setId("1");
        province.setProvince("TestProvince");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn(province.getId());
        when(mockResultSet.getString("province")).thenReturn(province.getProvince());

        List<Province> provinces = provinceDAO.getAllProvinces();

        assertNotNull(provinces);
        assertEquals(1, provinces.size());
        assertEquals("1", provinces.get(0).getId());
        assertEquals("TestProvince", provinces.get(0).getProvince());
    }
}
