
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
// import { inject } from 'vue';
// import {
//   UUserRequest,
//   URedirect,
//   UUserResponse,
// } from "@/ups_user_pb.js";
import axios from "axios";
export default {
  data() {
    return {
      packageId: "",
      newX: "",
      newY: ""
    };
  },
  // setup() {
  //   const socket = inject('$socket');
  //   return { socket };
  // },
  methods: {

    async redirectOrder() {
      // const redirect = new URedirect();
      // redirect.setPackageId(this.packageId);
      // redirect.setX(this.newX);
      // redirect.setY(this.newY);
      //
      // const request = new UUserRequest();
      // request.setRedirectCommand(redirect);
      //
      // this.$socket.send("redirectOrder", request);

        try {
            const response = await axios.post("http://localhost:8080/redirect", {
                packageId: this.packageId,
                newX: this.newX,
                newY: this.newY,
            });
            console.log(response.data);
            alert(response.data);
            this.$router.push("/");
        } catch (error) {
            console.error("Error creating order:", error);
        }
    }
      // const binaryPayload = await this.$socket.onMessage("redirectOrderResponse");
      // const response = UUserResponse.deserializeBinary(binaryPayload);
      // const redirectResult = response.getRedirectResult();

      // if (redirectResult.getMessage() === "success") {
      //   alert("redirect success");
      //   this.$router.push("/");
      // } else {
      //   alert("redirect failed");
      // }
    },
};
</script>
