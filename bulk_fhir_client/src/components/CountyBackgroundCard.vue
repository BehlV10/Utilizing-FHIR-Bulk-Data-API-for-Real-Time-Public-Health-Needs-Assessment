<template>
  <card-wrapper>
    <template slot="title">
      <div>
        <h3 class="headline mb-0">Sample Metrics</h3>
      </div>
    </template>
    <template slot="content">
      <v-data-table :headers="headers" :items="metrics" hide-actions class="metrics">
        <template class="metric-container" slot="items" slot-scope="props">
          <td class="metric text-xs-center">{{ props.item.name }}</td>
          <td class="metric-value text-xs-center">{{ props.item.value }}</td>
        </template>
      </v-data-table>
    </template>
  </card-wrapper>
</template>

<script>
import { mapState } from 'vuex'
import CardWrapper from '@/components/CardWrapper.vue'

export default {
  data () {
    return {
      headers: [
        {
          text: 'Metric',
          align: 'center',
          sortable: false,
          value: 'metric'
        },
        {
          text: 'Value',
          align: 'center',
          sortable: false,
          value: 'value'
        }
      ],
      totals: {
        count: 0
      }
    }
  },
  computed: {
    ...mapState(['sources']),
    currentCounty () {
      return this.$store.getters.selectedCountyDetails
    },
    metrics () {
      return [
        {
          name: `${this.currentCounty.county} Population`,
          value: this.currentCounty.population
        },
        {
          name: `${this.currentCounty.county} Patient Sample`,
          value: this.totals.count
        },
        {
          name: 'Population Coverage of Sample (%)',
          value: (
            (this.totals.count / this.currentCounty.population) *
            100
          ).toFixed(2)
        },
        {
          name: 'Medical Facilty Coverage',
          value: 0
        }
      ]
    }
  },
  watch: {
    sources (sources) {
      sources.forEach(source => {
        this.totals.count += source.count
      })
    }
  },
  components: {
    'card-wrapper': CardWrapper
  }
}
</script>

<style lang="scss" scoped>
table.metrics {
  width: 80%;
  margin: auto;
  font-size: 20px;

  .metric {
    float: left;
  }
}
</style>
