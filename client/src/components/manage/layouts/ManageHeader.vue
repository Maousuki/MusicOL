<template>
<div class="header">
  <!-- 控制左侧菜单折叠 -->
    <div class="collapse-btn" @click="collapseChange">
      <el-icon v-if="!collapse"><expand /></el-icon>
      <el-icon v-else><fold /></el-icon>
    </div>
    <!-- 标题名 -->
    <div class="logo">{{ appName }}</div>
    <!-- 右侧导航 -->
    <div class="header-right">
      <!-- 用户头像 -->
      <div class="header-user-con">
        <div class="user-avatar">
          <img :src="attachImageUrl(userPic)" />
        </div>
        <!-- 下拉栏 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            {{ username }}
            <i class="el-icon-caret-bottom"></i>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="returnClient">返回客户端</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
    
<script setup>
import { Expand, Fold } from "@element-plus/icons-vue";
import { defineComponent, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";
import { RouterName, MANAGE_NAME, NavName } from "@/enums";
import utils from "@/utils";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";

const store = useStore()
const { changeActiveName, routerManager, resetSongState } = utils()
const attachImageUrl = HttpManager.attachImageUrl

const collapse = computed(() => store.getters.collapse)
const username = computed(() => store.getters.username)
const userPic = computed(() => store.getters.userPic)
const appName = ref(MANAGE_NAME)

const { logOut } = utils()

onMounted(() => {
    if (document.body.clientWidth < 1500) {
        collapseChange()
    }
})

function collapseChange(){
    store.commit('setCollapse', !store.getters.collapse)
    
}

async function handleCommand(command) {
    if(command === "logout"){
      resetSongState()
      logOut()
    }else if(command === "returnClient"){
      resetSongState()
      changeActiveName(NavName.Home)
      routerManager(RouterName.Home, { path: RouterName.Home })
    }
}

</script>


    
<style scoped>
.header {
  position: absolute;
  z-index: 100;
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  font-size: 20px;
  color: #2c3e50;
  background: #429ca0;
  box-shadow: 0px 0px 8px 2px rgba(0, 0, 0, 0.3);
}

.collapse-btn {
  display: flex;
  padding: 0 21px;
  cursor: pointer;
  color: #ffffff;
}

.header .logo {
  width: 250px;
  font-weight: bold;
  color: #ffffff;
}

.header-right {
  position: absolute;
  right: 0;
  padding-right: 30px;
}

.header-user-con {
  display: flex;
  align-items: center;
}

.user-name {
  margin-left: 10px;
}

.user-avatar img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-dropdown-link {
  cursor: pointer;
  color: #c9d3db;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>