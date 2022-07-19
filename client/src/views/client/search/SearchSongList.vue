<template>
    <div class="search-song-list">
        <play-list :playList="playList" path="song-sheet-detail"></play-list>
    </div>
</template>

<script setup>
import { defineComponent, ref, computed, watch, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import PlayList from "@/components/client/PlayList.vue";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";

const store = useStore();
const route = useRoute();

const playList = ref([]);
const searchWord = computed(() => store.getters.searchWord);
watch(searchWord, (value) => {
    getSearchList(value);
});

async function getSearchList(value) {
    if (!value) return;
    const result = (await HttpManager.getSongListOfLikeTitle(value));
    if (!result.data.length) {
        ElMessage.warning("暂无该歌单内容")
    } else {
        playList.value = result.data;
    }
}

onMounted(() => {
    getSearchList(route.query.keywords);
});
</script>
