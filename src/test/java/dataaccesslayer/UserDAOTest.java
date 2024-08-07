package dataaccesslayer;

import model.User;
import model.constants.MethodType;
import model.constants.UserType;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private AutoCloseable closeable;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<Database> mockedDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        userDAO = new UserDAO();
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
    void testGetUserById() throws SQLException {
        int userId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("password")).thenReturn("password");
        when(mockResultSet.getString("type")).thenReturn("consumer");
        when(mockResultSet.getBoolean("subscription")).thenReturn(true);
        when(mockResultSet.getString("city")).thenReturn("New York");
        when(mockResultSet.getString("province")).thenReturn("NY");
        when(mockResultSet.getString("method")).thenReturn("email");
        when(mockResultSet.getString("contact_email")).thenReturn("contact@example.com");
        when(mockResultSet.getString("contact_phone")).thenReturn("1234567890");

        User user = userDAO.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("consumer", user.getTypeName());
        assertTrue(user.getSubscription());
        assertEquals("New York", user.getCity());
        assertEquals("NY", user.getProvince());
        assertEquals("email", user.getMethodName());
        assertEquals("contact@example.com", user.getContactEmail());
        assertEquals("1234567890", user.getContactPhone());
    }

    @Test
    void testUpdateSubscription() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setMethod(MethodType.email);
        user.setContactEmail("newcontact@example.com");
        user.setSubscription(true);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = userDAO.updateSubscription(user);

        verify(mockPreparedStatement).setString(1, user.getMethodName());
        verify(mockPreparedStatement).setString(2, user.getContactEmail());
        verify(mockPreparedStatement).setBoolean(3, user.getSubscription());
        verify(mockPreparedStatement).setInt(4, user.getId());
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testUpdateStatus() throws SQLException {
        int userId = 1;
        boolean status = true;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = userDAO.updateStatus(status, userId);

        verify(mockPreparedStatement).setBoolean(1, status);
        verify(mockPreparedStatement).setInt(2, userId);
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, affectedRows);
    }

    @Test
    void testRegister() throws SQLException {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setPassword("password");
        user.setType("consumer");
        user.setCity("New York");
        user.setProvince("NY");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        userDAO.register(user);

        verify(mockPreparedStatement).setString(1, user.getName());
        verify(mockPreparedStatement).setString(2, user.getEmail());
        verify(mockPreparedStatement).setString(3, user.getPassword());
        verify(mockPreparedStatement).setString(4, user.getTypeName());
        verify(mockPreparedStatement).setString(5, user.getCity());
        verify(mockPreparedStatement).setString(6, user.getProvince());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetUserByEmail() throws SQLException {
        String email = "john@example.com";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("password")).thenReturn("password");
        when(mockResultSet.getString("type")).thenReturn("consumer");
        when(mockResultSet.getBoolean("subscription")).thenReturn(true);
        when(mockResultSet.getString("city")).thenReturn("New York");
        when(mockResultSet.getString("province")).thenReturn("NY");
        when(mockResultSet.getString("method")).thenReturn("email");
        when(mockResultSet.getString("contact_email")).thenReturn("contact@example.com");
        when(mockResultSet.getString("contact_phone")).thenReturn("1234567890");

        User user = userDAO.getUserByEmail(email);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals(email, user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("consumer", user.getTypeName());
        assertTrue(user.getSubscription());
        assertEquals("New York", user.getCity());
        assertEquals("NY", user.getProvince());
        assertEquals("email", user.getMethodName());
        assertEquals("contact@example.com", user.getContactEmail());
        assertEquals("1234567890", user.getContactPhone());
    }

    @Test
    void testGetSubscribers() throws SQLException {
        int retailerId = 1;
        UserType userType = UserType.retailer;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("Jane Doe");
        when(mockResultSet.getString("email")).thenReturn("jane@example.com");
        when(mockResultSet.getString("password")).thenReturn("password");
        when(mockResultSet.getString("type")).thenReturn("retailer");
        when(mockResultSet.getBoolean("subscription")).thenReturn(true);
        when(mockResultSet.getString("city")).thenReturn("New York");
        when(mockResultSet.getString("province")).thenReturn("NY");
        when(mockResultSet.getString("method")).thenReturn("email");
        when(mockResultSet.getString("contact_email")).thenReturn("contact@example.com");
        when(mockResultSet.getString("contact_phone")).thenReturn("1234567890");

        List<User> subscribers = userDAO.getSubscribers(userType, retailerId);

        assertNotNull(subscribers);
        assertEquals(1, subscribers.size());
        User user = subscribers.get(0);
        assertEquals(2, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("retailer", user.getTypeName());
        assertTrue(user.getSubscription());
        assertEquals("New York", user.getCity());
        assertEquals("NY", user.getProvince());
        assertEquals("email", user.getMethodName());
        assertEquals("contact@example.com", user.getContactEmail());
        assertEquals("1234567890", user.getContactPhone());
    }
}

