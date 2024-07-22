package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MyUserDetailService {
    String[] getRoles(UserEntity user);
    UserEntity updateUserDetailsById(UserEntity user, Long id);
    Long getUserId(Authentication authentication);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public Boolean isAdmin(String username) throws UsernameNotFoundException;

    void deleteById(Long id);
}
