<template>
    <div id="query-order-container">
        <div class="query-order-form">
            <h1>Query Order</h1>
            <form @submit.prevent="queryOrderByPackageId">
                <label for="packageId">Package ID:</label>
                <input v-model="packageId" type="text" id="packageId" name="packageId" />
                <br />
                <button class="query-btn" type="submit">Query Order by Package ID</button>
            </form>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
// import * as path from "path";
// import response from "core-js/internals/is-forced";
export default {
    data() {
        return {
            packageId: "",
            username: "",
        };
    },
    methods: {
        async queryOrderByPackageId() {
            console.log("query by packageId");
            console.log(`${this.$hostname}:8080/query/1/${this.packageId}`);
            try {
                const response = await axios.get(
                    `http://${this.$hostname}:8080/query/1/${this.packageId}`
                );
                console.log(response.data.PackageInfoList[0]);
                const packageInfo = response.data.PackageInfoList[0];
                this.$router.push({
                    path: "/package-info",
                    query: {
                        packageId: packageInfo.packageId,
                        description: packageInfo.description,
                        count: packageInfo.count,
                        destX: packageInfo.destX,
                        destY: packageInfo.destY,
                        username: packageInfo.username,
                        startX: packageInfo.startX,
                        startY: packageInfo.startY,
                        currX: packageInfo.currX,
                        currY: packageInfo.currY,
                    },
                });
            } catch (error) {
                console.error(error);
                Swal.fire({
                    icon: 'error',
                    title: 'Query failed! \n Package not exist or error happens.',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        },
    },
};
</script>

<style scoped>
#query-order-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
}

.query-order-form {
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

input[type="text"] {
    width: 100%;
    padding: 10px;
    margin: 5px 0 15px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

.query-btn {
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

.query-btn:hover {
    background-color: #2962ff;
}
</style>
