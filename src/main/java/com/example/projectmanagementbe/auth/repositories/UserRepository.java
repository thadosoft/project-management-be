package com.example.projectmanagementbe.auth.repositories;

import com.example.projectmanagementbe.auth.enums.UserRole;
import com.example.projectmanagementbe.auth.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByUsername(String username);

  List<User> findByRole(UserRole role);
}
