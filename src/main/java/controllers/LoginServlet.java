package controllers;

import businesslayer.AuthenticationService;
import com.google.gson.Gson;
import model.User;
import utilities.MyGson;
import utilities.JsonResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling user login functionality.
 */
public class LoginServlet extends HttpServlet {

    /**
     * Handles GET requests to display the login page.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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

    /**
     * Handles POST requests to authenticate a user and create a session.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message;
            try {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                AuthenticationService service = new AuthenticationService();
                User user = service.authenticate(email, password);
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
            String jsonResponse = gson.toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }
}
