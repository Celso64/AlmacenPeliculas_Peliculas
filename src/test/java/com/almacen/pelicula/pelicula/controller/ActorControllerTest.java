package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.DirectorTest;
import com.almacen.pelicula.pelicula.dto.in.ActorCreate;
import com.almacen.pelicula.pelicula.dto.out.ActorOut;
import com.almacen.pelicula.pelicula.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ActorController.class})
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorServiceImpl actorService;

    // -------- Test GET -------------

    @Test
    public void listarActores() throws Exception {
        ActorOut actorOut = new ActorOut(1L, "Chris", "Evans");
        when(actorService.listAll()).thenReturn(List.of(actorOut));

        mockMvc.perform(MockMvcRequestBuilders.get("/actor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(actorOut.id()))
                .andExpect(jsonPath("$[0].nombre").value(actorOut.nombre()))
                .andExpect(jsonPath("$[0].apellido").value(actorOut.apellido()));
    }

    @Test
    public void buscarActorExistente() throws Exception {
        ActorOut actorOut = new ActorOut(1L, "Chris", "Evans");
        when(actorService.findByID(1L)).thenReturn(actorOut);

        mockMvc.perform(MockMvcRequestBuilders.get("/actor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(actorOut.nombre()))
                .andExpect(jsonPath("$.apellido").value(actorOut.apellido()));
    }

    @Test
    public void buscarActorNoExistente() throws Exception {
        when(actorService.findByID(2L)).thenThrow(new ResourceNotFoundException("El actor no existe."));

        mockMvc.perform(MockMvcRequestBuilders.get("/actor/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("El actor no existe."));
    }

    // -------- Test POST -------------

    @Test
    public void agregarActorBien() throws Exception {
        ActorOut actorOut = new ActorOut(1L, "Chris", "Evans");
        when(actorService.crearActor(any(ActorCreate.class))).thenReturn(actorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("Chris", "Evans").toJSON())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Chris"))
                .andExpect(jsonPath("$.apellido").value("Evans"));
    }

    @Test
    public void agregarActorSinNombre() throws Exception {
        ActorOut actorOut = new ActorOut(1L, "Chris", "Evans");
        when(actorService.crearActor(any(ActorCreate.class))).thenReturn(actorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("", "Evans").toJSON())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("Nombre obligatorio."));
    }

    @Test
    public void agregarActorSinApellido() throws Exception {
        ActorOut actorOut = new ActorOut(1L, "Chris", "Evans");
        when(actorService.crearActor(any(ActorCreate.class))).thenReturn(actorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/actor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("Chris", "").toJSON())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.apellido").value("Apellido obligatorio."));
    }

}
