package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscricaoRepository  extends JpaRepository<InscricaoEntity, Integer> {

    InscricaoEntity findByIdInscricao(Integer idInscricao);

}
