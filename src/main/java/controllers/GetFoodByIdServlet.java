package controllers;

import businesslayer.PurchaseFoodManager;
import model.AvailableSaleFood;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetFoodByIdServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PurchaseFoodManager manager = new PurchaseFoodManager();
        try {
            String id = request.getParameter("id");
            AvailableSaleFood food = manager.getFoodById(Integer.parseInt(id));
            request.setAttribute("food", food);
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/purchase-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
