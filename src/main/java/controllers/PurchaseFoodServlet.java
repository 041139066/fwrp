package controllers;

import businesslayer.PurchaseFoodManager;
import model.AvailableFood;
import model.PurchasedFood;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/PurchaseFoodServlet")
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

        // 获取登录用户ID
        Integer userId = getUserId(request);

        try {
            manager.purchaseFood(Integer.parseInt(id), Integer.parseInt(need), userId);
            response.sendRedirect("PurchaseFoodServlet?action=listAvailableFood");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listAvailableFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AvailableFood> list = manager.getAllAvailableFood();
        request.setAttribute("list", list);

        // 获取登录用户ID并设置为请求属性
        Integer userId = getUserId(request);
        request.setAttribute("userId", userId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/purchase-food.jsp");
        dispatcher.forward(request, response);
    }

    private void transactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PurchasedFood> list = manager.getAllPurchasedFood();
        request.setAttribute("list", list);

        // 获取登录用户ID并设置为请求属性
        Integer userId = getUserId(request);
        request.setAttribute("userId", userId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("purchase/transactions.jsp");
        dispatcher.forward(request, response);
    }

    private Integer getUserId(HttpServletRequest request) {
        return (request.getSession().getAttribute("userId") != null) ? 
                (Integer) request.getSession().getAttribute("userId") : 7;
    }
}
