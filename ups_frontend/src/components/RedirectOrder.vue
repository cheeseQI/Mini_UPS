<template>
    <div id="redirect-order">
        <h1>Redirect Order</h1>
        <form @submit.prevent="redirectOrder">
            <div class="form-field">
                <label for="packageId">Package ID: {{$route.query.packageId}}</label>
            </div>
            <div class="form-field">
                <label for="description">Description: {{$route.query.description}}</label>
            </div>
            <div class="form-field">
                <label for="newX">New X:</label>
                <input v-model="newX" type="number" id="newX" name="newX" min="0"  />
            </div>
            <div class="form-field">
                <label for="newY">New Y:</label>
                <input v-model="newY" type="number" id="newY" name="newY" min="0"  />
            </div>
            <button class="action-btn" type="submit">Redirect Order</button>
        </form>
    </div>
</template>
<script>
// ...
import Swal from "sweetalert2";
import axios from "axios";

export default {
    // ...
    methods: {
        async queryOrderByUsername() {
            console.log("query by username");

            try {
                const response = await axios.get(
                    `http://${this.$hostname}:8080/query/2/${this.username}`
                );
                const packageList = response.data.PackageInfoList;
                this.$router.push({
                    path: "/package-list",
                    query: {
                        packageList: JSON.stringify(packageList)
                    }
                });
            } catch (error) {
                console.error(error);
                // Replace alert with SweetAlert2
                Swal.fire({
                    icon: 'error',
                    title: 'Redirect failed',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        },
        async redirectOrder() {
            try {
                console.log(this.$route.query.packageId);
                const response = await axios.post(
                    `http://${this.$hostname}:8080/redirect`, {
                    packageId: this.$route.query.packageId,
                    newX: this.newX,
                    newY: this.newY,
                });
                console.log(response.data);
                // Replace alert with SweetAlert2
                Swal.fire({
                    icon: response.data.includes("succeeded") ? 'success' : 'error',
                    title: response.data,
                    showConfirmButton: false,
                    timer: 1500
                });
                this.$router.push("/personalCenter");
            } catch (error) {
                console.error("Error creating order:", error);
            }
        },
    },
};
</script>

<style scoped>
#redirect-order {
    max-width: 600px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin: 30px auto;
}

h1 {
    color: #1e4b88;
    font-weight: bold;
    margin-bottom: 20px;
}

.form-field {
    margin-bottom: 20px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
}

.action-btn {
    background-color: #1e4b88;
    color: #ffffff;
    font-size: 16px;
    padding: 10px 20px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    transition: background-color 0.2s;
}

.action-btn:hover {
    background-color: #356aa0;
}
</style>
