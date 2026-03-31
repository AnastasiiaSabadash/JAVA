<template>
  <div v-if="cosmetics" class="centered-wrapper">
    <h2>{{ cosmetics.name }}</h2>
    <img :src="cosmetics.imageUrl" alt="Cosmetics image" style="max-width: 300px;" />
    <p>{{ cosmetics.description }}</p>
    <p>Об'єм: {{ cosmetics.capacity }} мл</p>
    <p>Класифікація: {{ cosmetics.classification }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import myPhoto from './assets/bottle.jpg'; // фото з assets
const cosmetics = ref(null);
onMounted(async () => {
  const response = await fetch('/cosmetic');
  const data = await response.json();
  
  //зміна: підставляємо фото в imageUrl
  data.imageUrl = myPhoto; 
  cosmetics.value = data;
});
</script>
