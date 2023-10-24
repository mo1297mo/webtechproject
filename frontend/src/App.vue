<template>
  <div class="App">
    <ReservationForm />
  </div>
</template>

<script>
import ReservationForm from './components/ReservationForm.vue';

export default {
  name: 'App',
  components: {
    ReservationForm
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
