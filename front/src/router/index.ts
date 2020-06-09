import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Main from "@/views/main/Main.vue";
import Signup from "@/views/user/Signup.vue";
import Account from "@/views/user/Account.vue";
import Article from "@/views/board/Article.vue";
import Detail from "@/views/board/Detail.vue";
import cookie from "@/cookie";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/auth",
    name: "Signup",
    component: Signup
  },
  {
    path: "/account/:userId",
    name: "Account",
    component: Account,
    meta: { loginRequire: true },
  },
  {
    path: "/article",
    name: "Article",
    component: Article,
    meta: { loginRequire: true },
  },
  {
    path: "/detail/:boardId",
    name: "Detail",
    component: Detail,
    meta: { loginRequire: true },
  },
  {
    path: "/*",
    name: "404Error",
    redirect: { name: "Main" },
  },
];

const router = new VueRouter({
  mode: "history",
  routes,
  scrollBehavior() {
    return {x: 0, y: 0}
  }
});

router.beforeEach((to, from, next) => {
  if (cookie.getCookie()) {
    if (to.meta.loginRequire === false) {
      if (from.name === null) {
        return next("Main");
      } else {
        return next(false);
      }
    }
    next();
  } else {
    if (to.meta.loginRequire === true) {
      alert("로그인 해주세요");
      return next("/");
    }
    return next();
  }
});

export default router;
