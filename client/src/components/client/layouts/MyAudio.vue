<template>
  <audio :src="attachImageUrl(songUrl)" controls="controls" :ref="player" preload="true" @canplay="canplay"
    @timeupdate="timeupdate" @ended="ended"></audio>

</template>
    
<script setup>
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";
import { HttpManager } from "@/api";
import { Icon } from "@/enums";

const store = useStore();
const attachImageUrl = HttpManager.attachImageUrl
const divRef = ref();
const player = (el) => {
  divRef.value = el;
};

const songUrl = computed(() => store.getters.songUrl); // 音乐链接
const isPlay = computed(() => store.getters.isPlay); // 播放状态
const volume = computed(() => store.getters.volume); // 音量
const changeTime = computed(() => store.getters.changeTime); // 指定播放时刻
const autoNext = computed(() => store.getters.autoNext); // 用于触发自动播放下一首
// 监听播放还是暂停
watch(isPlay, () => togglePlay());
// 跳到指定时刻播放
watch(changeTime, () => (divRef.value.currentTime = changeTime.value));
watch(volume, (value) => (divRef.value.volume = value));

// 开始 / 暂停
function togglePlay() {
  // console.log(isPlay.value)
  if(isPlay.value === true) {
    divRef.value.play()
    store.commit('setPlayBtnIcon', Icon.ZANTING)
  }else{
    divRef.value.pause()
    store.commit('setPlayBtnIcon', Icon.BOFANG)
  }
}
// 获取歌曲链接后准备播放
function canplay() {
  //  记录音乐时长
  store.commit("setDuration", divRef.value.duration);
  //  开始播放
  if(isPlay.value){
    divRef.value.play();
  }
  // store.commit("setIsPlay", true);
}
// 音乐播放时记录音乐的播放位置
function timeupdate() {
  store.commit("setCurTime", divRef.value.currentTime);
}
// 音乐播放结束时触发
function ended() {
  store.commit("setIsPlay", false);
  store.commit("setCurTime", 0);
  store.commit("setAutoNext", !autoNext.value);
}
</script>
    
<style scoped>
audio {
  display: none;
}
</style>