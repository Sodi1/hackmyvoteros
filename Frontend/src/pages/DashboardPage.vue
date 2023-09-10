<template>
  <div class="dashboard-wrapper">
    <div class="dashboard-head">
      Общий анализ вопросов и ответов
    </div>
    <div class="group-50-50">
      <TopQuestionsWidget />
      <EmotionalDestribution />
    </div>

    <div class="indicators-group">
      <WidgetComponent title="Распределение эмоций по ответам">
        <template #default>
          <PieChart :chart-data="{
            name: 'распределение эмоций', data: pie
          }" />
        </template>
      </WidgetComponent>
      <div class="indicators-grid">
        <IndicatorComponent v-for="i in indicators" :title="i.title" :value="i.value" :percentage="i.percentage" />
      </div>
    </div>
    <TagCloud></TagCloud>
  </div>
</template>
<script setup>
import { AnalyticController } from '@/api/controllers/AnalyticController';
import { QuestionController } from '@/api/controllers/QuestionController';
import EmotionalDestribution from '@/components/EmotionalDestribution.vue';
import IndicatorComponent from '@/components/IndicatorComponent.vue';
import TagCloud from '@/components/TagCloud.vue';
import TopQuestionsWidget from '@/components/TopQuestionsWidget.vue';
import WidgetComponent from '@/components/WidgetComponent.vue';
import BarChart from '@/components/charts/BarChart.vue';
import PieChart from '@/components/charts/PieChart.vue'
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const route = useRoute()
const pie = ref([]);
const indicators = ref([])

onMounted(async () => {
  [pie.value] = await Promise.all([AnalyticController.getPie(route.query.fileId)])
  let sum = 0;

  pie.value.forEach((v) => {
    if (v.name == 'negative') {
      v.name = 'Негативно'
      sum += v.value
    }
    if (v.name === 'positive') {
      v.name = 'Позитивно'
      sum += v.value
    }
    if (v.name === 'neutral') {
      v.name = 'Нейтрально'
      sum += v.value
    }
  })
  pie.value.forEach((v) => {
    if (v.name == 'Негативно') {
      indicators.value.push({ title: "Негативных ответов", value: Math.round(v.value / sum * 100) + "%", percentage: v.value / sum * 100 + "%" })
    }
    if (v.name === 'Позитивно') {
      indicators.value.push({ title: "Позитивных ответов", value: Math.round(v.value / sum * 100) + "%", percentage: v.value / sum * 100 + "%" })
    }
    if (v.name === 'Нейтрально') {
      indicators.value.push({ title: "Нейтральных ответов", value: Math.round(v.value / sum * 100) + "%", percentage: v.value / sum * 100 + "%" })
    }
  })
  const stat = await AnalyticController.stat(route.query.fileId)
  const stat2 = await AnalyticController.incorrectAnswer(route.query.fileId)

  indicators.value.push({ title: "Нецензурных ответов", value: stat.data / 1000 + "%", percentage: stat.data / 1000 + "%" })
  indicators.value.push({ title: "Нелогичных ответов", value: Math.round(stat2.data / sum * 100) + "%", percentage: Math.round(stat2.data / sum * 100) + "%" })
})
</script>
<style scoped lang="scss">
.dashboard-wrapper {


  display: flex;
  flex-direction: column;
  padding: 24px;

  &>div {
    margin-bottom: 24px;
  }

  .dashboard-head {
    font-size: 22px;
    font-weight: 600;
    color: $text-light;
    margin: 12px 0 24px 6px;
  }

  .group-50-50 {
    display: flex;

    &> :first-child {
      width: calc(45% - 12px);
      margin-right: 24px;
    }

    &> :nth-child(2) {
      width: calc(55% - 12px);
    }
  }

  .indicators-group {
    display: flex;

    &> :first-child {
      height: 400px;
      width: 400px;
    }

    .indicators-grid {
      margin-left: 24px;
      flex-grow: 1;
      display: grid;
      grid-template-columns: 1fr 1fr;
      grid-row-gap: 24px;
      grid-column-gap: 24px;
      grid-auto-rows: min-content;
    }
  }
}
</style>