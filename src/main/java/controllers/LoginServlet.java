package controllers;

import dataaccesslayer.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        User user = new User();
        user = userDao.auth(email,password);
        if (user != null){
            HttpSession session = request.getSession();
            session.setAttribute("name",user.getName());
            session.setAttribute("Id",user.getId());
            session.setAttribute("userType",user.getType());

            response.sendRedirect("");
        }
        else {
            response.sendError(401);
//            response.sendRedirect("index.jsp?error=Invalid mail or password.");

        }



    }
}
