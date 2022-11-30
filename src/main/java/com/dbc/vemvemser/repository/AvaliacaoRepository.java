package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

    Optional<AvaliacaoEntity> findAvaliacaoEntitiesByInscricao_IdInscricao(Integer idInscricao);
}
