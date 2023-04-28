<template>
  <div id="app">
    <nav>
        <router-link to="/queryOrderById">Query Order By Id</router-link>
        <router-link to="/personalCenter">Login Amazon Account</router-link>
<!--        <router-link to="/login">Login</router-link>-->
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
nav {
  display: flex;
  justify-content: space-around;
  background-color: #f8f9fa;
  padding: 1rem;
  font-size: 1.2rem;
}

nav a {
  text-decoration: none;
  color: #495057;
}

nav a.router-link-exact-active {
  color: #007bff;
  font-weight: bold;
}
</style>
