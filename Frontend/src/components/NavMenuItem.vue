<template>
  <router-link :class="{
    'nav-menu-item': true,
    'nav-menu-item-highlighted': route.name === props.data.routeName,
  }" :to="{ name: props.data.routeName }">
    <v-icon :icon="props.data.icon"></v-icon>
    <div class="nav-menu-item-text">{{ $props.data.name }}</div>
  </router-link>
</template>
<script setup lang="ts">
import { useRoute } from "vue-router";

const route = useRoute();

const props = defineProps<{
  data: {
    name: string;
    routeName: string;
    icon: string;
  };
}>();
</script>
<style lang="scss" scoped>
.nav-menu-item {
  padding: 8px 24px 8px 12px;
  display: flex;
  text-decoration: none;
  color: black;

  &:hover {
    background-color: rgba(0, 0, 0, 0.075);
  }

  .nav-menu-item-text {
    margin-left: 12px;
    font-size: 16px;
  }
}

.nav-menu-item-highlighted {
  background-color: rgba(91, 74, 154, 0.15);
  color: $secondary;

  &:hover {
    background-color: rgba(91, 74, 154, 0.25) !important;
  }
}
</style>
