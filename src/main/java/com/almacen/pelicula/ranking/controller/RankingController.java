package com.almacen.pelicula.ranking.controller;

import com.almacen.pelicula.ranking.dto.in.RankingCreate;
import com.almacen.pelicula.ranking.dto.in.RankingUpdate;
import com.almacen.pelicula.ranking.dto.out.RankingOut;
import com.almacen.pelicula.ranking.service.RankingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ranking")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RankingController {

    @Autowired
    RankingService rankings;

    @GetMapping
    public ResponseEntity<List<RankingOut>> listAll(@RequestParam(required = false, value = "user_id") Optional<Long> idUser,
                                                    @RequestParam(required = false, value = "pelicula_id") Optional<Long> idPelicula) {

        var res = ResponseEntity.status(HttpStatus.OK);

        if (idUser.isPresent()) {
            return res.body(rankings.buscarPorUserID(idUser.get()));
        } else if (idPelicula.isPresent()) {
            return res.body(rankings.buscarPorUserID(idPelicula.get()));
        }
        return res.body(rankings.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RankingOut> listByID(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rankings.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<RankingOut> crearRanking(@Valid @RequestBody RankingCreate ranking) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var idUsuario = authentication.getPrincipal();

        log.debug(idUsuario.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(rankings.subirRanking(ranking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RankingOut> actualizarRanking(@PathVariable("id") Long id, @Valid @RequestBody RankingUpdate ranking) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(rankings.actualizarRanking(id, ranking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarRanking(@PathVariable("id") Long id) {
        rankings.borrarRanking(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
