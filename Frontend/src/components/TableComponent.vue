<template>
  <div class="common-table-wrapper">
    <div v-if="tableData.tableRows.length" class="common-table-body">
      <v-data-table v-model:items-per-page="countRows" :headers="tableData.tableHeaders" :items="tableData.tableRows"
        item-value="name"></v-data-table>
    </div>
  </div>
</template>
<script setup>
import { VDataTable } from "vuetify/lib/labs/components.mjs";
import { ref, onMounted, watch, computed } from "vue";

const countRows = ref(6)
const tableData = ref({ tableHeaders: [], tableRows: [] });

const props = defineProps({
  tableRows: Object,
  tableHeaders: Object
})
watch([() => props.tableRows, () => props.tableHeaders], ([rows, headers]) => {

  tableData.value = { tableHeaders: [headers[1], headers[2], headers[0], headers[3], headers[4]], tableRows: rows }
})
</script>
<style scoped lang="scss">
.common-table-wrapper {
  @apply shadow-sm;
  border-radius: 20px;
  background-color: white;

  .common-table-body {
    .common-table-selectors {
      display: flex;
      height: 84px;
      margin-top: 24px;

      &> :nth-child(1) {
        flex-grow: 1;
        margin-right: 12px;
      }

      &> :nth-child(2) {
        max-width: 500px;
        margin-left: 12px;
      }
    }
  }
}
</style>
