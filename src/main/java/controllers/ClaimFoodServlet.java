package controllers;

import businesslayer.DonationFoodManager;
import model.ClaimedFood;
import model.FoodInventory;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ClaimFoodServlet extends HttpServlet {

    private final DonationFoodManager manager = new DonationFoodManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "claimFood";
        }
        switch (action) {
            case "claimFood":
                claimFood(request, response);
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
            action = "listDonationFood";
        }
        switch (action) {
            case "listDonationFood":
                listDonationFood(request, response);
                break;
            case "myClaimedFood":
                myClaimedFood(request, response);
                break;
            default:
                // Handle other GET actions if needed
                break;
        }
    }

    private void claimFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String need = request.getParameter("need");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        try {
            manager.claimFood(Integer.parseInt(id), Integer.parseInt(need), userId);
            listDonationFood(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listDonationFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<FoodInventory> list = manager.getAllFoodInventoryForDonation();
            request.setAttribute("list", list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void myClaimedFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            List<ClaimedFood> list = manager.getAllClaimedFoodByCharitableId(userId);
            request.setAttribute("list", list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/my-claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
