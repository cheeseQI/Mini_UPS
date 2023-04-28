
<template>
  <div>
    <h1>Personal Center</h1>
    <form @submit.prevent="checkMyOrders">
        <br />
        <button type="submit">Check My Orders</button>
    </form>
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
                  `http://localhost:8080/query/2/${localStorage.getItem("username")}`
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
