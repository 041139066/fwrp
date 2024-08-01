package controllers;

import businesslayer.DonationFoodManager;
import businesslayer.PurchaseFoodManager;
import model.AvailableSaleFood;
import model.DonationFoodVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/purchaseFood")
public class PurchaseFoodServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PurchaseFoodManager manager = new PurchaseFoodManager();
        try {
            String foodItemId = request.getParameter("foodItemId");
            String customerId = request.getParameter("customerId");
            String need = request.getParameter("need");
            boolean b = manager.purchaseFood(Integer.parseInt(foodItemId), Integer.parseInt(customerId), Integer.parseInt(need));
            request.setAttribute("data", b);
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/available-food-list.jsp");
            dispatcher.forward(request, response);
        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
