package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoRepository  extends JpaRepository<InscricaoEntity, Integer> {

    Optional<InscricaoEntity> findInscricaoEntitiesByCandidato(CandidatoEntity candidatoEntity);

}
