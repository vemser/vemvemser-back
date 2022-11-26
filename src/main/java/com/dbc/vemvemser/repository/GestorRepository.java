package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.GestorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestorRepository extends JpaRepository<GestorEntity, Integer> {
}
