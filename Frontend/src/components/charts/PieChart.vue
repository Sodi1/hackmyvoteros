<template>
  <div ref="chartEntry" style="width: 100%; height: 100%; background-color: white"></div>
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
  type: "pie",
  radius: ["90%", "50%"],
  left: "24%",
  avoidLabelOverlap: false,
  itemStyle: {
    borderRadius: 10,
    borderColor: "#fff",
    borderWidth: 2,
  },
  label: {
    formatter: () => {
      let data = 0;
      chartOptions.series[0].data.forEach((v) => [
        data += v.value
      ])
      return "всего " + data
    },
    fontSize: 20,
    fontWeight: "bold",
    position: "center",
  },
  color: ["red", "#00AA00", "#F1CC22"],
  emphasis: {
    itemStyle: {
      borderColor: "none",
      shadowColor: "rgba(0, 0, 0, 0.5)",
    },
  },
  labelLine: {
    show: false,
  },
};

const chartOptions = {
  tooltip: {
    trigger: "item",
  },
  legend: {
    left: "left",
    top: "3%",
    orient: "vertical",
  },
  series: [],
};

function setChartSeries(chartSerie) {
  chartOptions.series = [{ ...chartSerieDefault, ...chartSerie }];
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
