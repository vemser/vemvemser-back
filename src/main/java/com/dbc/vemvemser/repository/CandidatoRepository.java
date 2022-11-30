package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.FormularioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {

    Optional<CandidatoEntity> findCandidatoEntitiesByFormulario(FormularioEntity formulario);
}
