package webtech.backend.controller;

import webtech.backend.model.Reservation;
import webtech.backend.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        if(reservationService.tableAvaiable(reservation)){
            System.out.println("Reservation completed");
            return reservationService.createReservation(reservation);
        }else{
            System.out.println("Reservation not possible");
            return null;
        }
    }

    @GetMapping("res/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @DeleteMapping("delete/{id}")
    public String deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservation(id);
    }

    @PutMapping
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

}
