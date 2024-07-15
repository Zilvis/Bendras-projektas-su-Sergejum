package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.FuleType;
import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.Make;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.CarAdPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarAdController {

    @Autowired
    CarAdPostService carAdPostService;

    @PostMapping("/new")
    public CarAdPostEntity createNew (@RequestBody CarAdPostEntity newCarAdPostEntity){
        return carAdPostService.createNewAd(newCarAdPostEntity);
    }

    @GetMapping("/all")
    public List<CarAdPostEntity> getAll(
            @RequestParam(required = false) Enum<Make> make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) LocalDate year,
            @RequestParam(required = false) int millage,
            @RequestParam(required = false) float price,
            @RequestParam(required = false) Enum<FuleType> fuelType
            ){
        return carAdPostService.getAll();
    }

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
