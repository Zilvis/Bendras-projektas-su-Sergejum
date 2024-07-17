package dev.zilvis.Bendras.projektas.su.Sergejum.Repository;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}