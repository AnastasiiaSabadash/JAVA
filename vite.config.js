import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
plugins: [vue()],
build: {
  outDir: 'dist',
  emptyOutDir: true
},
server: {
  proxy: {
    '/cosmetic': {
      target:'http://localhost:8181/Lab_2_Cosmetics-1.0-SNAPSHOT/',
      changeOrigin: true,
      secure: false
    }
  }
}
});