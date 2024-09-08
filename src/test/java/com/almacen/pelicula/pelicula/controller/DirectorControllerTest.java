package com.almacen.pelicula.pelicula.controller;

import com.almacen.pelicula.pelicula.dto.DirectorTest;
import com.almacen.pelicula.pelicula.dto.in.DirectorCreate;
import com.almacen.pelicula.pelicula.dto.out.DirectorOut;
import com.almacen.pelicula.pelicula.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    // -------- Test DELETE -------------

    @Test
    public void borrarDirectorBien() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");

        when(directorService.borrarDirector(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/director/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void borrarDirectorInexistente() throws Exception {
        DirectorOut directorOut = new DirectorOut(1L, "Tim", "Burton");

        when(directorService.borrarDirector(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/director/2"))
                .andExpect(status().isNotFound());
    }
}
