<template>
  <!-- header, 这里应该有一个logo组件，左侧导航，搜索栏，右侧导航 -->
  <div class="my-header">
    <div class="header-logo">
      <my-logo @click="goPage" :icon="iconList.ERJI" />
      <span @click="goPage">{{ appName }}</span>
    </div>
    <!-- 左侧导航 -->
    <my-nav class="my-header-nav" :options="headerList" :activeNavName="activeNavName" @click="goPage" />
    <!-- 搜索栏 -->
    <div class="header-search">
      <el-input placeholder="搜索" :prefix-icon="Search" v-model="keywords" @keyup.enter="goSearch()" />
    </div>
    <!-- 右侧导航 -->
    <my-nav v-if="token === ''" class="my-header-nav" :options="signList" :activeName="activeNavName" @click="goPage" />
    <!-- 用户 -->
    <el-dropdown class="user-wrap" v-if="token !== ''" trigger="click">
      <el-image class="user" fit="contain" :src="attachImageUrl(userPic)" />
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item v-if="!isAdmin" v-for="(item, index) in menuList" :key="index"
            @click.stop="goMenuList(item.path)">{{ item.name }}</el-dropdown-item>
          <el-dropdown-item v-if="isAdmin" v-for="(item, index) in ADMINLIST" :key="index"
            @click="goMenuList(item.path)">{{ item.name }}</el-dropdown-item>

        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>
    
<script setup>
import MyNav from './MyNav.vue'
import MyLogo from './MyLogo.vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { Search } from "@element-plus/icons-vue";
import { HEADERNAVLIST, SIGNLIST, MENULIST, ADMINLIST, RouterName, NavName, Icon, APP_NAME } from '@/enums';
import utils from '@/utils'
import { ref, computed, reactive } from 'vue';
import { HttpManager } from '@/api';
import { ElMessage } from 'element-plus';

const store = useStore()
const router = useRouter()

const { changeActiveName, routerManager, logOut, resetSongState } = utils()

const headerList = ref(HEADERNAVLIST)
const signList = ref(SIGNLIST)
const menuList = ref(MENULIST)
const adminList = ref(ADMINLIST)
const keywords = ref("")
const iconList = reactive({
  ERJI: Icon.ERJI
})
const appName = ref(APP_NAME)

const activeNavName = computed(() => store.getters.activeNavName);
const userPic = computed(() => store.getters.userPic);
const token = computed(() => store.getters.token)
const isAdmin = computed(() => store.getters.userRole === 1)

function goPage(path, name) {
  if (path && name) {
    changeActiveName(name)
    routerManager(path, { path })
  } else {
    changeActiveName(NavName.Home)
    routerManager(RouterName.Home, { path: RouterName.Home })
  }
}

function goSearch() {
  if (keywords.value !== "") {
    store.commit("setSearchWords", keywords.value);
    routerManager(RouterName.Search, { path: RouterName.Search, query: { keywords: keywords.value } });
  } else {
    ElMessage({
      message: "搜索内容不能为空",
      type: "error",
    })
  }
}

async function goMenuList(path) {
  switch (path){
    case RouterName.SignOut:
      logOut();
      break;
    case RouterName.ManageHome:
      resetSongState();
    default:
      routerManager(path, { path });
  }
}

const attachImageUrl = HttpManager.attachImageUrl

</script>
    
<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

@media screen and (min-width: $sm) {
  .header-logo {
    margin: 0 1rem;
  }
}

@media screen and (max-width: $sm) {
  .header-logo {
    margin: 0 1rem;

    span {
      display: none;
    }
  }

  .header-search {
    display: none;
  }
}

.my-header {
  position: fixed;
  width: 100%;
  height: $header-height;
  line-height: $header-height;
  padding: $header-padding;
  margin: $header-margin;
  background-color: $theme-header-color;
  box-shadow: $box-shadow;
  box-sizing: border-box;
  z-index: 100;
  display: flex;
  white-space: nowrap;
  flex-wrap: nowrap;
}

/* LOGO */
.header-logo {
  font-size: $font-size-logo;
  font-weight: bold;
  cursor: pointer;
  color: #EFEFEF;

  .icon {
    @include icon(1.9rem, $color-white);
    vertical-align: middle;
  }

  span {
    margin-left: 1rem;
  }
}

.my-header-nav {
  flex: 1;
}

/*搜索输入框*/
.header-search {
  margin: 0 20px;
  width: 100%;

  &::v-deep input {
    text-indent: 5px;
    max-width: $header-search-max-width;
    min-width: $header-search-min-width;
    border-radius: $header-search-radius;
    box-shadow: none;
    background-color: $color-light-grey;
    color: $color-black;
  }
}

/*用户*/
.user-wrap {
  position: relative;
  display: flex;
  align-items: center;

  .user {
    width: $header-user-width;
    height: $header-user-width;
    border-radius: $header-user-radius;
    margin-right: $header-user-margin;
    cursor: pointer;
  }
}
</style>