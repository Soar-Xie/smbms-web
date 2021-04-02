package com.soar.filter;

import com.soar.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //从session中获取用户
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        if (user == null) {
            System.out.println("进入了");
            response.sendRedirect("/smbms/error.jsp");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {

    }
}
