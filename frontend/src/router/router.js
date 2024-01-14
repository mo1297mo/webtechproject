import Vue from 'vue';
import Router from 'vue-router';
import ConfirmationPage from '@/components/ConfirmationPage.vue';
import CancelReservation from '@/components/CancelReservation.vue';
import ReservationForm from '@/components/ReservationForm.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'reservationForm',
            component: ReservationForm // Setzen Sie die ReservationForm als Startseite
        },
        {
            path: '/confirmation',
            name: 'confirmation',
            component: ConfirmationPage,
            props: true
        },
        {
            path: '/cancel-reservation', 
            name: 'cancelReservation',   
            component: CancelReservation 
        }
    ]
});
