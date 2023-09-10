<template>
  <WidgetComponent title="Популярные ответы">
    <template #default>
      <div class="top-answers">

        <div class="top-answer" v-for="(i, j) in chartData" :key="j">
          №{{ j + 1 }}&nbsp; &nbsp;{{ i }}
        </div>

      </div>
    </template>
  </WidgetComponent>
</template>
<script setup>
import { onMounted } from 'vue';
import WidgetComponent from './WidgetComponent.vue';
import BarChart from './charts/BarChart.vue';
import { AnalyticController } from "@/api/controllers/AnalyticController"
import { ref } from "vue"
import { useRoute } from 'vue-router';

const route = useRoute()
const chartData = ref([])

const select = ref("Позитивные")
const getData = async () => {

  const resp = await AnalyticController.anwerTop(route.query.fileId, route.query.questionId)
  chartData.value = resp.data
}
onMounted(getData)
</script>
<style scoped lang="scss">
.top-answers {
  padding: 20px;
  position: relative;



  &> :last-child {
    border-bottom: none !important;
  }

  .top-answer {
    font-size: 18px;
    padding: 14px 14px 14px 4px;
    border-bottom: 1px solid $text-lightest;

    &:hover {
      background-color: rgba(0, 0, 0, 0.045);
    }
  }
}
</style>