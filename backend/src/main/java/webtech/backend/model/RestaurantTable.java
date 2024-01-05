package webtech.backend.model;

import jakarta.persistence.*;

import java.util.List;

// Das wird nicht in der Datenbank gespeichert, sondern ist nur ein Java-Objekt

@Entity
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfSeats;

    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableAvailability> availabilities;


    // Constructors
    public RestaurantTable() {
    }

    public RestaurantTable(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<TableAvailability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<TableAvailability> availabilities) {
        this.availabilities = availabilities;
    }
}