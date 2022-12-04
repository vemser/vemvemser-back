package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.CandidatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {

    Optional<CandidatoEntity> findCandidatoEntitiesByFormulario_IdFormulario(Integer idFormulario);

    List<CandidatoEntity> findCandidatoEntitiesByEmail(String email);
}
