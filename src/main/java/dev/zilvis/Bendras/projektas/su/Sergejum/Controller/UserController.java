package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.MyUserRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.MyUserDetailService;
import dev.zilvis.Bendras.projektas.su.Sergejum.webtoken.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;

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


    // TODO User not found sutvarkyti
    // TODO Jeigu iseina po update jwt token padaryti negaliojanti (Sena)
    // TODO Padaryti patikra jei email yra egzistuojantis kad neleistu jo keisti !
    // Updeitina tik jei jwt key yra galiojantis ir tik jei username (Email yra atitinkamas)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Surasti vartotoją pagal ID
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Patikrinti, ar vartotojas bando keisti tik savo duomenis
        if (!user.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own data");
        }

        // Atnaujinti vartotojo duomenis
        myUserDetailService.updateUserDetailsById(updatedUser,id);
        return ResponseEntity.ok("User updated successfully");
    }
    // Busi tik admin interface prieinamas
    // Delete mapping "/delete/{id}"
}
