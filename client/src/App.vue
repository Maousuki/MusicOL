<template>
  <router-view />
</template>

<script setup>
import { useStore } from 'vuex'
import { Icon } from './enums';

const store = useStore()

// 添加页面缓存，以使页面刷新后状态不会重置
if (sessionStorage.getItem("dataStore")) {
  store.replaceState(Object.assign({}, store.state, JSON.parse(sessionStorage.getItem("dataStore"))));
}

window.addEventListener("beforeunload", () => {
  store.commit('setIsPlay', false)
  store.commit('setPlayBtnIcon', Icon.BOFANG)
  sessionStorage.setItem("dataStore", JSON.stringify(store.state));
});

</script>

<style>
  body {
    font-family:”Microsoft YaHei”,Arial,Helvetica,sans-serif,”Microsoft YaHei”, Arial, Helvetica, sans-serif, ”heiti””;
  }
</style>