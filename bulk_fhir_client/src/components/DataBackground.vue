<template>
<div>
  <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <background-card></background-card>
    </v-flex>
  </v-layout>

  <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <county-background-card></county-background-card>
    </v-flex>
  </v-layout>

  <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <sample-statistics-card @show-graph-card="showGraphCard"></sample-statistics-card>
    </v-flex>
  </v-layout>

   <v-layout align-center justify-center row fill-height wrap>
    <v-flex xs12 xl8>
      <graph-card v-if="graphCardVisible" :filter="filter"></graph-card>
    </v-flex>
  </v-layout>
  </div>
</template>
<script>
import BackgroundCard from '@/components/BackgroundCard.vue'
import CountyBackgroundCard from '@/components/CountyBackgroundCard.vue'
import SampleStatisticsCard from '@/components/SampleStatisticsCard.vue'
import GraphCard from '@/components/GraphCard.vue'

export default {
  data () {
    return {
      graphCardVisible: false,
      filter: ''
    }
  },
  methods: {
    showGraphCard (filter) {
      console.log('filter-changed!!!!', filter)
      this.graphCardVisible = true
      this.filter = filter
    }
  },
  components: {
    'background-card': BackgroundCard,
    'county-background-card': CountyBackgroundCard,
    'sample-statistics-card': SampleStatisticsCard,
    'graph-card': GraphCard
  }
}
</script>
