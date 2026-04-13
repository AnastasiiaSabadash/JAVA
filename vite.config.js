// vite.config.js
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/cosmetic': { 
        target: 'http://localhost:8181/Lab_3_Cosmetics', // Шлях до сервлета
        changeOrigin: true,
        secure: false
      }
    }
  }
});