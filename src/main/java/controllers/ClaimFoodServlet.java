package controllers;

import businesslayer.DonationFoodManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/claimFood")
public class ClaimFoodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String need = request.getParameter("need");
        String userId = request.getParameter("userId");
        DonationFoodManager manager = new DonationFoodManager();
        try {
            manager.claimFood(Integer.parseInt(id), Integer.parseInt(need), Integer.parseInt(userId));
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
