<template>
  <WidgetComponent title="Популярные вопросы">
    <template #default>
      <v-select variant="underlined" class="mx-7" hide-details :items="['Позитивные', 'Негативные', 'Нейтральные']"
        v-model="select" @update:model-value="getData()"></v-select>
      <div style="height:520px">
        <BarChart :chart-data='chartData' />
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
  let lal = "positive"
  if (select.value == 'Позитивные') {
    lal = 'positive'
  }
  if (select.value == 'Негативные') {
    lal = 'negative'
  }
  if (select.value == 'Нейтральные') {
    lal = 'neutral'
  }

  const resp = await AnalyticController.getTop(route.query.fileId, route.query.questionId, lal)
  chartData.value = resp.reverse()
}
onMounted(getData)
</script>
<style scoped lang="scss"></style>