package com.dbc.vemvemser.repository;

import com.dbc.vemvemser.entity.TrilhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {

}
