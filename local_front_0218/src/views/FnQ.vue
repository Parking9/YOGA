<template>
  <div id="fnq" class="overflow-auto">
    <Navbar2 />
    <div id="fnqContent">
      <h2 id="noticeTxt">FnQ</h2>
      <Table
        :items="items"
        :perPage="perPage"
        :currentPage="currentPage"
        small
      />
      <div class="mt-3">
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          aria-controls="my-table"
        >
        </b-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar2 from "../components/Navbar2.vue";
import Table from "../components/Table.vue";
import axios from "axios";
export default {
  name: "fnq",
  components: {
    Navbar2,
    Table,
  },
  data: function () {
    return {
      items: [],
      currentPage: 1,
      totalRow: 100,
      perPage: 10,
    };
  },
  created() {
    axios.get(`https://localhost:5000/board/FnQ`).then(({ data }) => {
      this.items = data;
    });
  },
  computed: {
    rows: function () {
      return this.items.length;
    },
  },
};
</script>
<style >
#fnqContent {
  padding: 100px 0 0 0;
  margin: 0 auto;
  width: 60%;
}
.pagination {
  justify-content: center;
}
#noticeTxt {
  text-align: center;
  margin-bottom: 30px;
}
#table {
  border-left: 1px solid #ced4da;
  border-right: 1px solid #ced4da;
  word-wrap: break-word;
}
</style>
