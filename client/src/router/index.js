import { createRouter, createWebHistory } from "vue-router";

// 定义routes，这是一个json列表,每一项需要path, component两个属性，可选chidren, name
const routes = [
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
  {
    path: "/404",
    component: () => import("@/views/client/error/404.vue"),
  },
  {
    path: "/",
    name: "my-container",
    component: () => import("@/views/client/MyContainer.vue"),
    children: [
      {
        path: "/",
        name: "home",
        component: () => import("@/views/client/Home.vue"),
      },
      {
        path: "/log-in",
        name: "log-in",
        component: () => import("@/views/client/LogIn.vue"),
      },
      {
        path: "/sign-up",
        name: "sign-up",
        component: () => import("@/views/client/SignUp.vue"),
      },
      {
        path: "/personal",
        name: "personal",
        meta: {
          requireAuth: true,
        },
        component: () => import("@/views/client/personal/Personal.vue"),
      },
      {
        path: "/song-sheet",
        name: "song-sheet",
        component: () => import("@/views/client/song-sheet/SongSheet.vue"),
      },
      {
        path: "/song-sheet-detail/:id",
        name: "song-sheet-detail",
        component: () => import("@/views/client/song-sheet/SongSheetDetail.vue"),
      },
      {
        path: "/singer",
        name: "singer",
        component: () => import("@/views/client/singer/Singer.vue"),
      },
      {
        path: "/singer-detail/:id",
        name: "singer-detail",
        component: () => import("@/views/client/singer/SingerDetail.vue"),
      },
      {
        path: "/lyric/:id",
        name: "lyric",
        component: () => import("@/views/client/Lyric.vue"),
      },
      {
        path: "/search",
        name: "search",
        component: () => import("@/views/client/search/Search.vue"),
      },
      {
        path: "/personal-data",
        name: "personal-data",
        component: () => import("@/views/client/setting/PersonalData.vue"),
      },
      {
        path: "/setting",
        name: "setting",
        meta: {
          requireAuth: true,
        },
        component: () => import("@/views/client/setting/Setting.vue"),
        children: [
          {
            path: "/setting/PersonalData",
            name: "personalData",
            meta: {
              requireAuth: true,
            },
            component: () => import("@/views/client/setting/PersonalData.vue"),
          },
        ],
      },
      {
        path: "/test",
        name: "test",
        component: () => import("@/components/TestComp.vue"),
      },
    ]
  },
  {
    path: "/manage/info",
    component: () => import("@/views/manage/Home.vue"),
    meta: { title: "自述文件" },
    children: [
      {
        path: "/manage/info",
        component: () => import("@/views/manage/InfoPage.vue"),
        meta: { title: "Info" },
      },
      {
        path: "/manage/song",
        component: () => import("@/views/manage/SongPage.vue"),
        meta: { title: "Song" },
      },
      {
        path: "/manage/singer",
        component: () => import("@/views/manage/SingerPage.vue"),
        meta: { title: "Singer" },
      },
      {
        path: "/manage/song-list",
        component: () => import("@/views/manage/SongListPage.vue"),
        meta: { title: "SongList" },
      },
      {
        path: "/manage/list-song",
        component: () => import("@/views/manage/ListSongPage.vue"),
        meta: { title: "ListSong" },
      },
      {
        path: "/manage/comment",
        component: () => import("@/views/manage/CommentPage.vue"),
        meta: { title: "Comment" },
      },
      {
        path: "/manage/consumer",
        component: () => import("@/views/manage/ConsumerPage.vue"),
        meta: { title: "Consumer" },
      },
      {
        path: "/manage/collect",
        component: () => import("@/views/manage/CollectPage.vue"),
        meta: { title: "Collect" },
      },
    ],
  }
];

// 定义router
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
