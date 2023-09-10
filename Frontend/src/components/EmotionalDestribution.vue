<template>
  <WidgetComponent title="Эмоциональное распределние">
    <div class="d-flex align-center px-1 pb-9">
      <v-text-field class="mr-6" hide-details v-model="search" label="Поиск" color="#523ae5"
        prepend-inner-icon="mdi-magnify"></v-text-field>
      <v-select class="ml-6" style="width:50px" hide-details v-model="select" @update:model-value="getData"
        :items="['negative', 'positive', 'neutral', 'count']">
        <template v-slot:item="{ props, item }">
          <v-list-item v-bind="props" :title="obj[item.raw]"></v-list-item>
        </template>
        <template v-slot:selection="{ item, index }">
          {{ obj[item.raw] }}
        </template>
      </v-select>
    </div>

    <TableComponent :table-headers="tableH" :table-rows="tableItemsFiltered"></TableComponent>
    <div style="width:100%;height:100%;" class="d-flex align-center justify-center">
      <v-progress-circular v-show="isLoading" :size="120" :width="12" indeterminate color="primary"></v-progress-circular>
    </div>
  </WidgetComponent>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import TableComponent from './TableComponent.vue';
import { useRoute } from 'vue-router';
import { AnalyticController } from "@/api/controllers/AnalyticController"
import WidgetComponent from './WidgetComponent.vue';

const route = useRoute()
const select = ref('count')

const search = ref("");

const tableR = ref([])
const tableH = ref([])

const isLoading = ref(false);

const obj = {
  name: "вопрос",
  negative: "негативных",
  positive: "позитивных",
  neutral: "нейтральных",
  count: "общее кол-во",
}

const tableItemsFiltered = computed(() => {
  if (search.value.length) {
    return tableR.value.filter((v) => v.name.includes(search.value))
  }
  return tableR.value;
});

async function getData(v) {
  isLoading.value = true;
  const resp = await AnalyticController.getEmotionalDestribution(route.query.fileId, route.query.questionId, v)
  isLoading.value = false;
  const tableRows = resp
  const tableHeaders = []
  Object.keys(tableRows[0]).forEach((v) => {
    tableHeaders.push({ title: obj[v], key: v })
  })
  console.log(tableHeaders)
  tableH.value = tableHeaders
  tableR.value = tableRows
}

onMounted(async () => {
  isLoading.value = true;
  const resp = await AnalyticController.getEmotionalDestribution(route.query.fileId, route.query.questionId, select.value)
  isLoading.value = false;
  const tableRows = resp
  const tableHeaders = []
  Object.keys(tableRows[0]).forEach((v) => {
    tableHeaders.push({ title: obj[v], key: v })
  })
  tableH.value = tableHeaders
  tableR.value = tableRows
})
</script>
<style lang="scss" scoped></style>