<template>
  <div ref="chartEntry" style="width: 100%; height: 100%; background-color: white">
  </div>
</template>
<script setup>
import * as echarts from "echarts";
import { onMounted, ref, watch } from "vue";

const chartEntry = ref(null);
let chart;

const props = defineProps({
  chartData: Object
});

const chartSerieDefault = {
  type: 'bar',
  showBackground: true,
  label: {
    show: true,
    formatter: "{c}",
    align: "center",
    borderColor: "transparent",
    color: "black",
    position: [280, 20]
  },
  backgroundStyle: {
    color: 'rgba(180, 180, 180, 0.2)'
  }
}

const chartOptions = {
  xAxis: {
    type: 'value',
    position: 'top',
    splitLine: {
      lineStyle: {
        type: 'dashed'
      }
    }
  },
  color: ["#523ae5"],
  tooltip: {
    show: true,
  },
  grid: {
    left: 30,
    top: 50,
    bottom: 20,
    right: 30
  },
  yAxis: {
    type: 'category',
    axisLine: { show: false },
    axisLabel: { show: false },
    axisTick: { show: false },
    splitLine: { show: false },
  },
  series: [

  ]
};

function setChartSeries(chartData) {
  chartOptions.series = [{ ...chartSerieDefault, data: chartData.map((v) => [v.count, v.name]) }];
}

function buildChart(chartSerie) {
  setChartSeries(chartSerie);
  chart.setOption(chartOptions);
}

onMounted(() => {
  if (chartEntry.value) {
    chart = echarts.init(chartEntry.value);
    buildChart(props.chartData);
  }
});

watch(
  () => props.chartData,
  (chartData) => {
    buildChart(chartData);
  }
);
</script>
<style lang="scss">
.stacked-bar-tooltip {
  .stacked-bar-tooltip-title {
    font-size: 14px;
    font-weight: 600;
  }

  .stacked-bar-tooltip-subtitle {
    font-size: 12px;
    font-weight: 600;
    padding-left: 8px;
  }

  .stacked-bar-tooltip-row {
    font-size: 12px;
    font-weight: 600;
    padding-left: 16px;
  }
}
</style>
