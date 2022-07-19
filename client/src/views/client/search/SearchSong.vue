<template>
    <div class="search-song">
        <song-list :songList="currentSongList"></song-list>
    </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import SongList from "@/components/client/SongList.vue";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";

const store = useStore();
const route = useRoute();

const currentSongList = ref([]); // 存放的音乐
const searchWords = computed(() => store.getters.searchWords);
watch(searchWords, (value) => {
    searchSong(value);
});

// 搜索音乐
async function searchSong(value) {
    if (!value) {
        currentSongList.value = [];
        return;
    }
    const result = (await HttpManager.getSongOfSongName(value))
    if (!result.data.length) {
        currentSongList.value = [];
        ElMessage.warning("暂时没有相关歌曲")
    } else {
        currentSongList.value = result.data;
    }
}

onMounted(() => {
    searchSong(route.query.keywords);
});

</script>
