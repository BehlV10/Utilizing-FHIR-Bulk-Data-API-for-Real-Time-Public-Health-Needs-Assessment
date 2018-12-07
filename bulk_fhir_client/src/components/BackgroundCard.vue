<template>
    <card-wrapper>
        <template slot="title">
          <div>
            <h3 class="headline mb-0">Data Background</h3>
          </div>
        </template>
        <template slot="content">
          <v-data-table
            :headers="headers"
            :items="presented"
            :loading="loading"
            hide-actions
            class="sources">
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template class="source" slot="items" slot-scope="props">
            <td class="text-xs-center">{{ props.item.source }}</td>
            <td class="text-xs-center">{{ props.item.meta.patients_count }}</td>
          </template>
          </v-data-table>
        </template>
      </card-wrapper>
</template>

<script>
import { mapActions, mapState } from "vuex";
import CardWrapper from "@/components/CardWrapper.vue";

export default {
  data() {
    return {
      loading: true,
      headers: [
        {
          text: "Sources",
          align: "center",
          sortable: false,
          value: "source"
        },
        {
          text: "Patients",
          value: "patient",
          align: "center",
          sortable: false
        }
      ],
      totals: {
        source: "Total",
        meta: {
          patients_count: 0
        }
      }
    };
  },
  mounted() {
    this.fetchSources().finally(() => {
      this.loading = false;
    });
  },
  methods: {
    ...mapActions(["fetchSources"])
  },
  computed: {
    ...mapState(["sources"]),
    presented() {
      return [...this.sources, this.totals]
    }
  },
  watch: {
    sources(sources) {
      sources.forEach(source => {
        this.totals.meta.patients_count += source.meta.patients_count;
      });
    }
  },
  components: {
    "card-wrapper": CardWrapper
  }
};
</script>

<style lang="scss" scoped>
table.sources,
table.totals {
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
}
</style>
