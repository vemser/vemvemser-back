package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

    Optional<AvaliacaoEntity> findAvaliacaoEntitiesByInscricao_IdInscricao(Integer idInscricao);

    List<AvaliacaoEntity> findAvaliacaoEntitiesByInscricao_Candidato_Email(String email);
}
