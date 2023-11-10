package webtech.backend.model;

// Das wird nicht in der Datenbank gespeichert, sondern ist nur ein Java-Objekt
public class Table {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isAvailable;

    public Table(int tableNumber, int numberOfSeats, boolean isAvailable) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.isAvailable = isAvailable;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
