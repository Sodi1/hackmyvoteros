<template>
  <div class="dashboard-select-wrapper">
    <div></div>
    <div class="to-dashboard-file to-card elevation-2" @click="router.push({
      name: 'FileDashboard', query: { fileId: route.query.id }
    })">
      <div>Дашборд общий (по всему файлу)</div>
    </div>
    <div class="to-dashboard-question to-card elevation-2">
      <div>Дашборд вопросов</div>
      <div>
        <v-autocomplete color="primary " placeholder="Выбрать " variant="underlined" item-value="name" item-title="name"
          v-model="selectedQuestion" :items="questions">
          <template v-slot:item="{ props, item }">
            <v-list-item v-bind="props" :title="item.raw.name" @click="router.push({
              name: 'QuestionDashboard', query: { questionId: item.raw.id, fileId: route.query.id }
            })"></v-list-item>
          </template>
        </v-autocomplete>
      </div>
    </div>
    <div></div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { QuestionController } from '@/api/controllers/QuestionController';
import { useRoute, useRouter } from 'vue-router';
import "@/assets/one.png"
import "@/assets/many.png"

const router = useRouter()
const route = useRoute()
const questions = ref([])
const selectedQuestion = ref()

onMounted(async () => {
  [questions.value] = await Promise.all([QuestionController.getQuestions(route.query.id, route.query.questionId)])

})
</script>
<style scoped lang="scss">
.dashboard-select-wrapper {
  height: calc(100dvh - 70px);
  display: flex;
  align-items: center;
  justify-content: space-around;

  .to-card {
    padding: 20px;
    cursor: pointer;
    background-color: white;
    border-radius: 10px;
    height: 360px;
    width: 360px;
    transition: all 0.4s ease;
    background-size: contain;
    background-position: center;
    font-size: 16px;
    font-weight: 600;
    color: $text-light;

    &:hover {
      box-shadow: 0 0px 11px 0px $primary !important;
    }
  }

  .to-dashboard-file {
    background-image: url('@/assets/one.png');
  }

  .to-dashboard-question {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    background-image: url('@/assets/many.png');
  }
}
</style>