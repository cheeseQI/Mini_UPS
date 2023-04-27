<template>
    <div id="welcome-page">
        <div class="container">
            <div class="row justify-content-center align-items-center vh-100">
                <div class="col-md-8 text-center">
                    <h1 class="display-3 mb-4">Welcome to UPS</h1>
                    <p class="lead">
                        The world's largest package delivery company.
                        <br>You could query your package and redirect your package in our website.
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'WelcomePage',
};
</script>

<style scoped>
body {
    background-image: url('ups_frontend_background.jpg');
    background-size: cover;
    background-position: center;
    font-family: 'Arial', sans-serif;
}

h1 {
    color: #1e4b88;
    font-weight: bold;
}

.lead {
    color: #1e4b88;
    font-size: 1.5rem;
}
</style>
