package dataaccesslayer;

import model.FoodInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodInventoryDAOTest {

    private FoodInventoryDAO foodInventoryDAO; // Assume this is the class containing the methods
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;
    @BeforeEach
    public void setUp() throws SQLException {
        foodInventoryDAO = new FoodInventoryDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock the Database.getConnection() to return our mock connection
        mockedDatabase = mockStatic(Database.class);
        mockedDatabase.when(Database::getConnection).thenReturn(mockConnection);
    }
    @AfterEach
    public void tearDown() {
        // Clean up mocked static
        if (mockedDatabase != null) {
            mockedDatabase.close();
        }
    }

    @org.junit.jupiter.api.Test
    void testGetAllFoodInventory() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock ResultSet behavior
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Only one row
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("description")).thenReturn("Apple");
        when(mockResultSet.getDouble("standard_price")).thenReturn(1.99);
        when(mockResultSet.getInt("quantity")).thenReturn(100);
        when(mockResultSet.getDouble("average_rating")).thenReturn(4.5);
        when(mockResultSet.getTimestamp("expirationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
        when(mockResultSet.getBoolean("is_surplus")).thenReturn(false);
        when(mockResultSet.getBoolean("isForDonation")).thenReturn(true);
        when(mockResultSet.getBoolean("isForSale")).thenReturn(true);

        List<FoodInventory> result = foodInventoryDAO.getAllFoodInventory();

        // Verify and assert
        assertNotNull(result);
        assertEquals(1, result.size());
        FoodInventory item = result.get(0);
        assertEquals(1, item.getId());
        assertEquals("Apple", item.getDescription());
        assertEquals(1.99, item.getStandardPrice());
        assertEquals(100, item.getQuantity());
        assertEquals(4.5, item.getAverageRating());
        assertEquals(false, item.getIsSurplus());
        assertEquals(true, item.getIsForDonation());
        assertEquals(true, item.getIsForSale());
    }

    @org.junit.jupiter.api.Test
    void getFoodInventoryById() throws SQLException {
        int itemId = 1;

        // Prepare mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(itemId);
        when(mockResultSet.getString("description")).thenReturn("Apple");
        when(mockResultSet.getDouble("standard_price")).thenReturn(1.99);
        when(mockResultSet.getInt("quantity")).thenReturn(100);
        when(mockResultSet.getDouble("average_rating")).thenReturn(4.5);
        when(mockResultSet.getTimestamp("expirationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
        when(mockResultSet.getBoolean("is_surplus")).thenReturn(false);
        when(mockResultSet.getBoolean("isForDonation")).thenReturn(true);
        when(mockResultSet.getBoolean("isForSale")).thenReturn(true);

        // Execute method
        FoodInventory item = foodInventoryDAO.getFoodInventoryById(itemId);

        // Assertions
        assertNotNull(item);
        assertEquals(itemId, item.getId());
        assertEquals("Apple", item.getDescription());
        assertEquals(1.99, item.getStandardPrice());
    }

    @org.junit.jupiter.api.Test
    void addFoodInventory() throws SQLException {

        FoodInventory newItem = new FoodInventory(1, "Orange", 0.99, 50, 4.2, LocalDateTime.now().plusDays(10), false, true, true);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Perform the operation
        foodInventoryDAO.addFoodInventory(newItem);

        // Verify the interaction
        verify(mockPreparedStatement).setString(1, newItem.getDescription());
        verify(mockPreparedStatement).setDouble(2, newItem.getStandardPrice());
        verify(mockPreparedStatement).setInt(3, newItem.getQuantity());
        verify(mockPreparedStatement).setDouble(4, newItem.getAverageRating());
        verify(mockPreparedStatement).setTimestamp(5, Timestamp.valueOf(newItem.getExpirationDate()));
        verify(mockPreparedStatement).executeUpdate();
    }



    @org.junit.jupiter.api.Test
    void updateFoodInventory() throws SQLException {

        FoodInventory updatedItem = new FoodInventory(1, "Banana", 0.75, 200, 4.6, LocalDateTime.now().plusDays(2), true, false, true);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Perform the update
        foodInventoryDAO.updateFoodInventory(updatedItem);

        // Verify interactions
        verify(mockPreparedStatement).setString(1, updatedItem.getDescription());
        verify(mockPreparedStatement).setDouble(2, updatedItem.getStandardPrice());
        verify(mockPreparedStatement).setInt(3, updatedItem.getQuantity());
        verify(mockPreparedStatement).setDouble(4, updatedItem.getAverageRating());
        verify(mockPreparedStatement).setTimestamp(5, Timestamp.valueOf(updatedItem.getExpirationDate()));
        verify(mockPreparedStatement).setBoolean(6, updatedItem.getIsForDonation());
        verify(mockPreparedStatement).setBoolean(7, updatedItem.getIsForSale());
        verify(mockPreparedStatement).setInt(8, updatedItem.getId());
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

    @org.junit.jupiter.api.Test
    void updateSurplusStatus() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        FoodInventory item1 = new FoodInventory(1, "Milk", 2.5, 10, 4.2, now.plusDays(2), false, true, true);
        FoodInventory item2 = new FoodInventory(2, "Bread", 1.5, 20, 3.8, now.plusDays(10), false, true, false);
        FoodInventory item3 = new FoodInventory(3, "Eggs", 3.0, 30, 4.8, now.plusDays(6), false, false, true);

        // Mock getAllFoodInventory method
        FoodInventoryDAO spyService = spy(foodInventoryDAO);
        doReturn(Arrays.asList(item1, item2, item3)).when(spyService).getAllFoodInventory();

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Execute method
        spyService.updateSurplusStatus();

        // Verify surplus status update
        verify(mockPreparedStatement, times(2)).executeUpdate(); // Only items 1 and 3 should be updated

        // Capture arguments passed to updateSurplusStatusInDatabase
        ArgumentCaptor<Boolean> surplusCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(spyService, times(2)).updateSurplusStatusInDatabase(idCaptor.capture(), surplusCaptor.capture());

        // Check captured values
        List<Boolean> surplusValues = surplusCaptor.getAllValues();
        List<Integer> ids = idCaptor.getAllValues();
        assertTrue(surplusValues.contains(true));
        assertEquals(1, (int) ids.get(0));
        assertEquals(3, (int) ids.get(1));

        assertTrue(item1.getIsSurplus());
        assertFalse(item2.getIsSurplus());
        assertTrue(item3.getIsSurplus());
    }

    @org.junit.jupiter.api.Test
    void getSurplusFoodInventory() throws SQLException {
        // Prepare mock data
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock ResultSet behavior
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("description")).thenReturn("Milk").thenReturn("Bread");
        when(mockResultSet.getDouble("standard_price")).thenReturn(2.5).thenReturn(1.5);
        when(mockResultSet.getInt("quantity")).thenReturn(10).thenReturn(20);
        when(mockResultSet.getDouble("average_rating")).thenReturn(4.2).thenReturn(3.8);
        when(mockResultSet.getTimestamp("expirationDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now().plusDays(2))).thenReturn(Timestamp.valueOf(LocalDateTime.now().plusDays(10)));
        when(mockResultSet.getBoolean("is_surplus")).thenReturn(true).thenReturn(true);
        when(mockResultSet.getBoolean("isForDonation")).thenReturn(true).thenReturn(false);
        when(mockResultSet.getBoolean("isForSale")).thenReturn(true).thenReturn(true);

        // Execute method
        List<FoodInventory> surplusItems = foodInventoryDAO.getSurplusFoodInventory();

        // Assertions
        assertNotNull(surplusItems);
        assertEquals(2, surplusItems.size());

        FoodInventory item1 = surplusItems.get(0);
        assertEquals(1, item1.getId());
        assertEquals("Milk", item1.getDescription());
        assertEquals(2.5, item1.getStandardPrice());
        assertEquals(10, item1.getQuantity());
        assertEquals(4.2, item1.getAverageRating());
        assertTrue(item1.getIsSurplus());
        assertTrue(item1.getIsForDonation());
        assertTrue(item1.getIsForSale());

        FoodInventory item2 = surplusItems.get(1);
        assertEquals(2, item2.getId());
        assertEquals("Bread", item2.getDescription());
        assertEquals(1.5, item2.getStandardPrice());
        assertEquals(20, item2.getQuantity());
        assertEquals(3.8, item2.getAverageRating());
        assertTrue(item2.getIsSurplus());
        assertFalse(item2.getIsForDonation());
        assertTrue(item2.getIsForSale());
    }
}