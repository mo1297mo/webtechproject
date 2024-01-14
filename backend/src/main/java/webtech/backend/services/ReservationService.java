package webtech.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import webtech.backend.config.WebConfig;
import webtech.backend.model.*;
import webtech.backend.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private TableAvailabilityRepository tableAvailabilityRepository;
    @Autowired
    private EmailService emailService;

    @Value("${cors.allowed-origins}")
    private String corsAllowedOrigins;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        RestaurantTable suitableTable = findSuitableTable(reservation.getDate(), reservation.getTime(),
                reservation.getNumberOfPeople());
        if (suitableTable != null) {
            reservation.generateUniqueID();
            reservation.setRestaurantTable(suitableTable);
            Reservation newReservation = reservationRepository.save(reservation);
            updateTableAvailability(suitableTable, reservation.getDate(), reservation.getTime(), false);
            sendConfirmationEmail(reservation);
            return newReservation;
        } else {
            System.out.println("No available tables for the selected time and number of people.");
            return null;
        }
    }

    private void sendConfirmationEmail(Reservation newReservation) {
        String subject = "Reservation Confirmation";
        String reservationDetails = "<ul>" +
                "<li>Reservation ID: " + newReservation.getId() + "</li>" +
                "<li>Date: " + newReservation.getDate() + "</li>" +
                "<li>Time: " + newReservation.getTime() + "</li>" +
                "<li>Number of People: " + newReservation.getNumberOfPeople() + "</li>" +
                "<li>Table: " + newReservation.getRestaurantTable().getId() + "</li>" +
                "</ul>";

        String htmlContent = getString(newReservation, reservationDetails);

        emailService.sendEmail(newReservation.getEmail(), subject, htmlContent);
    }

    private String getString(Reservation newReservation, String reservationDetails) {
        String cancelReservationLink = "<p>If you want to cancel your reservation, please click <a href='" + corsAllowedOrigins + "/cancel-reservation'>here</a>.</p>";

        String htmlContent = "<h1>Reservation Confirmation</h1>" +
                "<p>Dear " + newReservation.getName() + ",</p>" +
                "<p>Thank you for your reservation.</p>" +
                "<p>Reservation details:</p>" +
                reservationDetails +
                cancelReservationLink +
                "<p>Kind regards,</p>" +
                "<p>Your Restaurant</p>";
        return htmlContent;
    }


    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public String deleteReservation(Long id) {
        if (reservationRepository.findById(id).isPresent()) {
            reservationRepository.deleteById(id);
            return "Reservation removed: " + id;
        } else {
            return "Reservation not found";
        }
    }

    private RestaurantTable findSuitableTable(LocalDate date, LocalTime time, int numberOfPeople) {
        return restaurantTableRepository.findAll().stream()
                .filter(table -> table.getNumberOfSeats() >= numberOfPeople
                        && isTableAvailableForTime(table, date, time))
                .findFirst()
                .orElse(null);
    }

    private boolean isTableAvailableForTime(RestaurantTable table, LocalDate date, LocalTime time) {
        List<TableAvailability> availabilities = tableAvailabilityRepository
                .findByRestaurantTableIdAndDate(table.getId(), date);
        return availabilities.stream()
                .anyMatch(availability -> availability.isAvailable() &&
                        !time.isBefore(availability.getStartTime()) &&
                        !time.isAfter(availability.getEndTime()));
    }

    private void updateTableAvailability(RestaurantTable table, LocalDate date, LocalTime time, boolean isAvailable) {
        List<TableAvailability> availabilities = tableAvailabilityRepository
                .findByRestaurantTableIdAndDate(table.getId(), date);
        availabilities.stream()
                .filter(availability -> !time.isBefore(availability.getStartTime())
                        && !time.isAfter(availability.getEndTime()))
                .findFirst()
                .ifPresent(availability -> {
                    availability.setAvailable(isAvailable);
                    tableAvailabilityRepository.save(availability);
                });
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.findById(reservation.getId())
                .map(existingReservation -> {
                    existingReservation.setName(reservation.getName());
                    existingReservation.setDate(reservation.getDate());
                    existingReservation.setTime(reservation.getTime());
                    existingReservation.setNumberOfPeople(reservation.getNumberOfPeople());
                    existingReservation.setRestaurantTable(reservation.getRestaurantTable());
                    existingReservation.setEmail(reservation.getEmail());
                    return reservationRepository.save(existingReservation);
                })
                .orElse(null);
    }

    public List<String> getAvailableTimeSlots(LocalDate date, int numberOfPeople) {
        LocalTime startTime = LocalTime.of(12, 0); // Example start time
        LocalTime endTime = LocalTime.of(21, 0); // Example end time

        return IntStream.iterate(0, i -> i + 30)
                .limit((endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 1800)
                .mapToObj(i -> startTime.plusMinutes(i).toString())
                .filter(time -> isTimeSlotAvailable(date, time, numberOfPeople))
                .collect(Collectors.toList());
    }

    private boolean isTimeSlotAvailable(LocalDate date, String time, int numberOfPeople) {
        List<Reservation> existingReservations = reservationRepository.findByDateAndTime(date, LocalTime.parse(time));
        return existingReservations.size() < numberOfPeople;
    }
}
