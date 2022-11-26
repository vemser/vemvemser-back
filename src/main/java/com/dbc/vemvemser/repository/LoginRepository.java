package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {

    Optional<LoginEntity> findLoginEntityByEmailAndSenha(String email, String senha);
}
