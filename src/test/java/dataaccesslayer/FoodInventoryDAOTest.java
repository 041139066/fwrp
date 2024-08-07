package dataaccesslayer;

import businesslayer.FoodInventoryManager;
import model.FoodInventory;
import model.constants.FoodStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FoodInventoryDAOTest {

    private FoodInventoryDAO foodInventoryDAO; // Assume this is the class containing the methods
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;
    private FoodInventoryManager foodInventoryManager;


    @BeforeEach
    public void setUp() throws Exception {

        // MockitoAnnotations.openMocks(this);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

         when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        mockedDatabase = Mockito.mockStatic(Database.class);
        mockedDatabase.when(Database::getConnection).thenReturn(mockConnection);

        foodInventoryDAO = new FoodInventoryDAO();
        foodInventoryManager = new FoodInventoryManager();
    }


    @AfterEach
    public void tearDown() {
        // Clean up mocked static
        if (mockedDatabase != null) {
            mockedDatabase.close();
        }
    }

    private void mockResultSetForFoodInventory() throws SQLException {

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Apple");
        when(mockResultSet.getDouble("price")).thenReturn(1.99);
        when(mockResultSet.getTimestamp("expiration_date")).thenReturn(Timestamp.valueOf(LocalDateTime.of(2024, 8, 6, 12, 0)));
        when(mockResultSet.getInt("quantity")).thenReturn(10);
        when(mockResultSet.getDouble("average_rating")).thenReturn(4.5);
        when(mockResultSet.getString("status")).thenReturn(FoodStatus.DONATION.name());
        when(mockResultSet.getInt("retailer_id")).thenReturn(1);
    }


    @Test
    public void testGetFoodInventoryListAllRecords() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Apple", "Banana");
        when(mockResultSet.getDouble("price")).thenReturn(1.99, 0.99);
        when(mockResultSet.getTimestamp("expiration_date")).thenReturn(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(7)));
        when(mockResultSet.getInt("quantity")).thenReturn(100, 200);
        when(mockResultSet.getInt("retailer_id")).thenReturn(1, 1);

        // Act
        List<FoodInventory> result = foodInventoryDAO.getFoodInventoryList(0, 0);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Banana", result.get(1).getName());
        verify(mockPreparedStatement, never()).setInt(anyInt(), anyInt());
    }

    @Test
    public void testGetFoodInventoryListByRetailer() throws Exception {
        // Arrange
        int type = 1;
        int id = 1;
        mockResultSetForFoodInventory();

        // Act
        List<FoodInventory> result = foodInventoryDAO.getFoodInventoryList(type, id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        // assertEquals("Apple", result.get(0).getName());

        // Verify that the prepared statement is set with the correct id
        //verify(mockPreparedStatement).setInt(1, id);

        FoodInventory item = result.get(0);
        assertEquals(1, item.getId());
        assertEquals("Apple", item.getName());
        assertEquals(1.99, item.getPrice());
        assertEquals(10, item.getQuantity());
        assertEquals(4.5, item.getAverageRating());
        assertEquals(FoodStatus.DONATION, item.getStatus());
        assertEquals(1, item.getRetailerId());

        // Verify interactions with the PreparedStatement
        verify(mockPreparedStatement).setInt(1, id);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetFoodInventoryListSurplusByRetailer() throws Exception {
        // Arrange
        int type = 2;
        int id = 1;
        mockResultSetForFoodInventory();

        // Act
        List<FoodInventory> result = foodInventoryDAO.getFoodInventoryList(type, id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        // assertEquals("Apple", result.get(0).getName());

        // Verify that the prepared statement is set with the correct id
        // verify(mockPreparedStatement).setInt(1, id);
        FoodInventory item = result.get(0);
        assertEquals(1, item.getId());
        assertEquals("Apple", item.getName());
        assertEquals(1.99, item.getPrice());
        assertEquals(10, item.getQuantity());
        assertEquals(4.5, item.getAverageRating());
        assertEquals(FoodStatus.DONATION, item.getStatus());
        assertEquals(1, item.getRetailerId());
    }

    @Test
    void testGetAllFoodInventoryByLocation() throws SQLException {

        mockResultSetForFoodInventory();

        String city = "SampleCity";
        String province = "SampleProvince";

        List<FoodInventory> result = foodInventoryDAO.getAllFoodInventoryByLocation(city, province);

        assertNotNull(result);
        assertEquals(1, result.size());
        FoodInventory item = result.get(0);
        assertEquals(1, item.getId());
        assertEquals("Apple", item.getName());
        assertEquals(1.99, item.getPrice());
        assertEquals(10, item.getQuantity());
        assertEquals(4.5, item.getAverageRating());
        assertEquals(FoodStatus.DONATION, item.getStatus());
        assertEquals(1, item.getRetailerId());
    }


    @Test
    void testGetFoodInventoryById() throws SQLException {
        int id = 1;
        mockResultSetForFoodInventory();
        FoodInventory item = foodInventoryDAO.getFoodInventoryById(id);
        assertNotNull(item);
        assertEquals(1, item.getId());
        assertEquals("Apple", item.getName());
        assertEquals(1.99, item.getPrice());
        assertEquals(10, item.getQuantity());


        assertEquals(4.5, item.getAverageRating());
        assertEquals(FoodStatus.DONATION, item.getStatus());
        assertEquals(1, item.getRetailerId());
        verify(mockPreparedStatement).setInt(1, id);

    }

    @Test
    public void testGetFoodInventoryListForDonation() throws Exception {
        // Arrange
        int type = 3;
        int id = 0;
        mockResultSetForFoodInventory();

        // Act
        List<FoodInventory> result = foodInventoryDAO.getFoodInventoryList(type, id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getName());
    }

    @Test
    public void testGetFoodInventoryListForSale() throws Exception {
        // Arrange
        int type = 4;
        int id = 0;
        mockResultSetForFoodInventory();

        // Act
        List<FoodInventory> result = foodInventoryDAO.getFoodInventoryList(type, id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getName());
    }


    @org.junit.jupiter.api.Test
    void testAddFoodInventory() throws SQLException {

        FoodInventory newItem = new FoodInventory(
                "Orange",
                0.99,
                LocalDateTime.now().plusDays(10),
                50, 1);


        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);


        // Perform the operation
        foodInventoryDAO.addFoodInventory(newItem);

        // Verify the interaction
        verify(mockPreparedStatement).setString(1, newItem.getName());
        verify(mockPreparedStatement).setDouble(2, newItem.getPrice());
        verify(mockPreparedStatement).setTimestamp(3, Timestamp.valueOf(newItem.getExpirationDate()));
        verify(mockPreparedStatement).setInt(4, newItem.getQuantity());
        verify(mockPreparedStatement).setInt(5, newItem.getRetailerId());
        verify(mockPreparedStatement).executeUpdate();  verify(mockPreparedStatement).executeUpdate();
    }



    @org.junit.jupiter.api.Test
    void testUpdateFoodInventory() throws SQLException {

        FoodInventory updatedItem = new FoodInventory(1, "Banana", 0.75, 200, LocalDateTime.now().plusDays(2),
                4.6, FoodStatus.SALE, 1);


        // Perform the update
        foodInventoryManager.updateFoodInventory(updatedItem);

        // Verify interactions
        verify(mockPreparedStatement).setString(1, updatedItem.getName());
        verify(mockPreparedStatement).setDouble(2, updatedItem.getPrice());
        verify(mockPreparedStatement).setTimestamp(3, Timestamp.valueOf(updatedItem.getExpirationDate()));
        verify(mockPreparedStatement).setInt(4, updatedItem.getQuantity());
        verify(mockPreparedStatement).setInt(5, updatedItem.getId());
        verify(mockPreparedStatement).executeUpdate();

        verify(mockPreparedStatement).executeUpdate();

    }

    @org.junit.jupiter.api.Test
    void deleteFoodInventory() throws SQLException {
        int itemId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Perform delete operation
        foodInventoryDAO.deleteFoodInventory(itemId);

        // Verify the interaction
        verify(mockPreparedStatement).setInt(1, itemId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateFoodInventoryStatusWithValidStatus() throws SQLException {
        int id = 1;
        String status = "Available";

        foodInventoryManager.updateFoodInventoryStatus(id, status);

        verify(mockPreparedStatement).setString(1, status);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateFoodInventoryStatusWithNullStatus() throws SQLException {
        int id = 1;
        String status = null;

        foodInventoryManager.updateFoodInventoryStatus(id, status);

        verify(mockPreparedStatement).setNull(1, java.sql.Types.VARCHAR);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateFoodInventoryStatusWithEmptyStatus() throws SQLException {
        int id = 1;
        String status = "";

        foodInventoryManager.updateFoodInventoryStatus(id, status);

        verify(mockPreparedStatement).setNull(1, java.sql.Types.VARCHAR);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();

    }
}