package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;

public interface MyUserDetailService {
    String[] getRoles(UserEntity user);
    UserEntity updateUserDetailsById(UserEntity user, Long id);
}
