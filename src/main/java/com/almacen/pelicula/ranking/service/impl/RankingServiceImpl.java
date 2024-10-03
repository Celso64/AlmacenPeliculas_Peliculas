package com.almacen.pelicula.ranking.service.impl;

import com.almacen.pelicula.pelicula.entity.Pelicula;
import com.almacen.pelicula.pelicula.repository.PeliculaRepository;
import com.almacen.pelicula.ranking.dto.in.RankingCreate;
import com.almacen.pelicula.ranking.dto.in.RankingUpdate;
import com.almacen.pelicula.ranking.dto.out.RankingOut;
import com.almacen.pelicula.ranking.entity.Usuario;
import com.almacen.pelicula.ranking.repository.RankingRepository;
import com.almacen.pelicula.ranking.repository.UsuarioRepository;
import com.almacen.pelicula.ranking.service.RankingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RankingServiceImpl implements RankingService {

    @Autowired
    RankingRepository rankings;

    @Autowired
    UsuarioRepository users;

    @Autowired
    PeliculaRepository peliculas;

    @Override
    public RankingOut subirRanking(RankingCreate datos) {
        Usuario u = users.findById(datos.idCliente()).orElseThrow();
        Pelicula p = peliculas.findById(datos.idPelicula()).orElseThrow();
        var res = rankings.save(datos.toModel(u, p));

        log.info("SERVICE RANKING CREATE - {} ; {} ; {}", res.getId(), res.getUsername(), res.getTituloPelicula());

        return RankingOut.fromModel(res);
    }

    @Override
    public List<RankingOut> listarTodo() {
        var res = rankings.findAll().stream().map(RankingOut::fromModel).toList();
        log.info("SERVICE RANKING LIST - LENGHT: {}", res.size());
        return res;
    }

    @Override
    public RankingOut buscarPorID(Long id) {
        var res = RankingOut.fromModel(rankings.findById(id).orElseThrow());
        log.info("SERVICE RANKING FIND - PARAM: ID ; ID: {}", res.id());
        return res;
    }

    @Override
    public List<RankingOut> buscarPorUserID(Long idUser) {
        var res = rankings.findByUsuarioId(idUser).stream().map(RankingOut::fromModel).toList();
        log.info("SERVICE RANKING FIND - PARAM: USER ; LENGHT: {}", res.size());
        return res;
    }

    @Override
    public List<RankingOut> buscarPorPeliculaID(Long idPelicula) {
        var res = rankings.findByPeliculaId(idPelicula).stream().map(RankingOut::fromModel).toList();
        log.info("SERVICE RANKING FIND - PARAM: PELICULA ; LENGHT: {}", res.size());
        return res;
    }

    @Override
    public RankingOut actualizarRanking(Long id, RankingUpdate datos) {
        log.info("SERVICE RANKING UPDATE - ID: {}", id);
        return null;
    }

    @Override
    public void borrarRanking(Long id) {
        rankings.deleteById(id);
        log.info("SERVICE RANKING DELETE - ID: {}", id);
    }
}
