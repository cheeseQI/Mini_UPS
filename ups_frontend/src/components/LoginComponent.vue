<template>
    <div>
        <h1>Login</h1>
        <input type="text" v-model="username" placeholder="Enter Username" />
        <input type="password" v-model="password" placeholder="Enter Password" />
        <button @click="login">Login</button>
        <div v-if="loginMessage">{{ loginMessage }}</div>
    </div>
</template>

<script>

import axios from "axios";

export default {
    data() {
        return {
            username: "",
            password: "",
            loginMessage: ""
        };
    },
    methods: {
        async login() {

            try {
                const response = await axios.post("http://localhost:8080/login", {
                    username: this.username,
                    password: this.password,
                });
                console.log(response.data);
                if(response.data === true){
                    alert("login success");
                    localStorage.setItem("loggedIn","true") ;
                    localStorage.setItem("username",this.username) ;
                    this.$router.push("/");
                }
                else{
                    alert("login failed");
                    this.$router.push("/");
                }

            } catch (error) {
                console.error("Error creating order:", error);
            }
        }
    }
};
</script>

<style>
#app {
    font-family: Arial, sans-serif;
    text-align: center;
    margin-top: 60px;
}
</style>
