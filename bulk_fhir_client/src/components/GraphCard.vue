<template>
  <card-wrapper :csv="false" :pdf="false">
    <template slot="content">
      <line-graph
        v-if="!loading"
        class="line-graph"
        :labels="years"
        :males="males"
        :females="females"
        :height="500"
        :width="800"
      ></line-graph>
    </template>
  </card-wrapper>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import LineGraph from '@/components/LineGraph'
import CardWrapper from '@/components/CardWrapper.vue'

export default {
  data () {
    return {
      loading: true,
      males: [],
      females: []
    }
  },
  mounted () {
    this.fetchYearObesityStatistics.finally(() => {
      this.loading = false
      this.filterResults()
    })
  },
  methods: {
    filterResults () {
      this.males = this.obesityYear
        .filter(m => m.gender === 'Male')
        .map(m => m[this.filter])
      this.females = this.obesityYear
        .filter(m => m.gender === 'Female')
        .map(m => m[this.filter])
    }
  },
  computed: {
    ...mapActions(['fetchYearObesityStatistics']),
    ...mapState(['obesityYear']),
    years () {
      let years = new Set()
      this.obesityYear.forEach(m => {
        if (!years.has(m.year)) {
          years.add(m.year)
        }
      })

      return Array.from(years)
    }
  },
  watch: {
    filter () {
      this.filterResults()
    }
  },
  components: {
    'card-wrapper': CardWrapper,
    'line-graph': LineGraph
  },
  props: {
    filter: String
  }
}
</script>
<style lang="scss">
.line-graph {
  canvas {
    margin: auto;
  }
}
</style>
