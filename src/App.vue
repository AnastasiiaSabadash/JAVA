<template>
  <div>
    <h1>Система управління косметикою</h1>

    <h3>{{ isEditing ? "Редагувати косметику" : "Додати нову косметику" }}</h3>
    <form @submit.prevent="isEditing ? updateCosmetic() : addCosmetic()">
      <input v-model="currentCosmetic.name" placeholder="Назва" required />
      <input v-model="currentCosmetic.imageUrl" placeholder="URL зображення" required />
      <input v-model="currentCosmetic.description" placeholder="Опис" required />
      <input type="number" v-model="currentCosmetic.capacity" placeholder="Об'єм (мл)" required />
      <input v-model="currentCosmetic.classification" placeholder="Класифікація" required />
      <button type="submit">{{ isEditing ? "Оновити" : "Додати" }}</button>
      <button type="button" v-if="isEditing" @click="cancelEdit">Скасувати</button>
    </form>

    <h3>Список косметики</h3>
    <ul>
      <li v-for="item in cosmetics" :key="item.id">
        <h4>{{ item.name }}</h4>
        <img :src="item.imageUrl" alt="Cosmetic Image" width="150" />
        <p>{{ item.description }}</p>
        <p>Об'єм: {{ item.capacity }}</p>
        <p>Класифікація: {{ item.classification }}</p>
        <button @click="editCosmetic(item)">Редагувати</button>
        <button @click="deleteCosmetic(item.id)">Видалити</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const cosmetics = ref([]);
const currentCosmetic = ref({ id: null, name: "", imageUrl: "", description: "", capacity: "", classification: "" });
const isEditing = ref(false);

const fetchCosmetics = async () => {
  try {
    const response = await fetch("/cosmetic");
    cosmetics.value = await response.json();
  } catch (error) {
    console.error("Помилка отримання даних:", error);
  }
};

const addCosmetic = async () => {
  try {
    // АВТОМАТИЧНА ПРИВ'ЯЗКА ASSETS
    // Якщо користувач ввів назву файлу без шляху (наприклад, "bottle1.jpg")
    if (currentCosmetic.value.imageUrl && !currentCosmetic.value.imageUrl.includes('/')) {
      currentCosmetic.value.imageUrl = `/src/assets/${currentCosmetic.value.imageUrl}`;
    }

    const response = await fetch("/cosmetic", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(currentCosmetic.value),
    });

    if (response.ok) {
      fetchCosmetics();
      resetForm();
    }
  } catch (error) {
    console.error("Помилка додавання:", error);
  }
};

const editCosmetic = (item) => {
  currentCosmetic.value = { ...item };
  isEditing.value = true;
};

const updateCosmetic = async () => {
  try {
    const response = await fetch("/cosmetic", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(currentCosmetic.value),
    });
    if (response.ok) {
      fetchCosmetics();
      resetForm();
    }
  } catch (error) {
    console.error("Помилка оновлення:", error);
  }
};

const deleteCosmetic = async (id) => {
  try {
    const response = await fetch(`/cosmetic?id=${id}`, { method: "DELETE" });
    if (response.ok) fetchCosmetics();
  } catch (error) {
    console.error("Помилка видалення:", error);
  }
};

const resetForm = () => {
  currentCosmetic.value = { id: null, name: "", imageUrl: "", description: "", capacity: "", classification: "" };
  isEditing.value = false;
};

const cancelEdit = () => {
  resetForm();
};

onMounted(fetchCosmetics);
</script>

<style>
body {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 20px auto;
}
input {
  display: block;
  margin: 5px 0;
  padding: 8px;
  width: 100%;
}
</style>