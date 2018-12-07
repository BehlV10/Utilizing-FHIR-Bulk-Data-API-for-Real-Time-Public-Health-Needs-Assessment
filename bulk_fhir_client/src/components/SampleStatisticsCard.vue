<template>
  <card-wrapper>
    <template slot="title">
      <div>
        <h3 class="headline mb-0">Sample Statistics</h3>
      </div>
    </template>
    <template slot="content">
      <v-data-table
        :headers="headers"
        :items="stats"
        :loading="loading"
        hide-actions
        class="statistics"
      >
        <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
        <template class="source" slot="items" slot-scope="props">
          <td
            class="text-xs-center"
            @click="showGraphCard(props.item.identifier)"
            @mouseover="props.item.showTotals = true"
            @mouseleave="props.item.showTotals = false"
          >
            {{ props.item.metricName }}
            <div
              v-if="props.item.showTotals"
              class="sample-size"
            >{{ props.item.totals }} / {{ sampleSize }}</div>
          </td>
          <td class="text-xs-center">{{ props.item.populationPrevalence }} %</td>
          <td class="text-xs-center">{{ props.item.stratification.males }} %</td>
          <td class="text-xs-center">{{ props.item.stratification.females }} %</td>
        </template>
      </v-data-table>
    </template>
  </card-wrapper>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import CardWrapper from '@/components/CardWrapper.vue'

export default {
  data () {
    return {
      loading: true,
      showGraph: false,
      headers: [
        {
          text: 'Criteria',
          value: 'criteria',
          align: 'center',
          sortable: false
        },
        {
          text: 'Population Prevalence',
          value: 'population_prevalence',
          align: 'center',
          sortable: false
        },
        {
          text: 'Stratified by Male',
          value: 'stratified_by_male',
          align: 'center',
          sortable: false
        },
        {
          text: 'Stratified by Female',
          value: 'stratified_by_female',
          align: 'center',
          sortable: false
        }
      ],
      stats: [],
      sampleSize: 0
    }
  },
  mounted () {
    this.fetchGenderObesityStatistics().finally(() => {
      this.loading = false
    })
  },
  methods: {
    ...mapActions(['fetchGenderObesityStatistics']),
    showGraphCard (filter) {
      this.$emit('show-graph-card', filter)
    },
    calculatePopulationStatistics () {
      if (this.obesityGender.length) {
        // No longer necessary but ran out of time
        let adultMales = this.obesityGender
          .filter(m => (m.gender === 'Male') & (m.age !== '14 or younger'))
          .reduce(this.weightReducer)
        let adultFemales = this.obesityGender
          .filter(m => (m.gender === 'Female') & (m.age !== '14 or younger'))
          .reduce(this.weightReducer)
        console.log(adultMales)
        console.log(adultFemales)
        this.stats = [
          this.calculateObesity(adultMales, adultFemales),
          this.calculateNormalWeight(adultMales, adultFemales),
          this.calculateOverWeight(adultMales, adultFemales)
        ]
      }
    },
    weightReducer (acc, current) {
      return {
        obese: acc.obese + current.obese,
        healthy: acc.healthy + current.healthy,
        overweight: acc.overweight + current.overweight,
        underweight: acc.underweight + current.underweight
      }
    },
    calculateObesity (males, females) {
      console.log(males.healthy + females.healthy, this.sampleSize)
      return {
        identifier: 'obese',
        showTotals: false,
        metricName: 'Adult Obesity',
        populationPrevalence: (
          ((males.obese + females.obese) / this.sampleSize) *
          100
        ).toFixed(2),
        totals: males.obese + females.obese,
        stratification: {
          males: ((males.obese / this.sampleSize) * 100).toFixed(2),
          females: ((females.obese / this.sampleSize) * 100).toFixed(2)
        }
      }
    },
    calculateNormalWeight (males, females) {
      console.log(males.healthy + females.healthy, this.sampleSize)
      return {
        identifier: 'healthy',
        showTotals: false,
        metricName: 'Adult Normal Weight',
        populationPrevalence: (
          ((males.healthy + females.healthy) / this.sampleSize) *
          100
        ).toFixed(2),
        totals: males.healthy + females.healthy,
        stratification: {
          males: ((males.healthy / this.sampleSize) * 100).toFixed(2),
          females: ((females.healthy / this.sampleSize) * 100).toFixed(2)
        }
      }
    },
    calculateOverWeight (males, females) {
      console.log(males.overweight + females.overweight)
      return {
        identifier: 'overweight',
        showTotals: false,
        metricName: 'Adult Overweight',
        populationPrevalence: (
          ((males.overweight + females.overweight) / this.sampleSize) *
          100
        ).toFixed(2),
        totals: males.overweight + females.overweight,
        stratification: {
          males: ((males.overweight / this.sampleSize) * 100).toFixed(2),
          females: ((females.overweight / this.sampleSize) * 100).toFixed(2)
        }
      }
    }
  },
  computed: {
    ...mapState(['obesityGender', 'sources'])
  },
  watch: {
    sources (sources) {
      sources.forEach(source => {
        this.sampleSize += source.count
      })
      this.calculatePopulationStatistics()
    }
  },
  components: {
    'card-wrapper': CardWrapper
  }
}
</script>

<style lang="scss" scoped>
table.statistics {
  width: 100%;
  .headers {
    th {
      &:first-child {
        padding-left: 10px;
        text-align: start;
        width: 39%;
      }
    }
  }
  tr {
    width: 100%;
    td {
      &:first-child {
        padding-left: 10px;
        text-align: start;
        width: 39%;
      }
      width: 22%;
    }
  }

  .sample-size {
    font-size: 12px;
  }
}
</style>
