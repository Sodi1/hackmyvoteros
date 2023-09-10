<template>
  <div class="widget-card elevation-2">
    <div class="widget-card-title">{{ props.title }}</div>
    <div class="widget-card-body">
      <slot name="default">

      </slot>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  title: String
})
</script>
<style scoped lang="scss">
.widget-card {
  border-radius: 10px;
  background-color: white;
  display: flex;
  flex-direction: column;

  .widget-card-title {
    font-size: 18px;
    font-weight: 500;
    margin: 16px;
    color: $text-light;
    padding: 0 0 12px 4px;
    border-bottom: 1px solid $text-lighter;
  }

  .widget-card-body {
    padding: 12px;
    flex-grow: 1;
  }
}
</style> 