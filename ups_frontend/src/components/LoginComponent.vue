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
            // 在这里添加逻辑以处理登录操作，例如向后端发送请求进行身份验证。
            // 以下是一个简单的示例，仅检查用户名和密码是否匹配预定义的值。

            if (this.username === "user" && this.password === "password") {
                this.loginMessage = "Login successful!";
            } else {
                this.loginMessage = "Invalid username or password.";
            }
            this.$router.push("/");
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
