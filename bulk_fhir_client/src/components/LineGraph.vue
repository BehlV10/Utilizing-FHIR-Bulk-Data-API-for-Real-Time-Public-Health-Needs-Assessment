<script>
import { Line } from 'vue-chartjs'

export default {
  extends: Line,
  mounted () {
    // Overwriting base render method with actual data.
    this.renderLineChart()
  },
  methods: {
    renderLineChart () {
      this.renderChart(
        {
          labels: this.labels,
          datasets: [
            {
              label: 'Males',
              backgroundColor: '#B5838D',
              data: this.males
            },
            {
              label: 'Females',
              backgroundColor: '#FFB4A2',
              data: this.females
            }
          ]
        },
        {
          responsive: false,
          maintainAspectRatio: true
        }
      )
    }
  },
  watch: {
    males () {
      this.$data._chart.destroy()
      this.renderLineChart()
    }
  },
  props: {
    labels: Array,
    males: Array,
    females: Array
  }
}
</script>
