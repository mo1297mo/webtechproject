package webtech.backend.services;

import webtech.backend.model.Reservation;
import webtech.backend.repositories.ReservationRepository;

import org.checkerframework.checker.units.qual.s;
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

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public String deleteReservation(Long id) {
        if (reservationRepository.findById(id).orElse(null) == null) {
            return "Reservation not found";
        }else{
            reservationRepository.deleteById(id);
            return "Reservation removed: " + id;
        }
    }

    public Reservation updateReservation(Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(reservation.getId()).orElse(null);
        if(tableAvaiable(reservation) == false) {
            System.out.println("Reservation not possible");
            return null;
        }
        existingReservation.setName(reservation.getName());
        existingReservation.setDate(reservation.getDate());
        existingReservation.setTableNumber(reservation.getTableNumber());
        existingReservation.setNumberOfPeople(reservation.getNumberOfPeople());
        existingReservation.setTime(reservation.getTime());
        System.out.println("Reservation updated");
        return reservationRepository.save(existingReservation);
    }

    public List<Reservation> getReservationsByTableNumber(int tableNumber) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getTableNumber() == tableNumber) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public List<Reservation> getReservationsByDate(String date) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getDate().equals(date)) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public List<Reservation> getReservationsByTime(String time) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getTime().equals(time)) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public boolean tableAvaiable (Reservation reservation) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation r : reservations) {
            if (r.getDate().equals(reservation.getDate()) 
                && r.getTime().equals(reservation.getTime()) 
                && r.getTableNumber() == reservation.getTableNumber()) {
                return false;
            }
        }
        return true;
    }
}
