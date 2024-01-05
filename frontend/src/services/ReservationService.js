import axios from "axios";

const RESERVATION_API_URL = "http://localhost:8080/api/reservation";

class ReservationService {
  createReservation(reservation) {
    return axios.post(RESERVATION_API_URL + '/create', reservation);
  }

  getAvailableTimeSlots(date, numberOfPeople) {
    return axios.get(RESERVATION_API_URL + '/available-timeslots', {
      params: { date, numberOfPeople }
    });
  }
}

export default new ReservationService();
