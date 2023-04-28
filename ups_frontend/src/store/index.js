// src/store/index.js

import { createStore } from "vuex";

export default createStore({
    state: {
        username: "",
        loggedin: false
    },
    mutations: {
        setUsername(state, value) {
            state.username = value;
        },
        setLoggedIn(state, value) {
            state.loggedin = value;
        },
    },
    getters: {
        getUsername: (state) => state.username,
        getLoggedin: (state) => state.loggedin,
    },
});
