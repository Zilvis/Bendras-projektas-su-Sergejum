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

    // TODO Uzsetinti vartotojo id skelbimu duomenu bazeje!
    
    @PostMapping("/new")
    public CarAdPostEntity createNew (@RequestBody CarAdPostEntity newCarAdPostEntity){
        return carAdPostService.createNewAd(newCarAdPostEntity);
    }

    // @RequestMapping(value = {""}, method = RequestMethod.GET)
    // public String search(
    //  @RequestParam Map<String,String> allRequestParams, ModelMap model) {
    //    return "viewName";
    // }

    // public String updateFoos(@RequestParam Map<String,String> allParams) {
    // return "Parameters are " + allParams.entrySet();}

    // Grazins visus skelbimus ir per parametrus
    // TODO !
    @GetMapping("/all") // Galbut pakeisti i /ads
    public List<CarAdPostEntity> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long userEntityEmailid,
            @RequestParam(required = false) Enum<Make> make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) LocalDate year,
            @RequestParam(required = false) int millage,
            @RequestParam(required = false) float price,
            @RequestParam(required = false) Enum<FuleType> fuelType
            ){
        return carAdPostService.getAll();
    }
    
    // https://www.dailycodebuffer.com/spring-requestparam-annotation/
    // TODO Padaryti patikra del istrynimo
    // TODO Padaryti profilyje user skelbimu skaiciu ir istrynus skelbima pagald user nusiminusuotu
    
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
