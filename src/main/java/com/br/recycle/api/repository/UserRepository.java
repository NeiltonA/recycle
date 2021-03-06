package com.br.recycle.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIndividualRegistration(String individualRegistration);

    Optional<User> findByEmail(String email);

    // Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

   
    Optional<User> findById(Long id);

    Boolean existsByEmail(String email);

}
