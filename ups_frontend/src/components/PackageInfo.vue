<!-- PackageInfo.vue -->
<template>
    <div>
        <h1>Package Info</h1>
        <p>PackageId: {{ this.$route.query.packageId }}</p>
        <p>Description: {{ this.$route.query.description }}</p>
        <p>Count: {{ this.$route.query.count }}</p>
        <p>Destination: ({{ this.$route.query.destX }}, {{ this.$route.query.destY }})</p>
        <p>Username: {{ this.$route.query.username }}</p>
        <button @click="$router.push('/query')">Back to Query</button>
        <button @click="$router.push('/')">Back to Welcome</button>
    </div>
</template>
<script>
export default {
    // props: {
    //     packageInfo: {
    //         type: Object,
    //         required: true,
    //     },
    // },
    // inject: ["packageInfo"],

    // data() {
    //     return {
    //         packageInfo: {}, // 在实际应用中，此数据应该从父组件传递或在此组件中请求
    //     };
    // },
};
</script>