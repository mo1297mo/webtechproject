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
                <input type="date" id="date" v-model="newReservation.date" required />
            </div>
            <div>
                <label for="time">Time:</label>
                <input type="text" id="time" v-model="newReservation.time" required />
            </div>
            <div>
                <label for="numberOfPeople">Number of People:</label>
                <input type="number" id="numberOfPeople" v-model="newReservation.numberOfPeople" required />
            </div>
            <button type="submit">Create Reservation</button>
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
                numberOfPeople: 1, // Set a default value if needed
            },
        };
    },
    methods: {
        createReservation() {
            ReservationService.createReservation(this.newReservation)
                .then(response => {
                    console.log('Reservation created:', response.data);
                    this.$router.push('/'); // Redirect to the reservation page (need to be done!)
                })
                .catch(error => {
                    console.error('Error creating reservation: ' + error);
                });
        },
    },
};
</script>  