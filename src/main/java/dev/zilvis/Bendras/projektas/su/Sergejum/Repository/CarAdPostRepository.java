package dev.zilvis.Bendras.projektas.su.Sergejum.Repository;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarAdPostRepository extends CrudRepository<CarAdPostEntity, Long>{

    @Query("SELECT make FROM CarAdPostEntity")
    List<String> findMakeCount();
}
