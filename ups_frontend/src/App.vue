<template>
    <div id="app">
        <nav>
            <router-link to="/queryOrderById">Query Order By Id</router-link>
            <router-link to="/personalCenter">Personal Center</router-link>
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
    background-color: #448aff;
    padding: 1rem;
    font-size: 1.2rem;
    border-radius: 5px;
    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
}

nav a {
    text-decoration: none;
    color: #ffffff;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    transition: background-color 0.2s ease-in-out;
}

nav a:hover {
    background-color: #2962ff;
}

nav a.router-link-exact-active {
    background-color: #2962ff;
    font-weight: bold;
}
</style>
