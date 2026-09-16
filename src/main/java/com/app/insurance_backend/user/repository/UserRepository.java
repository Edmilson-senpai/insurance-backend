package com.app.insurance_backend.user.repository;

import com.app.insurance_backend.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Optional<User> findByName(String name);
}
