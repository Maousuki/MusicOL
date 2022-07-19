<template>
  <div class="my-footer">
    <p v-for="(item, index) in footerList" :key="index">
      {{ item }}
    </p>
  </div>
</template>

<script setup>
import { readonly } from "vue";

// 暂时先这样
const footerList = readonly([
  "关于 | 帮助 | 条款 | 反馈",
  "Copyright © 2022",
]);

</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

.my-footer {
  width: 100%;
  height: $footer-height;
  padding: 20px 0;
  box-sizing: border-box;
  font-size: 14px;
  @include layout(center, center, column);
}

p {
  height: 30px;
}
</style>
