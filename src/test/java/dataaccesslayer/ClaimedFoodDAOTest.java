package dataaccesslayer;

import model.ClaimedFood;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClaimedFoodDAOTest {

    private ClaimedFoodDAO claimedFoodDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        claimedFoodDAO = new ClaimedFoodDAO();
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
    void testClaimFood_Success() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("quantity")).thenReturn(10);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> claimedFoodDAO.claimFood(foodInventoryId, need, userId));

        verify(mockPreparedStatement, atLeastOnce()).executeUpdate();
        verify(mockConnection).commit();
    }

    @Test
    void testClaimFood_InsufficientQuantity() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("quantity")).thenReturn(3);

        assertThrows(RuntimeException.class, () -> claimedFoodDAO.claimFood(foodInventoryId, need, userId));

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockConnection, never()).commit();
    }

    @Test
    void testClaimFood_FoodInventoryNotFound() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        assertThrows(RuntimeException.class, () -> claimedFoodDAO.claimFood(foodInventoryId, need, userId));

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockConnection, never()).commit();
    }

    @Test
    void testGetAllClaimedFoodByCharitableId() throws SQLException {
        int charitableId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("charitable_id")).thenReturn(charitableId);
        when(mockResultSet.getInt("food_inventory_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Test Food");
        when(mockResultSet.getInt("quantity")).thenReturn(5);
        when(mockResultSet.getTimestamp("claim_date")).thenReturn(Timestamp.valueOf("2022-01-01 10:00:00"));

        List<ClaimedFood> result = claimedFoodDAO.getAllClaimedFoodByCharitableId(charitableId);

        assertNotNull(result);
        assertEquals(1, result.size());
        ClaimedFood claimedFood = result.get(0);
        assertEquals(1, claimedFood.getId());
        assertEquals(charitableId, claimedFood.getCharitableId());
        assertEquals(1, claimedFood.getFoodInventoryId());
        assertEquals("Test Food", claimedFood.getFoodInventoryName());
        assertEquals(5, claimedFood.getQuantity());
        assertEquals("2022-01-01T10:00", claimedFood.getClaimDate().toString());
    }
}
