import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import VuexPersistense from "vuex-persist";
import router from "../router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    accessToken: null,
    userId: "",
    userName: "",
  },
  getters: {
    getAccessToken(state) {
      return state.accessToken;
    },
    getUserId(state) {
      return state.userId;
    },
    getUserName(state) {
      return state.userName;
    },
  },
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload["auth-token"];
      state.userId = payload["user-id"];
      state.userName = payload["user-name"];
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.userId = "";
      state.userName = "";
    },
    UPDATE(state, payload) {
      state.accessToken = payload["auth-token"];
      console.log(payload);
    },
  },
  actions: {
    LOGIN(context, user) {
      return axios
        .post(`https://localhost:5000/user/login`, user)
        .then(response => {
          context.commit("LOGIN", response.data);
          alert(response.data["message"]);

          if (response.data["auth-token"]) {
            axios.defaults.headers.common[
              "auth-token"
            ] = `${response.data["auth-token"]}`;
            router.push("/main");
          }
        });
    },
    FINDPW(context, user) {
      return axios
        .post(`https://localhost:5000/user/findpw`, user)
        .then(response => {
          alert(response.data["message"]);
        });
    },
    FINDUSERID(context, user) {
      return axios
        .get(`https://localhost:5000/user/finduserid/${user.email}`)
        .then(response => {
          alert(response.data["message"]);
        });
    },
    LOGOUT(context) {
      context.commit("LOGOUT");
      axios.defaults.headers.common["auth-token"] = undefined;
    },
    SETHEADER({ getters }) {
      axios.defaults.headers.common["auth-token"] = getters.getAccessToken;
    },
    SIGNUP(context, fd) {
      return axios
        .post(`https://localhost:5000/user/signup`, fd)
        .then(response => {
          alert(response.data["message"]);
          router.push({ path: "/" });
        })
        .catch(err => {
          console.log(err);
        });
    },
    MEMBERDELETE(context, user) {
      console.log(user);
      return axios
        .delete(`https://localhost:5000/user/delete/${user.id}`)
        .then(response => {
          alert(response.data["message"]);
          router.push({ path: "/" });
        })
        .catch(response => {
          alert(response.data["message"]);
        });
    },
    MEMBERUPDATE(context, user) {
      return axios
        .put(`https://localhost:5000/user/update/${user.id}`, user)
        .then(response => {
          // alert(response.data["message"]);
          axios.defaults.headers.common[
            "auth-token"
          ] = `${response.data["auth-token"]}`;
          context.commit("UPDATE", response.data);
        })
        .catch(response => {
          alert(response.data["message"]);
        });
    },
  },
  modules: {},
  plugins: [new VuexPersistense().plugin],
});
