package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.FormularioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioRepository extends JpaRepository<FormularioEntity, Integer> {

}
