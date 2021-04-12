import Vue from "vue";
import VueRouter from "vue-router";
import Find from "@/views/Find.vue";
import Home from "@/views/Home.vue";
import Notice from "@/views/Notice.vue";
import FnQ from "@/views/FnQ.vue";
import Info from "@/views/Info.vue";
import Main from "@/views/Main.vue";
import MakeRoom from "@/views/MakeRoom.vue";
import IntoRoom from "@/views/IntoRoom.vue";

// import store from "../store";

Vue.use(VueRouter);

// const requireAuth = () => (to, from, next) => {
//   // const nextRoute = to.path;

//   console.log(store.getters.getAccessToken);
//   // console.log(next);

//   if (store.getters.getAccessToken) {
//     return next();
//   } else {
//     alert("로그인 하셔야 서비스를 이용할 수 있습니다.");
//     // return next("/login" + nextRoute);
//     return;
//   }
// };

// const requireNonAuth = () => (to, from, next) => {
//   // const nextRoute = from.path;

//   if (store.getters.getAccessToken) {
//     // return next(nextRoute);
//   } else return next();
// };

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },

  {
    path: "/find",
    name: "Find",
    component: Find,
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
  },
  {
    path: "/fnq",
    name: "FnQ",
    component: FnQ,
  },
  {
    path: "/info",
    name: "Info",
    component: Info,
  },
  {
    path: "/main",
    name: "Main",
    component: Main,
  },
  {
    path: "/makeroom",
    name: "MakeRoom",
    component: MakeRoom,
  },
  {
    path: "/intoroom/:session_id",
    name: "IntoRoom",
    component: IntoRoom,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
