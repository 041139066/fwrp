package controllers;

import businesslayer.DonationFoodManager;
import model.DonationFoodVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseFoodListServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        DonationFoodManager manager = new DonationFoodManager();
        List<DonationFoodVO> list = new ArrayList<>();
        try {
            list.addAll(manager.getAllDonationFood());
            request.setAttribute("list", list);
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/available-food-list.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
