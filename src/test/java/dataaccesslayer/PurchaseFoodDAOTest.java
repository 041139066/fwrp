package dataaccesslayer;

import model.PurchasedFood;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PurchaseFoodDAOTest {

    private PurchaseFoodDAO purchaseFoodDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        purchaseFoodDAO = new PurchaseFoodDAO();
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
    void testPurchaseFood_Success() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;
        double cost = 10.0;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("quantity")).thenReturn(10);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> purchaseFoodDAO.purchaseFood(userId, foodInventoryId, need, cost));

        verify(mockPreparedStatement, atLeastOnce()).executeUpdate();
        verify(mockConnection).commit();
    }

    @Test
    void testPurchaseFood_InsufficientQuantity() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;
        double cost = 10.0;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("quantity")).thenReturn(3);

        assertThrows(RuntimeException.class, () -> purchaseFoodDAO.purchaseFood(userId, foodInventoryId, need, cost));

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockConnection, never()).commit();
    }

    @Test
    void testPurchaseFood_FoodInventoryNotFound() throws SQLException {
        int foodInventoryId = 1;
        int need = 5;
        int userId = 1;
        double cost = 10.0;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        assertThrows(RuntimeException.class, () -> purchaseFoodDAO.purchaseFood(userId, foodInventoryId, need, cost));

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockConnection, never()).commit();
    }

    @Test
    void testGetAllPurchasedFoodByConsumerId() throws SQLException {
        int consumerId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("consumer_id")).thenReturn(consumerId);
        when(mockResultSet.getInt("food_inventory_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Test Food");
        when(mockResultSet.getInt("quantity")).thenReturn(5);
        when(mockResultSet.getDouble("cost")).thenReturn(10.0);
        when(mockResultSet.getTimestamp("purchase_date")).thenReturn(Timestamp.valueOf("2022-01-01 10:00:00"));

        List<PurchasedFood> result = purchaseFoodDAO.getAllPurchasedFoodByConsumerId(consumerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        PurchasedFood purchasedFood = result.get(0);
        assertEquals(1, purchasedFood.getId());
        assertEquals(consumerId, purchasedFood.getConsumerId());
        assertEquals(1, purchasedFood.getFoodInventoryId());
        assertEquals("Test Food", purchasedFood.getFoodInventoryName());
        assertEquals(5, purchasedFood.getQuantity());
        assertEquals(10.0, purchasedFood.getCost());
        assertEquals("2022-01-01T10:00", purchasedFood.getPurchaseDate().toString());
    }
}
