<template>
    <div>
        <h2>Create Reservation</h2>
        <form @submit="createReservation">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" v-model="newReservation.name" required />
            </div>
            <div>
                <label for="date">Date:</label>
                <input type="date" id="date" v-model="newReservation.date" required @change="fetchAvailableTimeSlots" />
            </div>
                <label for="email">E-mail:</label>
                <input type="text" id="email" v-model="newReservation.email" required @change="fetchAvailableTimeSlots" />
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
            <button type="submit">Tisch reservieren</button>
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
        };
    },
    methods: {
        createReservation() {
            event.preventDefault();
            ReservationService.createReservation(this.newReservation)
                .then(response => {
                    console.log('Reservation created:', response.data);

                    this.$router.push({
                        name: 'confirmation',
                        params: {
                            name: this.newReservation.name,
                            date: this.newReservation.date,
                            time: this.newReservation.time
                        }
                    });

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
</style>