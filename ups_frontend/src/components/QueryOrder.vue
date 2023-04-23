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
import { inject } from 'vue';

  import {
    UUserRequest,
    UQuery,
    UUserResponse,
  } from "@/ups_user_pb";

export default {
  data() {
    return {
      orderId: "",
      order: null,
    };
  },
  setup() {
    const socket = inject('$socket');
    return { socket };
  },
  methods: {
    async queryOrder() {
      const query = new UQuery();
      query.setPackageId(this.packageId);
      query.setUserId(this.userId);

      const request = new UUserRequest();
      request.setQueryCommand(query);

      this.$socket.send("queryOrder", request);

      const binaryPayload = await this.$socket.onMessage("queryOrderResponse");
      
      const response = UUserResponse.deserializeBinary(binaryPayload);
      const packageList = response.getQueryResult().getPackageList();

      for (let i = 0; i < packageList.length; i++) {
        console.log(packageList[i]);
      }
      this.$router.push("/");
    },
  },
};
</script>
