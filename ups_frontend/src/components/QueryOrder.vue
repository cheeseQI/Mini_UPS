<template>
  <div>
    <h1>Query Order</h1>
    <form @submit.prevent="queryOrder">
      <label for="packageId">Package ID:</label>
      <input v-model="packageId" type="text" id="packageId" name="packageId" />
      <br />
      <label for="userId">User ID:</label>
      <input v-model="userId" type="text" id="userId" name="userId" />
      <br />
      <button type="submit">Query Order</button>
    </form>
  </div>
</template>


<script>
// import {
//     UUserRequest,
//     UQuery,
//     // UUserResponse,
// } from "@/ups_user_pb";

import axios from "axios";
export default {
    data() {
        return {
            packageId: '',
            userId: '',
        };
    },
    methods: {
        async queryOrder() {
            console.log("query");
            // const query = new UQuery();
            // query.setPackageId(this.packageId);
            // query.setUserId(this.userId);

            try {
                console.log("http get start");
                const response = await axios.get(
                    `https://localhost:1029/query/${this.packageId}`
                );
                console.log(response);

                console.log("http get finished");
                // this.package = response.data;
            } catch (error) {
                console.error(error);
                alert("查询订单失败，请检查订单号或稍后再试。");
            }
            // const request = new UUserRequest();
            // request.setQuerycommand(query);
            //
            // const response = UUserResponse.deserializeBinary(binaryPayload);
            // const packageList = response.getQueryResult().getPackageList();
            //
            // for (let i = 0; i < packageList.length; i++) {
            //   console.log(packageList[i]);
            // }
            this.$router.push("/");
        },
    },
};

// import { inject } from 'vue';

// export default {
//   data() {
//     return {
//       orderId: "",
//       order: null,
//       socket: inject('$socket')
//     };
//   },
//   // setup() {
//   //   const socket = inject('$socket');
//   //   return { socket };
//   // },
//   methods: {
//     async queryOrder() {
//       const query = new UQuery();
//       query.setPackageId(this.packageId);
//       query.setUserId(this.userId);
//
//       const request = new UUserRequest();
//       request.setQuerycommand(query);
//
//       this.socket.send("queryOrder", request);
//
//       // // const binaryPayload = await this.socket.onMessage("queryOrderResponse");
//       // //
//       // // const response = UUserResponse.deserializeBinary(binaryPayload);
//       // // const packageList = response.getQueryResult().getPackageList();
//       // //
//       // // for (let i = 0; i < packageList.length; i++) {
//       // //   console.log(packageList[i]);
//       // // }
//       // this.$router.push("/");
//     },
//   },
// };
</script>
