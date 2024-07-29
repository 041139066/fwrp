package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import businesslayer.LocationService;
import businesslayer.SubscriptionService;
import businesslayer.FoodInventoryManager;
import model.*;
import utilities.MyGson;
import utilities.Response;
import validators.SubscriptionValidator;
import validators.ValidationException;

public class SubscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfoStr = request.getPathInfo();
        String[] pathInfo = request.getPathInfo() != null ? request.getPathInfo().split("/") : new String[0];
        if (pathInfo.length >= 2) {
            String requestType = pathInfo[1];
            if ("deactivate".equalsIgnoreCase(requestType)) {
                handleDeactivate(request, response);
            } else if ("reactivate".equalsIgnoreCase(requestType)) {
                handleReactivate(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } else {
            Gson gson = MyGson.getMyGson();
//        int id = (Integer) request.getSession().getAttribute("id");
            int userId = 3;

            LocationService locationService = new LocationService();
            List<Province> provinces = locationService.getAllProvinces();
            request.setAttribute("provinces", provinces);
            String cities = gson.toJson(locationService.getAllCities());
            request.setAttribute("cities", cities);

            SubscriptionService subscriptionService = new SubscriptionService();
            Subscription subscription = subscriptionService.getSubscription(userId);
            request.setAttribute("subscription", subscription);

            String foodPreferences = gson.toJson(subscriptionService.getFoodPreferences(userId));
            request.setAttribute("foodPreferences", foodPreferences);

            FoodInventoryManager foodInventoryManager = new FoodInventoryManager();
            List<FoodInventory> foodInventoryList = foodInventoryManager.getAllFoodInventory();
            request.setAttribute("foodInventoryList", foodInventoryList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("surplus-food-alert/subscription.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] pathInfo = request.getPathInfo() != null ? request.getPathInfo().split("/") : new String[0];
        if (pathInfo.length >= 2) {
            String requestType = pathInfo[1];
            if ("subscribe".equalsIgnoreCase(requestType)) {
                handleSubscribe(request, response);
            } else if ("update".equalsIgnoreCase(requestType)) {
                handleUpdate(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    private void handleSubscribe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processSubscription(request, response, true);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processSubscription(request, response, false);
    }

    private void processSubscription(HttpServletRequest request, HttpServletResponse response, boolean isCreate) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";

            int consumerId = 3; // Placeholder for consumer ID

            Subscription subscription = new Subscription();
            subscription.setConsumerId(consumerId);
            subscription.setProvince(request.getParameter("province"));
            subscription.setCity(request.getParameter("city"));
            subscription.setMethod(request.getParameter("method"));
            if ("email".equalsIgnoreCase(request.getParameter("method"))) {
                subscription.setEmail(request.getParameter("email"));
            } else if ("sms".equalsIgnoreCase(request.getParameter("method"))) {
                subscription.setPhone(request.getParameter("phone"));
            }
            subscription.setStatus(true);

            try {
                SubscriptionValidator validator = new SubscriptionValidator();
                validator.validate(subscription);
                SubscriptionService subscriptionService = new SubscriptionService();
                int affectedRows = isCreate
                        ? subscriptionService.createSubscription(subscription)
                        : subscriptionService.updateSubscription(subscription);
                if (affectedRows == 0) {
                    message = isCreate ? "No subscription created." : "No subscription updated.";
                } else if (affectedRows == 1) {
                    message = isCreate ? "Subscription created successfully" : "Subscription updated successfully";
                }
                subscriptionService.updateFoodPreferences(consumerId, request.getParameter("foodPreferences"));
            } catch (ValidationException e) {
                code = 1;
                message = e.getMessage();
            }
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

    private void handleDeactivate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";
            //        int consumerId = (Integer) request.getSession().getAttribute("id");
            int consumerId = 3;
            SubscriptionService subscriptionService = new SubscriptionService();
            int affectedRoes = subscriptionService.unsubscribe(consumerId);
            if (affectedRoes == 0) {
                message = "No subscription deactivated.";
            } else {
                message = "Subscription deactivated successfully";
            }
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

    private void handleReactivate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Gson gson = MyGson.getMyGson();
            response.setContentType("application/json");
            int code = 0;
            String message = "";
            //        int consumerId = (Integer) request.getSession().getAttribute("id");
            int consumerId = 3;
            SubscriptionService subscriptionService = new SubscriptionService();
            int affectedRoes = subscriptionService.reactivate(consumerId);
            if (affectedRoes == 0) {
                message = "No subscription reactivated.";
            } else {
                message = "Subscription reactivated successfully";
            }
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

}
