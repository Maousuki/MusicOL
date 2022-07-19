<template>
  <el-container>
    <el-aside class="album-slide">
      <el-image class="album-img" fit="contain" :src="attachImageUrl(songDetails.pic)" />
      <h3 class="album-info">{{ songDetails.title }}</h3>
    </el-aside>
    <el-main class="album-main">
      <h1>简介</h1>
      <p>{{ songDetails.introduction }}</p>
      <!--歌曲-->
      <song-list class="album-body" :songList="currentSongList"></song-list>
      <comment :playId="songListId" :type="1"></comment>
    </el-main>
  </el-container>
</template>
    
<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import utils from "@/utils";
import SongList from "@/components/client/SongList.vue";
import Comment from "@/components/client/Comment.vue";
import { HttpManager } from "@/api";

const store = useStore()

const attachImageUrl = HttpManager.attachImageUrl

const currentSongList = ref([]); // 存放的音乐
const songListId = ref(""); // 歌单 ID
const songDetails = computed(() => store.getters.songDetails); // 单个歌单信息
const userId = computed(() => store.getters.userId);

songListId.value = songDetails.value.id; // 给歌单ID赋值

// 收集歌单里面的歌曲
async function getSongId(id) {
  const result = (await HttpManager.getListSongOfSongId(id));
  // 获取歌单里的歌曲信息
  for (const item of result.data) {
    // 获取单里的歌曲
    const resultSong = (await HttpManager.getSongOfId(item.songId));
    currentSongList.value.push(resultSong.data);
  }
}

getSongId(songListId.value); // 获取歌单里面的歌曲ID

</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";

.album-slide {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;

  .album-img {
    height: 250px;
    width: 250px;
    border-radius: 10%;
  }

  .album-info {
    width: 70%;
    padding-top: 2rem;
  }
}

.album-main {
  h1 {
    font-size: 22px;
  }

  p {
    color: rgba(0, 0, 0, 0.5);
    margin: 10px 0 20px 0px;
  }
  /*歌单打分*/
  .album-score {
    display: flex;
    align-items: center;
    margin: 1vw;

    h3 {
      margin: 10px 0;
    }
    span {
      font-size: 60px;
    }
    & > div:last-child {
      margin-left: 10%;
    }
  }

  .album-body {
    margin: 20px 0 20px 0px;
  }
}

@media screen and (min-width: $sm) {
  .album-slide {
    position: fixed;
    width: 400px;
  }
  .album-main {
    min-width: 600px;
    padding-right: 10vw;
    margin-left: 400px;
  }
}

@media screen and (max-width: $sm) {
  .album-slide {
    display: none;
  }
}
</style>
