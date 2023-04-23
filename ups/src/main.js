import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import Socket from "./socket";
const socket = new Socket("ws://your-backend-url");

const app = createApp(App);
app.provide('$socket', socket);
app.use(router).mount('#app');