<template>
  <div class="nav-menu-wrapper">
    <NavMenuItem v-for="(r, i) in routes" :key="i" :data="r"></NavMenuItem>
  </div>
</template>
<script setup lang="ts">
import NavMenuItem from "./NavMenuItem.vue";

const routes = [
  {
    name: "Домашняя",
    icon: "mdi-home-outline",
    routeName: "Home",
  },
  {
    name: "Тональность комментария",
    icon: "mdi-focus-field-horizontal",
    routeName: "Emotion",
  },
  // {
  //   name: "features.nav.CICDNav",
  //   icon: "mdi-rocket-launch-outline",
  //   routeName: "CI_CD",
  // },
  // {
  //   name: "features.nav.extraMetricsNav",
  //   icon: "mdi-plus-circle-outline",
  //   routeName: "ExtraMetrics",
  // },
  // {
  //   name: "features.nav.lossesNav",
  //   icon: "mdi-cash-minus",
  //   routeName: "Losses",
  // },
  // {
  //   name: "features.nav.codeAnalysisNav",
  //   icon: "mdi-file-code-outline",
  //   routeName: "CodeAnalysis",
  // },
  // {
  //   name: "features.nav.resourcePlanningNav",
  //   icon: "mdi-account-multiple-plus",
  //   routeName: "Resources",
  // },
  // {
  //   name: "features.nav.clustersNav",
  //   icon: "mdi-chart-scatter-plot",
  //   routeName: "Clusters",
  // },
  // {
  //   name: "features.nav.adminNav",
  //   icon: "mdi-account-key-outline",
  //   routeName: "Admin",
  // },
  // {
  //   name: "features.nav.docsNav",
  //   icon: "mdi-file-document-outline",
  //   routeName: "Docs",
  // },
  // {
  //   name: "features.nav.indicatorDocsNav",
  //   icon: "mdi-file-question-outline",
  //   routeName: "IndicatorsDocs",
  // },
];
</script>
<style lang="scss" scoped>
.nav-menu-wrapper {
  &> :nth-child(8) {
    border-bottom: 1px solid $text-lightest;
  }
}
</style>
