package com.dbc.vemvemser.service;


import com.dbc.vemvemser.repository.GestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestorService {

    private final GestorRepository gestorRepository;

    private final ObjectMapper objectMapper;


}
