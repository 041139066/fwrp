package controllers;

import businesslayer.DonationFoodManager;
import businesslayer.RatingService;
import model.ClaimedFood;
import model.FoodInventory;
import model.Rating;
import model.User;
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
 * Servlet for handling food claiming and donation-related actions.
 * Manages POST and GET requests for claiming food and listing donation options.
 */
public class ClaimFoodServlet extends HttpServlet {

    private final DonationFoodManager manager = new DonationFoodManager();

    /**
     * Handles POST requests for claiming food or other actions.
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

    /**
     * Handles GET requests for listing donation food options or viewing claimed food.
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

    /**
     * Claims a food item based on the provided ID and quantity.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
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

    /**
     * Lists available food items for donation.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    private void listDonationFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            int userId = user.getId();

            List<FoodInventory> list = manager.getAllFoodInventoryForDonation();
            request.setAttribute("list", list);
            RatingService service = new RatingService();
            List<Rating> consumerRatingList = service.getAllRatingsByConsumerId(userId);
            request.setAttribute("consumerRatingList", MyGson.getMyGson().toJson(consumerRatingList));
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lists food items that the current user has claimed.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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
