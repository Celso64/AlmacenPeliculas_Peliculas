package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.exception.ResourceNotFoundException;
import com.almacen.pelicula.pelicula.dto.DirectorTest;
import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.in.DirectorUpdate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@WebMvcTest(controllers = {DirectorController.class})
public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    // -------- Test GET -------------

    @Test
    public void listarDirectores() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");
        when(directorService.listar()).thenReturn(List.of(directorOut));

        mockMvc.perform(MockMvcRequestBuilders.get("/director"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(directorOut.id()))
                .andExpect(jsonPath("$[0].nombre").value(directorOut.nombre()))
                .andExpect(jsonPath("$[0].apellido").value(directorOut.apellido()));
    }

    @Test
    public void buscarDirectorExistente() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");
        when(directorService.buscarPorID(1L)).thenReturn(directorOut);

        mockMvc.perform(MockMvcRequestBuilders.get("/director/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tim"))
                .andExpect(jsonPath("$.apellido").value("Burton"));
    }

    @Test
    public void buscarDirectorNoExistente() throws Exception {
        when(directorService.buscarPorID(2L)).thenThrow(new ResourceNotFoundException("El director no existe."));

        mockMvc.perform(MockMvcRequestBuilders.get("/director/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("El director no existe."));
    }

    // -------- Test POST -------------

    @Test
    public void agregarDirectorBien() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");
        when(directorService.crearDirector(any(DirectorCreate.class))).thenReturn(directorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("Tim", "Burton").toJSON())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tim"))
                .andExpect(jsonPath("$.apellido").value("Burton"));
    }

    @Test
    public void agregarDirectorSinNombre() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");
        when(directorService.crearDirector(any(DirectorCreate.class))).thenReturn(directorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("", "Burton").toJSON())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("Nombre obligatorio."));
    }

    @Test
    public void agregarDirectorSinApellido() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");
        when(directorService.crearDirector(any(DirectorCreate.class))).thenReturn(directorOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("Tim", "").toJSON())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.apellido").value("Apellido obligatorio."));
    }

    // -------- Test PUT -------------

    @Test
    public void modificarDirectorCompleto() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Zack", "Snyder");

        when(directorService.update(Mockito.anyLong(), any(DirectorUpdate.class))).thenReturn(directorOut);

        mockMvc.perform(MockMvcRequestBuilders.put("/director/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new DirectorTest("Zack", "Snyder").toJSON())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Zack"))
                .andExpect(jsonPath("$.apellido").value("Snyder"));
    }

    // -------- Test DELETE -------------

    @Test
    public void borrarDirectorBien() throws Exception {
        when(directorService.borrarDirector(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/director/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void borrarDirectorInexistente() throws Exception {
        when(directorService.borrarDirector(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/director/2"))
                .andExpect(status().isNotFound());
    }
}
