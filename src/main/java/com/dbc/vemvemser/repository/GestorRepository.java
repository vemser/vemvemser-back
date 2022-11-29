package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.GestorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GestorRepository extends JpaRepository<GestorEntity, Integer> {

    Optional<GestorEntity> findGestorEntityByEmailAndAndSenha(String email, String senha);

//    List<GestorEntity> findByAtivo (Integer ativo);

}
