<template>
    <div id="package-info">
        <h1>Package Info</h1>
        <div class="package-details">
            <table>
                <tr>
                    <th>PackageId:</th>
                    <td>{{ this.$route.query.packageId }}</td>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td>{{ this.$route.query.description }}</td>
                </tr>
                <tr>
                    <th>Count:</th>
                    <td>{{ this.$route.query.count }}</td>
                </tr>
                <tr>
                    <th>Start Position:</th>
                    <td>({{ this.$route.query.startX }}, {{ this.$route.query.startY }})</td>
                </tr>
                <tr>
                    <th>Destination:</th>
                    <td>({{ this.$route.query.destX }}, {{ this.$route.query.destY }})</td>
                </tr>
                <tr>
                    <th>Current Position:</th>
                    <td>({{ this.$route.query.currX }}, {{ this.$route.query.currY }})</td>
                </tr>
                <tr>
                    <th>Username:</th>
                    <td>{{ this.$route.query.username }}</td>
                </tr>
            </table>
        </div>
        <button class="action-btn" @click="$router.push('/queryOrderById')">Back to Query</button>
        <button class="action-btn" @click="$router.push('/')">Back to Welcome</button>
    </div>
</template>

<script>
export default {
};
</script>

<style scoped>
#package-info {
    max-width: 600px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin: 30px auto;
}

h1 {
    color: #1e4b88;
    font-weight: bold;
    margin-bottom: 20px;
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
