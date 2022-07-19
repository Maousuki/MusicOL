<template>
  <div class="play-bar" :class="{ show: !toggle }">
    <div class="fold" :class="{ turn: toggle }">
      <el-icon @click="toggle = !toggle"><ArrowUpBold /></el-icon>
      <!-- <my-logo :icon="iconList.ZHEDIE" @click="toggle = !toggle"></my-logo> -->
    </div>
    <!--播放进度-->
    <el-slider class="progress" v-model="nowTime" @change="changeTime" size="small"></el-slider>
    <div class="control-box">
      <div class="info-box">
        <!--歌曲图片-->
        <div @click="goPlayerPage">
          <el-image class="song-bar-img" fit="contain" :src="attachImageUrl(songPic)" />
        </div>
        <!--播放开始结束时间-->
        <div v-if="songId">
          <div class="song-info">{{ songTitle }} - {{ singerName }}</div>
          <div class="time-info">{{ startTime }} / {{ endTime }}</div>
        </div>
      </div>
      <div class="song-ctr">
        <my-logo class="yin-play-show" :icon="playStateList[playStateIndex]" @click="changePlayState"></my-logo>
        <!--上一首-->
        <my-logo class="yin-play-show" :icon="iconList.SHANGYISHOU" @click="prev"></my-logo>
        <!--播放-->
        <my-logo :icon="playBtnIcon" @click="togglePlay"></my-logo>
        <!--下一首-->
        <my-logo class="yin-play-show" :icon="iconList.XIAYISHOU" @click="next"></my-logo>
        <!--音量-->
        <el-dropdown class="yin-play-show" trigger="click">
          <my-logo v-if="volume !== 0" :icon="iconList.YINLIANG"></my-logo>
          <my-logo v-else :icon="iconList.JINGYIN"></my-logo>
          <template #dropdown>
            <el-dropdown-menu>
              <el-slider class="yin-slider" style="height: 150px; margin: 10px 0" v-model="volume" :vertical="true">
              </el-slider>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="song-ctr song-edit">
        <!--收藏-->
        <my-logo class="yin-play-show" :class="{ active: isCollection }"
          :icon="isCollection ? iconList.like : iconList.dislike" @click="changeCollection"></my-logo>
        <!--下载-->
        <my-logo class="yin-play-show" :icon="iconList.download" @click="
          downloadMusic({
            songUrl,
            songName: singerName + '-' + songTitle,
          })
        "></my-logo>
        <!--歌曲列表-->
        <my-logo :icon="iconList.LIEBIAO" @click.stop="changeAside"></my-logo>
      </div>
    </div>
  </div>
</template>
    
<script setup>
import { ref, computed, onMounted, watch, reactive, toRaw } from "vue";
import { mapGetters, useStore } from "vuex";
import utils from "@/utils";
import MyLogo from "./MyLogo.vue";
import { HttpManager } from "@/api";
import { Icon, RESPONSE_CODE, RouterName } from "@/enums";
import { ElMessage } from "element-plus";
import { ArrowUpBold } from "@element-plus/icons-vue";

const store = useStore();
const { routerManager, playMusic, checkStatus, downloadMusic, formatSeconds } = utils();
const attachImageUrl = HttpManager.attachImageUrl

const isCollection = ref(false); // 是否收藏

const userId = computed(() => store.getters.userId);
const isPlay = computed(() => store.getters.isPlay);
const playBtnIcon = computed(() => store.getters.playBtnIcon)
const songId = computed(() => store.getters.songId);
const songUrl = computed(() => store.getters.songUrl);
const songTitle = computed(() => store.getters.songTitle);
const singerName = computed(() => store.getters.singerName);
const songPic = computed(() => store.getters.songPic)
const curTime = computed(() => store.getters.curTime);
const duration = computed(() => store.getters.duration);
const currentPlayList = computed(() => store.getters.currentPlayList);
const currentPlayIndex = computed(() => store.getters.currentPlayIndex);
const showAside = computed(() => store.getters.showAside);
const autoNext = computed(() => store.getters.autoNext);
const token = computed(() => store.getters.token);

const startTime = ref("00:00")
const endTime = ref("00:00")
const nowTime = ref(0) // 进度条的位置
const toggle = ref(true)
const volume = ref(50)
const playState = ref(Icon.XUNHUAN)
const playStateList = ref([Icon.XUNHUAN, Icon.LUANXU])
const playStateIndex = ref(0)
const iconList = reactive({
  download: Icon.XIAZAI,
  ZHEDIE: Icon.ZHEDIE,
  SHANGYISHOU: Icon.SHANGYISHOU,
  XIAYISHOU: Icon.XIAYISHOU,
  YINLIANG: Icon.YINLIANG1,
  JINGYIN: Icon.JINGYIN,
  LIEBIAO: Icon.LIEBIAO,
  dislike: Icon.Dislike,
  like: Icon.Like,
})

watch(songId, () => {
  initCollection();
});

watch(token, (value) => {
  if (!value) isCollection.value = false;
});

watch(isPlay, (value) => {
  // console.log(value)
  store.commit("setPlayBtnIcon", value ? Icon.ZANTING : Icon.BOFANG)
})

watch(volume, () => {
  store.commit("setVolume", volume.value / 100)
})

watch(curTime, () => {
  startTime.value = formatSeconds(curTime.value);
  endTime.value = formatSeconds(duration.value);
  // 移动进度条
  nowTime.value = (curTime.value / duration.value) * 100;
})

watch(autoNext, () => {
  next()
})

function changeAside() {
  store.commit("setShowAside", !showAside.value);
}

function togglePlay() {
  store.commit("setIsPlay", isPlay.value ? false : true);

}

function changeTime() {
  store.commit("setChangeTime", duration.value * (nowTime.value * 0.01));

}

function changePlayState() {
  playStateIndex.value = playStateIndex.value >= playStateList.value.length - 1 ? 0 : ++playStateIndex.value;
  playState.value = playStateList.value[playStateIndex.value];
}

function prev() {
  // console.log(currentPlayList.value[currentPlayIndex.value])
  if (playState.value === Icon.LUANXU) {
    let playIndex = Math.floor(Math.random() * currentPlayList.value.length);
    playIndex = playIndex === currentPlayIndex ? playIndex + 1 : playIndex;
    store.commit("setCurrentPlayIndex", playIndex);
    toPlay(currentPlayList.value[playIndex].url);
  } else if (currentPlayIndex.value !== -1 && currentPlayList.value.length > 1) {
    if (currentPlayIndex.value > 0) {
      store.commit("setCurrentPlayIndex", currentPlayIndex.value - 1);
      toPlay(currentPlayList.value[currentPlayIndex.value].url);
    } else {
      store.commit("setCurrentPlayIndex", currentPlayList.value.length - 1);
      toPlay(currentPlayList.value[currentPlayIndex.value].url);
    }
  }
}

function next() {
  if (playState.value === Icon.LUANXU) {
    let playIndex = Math.floor(Math.random() * currentPlayList.value.length);
    playIndex = playIndex === currentPlayIndex.value ? playIndex + 1 : playIndex;
    store.commit("setCurrentPlayIndex", playIndex);
    toPlay(currentPlayList.value[playIndex].url);
  } else if (currentPlayIndex.value !== -1 && currentPlayList.value.length > 1) {
    if (currentPlayIndex.value < currentPlayList.value.length - 1) {
      store.commit("setCurrentPlayIndex", currentPlayIndex.value + 1);
      toPlay(currentPlayList.value[currentPlayIndex.value].url);
    } else {
      store.commit("setCurrentPlayIndex", 0);
      toPlay(currentPlayList.value[0].url);
    }
  }
}

function toPlay(url) {
  if (url && url !== songUrl) {
    const song = currentPlayList.value[currentPlayIndex.value];
    playMusic({
      id: song.id,
      url,
      pic: song.pic,
      index: currentPlayIndex.value,
      name: song.name,
      lyric: song.lyric,
      currentSongList: currentPlayList.value,
    });
    store.commit("setIsPlay", true);
  }
}

function goPlayerPage() {
  if(store.getters.songId !== "")
    routerManager(RouterName.Lyric, { path: `${RouterName.Lyric}/${songId}` });
}

async function initCollection() {
  if (!checkStatus(false)) return;

  const data = {
    "userId": userId.value,
    "type": "0",
    "songId": songId.value
  }

  isCollection.value = ((await HttpManager.isCollection(JSON.stringify(data), { headers: { 'token': store.getters.token } }))).data;

}

async function changeCollection() {
  if (!checkStatus(true)) return;

  const data = {
    "userId": userId.value,
    "type": "0",
    "songId": songId.value
  }

  const result = isCollection.value
    ? ((await HttpManager.deleteCollection(userId.value, songId.value, { headers: { 'token': store.getters.token } })))
    : ((await HttpManager.setCollection(JSON.stringify(data), { headers: { 'token': store.getters.token } })));
  
  ElMessage({
    message: result.message,
    type: 'success',
  })

  if (result.code === RESPONSE_CODE.SUCCESS) isCollection.value = result.data !== null;

}

onMounted(() => {
  if (songId.value) initCollection();
});
</script>
    
<style lang="scss" scoped>
@import "@/assets/css/yin-play-bar.scss";
</style>
