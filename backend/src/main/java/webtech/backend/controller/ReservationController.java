package webtech.backend.controller;

import org.springframework.format.annotation.DateTimeFormat;
import webtech.backend.model.Reservation;
import webtech.backend.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("res")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // @PostMapping
    // public Reservation createReservation(@RequestBody Reservation reservation) {
    //     if(reservationService.tableAvaiable(reservation)){
    //         System.out.println("Reservation completed");
    //         return reservationService.createReservation(reservation);
    //     }else{
    //         System.out.println("Reservation not possible");
    //         return null;
    //     }
    // }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/res/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/res/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        String response = reservationService.deleteReservation(id);
        if (response.equals("Reservation removed: " + id)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/res/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(reservation);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/available-timeslots")
    public ResponseEntity<List<String>> getAvailableTimeSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int numberOfPeople) {
        List<String> availableTimeSlots = reservationService.getAvailableTimeSlots(date, numberOfPeople);
        return ResponseEntity.ok(availableTimeSlots);
    }

}
