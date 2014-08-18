package test.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String reqURI = request.getRequestURI();

        if (reqURI.endsWith(".css") || reqURI.endsWith(".js") || reqURI.endsWith("login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

//        if (!reqURI.endsWith("login.jsp") && request.getSession().getAttribute("authenticated") == null) {
        if (request.getSession().getAttribute("authenticated") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            //request.getServletContext().getRequestDispatcher("/login").forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {}
}
