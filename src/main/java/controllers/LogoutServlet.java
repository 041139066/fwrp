package controllers;

import businesslayer.UserManager;
import com.google.gson.Gson;
import model.User;
import utilities.MyGson;
import utilities.PasswordHasher;
import utilities.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "Successfully logged out!";
            try {
                if (session != null) {
                    session.invalidate();
                }
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
