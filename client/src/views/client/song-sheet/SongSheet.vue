<template>
  <div class="play-list-container">
    <my-nav :styleList="songStyle" :activeName="activeName" @click="handleChangeView" />
    <play-list :playList="data" path="song-sheet-detail"></play-list>
    <el-pagination
      class="pagination"
      background
      layout="total, prev, pager, next"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="allPlayList.length"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>
    
<script setup>
import { ref, computed } from "vue";
import MyNav from "@/components/client/layouts/MyNav.vue";
import PlayList from "@/components/client/PlayList.vue";
import { SONGSTYLE } from "@/enums";
import { HttpManager } from "@/api";

const activeName = ref("全部歌单");
    const pageSize = ref(15); // 页数
    const currentPage = ref(1); // 当前页
    const songStyle = ref(SONGSTYLE); // 歌单导航栏类别
    const allPlayList = ref([]); // 歌单
    const data = computed(() => allPlayList.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value));

    // 获取全部歌单
    async function getSongList() {
      allPlayList.value = (await HttpManager.getSongList()).data;
      currentPage.value = 1;
    }
    // 通过类别获取歌单
    async function getSongListOfStyle(style) {
      allPlayList.value = (await HttpManager.getSongListOfStyle(style)).data;
      currentPage.value = 1;
    }

    try {
      getSongList();
    } catch (error) {
      console.error(error);
    }

    // 获取歌单
    async function handleChangeView(item) {
      activeName.value = item.name;
      allPlayList.value = [];
      try {
        if (item.name === "全部歌单") {
          await getSongList();
        } else {
          // console.log(item.name)
          await getSongListOfStyle(item.name);
        }
      } catch (error) {
        console.error(error);
      }
    }
    // 获取当前页
    function handleCurrentChange(value) {
      currentPage.value = value;
    }


</script>