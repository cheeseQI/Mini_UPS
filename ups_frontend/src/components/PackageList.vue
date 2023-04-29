<template>
    <div id="package-list-container">
        <h1>Package List</h1>
        <div class="package-list-content">
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
            <div v-if="selectedPackageId" class="package-details">
                <h2>Selected Package Details</h2>
                <table>
                    <tr>
                        <th>PackageId</th>
                        <td>{{ getPackageInfo(selectedPackageId).packageId }}</td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>{{ getPackageInfo(selectedPackageId).description }}</td>
                    </tr>
                    <tr>
                        <th>Count</th>
                        <td>{{ getPackageInfo(selectedPackageId).count }}</td>
                    </tr>
                    <tr>
                        <th>Start Position</th>
                        <td>
                            ({{ getPackageInfo(selectedPackageId).startX }},
                            {{ getPackageInfo(selectedPackageId).startY }})
                        </td>
                    </tr>
                    <tr>
                        <th>Destination</th>
                        <td>
                            ({{ getPackageInfo(selectedPackageId).destX }},
                            {{ getPackageInfo(selectedPackageId).destY }})
                        </td>
                    </tr>
                    <tr>
                        <th>Current Position</th>
                        <td>
                            ({{ getPackageInfo(selectedPackageId).currX }},
                            {{ getPackageInfo(selectedPackageId).currY }})
                        </td>
                    </tr>
                    <tr>
                        <th>Username</th>
                        <td>{{ getPackageInfo(selectedPackageId).username }}</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>{{ getPackageInfo(selectedPackageId).status }}</td>
                    </tr>
                </table>
                <button
                    class="action-btn"
                    @click="$router.push({
            path: '/redirect',
            query: {
              packageId: getPackageInfo(selectedPackageId).packageId,
              description: getPackageInfo(selectedPackageId).description,
            },
          })"
                >
                    Redirect Order
                </button>
            </div>

            <button class="action-btn" @click="$router.push('/queryOrderById')">Back to Query</button>
            <button class="action-btn" @click="$router.push('/')">Back to Welcome</button>
        </div>
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

<style scoped>
#package-list-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #f0f2f5;
    min-height: 100vh;
}

.package-list-content {
    width: 100%;
    max-width:600px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin-top: 30px;
}

h1, h2 {
    color: #1e4b88;
    font-weight: bold;
    margin-bottom: 20px;
}

select {
    width: 100%;
    padding: 10px 15px;
    margin-bottom: 30px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
}

.package-details {
    margin-bottom: 20px;
}

.package-details table {
    width: 100%;
    border-collapse: collapse;
    font-size: 16px;
    margin-bottom: 20px;
}

.package-details th {
    font-weight: bold;
    text-align: left;
    padding: 8px;
    border-bottom: 1px solid #ccc;
}

.package-details td {
    padding: 8px;
    border-bottom: 1px solid #ccc;
}

.action-btn {
    background-color: #1e4b88;
    color: #ffffff;
    font-size: 16px;
    padding: 10px 20px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    transition: background-color 0.2s;
    margin-top: 20px;
    margin-right: 10px;
}

.action-btn:hover {
    background-color: #356aa0;
}
</style>
