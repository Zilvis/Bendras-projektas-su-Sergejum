package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/test/user")
    public String welcomeUser(){
        return "Welcome user";
    }

    @GetMapping("/test/admin")
    public String welcomeAdmin(){
        return "Welcome user";
    }
}
