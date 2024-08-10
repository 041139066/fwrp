package controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import businesslayer.FoodInventoryManager;
import businesslayer.RatingService;
import businesslayer.SurplusFoodAlert;

import model.FoodInventory;
import model.User;

import utilities.JsonResponse;
import utilities.MyGson;

/**
 * Servlet for managing food inventory operations.
 * Handles both GET and POST requests for displaying, adding, updating, and deleting food inventory items.
 */
public class FoodInventoryServlet extends HttpServlet {

    /**
     * Handles GET requests for displaying food inventory information or forms.
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

    /**
     * Displays the list of food inventory items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @param isSurplus boolean indicating whether to show surplus food items
     * @throws SQLException if a database access error occurs
     * @throws IOException  if an input or output error is detected
     * @throws ServletException if the request for the GET could not be handled
     */
    private void displayFoodInventoryList(HttpServletRequest request, HttpServletResponse response, boolean isSurplus)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        FoodInventoryManager manager = new FoodInventoryManager();
        List<FoodInventory> list = isSurplus ? manager.getSurplusFoodInventoryByRetailerId(id) : manager.getAllFoodInventoryByRetailerId(id);
        request.setAttribute("foodInventoryList", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher(isSurplus ? "/retailer/surplus-food.jsp" : "/retailer/food-inventory.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Displays the form for adding new food inventory items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws SQLException if a database access error occurs
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException  if an input or output error is detected
     */
    private void displayFoodInventoryAddForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/retailer/add_food_inventory.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Displays the form for editing existing food inventory items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws SQLException if a database access error occurs
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException  if an input or output error is detected
     */
    private void displayFoodInventoryEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        int id = Integer.parseInt(request.getParameter("id"));
        FoodInventory foodInventory = manager.getFoodInventoryById(id);
        request.setAttribute("foodInventory", foodInventory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/retailer/edit_food_inventory.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests for adding, updating, and updating the status of food inventory items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
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

    /**
     * Handles adding or updating food inventory items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @param isAdd    boolean indicating whether to add (true) or update (false) the food inventory item
     * @throws SQLException if a database access error occurs
     * @throws IOException  if an input or output error is detected
     * @throws ServletException if the request for the POST could not be handled
     */
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

    /**
     * Updates the status of food inventory items and notifies subscribers of surplus food.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws SQLException if a database access error occurs
     * @throws IOException  if an input or output error is detected
     * @throws ServletException if the request for the POST could not be handled
     */
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

    /**
     * Converts a comma-separated string of integers to a list of integers.
     *
     * @param input the comma-separated string of integers
     * @return a list of integers
     */
    public static List<Integer> splitStringToIntList(String input) {
        if (input == null || input.isEmpty()) {
            return List.of(); // Return an empty list if input is null or empty
        }
        // Split the string by commas and convert each element to an integer
        return Arrays.stream(input.split("\\s*,\\s*"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a food inventory item.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws SQLException if a database access error occurs
     * @throws IOException  if an input or output error is detected
     */
    private void deleteFoodInventory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        int id = Integer.parseInt(request.getParameter("id"));
        manager.deleteFoodInventory(id);
        response.sendRedirect(request.getContextPath() + "/food-inventory");
    }
}
