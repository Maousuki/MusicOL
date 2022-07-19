<template>
  <ul class="yin-nav">
    <li v-for="(item, index) in styleList" :key="index" :class="{ active: item.name == activeName }" @click="handleChangeView(item)">
      {{ item.name }}
    </li>
  </ul>
</template>

<script setup>

const props = defineProps({
    styleList: Array,
    activeName: String,
})

const emits = defineEmits(['click'])

function handleChangeView(value) {
    emits('click', value)
}

</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";

.yin-nav {
  width: 100%;
  li {
    line-height: 2rem;
    font-size: 1rem;
    color: $color-grey;
    border-bottom: none;
    cursor: pointer;
  }
  li.active {
    color: $color-black;
    font-weight: 600;
  }
}

@media screen and (min-width: $sm) {
  .yin-nav {
    li {
      margin: 0.5rem 1rem;
    }
  }
}

@media screen and (max-width: $sm) {
  .yin-nav {
    li {
      margin: 0.3rem 0.4rem;
    }
  }
}
</style>
