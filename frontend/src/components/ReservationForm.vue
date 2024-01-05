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
            <!-- <div>
                <label for="time">Time:</label>
                <input type="text" id="time" v-model="newReservation.time" required />
            </div> -->
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