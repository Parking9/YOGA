<template>
  <div id="notice" class="overflow-auto">
    <Navbar2 />
    <div id="noticeContent">
      <h2 id="noticeTxt">공지사항</h2>
      <Table
        :items="items"
        :perPage="perPage"
        :currentPage="currentPage"
        small
      />
      <div id="paginationSection" class="mt-3">
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
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
  name: "notice",
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
    axios.get(`https://localhost:5000/board/notice`).then(({ data }) => {
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

<style scoped>
#noticeTxt {
  text-align: center;
  margin-bottom: 30px;
}
#noticeContent {
  padding: 100px 0 0 0;
  margin: 0 auto;
  width: 60%;
}
#table {
  border-left: 1px solid #ced4da;
  border-right: 1px solid #ced4da;
  word-wrap: break-word;
}
.pagination {
  justify-content: center;
}
</style>
