import Vue from 'vue';
import Router from 'vue-router';
import ConfirmationPage from '@/components/ConfirmationPage.vue';
import CancelReservation from '@/components/CancelReservation.vue'; // Import the CancelReservation component

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
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
