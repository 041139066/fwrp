package controllers;

import businesslayer.FoodInventoryManager;
import businesslayer.RatingService;
import com.google.gson.Gson;
import model.FoodInventory;
import model.Rating;
import utilities.MyGson;
import utilities.Response;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RatingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            RatingService service = new RatingService();
            String pathInfo = req.getPathInfo();
            if (pathInfo == null) {
                int id = 3;
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
        }
        res.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handleRating(req, res, true);
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handleRating(req, res, false);
    }

    private void handleRating(HttpServletRequest req, HttpServletResponse res, boolean isCreate) throws IOException {
        try (PrintWriter out = res.getWriter()) {
            Gson gson = MyGson.getMyGson();
            res.setContentType("application/json");
            int code = 0;
            String message = "";
            try {
                int consumerId = 3; // Placeholder for consumer ID
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
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (PrintWriter out = res.getWriter()) {
            Gson gson = MyGson.getMyGson();
            res.setContentType("application/json");
            int code = 0;
            String message;
            try {
                int consumerId = Integer.parseInt(req.getParameter("consumerId"));
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
            String jsonResponse = gson.toJson(new Response(code, message));
            out.print(jsonResponse);
            out.flush();
        }
    }

}
