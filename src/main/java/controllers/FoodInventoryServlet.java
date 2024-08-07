package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import businesslayer.FoodInventoryManager;
import businesslayer.RatingService;
import businesslayer.SurplusFoodAlert;
import com.google.gson.Gson;
import model.FoodInventory;
import model.Rating;
import model.User;
import model.constants.FoodStatus;
import utilities.JsonResponse;
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
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        FoodInventoryManager manager = new FoodInventoryManager();
        List<FoodInventory> list = isSurplus ? manager.getSurplusFoodInventoryByRetailerId(id) : manager.getAllFoodInventoryByRetailerId(id);
        request.setAttribute("foodInventoryList", list);
        RatingService service = new RatingService();
        RequestDispatcher dispatcher = request.getRequestDispatcher(isSurplus ? "/retailer/surplus-food.jsp" : "/retailer/food-inventory.jsp");
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
                        case "updateStatus":
                            updateFoodInventoryStatus(request, response);
                            break;
                        // TODO: handle other paths
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/food-inventory");
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleFoodInventory(HttpServletRequest request, HttpServletResponse response, boolean isAdd)
            throws SQLException, IOException, ServletException {
        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String expirationDate = request.getParameter("expirationDate");
//        String status = request.getParameter("status");

            FoodInventory foodInventory = new FoodInventory();
            foodInventory.setName(name);
            foodInventory.setPrice(price);
            foodInventory.setQuantity(quantity);
            foodInventory.setLocalExpirationDate(expirationDate);
//        foodInventory.setStatus(status);

            FoodInventoryManager manager = new FoodInventoryManager();
            if (isAdd) {
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                int id = user.getId();
                foodInventory.setRetailerId(id);
                manager.addFoodInventory(foodInventory);
            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                foodInventory.setId(id);
                manager.updateFoodInventory(foodInventory);
            }
            response.sendRedirect(request.getContextPath() + "/food-inventory");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

    }

    private void updateFoodInventoryStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int code = 0;
            String message = "Food status updated successfully";
            try {
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                int userId = user.getId();
                SurplusFoodAlert alert = new SurplusFoodAlert(userId);
                FoodInventoryManager manager = new FoodInventoryManager();
                String idsStr = request.getParameter("ids");
                List<Integer> ids = splitStringToIntList(idsStr);
                String status = request.getParameter("status");
                for (int id : ids) {
                    manager.updateFoodInventoryStatus(id, status);
                    alert.notifySubscribers(manager.getFoodInventoryById(id));
                }

            } catch (Exception e) {
                code = 1;
                message = e.getMessage();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            String jsonResponse = MyGson.getMyGson().toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }


    public static List<Integer> splitStringToIntList(String input) {
        if (input == null || input.isEmpty()) {
            return List.of(); // Return an empty list if input is null or empty
        }
        // Split the string by commas and convert each element to an integer
        return Arrays.stream(input.split("\\s*,\\s*"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void deleteFoodInventory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        int id = Integer.parseInt(request.getParameter("id"));
        manager.deleteFoodInventory(id);
        response.sendRedirect(request.getContextPath() + "/food-inventory");
    }
}
