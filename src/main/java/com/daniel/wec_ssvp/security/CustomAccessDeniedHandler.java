package com.daniel.wec_ssvp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = "SEM_ROLE";
        if (authentication != null && authentication.getAuthorities() != null
                && !authentication.getAuthorities().isEmpty()) {
            role = authentication.getAuthorities().iterator().next().getAuthority();
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 403);
        body.put("error", "FORBIDDEN");
        body.put("message", "Acesso negado, seu usuário '" + role + "' não tem autorização para esta ação");
        body.put("path", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), body);
    }
}
