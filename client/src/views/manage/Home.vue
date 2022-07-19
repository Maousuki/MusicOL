<template>
    <manage-header />
    <manage-aside />
    <div class="content-box" :class="{'content-collapse': collapse}">
        <router-view></router-view>
    </div>
    <manage-audio />
</template>
    
<script setup>
import ManageHeader from '@/components/manage/layouts/ManageHeader.vue';
import ManageAudio from '@/components/manage/layouts/ManageAudio.vue';
import ManageAside from '@/components/manage/layouts/ManageAside.vue';

import { ref, computed } from 'vue';
import { useStore } from 'vuex';

const store = useStore()

const collapse = computed(() => store.getters.collapse)



</script>

<style scoped>
.content-box {
    position: absolute;
    left: 150px;
    right: 0;
    top: 60px;
    bottom: 0;
    overflow-y: scroll;
    transition: left 0.3s ease-in-out;
    padding: 20px;
}

.content-collapse {
    left: 65px;
}
</style>