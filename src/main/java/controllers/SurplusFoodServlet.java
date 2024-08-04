package controllers;

import java.util.List;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import businesslayer.FoodInventoryManager;
import model.FoodInventory;

public class SurplusFoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 1; // TODO
        FoodInventoryManager manager = new FoodInventoryManager();
        List<FoodInventory> foodInventoryList = manager.getSurplusFoodInventoryByRetailerId(userId);
        request.setAttribute("foodInventoryList", foodInventoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("retailer/surplus-food-inventory.jsp");
        dispatcher.forward(request, response);
    }
}