import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import store from './store'
import "./assets/css/index.scss";
// import "./assets/css/main.css";
import "./assets/icons/index.js";

createApp(App).use(router).use(store).use(ElementPlus).mount('#app')
