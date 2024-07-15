package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.CarAdPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarAdPostService {

    @Autowired
    private CarAdPostRepository carAdPostRepository;

    public CarAdPostEntity createNewAd (CarAdPostEntity carAdPostEntity){
        return carAdPostRepository.save(carAdPostEntity);
    }

    public List<CarAdPostEntity> getAll(){
        return carAdPostRepository.findAll();
    }

    public String deleteById(Long id){
        carAdPostRepository.deleteById(id);
        return "OK"; // TODO padaryti patikra kad posto savininkas atitinka :)
    }

    public CarAdPostEntity updateById(CarAdPostEntity newCarAdPostEntity, Long id) {
        return null;
    }
}
