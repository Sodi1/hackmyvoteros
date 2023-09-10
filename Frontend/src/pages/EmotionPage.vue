<template>
  <div class="emotions-wrapper">
    <div class="emotions-question">
      <v-text-field v-model="answer" @update:model-value="setEmotion" color="primary" variant="solo" placeholder="Ответ">
        <template #prepend-inner>
          <div style="width:140px">Как Ваши дела?</div>
        </template>
        <template #append>
          <v-btn color="primary" @click="check()">Проверить</v-btn>
        </template>
      </v-text-field>
    </div>
  </div>
</template>
<script setup lang="ts">
import { EmotionController } from '@/api/controllers';
import { CensorController } from '@/api/controllers/CensorController';
import { PuntoController } from '@/api/controllers/PuntoSwitcherController';
import { ref } from 'vue';

const answer = ref("")

function gradientFromTo(
  rgbFrom: [number, number, number],
  rgbTo: [number, number, number],
  from: number,
  to: number,
  len: number
) {
  const arr = [] as [number, string][];
  for (let i = 0; i < len; i++) {
    const num = from + (to - from) * (i / len);
    arr.push([
      num,
      `rgb(${rgbFrom[0] + (rgbTo[0] - rgbFrom[0]) * (i / len)},${rgbFrom[1] + (rgbTo[1] - rgbFrom[1]) * (i / len)
      },${rgbFrom[2] + (rgbTo[2] - rgbFrom[2]) * (i / len)})`,
    ]);
  }
  return arr;
}

const gradientRedGreen = (() => {
  const arr = [] as [number, string][];
  arr.push(...gradientFromTo([255, 13, 13], [255, 78, 17], 0, 1 / 5, 43));
  arr.push(...gradientFromTo([255, 78, 17], [255, 142, 21], 1 / 5, 2 / 5, 42));
  arr.push(...gradientFromTo([235, 142, 21], [250, 193, 51], 2 / 5, 3 / 5, 43));
  arr.push(...gradientFromTo([210, 193, 51], [172, 209, 52], 3 / 5, 4 / 5, 42));
  arr.push(...gradientFromTo([162, 219, 52], [105, 229, 76], 4 / 5, 1, 42));
  return arr;
})();

async function setEmotion(str: string) {
  const resp = await EmotionController.getEmotionStats([str])
  const pos = (resp.emotions[0][2].positive - resp.emotions[0][2].negative + 1) / 2;
  const diff = pos - 0.5
  const itg = pos - diff * resp.emotions[0][2].neutral
  let i = 0
  while (true) {
    if (itg < gradientRedGreen[i][0]) {
      backgroundColor.value = gradientRedGreen[i][1]
      return
    }
    i++;
  }
}

async function check() {
  const resp = await PuntoController.getTranslation(answer.value)
  const spellCheck = await EmotionController.getSpellCheck(resp.message)
  await setEmotion(spellCheck.spells[0])
  answer.value = (await CensorController.censorStr(spellCheck.spells[0])).censored
}

const backgroundColor = ref("#e5e5e5")

</script>
<style scoped lang="scss">
.emotions-wrapper {
  height: calc(100dvh - 90px);
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: v-bind(backgroundColor);
  transition: background-color 0.7s ease;

  .emotions-question {
    display: flex;
    width: 600px;
    min-width: 33%;
  }
}
</style>