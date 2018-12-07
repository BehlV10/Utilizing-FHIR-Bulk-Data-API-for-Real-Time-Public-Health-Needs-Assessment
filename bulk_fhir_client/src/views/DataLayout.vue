<template>
<v-container grid-list-xl text-xs-center>
    <v-layout row wrap>
      <v-flex xs12>
        <data-background></data-background>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import DataBackground from '../components/DataBackground'

export default {
  components: {
    'data-background': DataBackground
  }
}
</script>

<style>

</style>
