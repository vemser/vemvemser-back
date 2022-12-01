package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository  extends JpaRepository<InscricaoEntity, Integer> {

    Optional<InscricaoEntity> findInscricaoEntitiesByCandidato_IdCandidato(Integer idCandidato);

    List<InscricaoEntity> findInscricaoEntitiesByCandidato_Email(String email);

}
