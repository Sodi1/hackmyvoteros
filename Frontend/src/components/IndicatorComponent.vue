<template>
  <div class="indicator elevation-2">
    <div class="indicator-title">{{ props.title }}: {{ props.value }}</div>
    <div class="indicator-line-wrapper">
      <div class="indicator-line"></div>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  title: String,
  value: String,
  percentage: String
})
</script>
<style scoped lang="scss">
.indicator {
  border-radius: 10px;
  background-color: white;
  height: min-content;
  padding: 20px;
  .indicator-title {
    font-size: 16px;
    font-weight: 400;
    color: $text-light;
    padding-bottom: 12px;
  }

  .indicator-line-wrapper {
    width: 100%;
    height: 4px;
    border-radius: 4px;
    background-color: $text-lightest;

    .indicator-line {
      background-color: $primary;
      border-radius: 4px;
      height: 100%;
      width: v-bind(percentage)
    }
  }
}
</style>