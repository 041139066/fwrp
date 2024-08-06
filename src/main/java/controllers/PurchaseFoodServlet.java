package controllers;

import businesslayer.PurchaseFoodManager;
import businesslayer.RatingService;
import model.*;
import utilities.MyGson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PurchaseFoodServlet extends HttpServlet {

    private PurchaseFoodManager manager = new PurchaseFoodManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "purchaseFood";
        }

        switch (action) {
            case "purchaseFood":
                purchaseFood(request, response);
                break;
            default:
                // Handle other POST actions if needed
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "listAvailableFood";
        }
        switch (action) {
            case "listAvailableFood":
                listAvailableFood(request, response);
                break;
            case "transactions":
                transactions(request, response);
                break;
            default:
                // Handle other GET actions if needed
                break;
        }
    }

    private void purchaseFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String need = request.getParameter("need");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        try {
            manager.purchaseFood(Integer.parseInt(id), Integer.parseInt(need), userId);
            listAvailableFood(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listAvailableFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();

            List<FoodInventory> list = manager.getAllFoodInventoryForSale();
            request.setAttribute("list", list);
            RatingService service = new RatingService();
            List<Rating> consumerRatingList = service.getAllRatingsByConsumerId(userId);
            request.setAttribute("consumerRatingList", MyGson.getMyGson().toJson(consumerRatingList));
            RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/purchase-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            List<PurchasedFood> list = manager.getAllPurchasedFoodByConsumerId(userId);
            request.setAttribute("list", list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/transactions.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }

}
