package webtech.backend.repositories;

import webtech.backend.model.Reservation;

import java.util.List;
import java.time.*; 

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // This method assumes you have 'date' as LocalDate and 'time' as a String in your Reservation entity
    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);
}
