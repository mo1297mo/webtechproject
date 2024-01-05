package webtech.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webtech.backend.model.TableAvailability;

import java.time.*;
import java.util.List;

@Repository
public interface TableAvailabilityRepository extends JpaRepository<TableAvailability, Long> {
    List<TableAvailability> findByRestaurantTableIdAndDate(Long restaurantTableId, LocalDate date);
    List<TableAvailability> findByRestaurantTableIdAndDateAndStartTimeAndEndTime(Long restaurantTableId, LocalDate date, LocalTime startTime, LocalTime endTime);

}

