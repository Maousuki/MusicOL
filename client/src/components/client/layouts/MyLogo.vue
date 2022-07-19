<template>
  <svg class="icon" aria-hidden="true">
    <use :xlink:href="icon"></use>
  </svg>
</template>
    
<script setup>
defineProps({
  icon: String,
})
</script>