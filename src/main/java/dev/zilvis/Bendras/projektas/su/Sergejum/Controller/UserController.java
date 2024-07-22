package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.MyUserRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.MyUserDetailService;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.MyUserDetailServiceImpl;
import dev.zilvis.Bendras.projektas.su.Sergejum.webtoken.JwtService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping
    public ResponseEntity<?> getEmail(@RequestParam String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        // Surasti vartotoją pagal ID
        UserEntity user = userRepository.findByUsername(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!user.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own data");
        }
        Long s = userRepository.findByUsername(email).get().getId();


        return ResponseEntity.ok(s);
    }

    @GetMapping("/test")
    public String test (){
        return "user";
    }

    // TODO Sutvarkyti
    @GetMapping("/autoLogin")
    public ResponseEntity<?> autoLogin(HttpServletRequest request) {
        try {
            // Get all cookies from the request
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                // TODO
                // Find the specific cookie (e.g., "jwtToken")
                Optional<Cookie> jwtTokenCookie = Arrays.stream(cookies)
                       // .filter(cookie -> "jwtToken".equals(cookie.getName()))
                        .findFirst();

                if (jwtTokenCookie.isPresent() && jwtService.isTokenValid(jwtTokenCookie.get().getValue())) {
                    String jwtToken = jwtTokenCookie.get().getValue();
                    return ResponseEntity.ok(jwtToken);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Optionally log the exception
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Updeitina tik jei jwt key yra galiojantis ir tik jei username (Email yra atitinkamas)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserEntity user = userRepository.findById(id).orElse(null);

        if (!user.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own data");
        }

        // Atnaujinti vartotojo duomenis
        myUserDetailService.updateUserDetailsById(updatedUser,id);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (myUserDetailService.isAdmin(authentication.getName())){
            if (userRepository.findById(id).isEmpty() || myUserDetailService.isAdmin(userRepository.findById(id).get().getUsername())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or he have ADMIN role!");
            }
            myUserDetailService.deleteById(id);
            return ResponseEntity.ok("Deleted!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission!");
    }
}
