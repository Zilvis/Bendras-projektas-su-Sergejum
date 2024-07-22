package dev.zilvis.Bendras.projektas.su.Sergejum.Security;

import dev.zilvis.Bendras.projektas.su.Sergejum.Service.MyUserDetailServiceImpl;
import dev.zilvis.Bendras.projektas.su.Sergejum.webtoken.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
            if (userDetails != null && jwtService.isTokenValid(jwt)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Create a cookie to store the JWT
                Cookie jwtCookie = new Cookie("JWT_TOKEN", jwt);
                jwtCookie.setHttpOnly(true); // Ensure the cookie is not accessible via JavaScript for security reasons
                jwtCookie.setSecure(true); // Use only if your application is accessed over HTTPS
                jwtCookie.setPath("/"); // Set the path as per your application's context
                jwtCookie.setMaxAge(60 * 60); // Set expiry time, e.g., 1 hour (in seconds)

                // Add the cookie to the response
                response.addCookie(jwtCookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}