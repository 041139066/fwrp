package controllers;

import dataaccesslayer.UserDAO;
import model.User;
import utilities.PasswordHasher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDAO userDao = new UserDAO();
            User user = userDao.findByEmail(email);

            if (user == null) {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                if (password != null && PasswordHasher.checkPassword(password, user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", user.getName());
                    session.setAttribute("Id", user.getId());
                    session.setAttribute("userType", user.getTypeName());
                    response.sendRedirect(request.getContextPath() + "/Dashboard/dashboard.jsp");

                } else {
                    request.setAttribute("errorMessage", "Incorrect password.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again later.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}