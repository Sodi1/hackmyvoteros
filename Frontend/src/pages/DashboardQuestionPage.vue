<template>
  <div class="dashboard-wrapper">
    <div class="dashboard-head" :key="indicators.length">
      Общий анализ вопроса: <span style="font-weight: 500;text-decoration: underline;"> "{{ text }}"</span>
    </div>
    <div class="group-50-50" >
      <TopAnswers :key="indicators.length" />
      <TagCloud :key="indicators.length"  />
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
    <div class="dashboard-commoon-words">
      <v-autocomplete color="primary " placeholder="Перейти на анализ похожего вопроса" variant="solo" item-value="name"
        item-title="name" v-model="selectedQuestion" :items="questions">
        <template v-slot:item="{ props, item }">
          <v-list-item v-bind="props" :title="item.raw.name" @click="router.push({
            name: 'QuestionDashboard', query: { questionId: item.raw.id, fileId: route.query.fileId }
          });">
            <template #prepend>
              <div class="rating">{{ item.raw.score.toFixed(3) }}</div>
            </template></v-list-item>
        </template>
      </v-autocomplete>
      <v-text-field v-model="numberValue" placeholder="0 < v < 100" hide-details single-line variant="solo"
        type="number" />
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { QuestionController } from '@/api/controllers/QuestionController';
import { useRoute, useRouter } from 'vue-router';
import { watch } from 'vue';
import { AnalyticController } from '@/api/controllers/AnalyticController';
import EmotionalDestribution from '@/components/EmotionalDestribution.vue';
import IndicatorComponent from '@/components/IndicatorComponent.vue';
import TopQuestionsWidget from '@/components/TopQuestionsWidget.vue';
import WidgetComponent from '@/components/WidgetComponent.vue';
import BarChart from '@/components/charts/BarChart.vue';
import PieChart from '@/components/charts/PieChart.vue'
import TagCloud from "@/components/TagCloud.vue"
import TopAnswers from '@/components/TopAnswers.vue';

const router = useRouter()
const route = useRoute()
const questions = ref([])
const numberValue = ref(50)
const selectedQuestion = ref()

const text = ref("")

const pie = ref()
const indicators = ref([])

watch(numberValue, (v) => {
  if (v > 100) {
    numberValue.value = 100
  }
  if (v < 0) {
    numberValue.value = 0
  }
})

const getData = async () => {
  text.value = (await QuestionController.getQuestionsById(route.query.questionId)).name
  indicators.value = [];
  pie.value = await AnalyticController.getPie(route.query.fileId, route.query.questionId)
  let sum = 0

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
  const stat = await AnalyticController.stat(route.query.fileId, route.query.questionId)
  const stat2 = await AnalyticController.incorrectAnswer(route.query.fileId, route.query.questionId)

  indicators.value.push({ title: "Нецензурных ответов", value: stat.data + "%", percentage: stat.data + "%" })
  indicators.value.push({ title: "Нелогичных ответов", value: Math.round(stat2.data / sum * 100) + "%", percentage: Math.round(stat2.data / sum * 100) + "%" })

  questions.value = []
  questions.value = await QuestionController.getSearchedQuestions(route.query.fileId, route.query.questionId, numberValue.value / 100)
}

watch(() => route.query, () => {
  getData()
})

onMounted(getData)
</script>
<style scoped lang="scss">
.rating {
  margin: 0px 12px 0 8px;
  color: $primary
}

.dashboard-head {
  font-size: 22px;
  font-weight: 600;
  color: $text-light;
  margin: 12px 0 24px 6px;
}

.dashboard-wrapper {
  display: flex;
  flex-direction: column;
  padding: 24px;

  &>div {
    margin-bottom: 24px;
  }

  .group-50-50 {
    display: flex;

    &> :first-child {
      width: 400px;
    }

    &> :nth-child(2) {
      width: calc(100% - 412px);
      margin-left: 24px;
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

  .dashboard-commoon-words {
    display: flex;

    &> :nth-child(1) {
      margin-right: 24px;
      flex-grow: 1;
    }

    &> :nth-child(2) {
      max-height: 20px;
      margin-right: 24px;
      max-width: 150px;
    }


  }
}
</style>