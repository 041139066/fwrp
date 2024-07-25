package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import businesslayer.LocationService;
import businesslayer.SubscriptionService;
import businesslayer.FoodInventoryManager;
import model.*;

public class SubscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        int id = (Integer) request.getSession().getAttribute("id");
        int userId = 3;

        LocationService locationService = new LocationService();
        List<Province> provinces = locationService.getAllProvinces();
        request.setAttribute("provinces", provinces);
        String cities = new Gson().toJson(locationService.getAllCities());
        request.setAttribute("cities", cities);

        SubscriptionService subscriptionService = new SubscriptionService();
        Subscription subscription = subscriptionService.getSubscription(userId);
        request.setAttribute("subscription", subscription);

        List<Integer> foodPreferences = subscriptionService.getFoodPreferences(userId);
        request.setAttribute("foodPreferences", foodPreferences);

        FoodInventoryManager foodInventoryManager = new FoodInventoryManager();
        List<FoodInventory> foodInventoryList = foodInventoryManager.getAllFoodInventory();
        request.setAttribute("foodInventoryList", foodInventoryList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("surplus-food-alert/subscription.jsp");
        dispatcher.forward(request, response);
    }
}
