package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CarAdPostService {
    CarAdPostEntity createNewAd (CarAdPostEntity carAdPostEntity);
    List<CarAdPostEntity> getAll();
    ResponseEntity deleteById(Long id);
    ResponseEntity<?>  updateById(CarAdPostEntity newCarAdPostEntity, Long id);
    List<String> getModelsAndCount();
    Float getPrice(Boolean minOrMax);

    Object getOneById(Long id);
}
