import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    county: {
      county: 'Cameron County',
      population: 423725
    },
    sources: [],
    obesityGender: [],
    obesityYear: []
  },
  getters: {
    selectedCountyDetails: state => {
      return state.county
    }
  },
  mutations: {
    setSources (state, sources) {
      state.sources = sources
    },
    setGenderObesityMetrics (state, data) {
      state.obesityGender = data
    },
    setGenderYearObesityMetrics (state, data) {
      state.obesityYear = data
    }
  },
  actions: {
    fetchSources: ({ commit }) => {
      return fetch(`${process.env.VUE_APP_BULK_FHIR_SERVER_HOST}/api/sources`)
        .then((response) => response.json())
        .then((json) => {
          commit('setSources', json.data)
        })
    },
    fetchGenderObesityStatistics: ({ commit }) => {
      return fetch(`${process.env.VUE_APP_BULK_FHIR_SERVER_HOST}/api/metrics/obesity/gender`)
        .then((response) => response.json())
        .then((json) => {
          commit('setGenderObesityMetrics', json.data)
        })
    },
    fetchYearObesityStatistics: ({ commit }) => {
      return fetch(`${process.env.VUE_APP_BULK_FHIR_SERVER_HOST}/api/metrics/obesity/year`)
        .then((response) => response.json())
        .then((json) => {
          commit('setGenderYearObesityMetrics', json.data)
        })
    }
  }
})
