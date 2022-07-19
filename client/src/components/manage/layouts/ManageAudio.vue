<template>
  <audio controls="controls" preload="true" v-if="url" :ref="player" :src="attachImageUrl(url)" @canplay="startPlay"
    @ended="ended"></audio>
</template>

<script setup>
import { computed, watch, ref } from "vue";
import { useStore } from "vuex";
import { HttpManager } from "@/api";

const store = useStore();
const attachImageUrl = HttpManager.attachImageUrl
const divRef = ref();
const player = (el) => {
  divRef.value = el;
};

const url = computed(() => store.getters.songUrl); // 音乐链接
const isPlay = computed(() => store.getters.isPlay); // 播放状态
// 监听播放还是暂停
watch(isPlay, () => {
  togglePlay();
});

// 开始/暂停
function togglePlay() {
  isPlay.value ? divRef.value.play() : divRef.value.pause();
}

// 获取歌曲链接后准备播放
function startPlay() {
  divRef.value.play();
}
// 音乐播放结束时触发
function ended() {
  store.commit("setIsPlay", false);
}
</script>

<style>
audio {
  display: none;
}
</style>
