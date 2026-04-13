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
      <li v-for="cosmetic in cosmetics" :key="cosmetic.id">
        <h4>{{ cosmetic.name }}</h4>
        <img :src="cosmetic.imageUrl" alt="Cosmetic Image" width="150" />
        <p>{{ cosmetic.description }}</p>
        <p>Об'єм: {{ cosmetic.capacity }} мл</p>
        <p>Класифікація: {{ cosmetic.classification }}</p>
        <button @click="editCosmetic(cosmetic)">Редагувати</button>
        <button @click="deleteCosmetic(cosmetic.id)">Видалити</button>
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
    const response = await fetch("/cosmetic"); // Короткий шлях як у неї
    cosmetics.value = await response.json();
  } catch (error) {
    console.error("Помилка отримання даних:", error);
  }
};

const addCosmetic = async () => {
  try {
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

const editCosmetic = (cosmetic) => {
  currentCosmetic.value = { ...cosmetic };
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
    const response = await fetch(`/cosmetic?id=${id}`, { method: "DELETE" }); // Запит з параметром ID
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