package dataaccesslayer;

import model.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RatingDAOTest {

    private RatingDAO ratingDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        ratingDAO = new RatingDAO();
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
        // Clean up mocked static
        if (mockedDatabase != null) {
            mockedDatabase.close();
        }
        closeable.close();
    }

    @Test
    void testGetAllRatings() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock ResultSet behavior
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Only one row
        when(mockResultSet.getInt("consumer_id")).thenReturn(1);
        when(mockResultSet.getString("consumerName")).thenReturn("John Doe");
        when(mockResultSet.getInt("food_inventory_id")).thenReturn(101);
        when(mockResultSet.getString("food_inventory_name")).thenReturn("Apple");
        when(mockResultSet.getDouble("rating")).thenReturn(4.5);
        when(mockResultSet.getString("comment")).thenReturn("Great!");
        when(mockResultSet.getTimestamp("last_modified")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        List<Rating> result = ratingDAO.getAllRatings();

        // Verify and assert
        assertNotNull(result);
        assertEquals(1, result.size());
        Rating rating = result.get(0);
        assertEquals(1, rating.getConsumerId());
        assertEquals("John Doe", rating.getConsumerName());
        assertEquals(101, rating.getFoodInventoryId());
        assertEquals("Apple", rating.getFoodInventoryName());
        assertEquals(4.5, rating.getRating());
        assertEquals("Great!", rating.getComment());
    }

    @Test
    void testCreateRating() throws SQLException {
        Rating newRating = new Rating();
        newRating.setConsumerId(1);
        newRating.setFoodInventoryId(101);
        newRating.setRating(4.5);
        newRating.setComment("Good");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock executeUpdate to return 1 row affected
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the operation
        int affectedRows = ratingDAO.createRating(newRating);

        // Verify the interaction
        verify(mockPreparedStatement).setDouble(1, newRating.getRating());
        verify(mockPreparedStatement).setString(2, newRating.getComment());
        verify(mockPreparedStatement).setInt(3, newRating.getConsumerId());
        verify(mockPreparedStatement).setInt(4, newRating.getFoodInventoryId());
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testUpdateRating() throws SQLException {
        Rating updatedRating = new Rating();
        updatedRating.setConsumerId(1);
        updatedRating.setFoodInventoryId(101);
        updatedRating.setRating(3.5);
        updatedRating.setComment("Average");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock executeUpdate to return 1 row affected
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform the update
        int affectedRows = ratingDAO.updateRating(updatedRating);

        // Verify interactions
        verify(mockPreparedStatement).setDouble(1, updatedRating.getRating());
        verify(mockPreparedStatement).setString(2, updatedRating.getComment());
        verify(mockPreparedStatement).setInt(3, updatedRating.getConsumerId());
        verify(mockPreparedStatement).setInt(4, updatedRating.getFoodInventoryId());
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testDeleteRating() throws SQLException {
        int consumerId = 1;
        int foodInventoryId = 101;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock executeUpdate to return 1 row affected
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Perform delete operation
        int affectedRows = ratingDAO.deleteRating(consumerId, foodInventoryId);

        // Verify the interaction
        verify(mockPreparedStatement).setInt(1, consumerId);
        verify(mockPreparedStatement).setInt(2, foodInventoryId);
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testGetAverageRating() throws SQLException {
        int foodInventoryId = 101;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock ResultSet behavior
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble("average_rating")).thenReturn(4.0);

        Double averageRating = ratingDAO.getAverageRating(foodInventoryId);

        // Verify and assert
        assertNotNull(averageRating);
        assertEquals(4.0, averageRating);
    }
}
