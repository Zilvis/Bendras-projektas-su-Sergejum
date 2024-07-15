package dev.zilvis.Bendras.projektas.su.Sergejum.Repository;

import dev.zilvis.Bendras.projektas.su.Sergejum.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
