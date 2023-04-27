import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import QueryOrder from "../components/QueryOrder.vue";
import RedirectOrder from "../components/RedirectOrder.vue";
import Login from "@/components/LoginComponent.vue";
import PackageList from "@/components/PackageList.vue";
import PackageInfo from "@/components/PackageInfo.vue";
import Welcome from "@/components/Welcome.vue"
// import App from "@/App.vue";

const routes = [
  {
    path: "/",
    name: "WelcomePage",
    component: Welcome,
  },
  {
    path: "/query",
    name: "QueryOrder",
    component: QueryOrder,
  },
  {
    path: "/redirect",
    name: "RedirectOrder",
    component: RedirectOrder,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/package-list",
    name: "PackageList",
    component: PackageList,
  },
  {
    path: "/package-info",
    name: "PackageInfo",
    component: PackageInfo,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
