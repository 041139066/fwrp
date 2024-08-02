package controllers;

import dataaccesslayer.UserDAO;
import model.User;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Class.forName;

@WebServlet(name = "RegisterServlet", value = "/Register-servlet")
public class RegisterServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setType(type);

        try{
            UserDAO userDAO = new UserDAO();
            userDAO.register(name,email,password,type);
            response.sendRedirect("index.jsp");

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

}