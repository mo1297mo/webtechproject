<template>
  <div>

    <!-- Header -->
    <header class="header">
      <div class="header-content">
        <div class="logo">
          <img src="../assets/logo.jpeg" alt="Logo" />
        </div>
      </div>
    </header>

    <!-- Menu Toggle Button -->
    <div class="menu-button" @click="toggleMenu">
      <i class="fas fa-bars"></i>
    </div>

    <!-- Menu -->
    <div class="menu" :class="{ 'menu-open': isMenuOpen }">
      <!-- Menu Content -->
      <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/cancel-reservation">Cancel Reservation</a></li>
      </ul>
    </div>


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
          message: '', // Feedback message
          isMenuOpen: false
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
      },

      toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen;
      }

  }
};
</script>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&display=swap');

body {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
    color: #333;
    line-height: 1.6;
}


/* Header Styles */
.header {
  background-color: #333;
  padding: 10px 0;
  color: white;
  position: relative;
}

.header-content {
  display: flex; /* Changed to flex */
  justify-content: space-between;
  align-items: center; /* Added to vertically center logo and menu button */
  max-width: 1200px; /* Adjust the max-width as needed */
  margin: 0 auto;
  padding: 0 20px;
  position: relative;
}

.logo img {
  max-height: 60px; /* Adjust the height of your logo */
}

.menu-button {
  cursor: pointer;
  position: absolute;
  top: 50%;
  right: 20px;
  transform: translateY(-50%);
}

/* Menu Button Styles */
.menu-button {
  position: fixed;
  top: 20px;
  right: 20px;
  cursor: pointer;
  z-index: 1000;
}

/* Menu Styles */
.menu {
  position: fixed;
  top: 0;
  right: -300px; /* Start with the menu off-screen */
  width: 300px;
  height: 100%;
  background-color: #333;
  transition: right 0.3s;
  z-index: 999;
  margin: 0; /* Added to remove any margin */
  padding: 0; /* Added to remove any padding */
  border: none; /* Added to remove any border */
}

.menu-open {
  right: 0; /* Move the menu on-screen when it's open */
}

.menu ul {
  list-style: none;
  padding: 0;
}

.menu li {
  margin: 0;
  padding: 0;
}

.menu a {
  display: block;
  padding: 15px;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.2s;
}

.menu a:hover {
  background-color: #555;
}


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
