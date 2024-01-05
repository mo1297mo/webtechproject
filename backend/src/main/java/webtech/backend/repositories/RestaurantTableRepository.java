package webtech.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webtech.backend.model.RestaurantTable;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}

