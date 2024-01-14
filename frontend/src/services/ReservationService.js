import axios from "axios";

const RESERVATION_API_URL = process.env.VUE_APP_API_BASE_URL;

class ReservationService {
  createReservation(reservation) {
    return axios.post(RESERVATION_API_URL + '/api/reservation/create', reservation);
  }

  getAvailableTimeSlots(date, numberOfPeople) {
    return axios.get(RESERVATION_API_URL + '/api/reservation/available-timeslots', {
      params: { date, numberOfPeople }
    });
  }

  // Add a method to cancel a reservation
  cancelReservation(reservationId) {
    return axios.delete(RESERVATION_API_URL + '/api/reservation/res/' + reservationId);
  }
}

export default new ReservationService();
