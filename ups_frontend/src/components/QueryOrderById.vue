<template>
    <div>
        <h1>Query Order</h1>
        <form @submit.prevent="queryOrderByPackageId">
            <label for="packageId">Package ID:</label>
            <input v-model="packageId" type="text" id="packageId" name="packageId" />
            <br />
            <button type="submit">Query Order by Package ID</button>
        </form>
    </div>
</template>

<script>
import axios from "axios";
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

            try {
                const response = await axios.get(
                    `http://localhost:8080/query/1/${this.packageId}`
                );
                console.log(response.data.PackageInfoList[0]);
                const packageInfo = response.data.PackageInfoList[0];
                this.$router.push({
                    path:"/package-info",
                    query:{
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
                    }
                });
            } catch (error) {
                console.error(error);
                alert("query failed, please try again later。");
            }
        },
    },
};
</script>
