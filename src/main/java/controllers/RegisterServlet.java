package controllers;

import businesslayer.LocationService;
import businesslayer.UserManager;
import com.google.gson.Gson;
import dataaccesslayer.UserDAO;
import model.Province;
import model.User;
import utilities.MyGson;
import utilities.Response;

import java.io.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilities.PasswordHasher;

@WebServlet(name = "RegisterServlet", value = "/Register-servlet")
public class RegisterServlet extends HttpServlet {
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";
            try {
                User user = new User();
                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));
                String password = request.getParameter("password");
                user.setPassword(PasswordHasher.hashPassword(password));
                user.setType(request.getParameter("type"));
                if ("retailer".equalsIgnoreCase(request.getParameter("type"))) {
                    user.setCity(request.getParameter("city"));
                    user.setProvince(request.getParameter("province"));
                }
                UserManager manager = new UserManager();
                manager.register(user);
                message = "Register successfully!";
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