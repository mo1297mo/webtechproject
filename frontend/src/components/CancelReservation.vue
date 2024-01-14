<template>
  <div>
      <h2>Cancel Reservation</h2>
      <form @submit.prevent="cancelReservation" class="cancel-form">
          <div class="form-group">
              <label for="reservationId">Reservation ID:</label>
              <input type="" id="reservationId" v-model.number="reservationId" required />
          </div>
          <button type="submit" class="cancel-button">Cancel Reservation</button>
          <p v-if="message" class="message">{{ message }}</p>
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
/* Component Styles */
.cancel-form {
  background-color: #f0f8ff; /* Light Blue Background Color */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 15px;
}

/* Label Styles */
label {
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 5px;
}

/* Input Styles */
input[type='number'] {
  width: 100%;
  padding: 10px;
  border: 2px solid #a0c5e8; /* Light Blue Border Color */
  border-radius: 5px;
  transition: border-color 0.3s;
}

input[type='number']:focus {
  border-color: #007bff; /* Blue Border Color on Focus */
  outline: none;
}

/* Button Styles */
.cancel-button {
  background-color: #007bff; /* Blue Button Background Color */
  color: #fff;
  border: none;
  padding: 12px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-weight: bold;
}

.cancel-button:hover {
  background-color: #0056b3; /* Darker Blue on Hover */
}

/* Message Styles */
.message {
  color: #333;
  font-weight: bold;
  margin-top: 15px;
  text-align: center;
}
</style>
