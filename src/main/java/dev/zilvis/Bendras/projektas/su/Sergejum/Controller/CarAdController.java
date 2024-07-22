package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.MyUserRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.CarAdPostService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarAdController {

    @Autowired
    CarAdPostService carAdPostService;

    @Autowired
    private MyUserRepository userRepository;

//    @PostMapping("/new")
//    public CarAdPostEntity createNew (@RequestBody CarAdPostEntity newCarAdPostEntity){
//        return carAdPostService.createNewAd(newCarAdPostEntity);
//    }

    @PostMapping("/new")
    public ResponseEntity<?> newUser (@Nullable Authentication authentication, @RequestBody CarAdPostEntity newCarAdPostEntity) {
        if (authentication == null){
            return new ResponseEntity<>(false ,HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(carAdPostService.createNewAd(newCarAdPostEntity));
    }

    @GetMapping
    public ResponseEntity<?> getById(@RequestParam Long id){
        return ResponseEntity.ok(carAdPostService.getOneById(id));
    }

    @GetMapping("/models")
    public ResponseEntity<?> getAllExistingModels (){
        Map<String, Long> modelAndModelCount = carAdPostService.getModelsAndCount();

        if (modelAndModelCount.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No existing data in database!");
        }
        return ResponseEntity.ok(modelAndModelCount);
    }

    // TODO padaryti su marke ir modeliu min max reiksmes
    @GetMapping("/price")
    public ResponseEntity<?> getPrice(
            @RequestParam boolean minOrMax){
        float price;
        if (minOrMax){
            price = carAdPostService.getPrice(true);
        } else {
            price = carAdPostService.getPrice(false);
        }
        return ResponseEntity.ok(price);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        return carAdPostService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById (@RequestBody CarAdPostEntity newCarAdPostEntity, @PathVariable("id") Long id){
        return carAdPostService.updateById(newCarAdPostEntity, id);
    }
}
