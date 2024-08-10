package controllers;

import businesslayer.PurchaseFoodManager;
import businesslayer.RatingService;
import model.*;
import utilities.MyGson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling food purchasing and transaction-related requests.
 */
public class PurchaseFoodServlet extends HttpServlet {

    private PurchaseFoodManager manager = new PurchaseFoodManager();

    /**
     * Handles POST requests for purchasing food or other actions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
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

    /**
     * Handles GET requests to list available food and transactions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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

    /**
     * Handles the purchase of food by a user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    private void purchaseFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String need = request.getParameter("need");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        try {
            manager.purchaseFood(userId, Integer.parseInt(id), Integer.parseInt(need), Double.parseDouble(request.getParameter("price")));
            listAvailableFood(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lists all available food for sale and displays consumer ratings.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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

    /**
     * Displays the transaction history for the logged-in user.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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
