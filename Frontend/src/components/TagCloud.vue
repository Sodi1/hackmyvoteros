<template>
  <WidgetComponent :title="mode == 'Все' ? 'Облако слов' : 'Кластер'">
    <div style="position: relative;">
      <div class="left-slide" @click="dec()">&lt;</div>

      <vue-word-cloud style="
    height: 480px;
    width: 100%;
  " :words="clustersFormatted" :color="([, weight]) => it[Math.round((weight / 2)) % 10]" font-family="Roboto" />
      <div class="mode-slide" @click="mode == 'Все' ? mode = 'Индекс' : mode = 'Все'">
        {{ mode }} <span v-show="mode !== 'Все'"> &nbsp;{{ index }} </span>
      </div>
      <div class="right-slide" @click="inc()">
        > </div>
    </div>

  </WidgetComponent>
</template>
<script setup>
import { EmotionController } from "@/api/controllers";
import { AnswerController } from "@/api/controllers/AnswerController";
import WidgetComponent from "@/components/WidgetComponent.vue"
import VueWordCloud from 'vuewordcloud';
import { onMounted, ref, computed } from "vue";
import { useRoute } from "vue-router";
import { watch } from "vue";
import { router } from "@/app/providers";

const it = [
  "#FE0048",
  "#BC13FE",
  "#0165FC",
  "#FF7124",
  "#009000",
  "#ba55d3",
  "#808000",
  "#b03060",
  "#159c7e",
  "#94d16c",
]
const clusters = ref([])
const clustersAll = ref([])
const mode = ref("Все")
const index = ref(0)
const isLoading = ref(false)

const route = useRoute()
function inc() {
  if (index.value + 1 < Object.keys(clusters.value).length - 1) {
    index.value += 1
  } else {
    index.value = 0
  }
}
function dec() {
  if (index.value > 0) {
    index.value -= 1
  } else {
    index.value = Object.keys(clusters.value).length - 1
  }
}

const clustersFormatted = computed(() => {
  if (mode.value === "Все") {
    return clustersAll.value
  }

  return clusters.value[index.value].map((v) => [v, index.value])
})

onMounted(async () => {
  const resp = await AnswerController.getAnswers(route.query.fileId, route.query.questionId);
  const stat = []
  resp.forEach((v) => {
    for (let i = v.count; i > 0; i--) {
      stat.push(v.answer)
    }
  })
  const set = new Set()

  let i = 0
  while (set.size < 170) {
    if (!stat[i]) {
      break
    }
    set.add(stat[i])

    i++
  }
  clusters.value = (await EmotionController.getClusters([...set])).clusters
  clustersAll.value = Object.entries(clusters.value).map((v) => {
    return [v[1][0], v[1].length]
  })
})
</script>
<style lang="scss" scoped>
.left-slide,
.right-slide {
  position: absolute;
  height: calc(100% + 40px);
  width: 20%;
  z-index: 1;
  cursor: pointer;
  display: flex;
  color: transparent;
  user-select: none;
  transition: all 0.14s ease;
  display: flex;
  align-items: center;
  padding: 20px;
  font-size: 204px;

  &:hover {
    background-color: rgba(0, 0, 0, 0.055);
    color: rgba(0, 0, 0, 0.555);
  }
}

.mode-slide {
  position: absolute;
  width: 120px;
  height: 40px;
  bottom: -12px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px 10px 0 0;
  left: calc(50% - 72px);
  color: $text-light;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;

  &:hover {
    background-color: rgba(0, 0, 0, 0.085);
  }
}

.left-slide {
  left: -12px;
  top: -28px;
  border-radius: 0 0 0 10px;
}

.right-slide {
  top: -28px;
  right: -12px;
  justify-content: end;
  border-radius: 0 0 10px 0;
}
</style>