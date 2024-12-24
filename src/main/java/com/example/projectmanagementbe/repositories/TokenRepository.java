package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.TokenEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

  @Query("""
      select t from TokenEntity t inner join UserEntity u on t.user.id = u.id
      where u.id = :userId and (t.expired = false or t.revoked = false)
      """)
  List<TokenEntity> findAllValidTokensByUser(Long userId);

  Optional<TokenEntity> findByToken(String token);
}
