import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Main from "@/views/main/Main.vue";
// import cookie from "@/cookie";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
];

const router = new VueRouter({
  mode: "history",
  routes
});

// router.beforeEach((to, from, next) => {
  // if (cookie.cookieAuth() == 0 && to.name != 'Admin' && to.name != 'Login') {
  //   return next('admin')
  // }
  // if (cookie.getCookie()) {
  //   if (to.meta.loginRequire === false) {
  //     if (from.name === null) {
  //       return next("Main");
  //     } else {
  //       return next(false);
  //     }
  //   }
  //   next();
  // } else {
  //   if (to.meta.loginRequire === true) {
  //     alert("로그인 해주세요");
  //     return next("/");
  //   }
  //   return next();
  // }
// });

export default router;
