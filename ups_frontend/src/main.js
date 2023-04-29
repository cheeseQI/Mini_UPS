import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// Vue.prototype.$globalVariable = 'Hello, world!';
const app = createApp(App);
app.config.globalProperties.$hostname = 'vcm-30653.vm.duke.edu';
localStorage.clear();
app.use(router).mount('#app');