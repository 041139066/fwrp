package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import businesslayer.LocationService;
import businesslayer.SubscriptionService;
import businesslayer.FoodInventoryManager;
import model.*;
import utilities.MyGson;
import utilities.JsonResponse;
import validators.SubscriptionValidator;

public class SubscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfoStr = request.getPathInfo();
            if (pathInfoStr == null) {
                Gson gson = MyGson.getMyGson();
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                int userId = user.getId();
                String city = user.getCity();
                String province = user.getProvince();

                LocationService locationService = new LocationService();
                List<Province> provinces = locationService.getAllProvinces();
                request.setAttribute("provinces", provinces);
                String cities = gson.toJson(locationService.getAllCities());
                request.setAttribute("cities", cities);

                SubscriptionService subscriptionService = new SubscriptionService();
                User subscription = subscriptionService.getSubscription(userId);
                request.setAttribute("subscription", subscription);


                FoodInventoryManager foodInventoryManager = new FoodInventoryManager();
                List<FoodInventory> foodInventoryList = foodInventoryManager.getAllFoodInventoryByLocation(city, province);
                request.setAttribute("foodInventoryList", foodInventoryList);

                List<Integer> foodPreferences = subscriptionService.getFoodPreferencesByUserId(userId);
                List<FoodInventory> filteredPreferences = foodInventoryList.stream()
                        .filter(item -> foodPreferences.contains(item.getId()))
                        .toList();
                request.setAttribute("foodPreferences", gson.toJson(filteredPreferences));

                RequestDispatcher dispatcher = request.getRequestDispatcher("surplus-food-alert/subscription.jsp");
                dispatcher.forward(request, response);
            } else {
                String[] pathInfo = request.getPathInfo().split("/");
                if (pathInfo.length >= 2) {
                    String requestType = pathInfo[1];
                    if ("deactivate".equalsIgnoreCase(requestType)) {
                        updateStatus(request, response, false);
                    } else if ("reactivate".equalsIgnoreCase(requestType)) {
                        updateStatus(request, response, true);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response, boolean status) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            SubscriptionService subscriptionService = new SubscriptionService();
            int affectedRoes = subscriptionService.updateStatus(status, userId);
            if (affectedRoes == 0) {
                code = 1;
                message = status ? "No subscription activated." : "No subscription deactivated.";
            } else {
                message = status ? "Subscription activated successfully." : "Subscription deactivated successfully.";
            }
            String jsonResponse = gson.toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] pathInfo = request.getPathInfo() != null ? request.getPathInfo().split("/") : new String[0];
        if (pathInfo.length >= 2) {
            String requestType = pathInfo[1];
            if ("subscribe".equalsIgnoreCase(requestType)) {
                updateSubscription(request, response, true);
            } else if ("update".equalsIgnoreCase(requestType)) {
                updateSubscription(request, response, false);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    private void updateSubscription(HttpServletRequest request, HttpServletResponse response, boolean isCreate) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();

            User subscription = new User();
            subscription.setId(userId);
//            subscription.setCity(request.getParameter("city"));
//            subscription.setProvince(request.getParameter("province"));
            subscription.setMethod(request.getParameter("method"));
            if ("email".equalsIgnoreCase(request.getParameter("method"))) {
                subscription.setContactEmail(request.getParameter("contactEmail"));
            } else if ("sms".equalsIgnoreCase(request.getParameter("method"))) {
                subscription.setContactPhone(request.getParameter("contactPhone"));
            }
            subscription.setSubscription(true);

            try {
                SubscriptionValidator validator = new SubscriptionValidator();
                validator.validate(subscription);
                SubscriptionService subscriptionService = new SubscriptionService();
                subscriptionService.updateFoodPreferences(userId, request.getParameter("foodPreferences"));
                int affectedRows = subscriptionService.updateSubscription(subscription);
                if (affectedRows == 0) {
                    message = isCreate ? "No subscription created." : "No subscription updated.";
                } else if (affectedRows == 1) {
                    message = isCreate ? "Subscription created successfully" : "Subscription updated successfully";
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
