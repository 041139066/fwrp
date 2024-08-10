package controllers;

import businesslayer.LocationService;
import businesslayer.AuthenticationService;
import com.google.gson.Gson;
import model.Province;
import model.User;
import utilities.MyGson;
import utilities.JsonResponse;
import utilities.PasswordHasher;

import java.io.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for handling user registration requests.
 */
@WebServlet(name = "RegisterServlet", value = "/Register-servlet")
public class RegisterServlet extends HttpServlet {

    /**
     * Handles GET requests to display the registration form.
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
            Gson gson = MyGson.getMyGson();
            LocationService locationService = new LocationService();
            List<Province> provinces = locationService.getAllProvinces();
            request.setAttribute("provinces", provinces);
            String cities = gson.toJson(locationService.getAllCities());
            request.setAttribute("cities", cities);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/authentication/register.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests to register a new user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message;
            try {
                User user = new User();
                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));
                String password = request.getParameter("password");
                user.setPassword(PasswordHasher.hashPassword(password));
                user.setType(request.getParameter("type"));
                user.setCity(request.getParameter("city"));
                user.setProvince(request.getParameter("province"));
                AuthenticationService service = new AuthenticationService();
                service.register(user);
                message = "Register successfully!";
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
