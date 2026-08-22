package com.tokio.filme.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = """
                {
                    "status": 403,
                    "error": "Forbidden",
                    "message": "Não tem permissão para acessar este recurso",
                    "path": "%s",
                    "timestamp": "%s"
                }
                """.formatted(
                request.getRequestURI(),
                LocalDateTime.now()
        );

        response.getWriter().write(json);
    }
}
