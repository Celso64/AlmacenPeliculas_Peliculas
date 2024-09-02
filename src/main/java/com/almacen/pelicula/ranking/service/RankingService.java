package com.almacen.pelicula.ranking.service;

import com.almacen.pelicula.ranking.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankings;

}
