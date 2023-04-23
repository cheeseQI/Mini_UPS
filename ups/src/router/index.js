import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import QueryOrder from "../components/QueryOrder.vue";
import RedirectOrder from "../components/RedirectOrder.vue";

const routes = [
  {
    path: "/",
    name: "QueryOrder",
    component: QueryOrder,
  },
  {
    path: "/redirect-order",
    name: "RedirectOrder",
    component: RedirectOrder,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
