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

    //@GetMapping
    //public ResponseEntity<?> getAllTasks(@RequestParam(value = "status", required = false) Integer status) {
    //    if(status == null) {
    //        return new ResponseEntity<>(this.taskResource.findAll(), HttpStatus.OK);
    //    }
    //    return new ResponseEntity<>(this.tacheResource.getTachesByEtat(status), HttpStatus.OK);
    //}

    @GetMapping("/all")
    public List<CarAdPostEntity> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long userEntityEmailid,
            @RequestParam(required = false) Enum<Make> make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) LocalDate year,
            @RequestParam(required = false) Integer millage,
            @RequestParam(required = false) Float price,
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
