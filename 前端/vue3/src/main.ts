import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(router)
app.use(pinia)

// 定义全局baseUrl
app.provide('baseUrl', 'http://114.55.245.132:8080')
// app.provide('baseUrl', 'http://localhost:8080')


app.mount('#app')