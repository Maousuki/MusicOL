<template>

  <el-button type="primary" size="default" @click="sendRequest">发送GET测试</el-button>
  <el-button type="primary" size="default" @click="refBool = !refBool">改变布尔常量ref测试</el-button>
  <el-button type="primary" size="default" @click="logToken">log token</el-button>
  <el-button type="primary" size="default" @click="testStore">test Store</el-button>
  <el-button type="primary" size="default" @click="changeAside">change showAside</el-button>
  <el-button type="primary" size="default" @click="changePlay">change play</el-button>
  <el-button type="primary" size="default" @click="showRoute">show route</el-button>
  <el-button type="primary" size="default" @click="showPlayIndex">show currentPlayIndex</el-button>
  <a href="http://47.108.153.18:8481/songs/shendesuibozhuliu.mp3" download="test.mp3">尝试下载</a>


  <p>method: {{ tokenObj }}</p>
  <p>computed: {{ tokenObj2 }}</p>
  <p>internal computed: {{ tokenObj3 }}</p>
  <p>showAside: {{ showAside }}</p>
  <p>isPlay: {{ isPlay }}</p>
  <p>userPic:{{ userPic }}</p>
</template>
    
<script setup>
import { HttpManager } from '@/api';
import { ref, computed, reactive } from 'vue'
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'

const refBool = ref(false)
const store = useStore()
const route = useRoute()

const tokenObj = reactive({
  token: ""
})
const tokenObj2 = computed(() => { return { token: store.getters.token } })
const tokenObj3 = { token: computed(() => store.getters.token) }

const showAside = computed(() => store.getters.showAside)

const userPic = computed(() => store.getters.userPic)

const isPlay = computed(() => store.getters.isPlay)

async function sendRequest() {
  const result = await HttpManager.test()
  console.log(result)

}

function logToken() {
  tokenObj.token = store.getters.token
  console.log(tokenObj)
  console.log(tokenObj2)
  console.log(tokenObj3)
}

function testStore() {
  console.log(store)
}

function changeAside() {
  store.commit("setShowAside", !showAside.value);
}

function changePlay() {
  store.commit("setIsPlay", !isPlay.value);
}

function showRoute() {
  console.log(route)
}

function showPlayIndex() {
  console.log(store.getters.currentPlayIndex)
}


</script>
    
<style lang='stylus' scoped></style>
    