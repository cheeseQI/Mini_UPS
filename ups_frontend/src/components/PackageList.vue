<!-- PackageList.vue -->
<template>
    <div>
        <h1>Package List</h1>
        <select v-model="selectedPackageId">
            <option disabled value="">Please select a package</option>
            <option
                v-for="(packageInfo, i) in this.packageList"
                :key="packageInfo.packageId"
                :value="packageInfo.packageId"
            >
                {{ i + 1 }}. {{ packageInfo.description }}
            </option>
        </select>
        <div v-if="selectedPackageId">
            <h2>Selected Package Details</h2>
            <p>PackageId: {{ getPackageInfo(selectedPackageId).packageId }}</p>
            <p>Description: {{ getPackageInfo(selectedPackageId).description }}</p>
            <p>Count: {{ getPackageInfo(selectedPackageId).count }}</p>
            <p>
                Start Position: ({{ getPackageInfo(selectedPackageId).startX }},
                {{ getPackageInfo(selectedPackageId).startY }})
            </p>
            <p>
                Destination: ({{ getPackageInfo(selectedPackageId).destX }},
                {{ getPackageInfo(selectedPackageId).destY }})
            </p>
            <p>
                Current Position: ({{ getPackageInfo(selectedPackageId).currX }},
                {{ getPackageInfo(selectedPackageId).currY }})
            </p>
            <p>Username: {{ getPackageInfo(selectedPackageId).username }}</p>
            <button @click="$router.push({
                path:'/redirect',
                query:{
                    packageId: getPackageInfo(selectedPackageId).packageId,
                    description: getPackageInfo(selectedPackageId).description,
                },
            })">Redirect Order</button>
        </div>

        <button @click="$router.push('/queryOrderById')">Back to Query</button>
        <button @click="$router.push('/')">Back to Welcome</button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            packageList: [],
            selectedPackageId: "",
        };
    },
    methods: {
        getPackageInfo(packageId) {
            return this.packageList.find(
                (packageInfo) => packageInfo.packageId === packageId
            );
        },
    },
    created() {
        if (this.$route.query.packageList) {
            this.packageList = JSON.parse(this.$route.query.packageList);
        }
    },
};
</script>
