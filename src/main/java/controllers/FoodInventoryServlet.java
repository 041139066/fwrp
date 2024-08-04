package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import businesslayer.FoodInventoryManager;
import businesslayer.RatingService;
import model.FoodInventory;
import model.Rating;
import utilities.MyGson;

public class FoodInventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.isEmpty()) {
                displayFoodInventoryList(request, response, false);
            } else {
                String[] pathInfoSegments = pathInfo.split("/");
                if (pathInfoSegments.length >= 2) {
                    String requestType = pathInfoSegments[1];
                    switch (requestType) {
                        case "surplus-food":
                            displayFoodInventoryList(request, response, true);
                            break;
                        case "add":
                            displayFoodInventoryAddForm(request, response);
                            break;
                        case "edit":
                            displayFoodInventoryEditForm(request, response);
                            break;
                        case "delete":
                            deleteFoodInventory(request, response);
                            break;
                        // TODO: handle other paths
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/food-inventory");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayFoodInventoryList(HttpServletRequest request, HttpServletResponse response, boolean isSurplus)
            throws SQLException, IOException, ServletException {
        int userId = 1; // TODO: get user id from session, and check the user type to be "retailer"
        FoodInventoryManager manager = new FoodInventoryManager();
        List<FoodInventory> list = isSurplus ? manager.getSurplusFoodInventoryByRetailerId(userId) : manager.getAllFoodInventoryByRetailerId(userId);
        request.setAttribute("foodInventoryList", list);
        request.setAttribute("isSurplus", isSurplus);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/retailer/food-inventory.jsp");
        dispatcher.forward(request, response);
    }

    private void displayFoodInventoryAddForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/retailer/add_food_inventory.jsp");
        dispatcher.forward(request, response);
    }

    private void displayFoodInventoryEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        int id = Integer.parseInt(request.getParameter("id"));
        FoodInventory foodInventory = manager.getFoodInventoryById(id);
        request.setAttribute("foodInventory", foodInventory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/retailer/edit_food_inventory.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.isEmpty()) {
                displayFoodInventoryList(request, response, false);
            } else {
                String[] pathInfoSegments = pathInfo.split("/");
                if (pathInfoSegments.length >= 2) {
                    String requestType = pathInfoSegments[1];
                    switch (requestType) {
                        case "add":
                            handleFoodInventory(request, response, true);
                            break;
                        case "update":
                            handleFoodInventory(request, response, false);
                            break;
                        // TODO: handle other paths
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/food-inventory");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleFoodInventory(HttpServletRequest request, HttpServletResponse response, boolean isAdd)
            throws SQLException, IOException, ServletException {

        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expirationDate = request.getParameter("expirationDate");
        String status = request.getParameter("status");

        FoodInventory foodInventory = new FoodInventory();
        foodInventory.setName(name);
        foodInventory.setPrice(price);
        foodInventory.setQuantity(quantity);
        foodInventory.setLocalExpirationDate(expirationDate);
        foodInventory.setStatus(status);

        FoodInventoryManager manager = new FoodInventoryManager();
        if (isAdd) {
            int retailerId = 1; // TODO
            foodInventory.setRetailerId(retailerId);
            manager.addFoodInventory(foodInventory);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            foodInventory.setId(id);
            manager.updateFoodInventory(foodInventory);
        }
        response.sendRedirect(request.getContextPath() + "/food-inventory");
    }

    private void deleteFoodInventory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        int id = Integer.parseInt(request.getParameter("id"));
        manager.deleteFoodInventory(id);
        response.sendRedirect(request.getContextPath() + "/food-inventory");
    }


    private void listSurplusFoodInventory(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, IOException, ServletException {
        List<FoodInventory> list = manager.getAllFoodInventory();
        List<FoodInventory> surplusFoodItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (FoodInventory item : list) {
            if (item.getExpirationDate().isBefore(now.plusWeeks(1))) {
                surplusFoodItems.add(item);
            }
        }
        request.setAttribute("foodInventoryList", surplusFoodItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../retailer/surplus-food-inventory.jsp");
        dispatcher.forward(request, response);
    }

}
