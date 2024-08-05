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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ClaimFoodServlet")
public class ClaimFoodServlet extends HttpServlet {

    private DonationFoodManager manager = new DonationFoodManager();

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

        // 获取登录用户ID
        Integer userId = getUserId(request);

        try {
            manager.claimFood(Integer.parseInt(id), Integer.parseInt(need), userId);
            request.setAttribute("userId", userId);  // Set userId attribute for display
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listDonationFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DonationFoodVO> list = new ArrayList<>();
        try {
            list.addAll(manager.getAllDonationFood());
            request.setAttribute("list", list);

            // 获取登录用户ID并设置为请求属性
            Integer userId = getUserId(request);
            request.setAttribute("userId", userId);

            request.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/claim-food.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void myClaimedFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ClaimedFood> list = manager.getAllClaimedFood();
        request.setAttribute("list", list);
        
        // 获取登录用户ID并设置为请求属性
        Integer userId = getUserId(request);
        request.setAttribute("userId", userId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("charitable/my-claim-food.jsp");
        dispatcher.forward(request, response);
    }

    private Integer getUserId(HttpServletRequest request) {
        return (request.getSession().getAttribute("userId") != null) ? 
                (Integer) request.getSession().getAttribute("userId") : 7;
    }
}
