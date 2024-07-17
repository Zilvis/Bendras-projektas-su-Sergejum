package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.CarAdPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarAdPostServiceImpl implements CarAdPostService{

    @Autowired
    private CarAdPostRepository carAdPostRepository;

    @Override
    public CarAdPostEntity createNewAd (CarAdPostEntity carAdPostEntity){
        return carAdPostRepository.save(carAdPostEntity);
    }

    @Override
    public List<CarAdPostEntity> getAll(){
        return (List<CarAdPostEntity>) carAdPostRepository.findAll();
    }

    @Override
    public String deleteById(Long id){
        carAdPostRepository.deleteById(id);
        return "OK"; // TODO padaryti patikra kad posto savininkas atitinka :)
    }

    @Override
    public CarAdPostEntity updateById(CarAdPostEntity newCarAdPostEntity, Long id) {
        return null;
    }

    @Override
    public Map<String, Long> getModelsAndCount() {

        List<String> results = carAdPostRepository.findMakeCount();
        Map<String, Long> makeCount = new HashMap<>();

        for (String model : results) {
            makeCount.put(model, makeCount.getOrDefault(model, 0L) + 1);
        }
        return makeCount;
    }
}
