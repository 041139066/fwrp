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
import model.FoodInventory;

public class FoodInventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "delete":
                    deleteFoodInventory(request, response, manager);
                    break;
                case "edit":
                    showEditForm(request, response, manager);
                    break;
                case "listSurplus":
                    listSurplusFoodInventory(request, response, manager);
                    break;
                default:
                    listFoodInventory(request, response, manager);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addFoodInventory(request, response, manager);
                    break;
                case "update":
                    updateFoodInventory(request, response, manager);
                    break;
                default:
                    listFoodInventory(request, response, manager);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listFoodInventory(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, IOException, ServletException {
        List<FoodInventory> list = new ArrayList<>(manager.getAllFoodInventory());
        request.setAttribute("foodInventoryList", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../retailer/food-inventory.jsp");
        dispatcher.forward(request, response);
    }

    private void addFoodInventory(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, IOException {
        String description = request.getParameter("description");
        double standardPrice = Double.parseDouble(request.getParameter("standardPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double averageRating = Double.parseDouble(request.getParameter("averageRating"));
        //LocalDateTime expirationDate = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.parse(request.getParameter("expirationDate"));


        boolean isForDonation = request.getParameter("isForDonation") != null;
        boolean isForSale = request.getParameter("isForSale") != null;


        FoodInventory newFood = new FoodInventory( description,standardPrice,quantity,averageRating,expirationDate,
                false,isForDonation,isForSale);


        manager.addFoodInventory(newFood);
        response.sendRedirect("FoodInventoryServlet?action=list");
    }

    private void updateFoodInventory(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("description");
        double standardPrice = Double.parseDouble(request.getParameter("standardPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double averageRating = Double.parseDouble(request.getParameter("averageRating"));
        //String  lastModified = String.valueOf(LocalDateTime.now());
       // LocalDateTime expirationDate = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.parse(request.getParameter("expirationDate"));

        //boolean is_surplus;

        boolean isForDonation = request.getParameter("isForDonation") != null;
        boolean isForSale = request.getParameter("isForSale") != null;


        FoodInventory updatedFood = new FoodInventory(id, description,standardPrice,quantity,averageRating,expirationDate,
                false,isForDonation,isForSale);
        manager.updateFoodInventory(updatedFood);
        response.sendRedirect("FoodInventoryServlet?action=list");

        //List<FoodInventory> foodList= manager.getAllFoodInventory();
        //request.setAttribute("foodList", foodList);
        //RequestDispatcher dispatcher = request.getRequestDispatcher("retailer/food-inventory-list.jsp");
        //dispatcher.forward(request, response);

    }

    private void deleteFoodInventory(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        manager.deleteFoodInventory(id);
        response.sendRedirect("FoodInventoryServlet?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, FoodInventoryManager manager)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        FoodInventory existingFood = manager.getFoodInventory(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("retailer/edit_food_inventory.jsp");
        request.setAttribute("foodInventory", existingFood);
        dispatcher.forward(request, response);
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
