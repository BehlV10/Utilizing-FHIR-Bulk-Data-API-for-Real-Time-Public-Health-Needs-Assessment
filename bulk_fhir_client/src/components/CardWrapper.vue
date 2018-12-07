<template>
    <v-card>
        <v-card-title class="title" primary-title>
            <slot name="title"></slot>
            <v-spacer></v-spacer>
            <v-menu bottom left>
              <v-btn slot="activator" icon>
                <v-icon>more_vert</v-icon>
              </v-btn>
              <v-list>
                <v-list-tile v-for="(item, i) in actions" :key="i" @click="onMenuClick(item.type)">
                  <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                </v-list-tile>
              </v-list>
          </v-menu>
        </v-card-title>
        <v-card-text class="content">
            <slot name="content"></slot>
        </v-card-text>
    </v-card>
</template>

<script>
import DomToImage from "dom-to-image";
import SaveAs from "file-saver";
import JsPDF from "jspdf";
import TableExport from "tableexport";
import { CSV, PNG, PDF } from "@/types/download_types";
import JQuery from 'jquery'

export default {
  data() {
    return {
      actions: [
        { title: "Download as CSV", type: CSV },
        { title: "Download as PDF", type: PDF },
        { title: "Download as PNG", type: PNG }
      ]
    };
  },
  methods: {
    handlePNGType() {
      DomToImage.toPng(this.content)
        .then(blob => {
          SaveAs(blob, `${this.title}.png`);
        })
        .catch(error => {
          console.error("oops, something went wrong!", error);
        });
    },
    handlePDFType() {
      var doc = new JsPDF();
      doc.fromHTML(this.content, 20, 20, { width: 300 });
      doc.save(`${this.title}.pdf`);
    },
    handleCSVType() {
      var te = TableExport(this.content.querySelector('table'), {
        headers: false,
        footers: false,
        formats: ['csv'],
        filename: this.title,
        bootstrap: false,
        exportButtons: false,
        ignoreRows: null,
        ignoreCols: null,
        trimWhitespace: false
      });

      var csv = Object.values(te.getExportData())[0].csv
      te.export2file(csv.data, csv.mimeType, csv.filename, csv.fileExtension, csv.merges)
    },
    onMenuClick(type) {
      switch (type) {
        case CSV:
          this.handleCSVType();
          break;
        case PNG:
          this.handlePNGType();
          break;
        case PDF:
          this.handlePDFType();
          break;
        default:
          break;
      }
    }
  },
  computed: {
    title() {
      let text = this.$el.querySelector(".title > div").innerText;
      if (text) {
        text = text.trim();
      }
      return text;
    },
    content() {
      return this.$el.querySelector(".content");
    }
  }
};
</script>

<style lang="scss" scoped>
.content {
  padding: 0px;  
}
</style>
