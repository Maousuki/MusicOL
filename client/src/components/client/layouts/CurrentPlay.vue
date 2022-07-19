<template>
  <transition name="aside-fade">
    <div class="current-play" v-if="showAside">
      <h2 class="title">当前播放</h2>
      <div class="control">共 {{ (currentPlayList && currentPlayList.length) || 0 }} 首</div>
      <ul class="menus">
        <li v-for="(item, index) in currentPlayList" :class="{ 'is-play': songId === item.id }" :key="index" @click="playMusic({
          id: item.id,
          url: item.url,
          pic: item.pic,
          index: index,
          name: item.name,
          lyric: item.lyric,
          currentSongList: currentPlayList,
        })">
          {{ getSongTitle(item.name) }}
        </li>
      </ul>
    </div>
  </transition>
</template>

<script setup>
import { computed } from 'vue';
import { useStore } from 'vuex';
import utils from '@/utils';

const store = useStore()
const { getSongTitle, playMusic } = utils()

const songId = computed(() => store.getters.songId); // 音乐 ID
const currentPlayList = computed(() => store.getters.currentPlayList); // 当前播放
const showAside = computed(() => store.getters.showAside)

</script>

<style lang="scss" scoped>
@import "@/assets/css/yin-current-play.scss";
</style>
