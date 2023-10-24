<template>
    <div class="confirmation">
        <h2>Bestätigung</h2>
        <p>Vielen Dank, {{ name }}! Ihre Reservierung wurde erfolgreich erstellt für den {{ date }} um {{ time }} Uhr.</p>
        <button @click="goHome">Zur Startseite</button>
    </div>
</template>
  
<script>
export default {
    props: {
        name: {
            type: String,
            required: true
        },
        date: {
            type: String,
            required: true
        },
        time: {
            type: String,
            required: true
        }
    },
    methods: {
        goHome() {
            this.$router.push('/'); // Geht zur Hauptseite zurück
        }
    }
}
</script>
  
<style scoped>
.confirmation {
    padding: 20px;
    text-align: center;
    border: 1px solid #e8e8e8;
    border-radius: 10px;
    max-width: 500px;
    margin: 40px auto;
}

button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #2c3e50;
    color: #fff;
    cursor: pointer;
}
</style>
  