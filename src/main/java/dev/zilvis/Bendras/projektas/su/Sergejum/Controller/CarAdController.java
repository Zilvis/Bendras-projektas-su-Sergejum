package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.CarAdPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarAdController {

    @Autowired
    CarAdPostService carAdPostService;

    @PostMapping("/new")
    public CarAdPostEntity createNew (@RequestBody CarAdPostEntity newCarAdPostEntity){
        return carAdPostService.createNewAd(newCarAdPostEntity);
    }

    @PostMapping("/raw")
    public ResponseEntity<byte[]> receiveRawByteArray(@RequestBody byte[] byteArray) {
        return new ResponseEntity<>(byteArray, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> filterCars(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) LocalDate yearFrom,
            @RequestParam(required = false) LocalDate yearTo,
            @RequestParam(required = false) Integer maxMillage,
            @RequestParam(required = false) Float priceFrom,
            @RequestParam(required = false) Float priceTo,
            @RequestParam(required = false) String fuelType) {

        List<CarAdPostEntity> cars = carAdPostService.getAll();

        List<CarAdPostEntity> filteredCars = cars.stream()
                .filter(car -> (make == null        || car.getMake().equalsIgnoreCase(make)))
                .filter(car -> (model == null       || car.getModel().equalsIgnoreCase(model)))
                .filter(car -> (yearFrom == null    || car.getYear().getYear() >= yearFrom.getYear()))
                .filter(car -> (yearTo == null      || car.getYear().getYear() >= yearTo.getYear()))
                .filter(car -> (maxMillage == null  || car.getMillage() <= maxMillage))
                .filter(car -> (fuelType == null    || car.getFuelType().equalsIgnoreCase(fuelType)))
                .filter(car -> (priceFrom == null   || car.getPrice() >= priceFrom))
                .filter(car -> (priceTo == null     || car.getPrice() <= priceTo))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredCars);
    }

    @GetMapping("/models")
    public ResponseEntity<?> getAllExistingModels (){
        Map<String, Long> modelAndModelCount = carAdPostService.getModelsAndCount();

        if (modelAndModelCount.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No existing data in database!");
        }
        return ResponseEntity.ok(modelAndModelCount);
    }

    //TODO Garzinti list unikaliu model

    //TODO List unikaliu visus pagal model esamas make

    //TODO pagrazint isflitruotu auto min max price



    // TODO Padaryti patikra del istrynimo
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id){
        carAdPostService.deleteById(id);
        return "OK";
    }

    @PutMapping("/update/{id}")
    public CarAdPostEntity updateById (@RequestBody CarAdPostEntity newCarAdPostEntity, @PathVariable("id") Long id){
        return carAdPostService.updateById(newCarAdPostEntity, id);
    }
}
