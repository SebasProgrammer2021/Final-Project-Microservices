package co.edu.uniquindio.inventario.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    @Autowired
    private JwtAuthConverter jwtAuthConverter;
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {


        http
                .authorizeExchange( e ->
                        e
                                .pathMatchers("/api/auth/**")
                                .permitAll()
                                .pathMatchers("/api/inventory/**")
                                .hasRole(ADMIN)
//                                .pathMatchers("/api/prestamos/**")
//                                .hasAnyRole("admin", "user")
                                .anyExchange()
                                .authenticated());

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        //Deshabilita la protección CSRF con la siguiente línea:
        http.csrf().disable();
        return http.build();
    }
}
