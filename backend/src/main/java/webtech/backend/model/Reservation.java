package webtech.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalTimeConverter;

@Entity
public class Reservation {

    @Id
    private Long id;

    private String name;
    private LocalDate date;
    private LocalTime time;
    private Integer numberOfPeople;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "restaurantTable_id", nullable = false)
    private RestaurantTable restaurantTable;

    // Constructors
    public Reservation() {}

    public Reservation(String name, LocalDate date, LocalTime time, int numberOfPeople, String email) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.numberOfPeople = numberOfPeople;
        this.email = email;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    // Method to generate a unique 8-digit ID
    public void generateUniqueID() {
        // Generate a random number between 10000000 (inclusive) and 100000000 (exclusive)
        this.id = ThreadLocalRandom.current().nextLong(10000000L, 100000000L);
    }
}
