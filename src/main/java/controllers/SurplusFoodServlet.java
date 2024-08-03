package controllers;

import dataaccesslayer.FoodInventoryDAO;
import model.FoodInventory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/surplus-food-inventory.jsp")
public class SurplusFoodServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FoodInventoryDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new FoodInventoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Update the surplus status first
        try {
            dao.updateSurplusStatus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Retrieve surplus items
        List<FoodInventory> foodInventoryList = dao.getAllFoodInventory();
        request.setAttribute("foodInventoryList", foodInventoryList);

        // Forward to the JSP page
        request.getRequestDispatcher("surplus-food-inventory.jsp").forward(request, response);
    }
}