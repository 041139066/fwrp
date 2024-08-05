package businesslayer;

import dataaccesslayer.UserDAO;
import model.User;
import utilities.PasswordHasher;
import validators.ValidationException;

import java.sql.SQLException;

public class UserManager {
    private final UserDAO userDAO;

    public UserManager() {
        userDAO = new UserDAO();
    }

    public User authenticate(String email, String password) throws ValidationException {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new ValidationException("The email address is not registered yet");
        }
        String hashedPassword = user.getPassword();
        if (!PasswordHasher.checkPassword(password, hashedPassword)) {
            throw new ValidationException("The email or password is incorrect");
        }
        return user;
    }

    public void register(User user) throws ValidationException, SQLException {
        userDAO.register(user);
    }

}
