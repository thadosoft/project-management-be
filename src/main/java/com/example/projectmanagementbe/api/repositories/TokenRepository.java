package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.auth.models.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, String> {

  @Query("""
      select t from Token t inner join User u on t.user.id = u.id
      where u.id = :userId and (t.expired = false or t.revoked = false)
      """)
  List<Token> findAllValidTokensByUser(String userId);

  Optional<Token> findByToken(String token);
}
