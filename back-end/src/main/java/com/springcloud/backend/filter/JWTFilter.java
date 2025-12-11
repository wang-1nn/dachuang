package com.springcloud.backend.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.springcloud.backend.util.JWTUtil;
import com.springcloud.backend.util.RestBean;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * JWT过滤器：保护业务接口，放行登录注册等公开接口。
 */
@Slf4j
@WebFilter(filterName = "JWTFilter", urlPatterns = "/*")
public class JWTFilter implements Filter {
    private static final String AUTH_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("UTF-8");

        // 放行无需鉴权的接口
        if (requestURI.startsWith("/api/auth/login") || requestURI.startsWith("/api/auth/register") || requestURI.startsWith("/actuator")) {
            chain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader(AUTH_HEADER);
        if (token == null || token.isBlank()) {
            response.getWriter().write(JSON.toJSONString(RestBean.failure(401, "未提供token")));
            return;
        }

        Map<String, Claim> userData = JWTUtil.verifyToken(token.replace("Bearer ", ""));
        if (userData == null) {
            response.getWriter().write(JSON.toJSONString(RestBean.failure(401, "token不合法")));
            return;
        }

        request.setAttribute("id", userData.get("id").asLong());
        request.setAttribute("username", userData.get("username").asString());
        request.setAttribute("role", userData.get("role").asString());
        chain.doFilter(req, res);
    }
}
