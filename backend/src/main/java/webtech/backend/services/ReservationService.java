package webtech.backend.services;

import webtech.backend.model.Reservation;
import webtech.backend.repositories.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
