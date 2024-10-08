package controllers;

import businesslayer.FoodInventoryManager;
import businesslayer.RatingService;
import com.google.gson.Gson;
import model.FoodInventory;
import model.Rating;
import model.User;
import utilities.MyGson;
import utilities.JsonResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet for handling rating-related requests, including creating, updating, deleting, and displaying ratings.
 */
@WebServlet(name = "RatingServlet", value = "/Rating-servlet")
public class RatingServlet extends HttpServlet {

    /**
     * Handles GET requests to display ratings.
     * If no specific food inventory ID is provided, it displays ratings for the logged-in user.
     * If a food inventory ID is provided, it displays ratings for that specific food item.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param res  the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            RatingService service = new RatingService();
            String pathInfo = req.getPathInfo();
            if (pathInfo == null) {
                HttpSession session = req.getSession(false);
                User user = (User) session.getAttribute("user");
                int id = user.getId();
                String foodInventoryId = req.getParameter("foodInventoryId");
                List<Rating> list;
                if (foodInventoryId != null) {
                    id = Integer.parseInt(foodInventoryId);
                    list = service.getAllRatingsByFoodInventoryId(id);
                    FoodInventoryManager manager = new FoodInventoryManager();
                    FoodInventory foodInventory = manager.getFoodInventoryById(id);
                    req.setAttribute("foodInventory", foodInventory);
                    req.setAttribute("ratingList", list);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("food-rating/food-rating-list.jsp");
                    dispatcher.forward(req, res);
                } else {
                    list = service.getAllRatingsByConsumerId(id);
                    req.setAttribute("ratingList", list);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("food-rating/consumer-rating-list.jsp");
                    dispatcher.forward(req, res);
                }
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests to create, update, or delete ratings based on the request path.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param res  the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] pathInfo = req.getPathInfo() != null ? req.getPathInfo().split("/") : new String[0];
        if (pathInfo.length >= 2) {
            String requestType = pathInfo[1];
            if ("create".equalsIgnoreCase(requestType)) {
                handleCreate(req, res);
            } else if ("update".equalsIgnoreCase(requestType)) {
                handleUpdate(req, res);
            } else if ("delete".equalsIgnoreCase(requestType)) {
                handleDelete(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    /**
     * Handles the creation of a new rating.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param res  the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    private void handleCreate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handleRating(req, res, true);
    }

    /**
     * Handles the update of an existing rating.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param res  the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    private void handleUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handleRating(req, res, false);
    }

    /**
     * Handles the creation or update of a rating based on the given flag.
     *
     * @param req       the HttpServletRequest object that contains the request the client made of the servlet
     * @param res       the HttpServletResponse object that contains the response the servlet returns to the client
     * @param isCreate  a boolean flag indicating whether to create or update the rating
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    private void handleRating(HttpServletRequest req, HttpServletResponse res, boolean isCreate) throws IOException {
        try (PrintWriter out = res.getWriter()) {
            Gson gson = MyGson.getMyGson();
            res.setContentType("application/json");
            int code = 0;
            String message = "";
            try {
                HttpSession session = req.getSession(false);
                User user = (User) session.getAttribute("user");
                int consumerId = user.getId();
                Rating rating = new Rating();
                rating.setConsumerId(consumerId);
                rating.setFoodInventoryId(Integer.parseInt(req.getParameter("foodInventoryId")));
                rating.setRating(Double.parseDouble(req.getParameter("rating")));
                rating.setComment(req.getParameter("comment"));

                RatingService service = new RatingService();
                int affectedRows = isCreate
                        ? service.createRating(rating)
                        : service.updateRating(rating);
                if (affectedRows == 0) {
                    message = isCreate ? "No rating created." : "No rating updated.";
                } else if (affectedRows == 1) {
                    message = isCreate ? "Rating created successfully" : "Rating updated successfully";
                }
            } catch (Exception e) {
                code = 1;
                message = e.getMessage();
            }
            String jsonResponse = gson.toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

    /**
     * Handles the deletion of a rating.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param res  the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    private void handleDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (PrintWriter out = res.getWriter()) {
            Gson gson = MyGson.getMyGson();
            res.setContentType("application/json");
            int code = 0;
            String message;
            try {
                HttpSession session = req.getSession(false);
                User user = (User) session.getAttribute("user");
                int consumerId = user.getId();
                int foodInventoryId = Integer.parseInt(req.getParameter("foodInventoryId"));
                RatingService service = new RatingService();
                int affectedRows = service.deleteRating(consumerId, foodInventoryId);
                if (affectedRows == 0) {
                    throw new Exception("Failed to delete rating.");
                }
                message = "Rating deleted successfully";
            } catch (Exception e) {
                code = 1;
                message = e.getMessage();
            }
            String jsonResponse = gson.toJson(new JsonResponse(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }
}
