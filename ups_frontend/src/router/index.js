
// src/router/index.js

import { createRouter, createWebHistory } from "vue-router";
// import store from "../store";
import RedirectOrder from "../components/RedirectOrder.vue";
import Login from "@/components/LoginComponent.vue";
import PackageList from "@/components/PackageList.vue";
import PackageInfo from "@/components/PackageInfo.vue";
import Welcome from "@/components/Welcome.vue";
import QueryOrderById from "@/components/QueryOrderById.vue";
import PersonalCenter from "@/components/PersonalCenter.vue";
const routes = [
  {
    path: "/",
    name: "WelcomePage",
    component: Welcome,
    meta: { requiresAuth: false },
  },
  {
    path: "/queryOrderById",
    name: "QueryOrderById",
    component: QueryOrderById,
    meta: { requiresAuth: false },
  },
  {
    path: "/personalCenter",
    name: "PersonalCenter",
    component: PersonalCenter,
    meta: { requiresAuth: true },
  },
  {
    path: "/redirect",
    name: "RedirectOrder",
    component: RedirectOrder,
    meta: { requiresAuth: true },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: { requiresAuth: false },
  },
  {
    path: "/package-list",
    name: "PackageList",
    component: PackageList,
    meta: { requiresAuth: true },
  },
  {
    path: "/package-info",
    name: "PackageInfo",
    component: PackageInfo,
    meta: { requiresAuth: false },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});


router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem("loggedIn") ;
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  if (requiresAuth && !loggedIn) {
    next({ name: "Login" });
  } else {
    next();
  }
});
export default router;

// import { createRouter, createWebHistory } from "vue-router";
// // import QueryOrder from "../components/QueryOrderById.vue";
// import RedirectOrder from "../components/RedirectOrder.vue";
// import Login from "@/components/LoginComponent.vue";
// import PackageList from "@/components/PackageList.vue";
// import PackageInfo from "@/components/PackageInfo.vue";
// import Welcome from "@/components/Welcome.vue";
// import QueryOrderById from "@/components/QueryOrderById.vue";
//
// const routes = [
//   {
//     path: "/",
//     name: "WelcomePage",
//     component: Welcome,
//     meta: { requiresAuth: false },
//   },
//   {
//     path: "/queryOrderById",
//     name: "QueryOrderById",
//     component: QueryOrderById,
//     meta: { requiresAuth: false },
//   },
//   {
//     path: "/redirect",
//     name: "RedirectOrder",
//     component: RedirectOrder,
//     meta: { requiresAuth: false },
//   },
//   {
//     path: "/login",
//     name: "Login",
//     component: Login,
//     meta: { requiresAuth: false },
//   },
//   {
//     path: "/package-list",
//     name: "PackageList",
//     component: PackageList,
//     meta: { requiresAuth: false },
//   },
//   {
//     path: "/package-info",
//     name: "PackageInfo",
//     component: PackageInfo,
//     meta: { requiresAuth: false },
//   },
// ];
//
// const router = createRouter({
//   history: createWebHistory(process.env.BASE_URL),
//   routes,
// });
//
// // 导航守卫
// router.beforeEach((to, from, next) => {
//   const loggedIn = localStorage.getItem("userToken") ;
//
//   const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
//
//   if (requiresAuth && !loggedIn) {
//     next({ name: "Login" });
//   } else {
//     next();
//   }
// });
//
// export default router;