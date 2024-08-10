package controllers;

import com.google.gson.Gson;
import utilities.MyGson;
import utilities.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling user logout functionality.
 */
public class LogoutServlet extends HttpServlet {

    /**
     * Handles GET requests to log out a user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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
            String jsonResponse = gson.toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }
}
