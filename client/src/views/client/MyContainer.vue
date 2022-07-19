<template>
  <el-container>
    <el-header>
        <!-- Header content -->
        <my-header/>
    </el-header>
    <el-main>
        <!-- Main content -->
    <router-view/>
    <current-play/>
    <play-bar/>
    <scroll-top/>
    <my-audio/>
    </el-main>
    <el-footer>
        <!-- Footer content -->
        <my-footer/>
    </el-footer>
  </el-container>
  
</template>
    
<script setup>
import MyHeader from "@/components/client/layouts/MyHeader.vue"
import MyFooter from "@/components/client/layouts/MyFooter.vue"
import CurrentPlay from "@/components/client/layouts/CurrentPlay.vue";
import PlayBar from "@/components/client/layouts/PlayBar.vue";
import ScrollTop from "@/components/client/layouts/ScrollTop.vue";
import MyAudio from "@/components/client/layouts/MyAudio.vue";
</script>
    
<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

.el-container {
  min-height: calc(100% - 60px);
}
.el-header {
  padding: 0;
}
.el-main {
  padding-left: 0;
  padding-right: 0;
}
</style>
    