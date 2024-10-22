package com.example.getiryemek_clone.security;

import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import com.example.getiryemek_clone.repository.AdminRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.RestaurantAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CostumerRepository costumerRepository;
    private final AdminRepository adminRepository;
    private final RestaurantAdminRepository restaurantAdminRepository;

    @Override
    public CostumUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Stream.of(
                        costumerRepository.findByEmail(email).map(this::costumerToCustomUserDetails),
                        adminRepository.findByEmail(email).map(this::adminToCustomUserDetails),
                        restaurantAdminRepository.findByEmail(email).map(this::restaurantAdminToCustomUserDetails)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    private CostumUserDetails costumerToCustomUserDetails(Costumer costumer) {
        return CostumUserDetails.builder()
                .id(costumer.getId())
                .name(costumer.getName())
                .username(costumer.getEmail())
                .email(costumer.getEmail())
                .password(costumer.getPassword())
                .roles(Set.of(costumer.getRole()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private CostumUserDetails adminToCustomUserDetails(Admin admin) {
        return CostumUserDetails.builder()
                .id(admin.getId())
                .name(admin.getName())
                .username(admin.getEmail())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .roles(Set.of(admin.getRole()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private CostumUserDetails restaurantAdminToCustomUserDetails(RestaurantAdmin restaurantAdmin) {
        return CostumUserDetails.builder()
                .id(restaurantAdmin.getId())
                .name(restaurantAdmin.getName())
                .username(restaurantAdmin.getEmail())
                .email(restaurantAdmin.getEmail())
                .password(restaurantAdmin.getPassword())
                .roles(Set.of(restaurantAdmin.getRole()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }



}
