<template>
  <div class="search">
    <search-nav :styleList="searchNavList" :activeName="activeName" @click="handleChangeView" />
    <component class="search-list" :is="currentView"></component>
  </div>
</template>

<script setup>
import { shallowReactive, ref, shallowRef, watch } from "vue";
import SearchNav from "@/components/client/layouts/SearchNav.vue";
import SearchSong from "./SearchSong.vue";
import SearchSongList from "./SearchSongList.vue";
import TestComp from "@/components/TestComp.vue";

const searchNavList = shallowReactive(
  [
    {
      name: "歌曲",
      value: SearchSong,
    },
    {
      name: "歌单",
      value: SearchSongList,
    },
  ]
)

watch()

const activeName = ref("歌曲")
const currentView = shallowRef(SearchSong)

function handleChangeView(item) {
  activeName.value = item.name;
  currentView.value = item.value;
}

</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

.search {
  margin: auto;
  width: 900px;

  .search-list {
    min-height: 480px;
  }
}
</style>
