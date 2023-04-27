<template>
    <div>
        <h1>Query Order</h1>
        <form @submit.prevent="queryOrderByPackageId">
            <label for="packageId">Package ID:</label>
            <input v-model="packageId" type="text" id="packageId" name="packageId" />
            <br />
            <button type="submit">Query Order by Package ID</button>
        </form>
        <form @submit.prevent="queryOrderByUserId">
            <label for="userId">User ID:</label>
            <input v-model="userId" type="text" id="userId" name="userId" />
            <br />
            <button type="submit">Query Order by User ID</button>
        </form>
<!--        <router-view :packageInfo="packageInfo" :packageList="packageList"></router-view>-->
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            packageId: "",
            userId: "",
            // packageInfo: {}, // 将这些数据传递给子组件
            // packageList: [],
        };
    },
    // provide() {
    //     return {
    //         packageInfo: this.packageInfo,
    //         packageList: this.packageList,
    //     };
    // },
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
                        userId: packageInfo.userId
                    }
                });
            } catch (error) {
                console.error(error);
                alert("查询订单失败，请检查订单号或稍后再试。");
            }
        },
        async queryOrderByUserId() {
            console.log("query by userId");

            try {
                const response = await axios.get(
                    `http://localhost:8080/query/2/${this.userId}`
                );
                const packageList = response.data.PackageInfoList;
                this.$router.push({
                    path:"/package-list",
                    query:{
                        packageList: JSON.stringify(packageList)
                    }
                });
            } catch (error) {
                console.error(error);
                alert("查询订单失败，请检查用户 ID 或稍后再试。");
            }
        },
    },
};
</script>


<!--<template>-->
<!--  <div>-->
<!--    <h1>Query Order</h1>-->
<!--    <form @submit.prevent="queryOrder">-->
<!--      <label for="packageId">Package ID:</label>-->
<!--      <input v-model="packageId" type="text" id="packageId" name="packageId" />-->
<!--      <br />-->
<!--      <label for="userId">User ID:</label>-->
<!--      <input v-model="userId" type="text" id="userId" name="userId" />-->
<!--      <br />-->
<!--      <button type="submit">Query Order</button>-->
<!--    </form>-->
<!--  </div>-->
<!--</template>-->


<!--<script>-->
<!--import axios from "axios";-->
<!--export default {-->
<!--    data() {-->
<!--        return {-->
<!--            packageId: '',-->
<!--            userId: '',-->
<!--        };-->
<!--    },-->
<!--    methods: {-->
<!--        async queryOrder() {-->
<!--            console.log("query");-->

<!--            try {-->
<!--                const response = await axios.get(-->
<!--                    `http://localhost:8080/query/1/${this.packageId}`-->
<!--                );-->
<!--                console.log(response.data);-->
<!--            } catch (error) {-->
<!--                console.error(error);-->
<!--                alert("查询订单失败，请检查订单号或稍后再试。");-->
<!--            }-->
<!--            this.$router.push("/");-->
<!--        },-->
<!--    },-->
<!--};-->
<!--</script>-->
