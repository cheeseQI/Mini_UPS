<template>
    <div id="welcome-page">
        <div class="container">
            <div class="row justify-content-center align-items-center vh-100">
                <div class="col-md-8 text-center welcome-content">
                    <h1 class="display-3 mb-4">Welcome to UPS</h1>
                    <p class="lead">
                        The world's largest package delivery company.
                        <br />You could query your package and redirect your package in
                        our website.
                    </p>
                    <button @click="navigateToLogin" class="welcome-btn">Get Started</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
body {
    background-image: url("../assets/ups_frontend_background.jpg");
    background-size: cover;
    background-position: center;
    font-family: "Arial", sans-serif;
}

.welcome-content {
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 10px;
    padding: 30px;
}

h1 {
    color: #1e4b88;
    font-weight: bold;
}

.lead {
    color: #1e4b88;
    font-size: 1.5rem;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.welcome-btn {
    padding: 12px 24px;
    background-color: #448aff;
    color: #ffffff;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.2s ease-in-out;
}

.welcome-btn:hover {
    background-color: #2962ff;
}
</style>

<script>
export default {
    name: "WelcomePage",
    methods: {
        navigateToLogin() {
            this.$router.push("/personalCenter");
        },
    },
};
</script>
