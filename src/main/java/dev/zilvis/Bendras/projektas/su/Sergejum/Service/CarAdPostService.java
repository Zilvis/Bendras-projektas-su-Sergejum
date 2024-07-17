package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;

import java.util.List;
import java.util.Map;

public interface CarAdPostService {
    CarAdPostEntity createNewAd (CarAdPostEntity carAdPostEntity);
    List<CarAdPostEntity> getAll();
    String deleteById(Long id);
    CarAdPostEntity updateById(CarAdPostEntity newCarAdPostEntity, Long id);
    Map<String, Long> getModelsAndCount();
}
