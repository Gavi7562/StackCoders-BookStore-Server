package com.stackcoders.bookstore.repository;

import com.stackcoders.bookstore.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    Optional<JwtToken> findByToken(String token);

    @Query("select t from JwtToken t where t.userId = :userId and t.isRevoked = false and t.isExpired = false")
    List<JwtToken> findAllValidTokensByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update JwtToken t set t.isRevoked = true where t.token = :token")
    void revokeByToken(@Param("token") String token);
}
