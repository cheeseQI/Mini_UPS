
<template>
  <div>
    <h1>Redirect Order</h1>
    <form @submit.prevent="redirectOrder">
      <label for="packageId">Package ID:</label>
      <input v-model="packageId" type="text" id="packageId" name="packageId" />
      <br />
      <label for="newX">New X:</label>
      <input v-model="newX" type="text" id="newX" name="newX" />
      <br />
      <label for="newY">New Y:</label>
      <input v-model="newY" type="text" id="newY" name="newY" />
      <br />
      <button type="submit">Redirect Order</button>
    </form>
  </div>
</template>
<script>
import { inject } from 'vue';
import {
  UUserRequest,
  URedirect,
  UUserResponse,
} from "@/ups_user_pb.js";
export default {
  data() {
    return {
      orderId: "",
      newDestination: "",
    };
  },
  setup() {
    const socket = inject('$socket');
    return { socket };
  },
  methods: {

    async redirectOrder() {
      const redirect = new URedirect();
      redirect.setPackageId(this.packageId);
      redirect.setX(this.newX);
      redirect.setY(this.newY);

      const request = new UUserRequest();
      request.setRedirectCommand(redirect);

      this.$socket.send("redirectOrder", request);

      const binaryPayload = await this.$socket.onMessage("redirectOrderResponse");
      const response = UUserResponse.deserializeBinary(binaryPayload);
      const redirectResult = response.getRedirectResult();

      if (redirectResult.getMessage() === "success") {
        alert("redirect success");
        this.$router.push("/");
      } else {
        alert("redirect failed");
      }
    },
  },
};
</script>
