package controllers;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A filter that ensures a user is authenticated before allowing access to certain resources.
 * If the user is not authenticated, they are redirected to the login page.
 */
public class AuthenticationFilter implements Filter {

    /**
     * Initializes the filter. This method is called once when the filter is first loaded.
     *
     * @param filterConfig the filter configuration object
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    /**
     * Processes the request and response objects, allowing or denying access based on authentication.
     *
     * @param request the servlet request object
     * @param response the servlet response object
     * @param chain the filter chain for further processing
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if the user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login";

        // Determine if the request is for the login page or if it is a login request
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        // Allow access if logged in, or if requesting the login page
        if (isLoggedIn || isLoginRequest || isLoginPage) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURI); // Redirect to login if not authenticated
        }
    }

    /**
     * Cleans up resources used by the filter. This method is called once when the filter is destroyed.
     */
    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
