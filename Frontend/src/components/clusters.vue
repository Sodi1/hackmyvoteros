<template>
  <div ref="chartEntry" v-resize="() => {
    chart.resize({
      animation: {
        duration: 100,
        easing: 'cubicInOut',
      },
    });
  }
    " style="width: 100%; height: 100%; background-color: white"></div>
</template>
<script setup>
import * as echarts from "echarts";
import { computed, onMounted, ref, watch } from "vue";

const chartEntry = ref < HTMLElement | null > (null);
let chart

const props = defineProps({

})

const allDataMinMax = computed(() => {
  const flatData = props.chartData
    .map((v) => v.data.map((v2) => v2.value[0] + v2.value[1]))
    .flat(2);
  return [Math.min(...flatData), Math.max(...flatData)];
});

const chartSerieDefault = {
  type: "scatter",
  emphasis: {
    focus: "series",
  },
  symbolSize: function (val) {
    return (
      10 +
      ((val[0] + val[1] - allDataMinMax.value[0]) /
        (allDataMinMax.value[1] - allDataMinMax.value[0])) *
      30
    );
  },
}

const chartOptions = {
  grid: {
    top: "24px",
    left: "24px",
    bottom: "32px",
    right: "160px",
  },
  axisPointer: {
    show: true,
    snap: true,
    lineStyle: {
      color: "#523ae5",
    },
  },
  tooltip: {
    trigger: "item",
    formatter: (params) => {
      if (!(params instanceof Array)) {
        const tooltip = document.createElement("div");
        tooltip.classList.add("clusters-tooltip");

        const str = document.createElement("div");
        str.classList.add("clusters-tooltip-str");
        str.innerHTML = params.name;
        str.style.color = "" + params.color;

        tooltip.appendChild(str);

        return tooltip;
      }
      return "";
    },
  },
  brush: {},
  legend: {
    show: true,
    right: "0",
    top: "middle",
    orient: "vertical",
  },
  toolbox: {
    show: false,
  },
  color: iteriableColors,
  xAxis: [
    {
      type: "value",
      scale: true,
      axisTick: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      axisLabel: {
        color: "#A3A1A1",
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: "#ECEAEA",
        },
      },
    },
  ],
  yAxis: [
    {
      type: "value",
      scale: true,
      axisTick: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      axisLabel: {
        color: "#A3A1A1",
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: "#ECEAEA",
        },
      },
    },
  ],
  series: [],
}

function setChartSeries(chartData) {
  chartData.forEach((d) => {
    d.data.forEach((v) => {
      v.value.push(v.value[0] + v.value[1]);
    });
  });
  const series = [];

  chartData.forEach((s) => {
    series.push({ ...s, ...chartSerieDefault });
  });
  chartOptions.series = series;
}

function buildChart(chartData) {
  setChartSeries(chartData);
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
.clusters-tooltip {
  .clusters-tooltip-str {
    font-size: 14px;
    font-weight: 600;
  }

  .clusters-tooltip-param {
    padding-left: 8px;
    font-size: 14px;
    font-weight: 600;
  }
}
</style>
