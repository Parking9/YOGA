<template>
  <div id="table">
    <b-table
      :items="items"
      :current-page="currentPage"
      :per-page="perPage"
      :fields="fields"
      small
      hover
      @row-clicked="(item) => $set(item, '_showDetails', !item._showDetails)"
    >
      <template #row-details="row">
        <b-card>
          <span>작성자 : </span><p>{{ row.item.writer }}</p>
          <p>{{ row.item.content }}</p>
        </b-card>
      </template>
    </b-table>
  </div>
</template>

<script>
export default {
  name: "table",
  props: {
    items: {
      type: Array,
    },
    perPage: {
      type: Number,
    },
    currentPage: {
      type: Number,
    },
  },
  computed: {
    totalRows: function () {
      return this.items.length;
    },
  },
  data: function () {
    return {
      fields: [
        // { key: "no", label: "번호" },
        { key: "title", label: "제목" },
        { key: "regtime", label: "작성시각" },
      ],
    };
  },
  methods: {
    rowClicked: function (row) {
      row.showDetails();
    },
  },
};
</script>

<style>
/* th:nth-child(1){
  width: 5%;
  border-right: 1px solid #ced4da;
  text-align : center;
} */
th:nth-child(1){
  width: 75%;
  border-right: 1px solid #ced4da;
  text-align : center;
}
th:nth-child(2){
  width: 25%;
  text-align : center;
}
</style>
