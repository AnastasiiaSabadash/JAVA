package com.fashion.config;

import com.fashion.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FashionCatalogApplication {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Повністю вимикаємо CSRF, оскільки ми авторизуємося через JWT
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Явно підключаємо CORS конфігурацію, щоб Spring Security бачив наш CorsConfig.java
                .cors(cors -> cors.configure(http))

                // 3. Налаштовуємо правила доступу до ендпоінтів
                .authorizeHttpRequests(auth -> auth
                        // Дозволяємо технічні запити OPTIONS від браузера (Preflight запити)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Дозволяємо відкриті сторінки авторизації, документації та Swagger
                        .requestMatchers("/login", "/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Захищаємо роботу з товарами — туди пускаємо тільки з валідним JWT токеном
                        .requestMatchers("/api/items/**").authenticated()

                        // Усі інші запити в системі також вимагають авторизації
                        .anyRequest().authenticated()
                )

                // 4. Робимо сесію STATELESS (без збереження стану на сервері), бо використовуємо токени
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 5. Підключаємо наш сервіс завантаження користувачів
                .userDetailsService(userDetailsService)

                // 6. Додаємо наш JwtFilter у ланцюжок безпеки ПЕРЕД стандартним фільтром авторизації
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}