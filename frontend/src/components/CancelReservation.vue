
<template>
    <div>
      <h2>Cancel Reservation</h2>
      <form @submit.prevent="cancelReservation">
        <div>
          <label for="reservationId">Reservation ID:</label>
          <input type="number" id="reservationId" v-model.number="reservationId" required />
        </div>
        <button type="submit">Cancel Reservation</button>
        <p v-if="message">{{ message }}</p>
      </form>
    </div>
  </template>
  
  <script>


import ReservationService from '@/services/ReservationService';

export default {
  data() {
    return {
      reservationId: null, // The ID of the reservation to cancel
      message: '' // Feedback message
    };
  },
  methods: {
    cancelReservation() {
      // Call to the reservation service to cancel the reservation
      ReservationService.cancelReservation(this.reservationId)
        .then(() => {
          this.message = `Reservation with ID ${this.reservationId} has been successfully cancelled.`;
        })
        .catch(error => {
          // If the response has a data property with a message, use it, otherwise use a generic message
          const errorMessage = error.response && error.response.data && error.response.data.message
            ? error.response.data.message
            : 'There was a problem cancelling the reservation.';
          this.message = `Error cancelling reservation: ${errorMessage}`;
        });
    }
  }
};
</script>

  
  <style scoped>
  /* Add your styles here */
  /* Reuse styles from your previous component as needed */
  </style>
  