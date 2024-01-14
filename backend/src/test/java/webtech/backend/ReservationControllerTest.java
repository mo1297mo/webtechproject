package webtech.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
// Import the necessary class
// Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import webtech.backend.controller.ReservationController;
import webtech.backend.services.ReservationService;
import webtech.backend.model.Reservation;
import webtech.backend.repositories.ReservationRepository;
import webtech.backend.seed.DatabaseSeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is; // Import the necessary class
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Import the necessary class
import java.util.Optional; // Import the necessary class

import static org.hamcrest.Matchers.hasSize; // Import the necessary class

@ExtendWith(MockitoExtension.class)

@WebMvcTest(ReservationController.class) // or @SpringBootTest if you need full application context
// @AutoConfigureMockMvc // if using @SpringBootTest
// @SpringBootTest(properties = {
// "spring.datasource.url=jdbc:postgresql://localhost:5432/testdb"})
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private DatabaseSeeder databaseSeeder;

    @Test
    public void createReservation_returnsCreatedReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setName("John Doe");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.of(12, 0, 0));
        reservation.setEmail("example@gmail.com");
        reservation.setNumberOfPeople(4);

        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        mockMvc.perform(post("/api/reservation/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateReservation_ReservationExists_UpdatesAndReturnsReservation() throws Exception {
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(1L);
        updatedReservation.setName("Updated Name");
        updatedReservation.setDate(LocalDate.now().plusDays(1));
        updatedReservation.setTime(LocalTime.of(12, 0, 0));
        updatedReservation.setEmail("");
        updatedReservation.setNumberOfPeople(4);

        when(reservationService.updateReservation(any(Reservation.class))).thenReturn(updatedReservation);

        mockMvc.perform(put("/api/reservation/res/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name")));
    }

    @Test
    public void deleteReservation_ReservationExists_DeletesReservation() throws Exception {
        when(reservationService.deleteReservation(1L)).thenReturn("Reservation removed: 1");

        mockMvc.perform(delete("/api/reservation/res/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation removed: 1"));
    }

    @Test
    public void getAllReservations_ReturnsListOfReservations() throws Exception {
        List<Reservation> reservations = Arrays.asList(
                new Reservation("Alice", LocalDate.now(), LocalTime.of(18, 0), 2, "alice@example.com"),
                new Reservation("Bob", LocalDate.now().plusDays(1), LocalTime.of(19, 0), 3, "bob@example.com"));

        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservation/res"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].name", is("Bob")));
    }

    @Test
    public void getReservationById_NotFound_ReturnsNotFoundStatus() throws Exception {
        Long reservationId = 123L;
        when(reservationService.getReservationById(reservationId)).thenReturn(null);

        mockMvc.perform(get("/api/reservation/res/" + reservationId))
                .andExpect(status().isNotFound());
    }

}