package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {
}
