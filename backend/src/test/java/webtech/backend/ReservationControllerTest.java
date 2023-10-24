package webtech.backend;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import webtech.backend.controller.ReservationController;
import webtech.backend.services.ReservationService;
import webtech.backend.model.Reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void createReservation_returnsCreatedReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setName("John Doe");
        reservation.setDate(LocalDate.now());
        reservation.setTime("12:00");
        reservation.setNumberOfPeople(4);

        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        mockMvc.perform(post("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());
    }
}