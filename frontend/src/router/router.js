import Vue from 'vue';
import Router from 'vue-router';
import ConfirmationPage from '@/components/ConfirmationPage.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/confirmation',
            name: 'confirmation',
            component: ConfirmationPage,
            props: true
        }
    ]
});
