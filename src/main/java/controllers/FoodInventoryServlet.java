package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import businesslayer.FoodInventoryManager;
import model.FoodInventory;

public class FoodInventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodInventoryManager manager = new FoodInventoryManager();


        List<FoodInventory> list = new ArrayList<>(manager.getAllFoodInventory());

        request.setAttribute("list", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("retailer/food-inventory.jsp");
        dispatcher.forward(request, response);
    }

}
