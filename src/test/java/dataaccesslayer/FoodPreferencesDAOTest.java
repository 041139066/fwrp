package dataaccesslayer;

import model.FoodPreference;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodPreferencesDAOTest {

    private FoodPreferencesDAO foodPreferencesDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        foodPreferencesDAO = new FoodPreferencesDAO();
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
    void testGetFoodPreferencesByUserId() throws SQLException {
        int userId = 1;
        int foodInventoryId = 2;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("food_inventory_id")).thenReturn(foodInventoryId);

        List<Integer> foodPreferences = foodPreferencesDAO.getFoodPreferencesByUserId(userId);

        assertNotNull(foodPreferences);
        assertEquals(1, foodPreferences.size());
        assertEquals(foodInventoryId, foodPreferences.get(0));
    }

    @Test
    void testAddFoodPreference() throws SQLException {
        FoodPreference foodPreference = new FoodPreference(1,2);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = foodPreferencesDAO.addFoodPreference(foodPreference);

        verify(mockPreparedStatement).setInt(1, foodPreference.getConsumerId());
        verify(mockPreparedStatement).setInt(2, foodPreference.getFoodInventoryId());
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testDeleteFoodPreference() throws SQLException {
        FoodPreference foodPreference = new FoodPreference(1, 2);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = foodPreferencesDAO.deleteFoodPreference(foodPreference);

        verify(mockPreparedStatement).setInt(1, foodPreference.getConsumerId());
        verify(mockPreparedStatement).setInt(2, foodPreference.getFoodInventoryId());
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }
}

