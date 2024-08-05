package controllers;

import businesslayer.UserManager;
import com.google.gson.Gson;
import model.User;
import utilities.MyGson;
import utilities.Response;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/authentication/login.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";
            try {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                UserManager manager = new UserManager();
                User user = manager.authenticate(email, password);
                HttpSession session = request.getSession();
                session.setAttribute("id", user.getId());
                session.setAttribute("name", user.getName());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("type", user.getTypeName());
                session.setAttribute("user", user);
                message = "Login successfully!";
            } catch (Exception e) {
                code = 1;
                message = e.getMessage();
            }
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }
}