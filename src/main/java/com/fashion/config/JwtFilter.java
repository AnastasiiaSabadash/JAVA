package com.fashion.config;

import com.fashion.service.JwtService;
import com.fashion.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Отримуємо заголовок Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Перевіряємо, чи заголовок коректний та містить JWT (Bearer)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Витягуємо токен (після "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Отримуємо username з токена через JwtService
        username = jwtService.extractUsername(jwt);

        // 5. Перевірка, чи username не порожній і чи користувач ще не автентифікований
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Завантажуємо інформацію про користувача з бази даних
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. Валідація токена
            if (jwtService.validateToken(jwt, userDetails)) {
                // Встановлюємо автентифікацію в контекст Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Продовжуємо виконання ланцюжка фільтрів
        filterChain.doFilter(request, response);
    }
}