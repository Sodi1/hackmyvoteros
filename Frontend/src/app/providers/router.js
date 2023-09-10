import { createRouter, createWebHistory } from "vue-router";
import { useUserStore } from "@/stores"
import { MissingPage } from "@/pages";

const routes = [
  {
    path: "",
    redirect: { name: "Home" },
    component: () => import("@/pages/layouts/BaseLayout.vue"),
    children: [
      {
        name: "Login",
        path: "/login",
        component: () => import("@/pages/LoginPage.vue"),
      },
    ],
  },
  {
    path: "/",
    redirect: { name: "Home" },
    component: () => import("@/pages/layouts/HeaderedLayout.vue"),
    children: [
      {
        name: "Home",
        path: "/home",
        component: () => import("@/pages/FilePage.vue"),
      },
      {
        name: "Emotion",
        path: "/emotion",
        component: () => import("@/pages/EmotionPage.vue"),
      }, 
      {
        name: "FileDashboard",
        path: "/fileDashboard",
        component: () => import("@/pages/DashboardPage.vue"),
      },
      {
        name: "QuestionDashboard",
        path: "/questionDashboard",
        component: () => import("@/pages/DashboardQuestionPage.vue"),
      },
      {
        name: "SelectDashboard",
        path: "/selectDashboard",
        component: () => import("@/pages/DashboardSelectPage.vue"),
      },
      {
        name: "FileDashboard",
        path: "/fileDashboard",
        component: () => import("@/pages/DashboardPage.vue"),
      },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: MissingPage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  if (!userStore.isSignedIn && to.name !== "Login") {
    next({ name: "Login" })
    return;
  } else {
    next()
  }
});

export { router };
