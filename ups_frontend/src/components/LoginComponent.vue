<template>
    <div id="login-container">
        <div class="login-form">
            <h1>Login</h1>
            <div class="input-group">
                <input type="text" v-model="username" placeholder="Enter Username" />
                <span class="icon">&#xe906;</span>
            </div>
            <div class="input-group">
                <input type="password" v-model="password" placeholder="Enter Password" />
                <span class="icon">&#xe90b;</span>
            </div>
            <button @click="login">Login</button>
<!--            <div v-if="loginMessage" class="login-message">{{ loginMessage }}</div>-->
        </div>
    </div>
</template>
<script>
import axios from "axios";
import Swal from "sweetalert2";
// import app from "@/App.vue"; // Import SweetAlert2

export default {
    //
    // mounted() {
    //     this.name = app.config.globalProperties;
    // },

    data() {
        return {
            username: '',
            password: '',
        };
    },
    methods: {
        async login() {
            console.log(`http://${this.$hostname}:8080/query/2/${localStorage.getItem("username")}`);

            try {
                const response = await axios.post(`http://${this.$hostname}:8080/login`, {
                    username: this.username,
                    password: this.password,
                });
                console.log(response.data);
                if (response.data === true) {
                    // Use SweetAlert2 instead of alert
                    Swal.fire({
                        icon: 'success',
                        title: 'Login success',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    localStorage.setItem("loggedIn", "true");
                    localStorage.setItem("username", this.username);
                    this.$router.push("/personalCenter");
                } else {
                    // Use SweetAlert2 instead of alert
                    Swal.fire({
                        icon: 'error',
                        title: 'Login failed',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    this.$router.push("/login");
                }
            } catch (error) {
                console.error("Error creating order:", error);
            }
        },
    },
};
</script>

<style scoped>
#login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
}

.login-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 350px;
    padding: 40px;
    background-color: #ffffff;
    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
    border-radius: 10px;
}

h1 {
    margin-bottom: 30px;
    font-size: 28px;
}

.input-group {
    position: relative;
    margin-bottom: 25px;
    width: 100%;
}

.input-group input {
    width: 100%;
    max-width: 100%; /* Add this line */
    padding: 12px 40px 12px 12px;
    font-size: 16px;
    border: 1px solid #dcdcdc;
    border-radius: 5px;
    box-sizing: border-box; /* Add this line */
}

.input-group .icon {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    font-family: "icomoon" !important;
    font-size: 20px;
    color: #9e9e9e;
}

button {
    width: 100%;
    padding: 12px;
    background-color: #448aff;
    color: #ffffff;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.2s ease-in-out;
}

button:hover {
    background-color: #2962ff;
}

.login-message {
    margin-top: 15px;
    color: #d32f2f;
    font-size: 14px;
}
</style>
