<template>
    <div id="personal-center-container">
        <div class="personal-center-form">
            <h1>Personal Center</h1>
            <button class="center-btn" @click="checkMyOrders">Check My Orders</button>
            <button class="center-btn" @click="Logout">Logout</button>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            username: "",
        };
    },
    methods: {
        async checkMyOrders() {
            console.log(localStorage.getItem("username"));
            try {
                const response = await axios.get(
                    `http://${this.$hostname}:8080/query/2/${localStorage.getItem("username")}`
                );
                const packageList = response.data.PackageInfoList;
                this.$router.push({
                    path: "/package-list",
                    query: {
                        packageList: JSON.stringify(packageList),
                    },
                });
            } catch (error) {
                console.error(error);
                alert("query failed");
            }
        },
        async Logout() {
            try {
                localStorage.removeItem("username");
                localStorage.removeItem("loggedIn");
                this.$router.push({
                    path: "/",
                });
            } catch (error) {
                console.error(error);
                alert("logout failed, please try again");
            }
        },
    },
};
</script>

<style scoped>
#personal-center-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
}

.personal-center-form {
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

.center-btn {
    width: 100%;
    padding: 12px;
    background-color: #448aff;
    color: #ffffff;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-bottom: 15px;
    transition: background-color 0.2s ease-in-out;
}

.center-btn:hover {
    background-color: #2962ff;
}
</style>
