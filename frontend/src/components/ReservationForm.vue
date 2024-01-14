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
        <li><a href="#">Home</a></li>
        <li><a href="/cancel-reservation">Cancel Reservation</a></li>
      </ul>
    </div>

  
      <!-- Reservation Form -->
      <h2></h2>
      <h2>Book a table</h2>
      <form @submit="createReservation">
        <div>
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="newReservation.name" required />
        </div>
        <div>
          <label for="date">Date:</label>
          <input type="date" id="date" v-model="newReservation.date" required @change="fetchAvailableTimeSlots" />
        </div>
        <div>
          <label for="email">E-mail:</label>
          <input type="text" id="email" v-model="newReservation.email" required @change="fetchAvailableTimeSlots" />
        </div>
        <div>
          <label for="numberOfPeople">Number of People:</label>
          <input type="number" id="numberOfPeople" v-model="newReservation.numberOfPeople" required @change="fetchAvailableTimeSlots" />
        </div>
        <div v-if="availableTimeSlots.length > 0">
          <h3>Available Time Slots</h3>
          <div>
            <button v-for="timeSlot in availableTimeSlots" :key="timeSlot" @click="selectTimeSlot(timeSlot)">
              {{ timeSlot }}
            </button>
          </div>
        </div>
        <button type="submit">Book</button>
      </form>
    </div>
  </template>
  
<script>
import ReservationService from '@/services/ReservationService';

export default {
    data() {
        return {
            newReservation: {
                name: '',
                date: '',
                time: '',
                email: '',
                numberOfPeople: 1, // Set a default value if needed
            },
            availableTimeSlots: [],
            isMenuOpen: false,
        };
    },
    methods: {
        createReservation() {
    event.preventDefault();
    ReservationService.createReservation(this.newReservation)
        .then(response => {
            console.log('Reservation created:', response.data);

            // Redirect to the confirmation page
            if (this.$route.path !== '/confirmation') {
                this.$router.push({
                    name: 'confirmation',
                    params: {
                        name: this.newReservation.name,
                        date: this.newReservation.date,
                        time: this.newReservation.time
                    }
                });
            }

        })
        .catch(error => {
            console.error('Error creating reservation: ' + error);
        });
},

        fetchAvailableTimeSlots() {
            if (this.newReservation.date && this.newReservation.numberOfPeople) {
                ReservationService.getAvailableTimeSlots(this.newReservation.date, this.newReservation.numberOfPeople)
                .then(response => {
                    this.availableTimeSlots = response.data;
                })
                .catch(error => {
                    console.error('Error fetching times: ' + error);
                });
            }
            },
            selectTimeSlot(timeSlot) {
            this.newReservation.time = timeSlot;
        },

        toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  },
    },

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

h2 {
    color: #333;
    text-align: center;
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

/* Form Styling */
form {
    background: #ffffff;
    padding: 30px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    max-width: 500px;
    margin: 40px auto;
    transition: all 0.3s ease-in-out;
}

form div {
    margin-bottom: 15px;
}
label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #555;
}

input[type='text'],
input[type='date'],
input[type='number'] {
    width: 100%;
    padding: 12px;
    border: 2px solid #ddd;
    border-radius: 5px;
    box-sizing: border-box; /* Added for better width control */
    transition: border-color 0.3s;
}

input[type='text']:focus,
input[type='date']:focus,
input[type='number']:focus {
    border-color: #80bdff;
    outline: none;
}

button {
    background: #007bff;
    color: #fff;
    border: none;
    padding: 12px 25px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background: #0056b3;
}

/* Time Slot Button Styles */
.available-time-slots button {
    .available-time-slots {
    margin-top: 20px;
    text-align: center;
}
}

.available-time-slots button {
    background: #28a745;
    margin: 5px;
    border-radius: 4px;
    color: white;
    padding: 8px 12px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s;
}

.available-time-slots button:hover {
    background: #218838;
}


/* Responsive Design */
@media (max-width: 600px) {
    form {
        width: calc(100% - 30px);
        padding: 20px;
    }
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
</style>