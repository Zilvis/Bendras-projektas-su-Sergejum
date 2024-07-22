package dev.zilvis.Bendras.projektas.su.Sergejum.Repository;

import dev.zilvis.Bendras.projektas.su.Sergejum.DTO.Filter;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarAdPostRepository extends JpaRepository<CarAdPostEntity, Long> {

    @Query("SELECT make FROM CarAdPostEntity")
    Set<String> findMakeCount();

    @Query("SELECT MIN(price) FROM CarAdPostEntity")
    Float findMinPrice();

    @Query("SELECT MAX(price) FROM CarAdPostEntity")
    Float findMaxPrice();

    //@Query("SELECT ** FROM CarAdPostEntity ORDER BY id DESC")
    List<CarAdPostEntity> findAllBy(Pageable pageable);

    Page<CarAdPostEntity> findAll(Specification<CarAdPostEntity> spec, Pageable paging);

    List<CarAdPostEntity> getByUserEntityEmail(@NonNull Long userEntityEmail);

    Optional<CarAdPostEntity> findByModel(String model);

    @Query("SELECT c.model AS model FROM CarAdPostEntity c WHERE c.make = ?1 GROUP BY c.model")
    List<CarAdPostEntity> findByMake(String make);

    @Query("SELECT c.model AS model FROM CarAdPostEntity c WHERE c.make = ?1 GROUP BY c.model")
    List<String> findByMakeByModel(String make);
}
