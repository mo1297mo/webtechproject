package webtech.backend.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import webtech.backend.model.RestaurantTable;
import webtech.backend.model.TableAvailability;
import webtech.backend.repositories.*;

@Component
public class DatabaseSeeder {

    @Autowired
    private RestaurantTableRepository tableRepository;
    
    @Autowired
    private TableAvailabilityRepository availabilityRepository;

    @PostConstruct
    public void updateTablesAndAvailability() {
        createOrUpdateTables();
        updateAvailabilities();
    }

    public void createOrUpdateTables() {
        if (tableRepository.count() < 10) {
            for (int i = 1; i <= 10; i++) {
                RestaurantTable table = new RestaurantTable(10); // Assuming each table has 10 seats
                tableRepository.save(table);
            }
        }
    }

    private void updateAvailabilities() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        LocalTime openingTime = LocalTime.of(12, 0); // Restaurant opens at 12:00
        LocalTime closingTime = LocalTime.of(21, 0); // Restaurant closes at 21:00
        int timeSlotDurationInMinutes = 30;

        List<RestaurantTable> tables = tableRepository.findAll();
        tables.forEach(table -> {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                LocalTime timeSlotStart = openingTime;
                while (timeSlotStart.isBefore(closingTime)) {
                    LocalTime timeSlotEnd = timeSlotStart.plusMinutes(timeSlotDurationInMinutes);
                    if (availabilityRepository.findByRestaurantTableIdAndDateAndStartTimeAndEndTime(table.getId(), date, timeSlotStart, timeSlotEnd).isEmpty()) {
                        TableAvailability availability = new TableAvailability();
                        availability.setTable(table);
                        availability.setDate(date);
                        availability.setStartTime(timeSlotStart);
                        availability.setEndTime(timeSlotEnd);
                        availability.setAvailable(true);
                        availabilityRepository.save(availability);
                    }
                    timeSlotStart = timeSlotEnd;
                }
            }
        });
    }
}