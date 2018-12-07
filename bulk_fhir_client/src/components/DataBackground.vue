<template>
<div>
  <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <background-card></background-card>
    </v-flex>
  </v-layout>

  <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <sample-metric-card></sample-metric-card>
    </v-flex>
  </v-layout>
  </div>
</template>
<script>
import BackgroundCard from "@/components/BackgroundCard.vue";
import SampleMetricCard from "@/components/SampleMetricCard.vue";

export default {
  components: {
    "background-card": BackgroundCard,
    "sample-metric-card": SampleMetricCard
  }
};
</script>
