<template>
<!-- 导航栏组件 -->
  <ul class="my-nav">
    <li :class="{active: activeNavName === option.name}" v-for="option in options" :key="option.path" @click.stop="onClick(option)">{{option.name}}</li>
  </ul>
</template>
    
<script setup>
const props = defineProps({
  options: Array,
  activeNavName: String,
})

const emits = defineEmits(['click'])

const onClick = (option) => {
  emits('click', option.path, option.name)
}

</script>
    
<style lang="scss" scoped>
@import "@/assets/css/var.scss";

li {
  margin: $header-nav-margin;
  padding: $header-nav-padding;
  line-height: 3.3rem;
  color: $color-light-grey;
  border-bottom: none;
  cursor: pointer;
}

li.active {
  color: $color-white;
  font-weight: 600;
  border-bottom: 5px solid $color-blue-green-light;
}
</style>
