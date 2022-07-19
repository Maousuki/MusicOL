<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      background-color="#ffffff"
      active-text-color="#30a4fc"
      default-active="2"
      router
      :collapse="collapse"
    >
      <el-menu-item index="info">
        <el-icon><pie-chart /></el-icon>
        <span>系统首页</span>
      </el-menu-item>
      <el-menu-item index="consumer">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="singer">
        <el-icon><mic /></el-icon>
        <span>歌手管理</span>
      </el-menu-item>
      <el-menu-item index="song-list">
        <el-icon><Document /></el-icon>
        <span>歌单管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>


    
<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { PieChart, Mic, Document, User } from "@element-plus/icons-vue";

const store = useStore()

const collapse = computed(() => store.getters.collapse);

</script>
    
<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar > ul {
  height: 100%;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 150px;
}
</style>
    