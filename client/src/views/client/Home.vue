<template>
  <!-- 主页应该由三个组件组成，一个轮播图，一个热门歌单，一个热门歌手 -->
  <el-carousel class="swiper-container" type="card" height="20vw" :interval="4000">
    <el-carousel-item v-for="(item, index) in swiper" :key="index">
      <!-- content -->
      <img :src="item.picImg" />
    </el-carousel-item>
  </el-carousel>

  <!-- 这是一个仅管理员可见的在主页上的进入test界面的按钮，用于前端调试，最后打包的时候可以注释掉 -->
  <!-- <el-button v-if="isAdmin" type="primary" size="default" @click="toTest">进入Test界面</el-button> -->

  <play-list class="play-list-container" title="歌单" path="song-sheet-detail" :play-list="songList" />
  <play-list class="play-list-container" title="歌手" path="singer-detail" :play-list="singerList" />

</template>
    
<script setup>
import PlayList from '@/components/client/PlayList.vue';
import { swiperList, NavName } from '@/enums';
import { ref, onMounted, computed } from 'vue'
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import utils from '@/utils'
import { HttpManager } from '@/api';

const swiper = ref(swiperList);
const store = useStore()
const router = useRouter()

const songList = ref([])
const singerList = ref([]); // 歌手列表
const { changeActiveName } = utils()

const isAdmin = computed(() => store.getters.userRole === 1 ? true : false)

function toTest() {
  router.push('/test')
}

// 获取歌单与歌手
try {
  HttpManager.getSongList().then((res) => {
    songList.value = res.data.sort().slice(0, 10);
  });

  HttpManager.getAllSinger().then((res) => {
    singerList.value = res.data.sort().slice(0, 10);
  });

  onMounted(() => {
    changeActiveName(NavName.Home);
  });
} catch (error) {
  console.error(error);
}
</script>
    
<style lang="scss" scoped>
@import "@/assets/css/var.scss";

/*轮播图*/
.swiper-container {
  width: 90%;
  margin: auto;
  padding-top: 20px;

  img {
    width: 100%;
  }
}

.swiper-container:deep(.el-carousel__indicators.el-carousel__indicators--outside) {
  display: inline-block;
  transform: translateX(30vw);
}

.el-slider__runway {
  background-color: $color-blue;
}
</style>

    