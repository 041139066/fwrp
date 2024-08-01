package controllers;

import businesslayer.DonationFoodManager;
import model.ClaimedFood;
import model.DonationFoodVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyClaimedFoodServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        DonationFoodManager manager = new DonationFoodManager();
        List<ClaimedFood> list = new ArrayList<>();
        try {
            list.addAll(manager.myClaimFood(2));
            req.setAttribute("list", list);
            req.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = req.getRequestDispatcher("charitable/my-claim-food.jsp");
            dispatcher.forward(req, res);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
