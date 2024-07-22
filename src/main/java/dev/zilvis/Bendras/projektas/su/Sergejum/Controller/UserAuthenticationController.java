package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.MyUserRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.MyUserDetailServiceImpl;
import dev.zilvis.Bendras.projektas.su.Sergejum.webtoken.JwtService;
import dev.zilvis.Bendras.projektas.su.Sergejum.webtoken.LoginForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserEntity createUser(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return myUserRepository.save(user);
    }

    // LOGIN kontroleris grazina JwtToken kuris galioja 120 min
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @PostMapping("/authenticate/cookie")
    public ResponseEntity<String> authenticateAndGetTokens(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));

            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setHttpOnly(true); // Make the cookie HTTP only for security
            cookie.setSecure(true); // Set it to true if your application is served over HTTPS
            cookie.setPath("/"); // The path the cookie is visible to
            cookie.setMaxAge(24 * 60 * 60); // Set expiry time (in seconds)

            response.addCookie(cookie);

            return ResponseEntity.ok("Authentication successful");
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}