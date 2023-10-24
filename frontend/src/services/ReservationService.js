import axios from "axios";

const RESERVATION_API_URL = "http://localhost:8080/api/reservation";

class ReservationService {

    createReservation(reservation) {
        return axios.post(RESERVATION_API_URL, reservation);
    }
}

export default new ReservationService();