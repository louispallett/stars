import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import macrosPlugin from 'vite-plugin-babel-macros';

export default defineConfig({
  plugins: [macrosPlugin(), react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        secure: false,
        ws: true,
      },
    }    
  },
});