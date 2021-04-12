<template>
  <ul id="List">
    <li class="roomLi" v-for="(room, idx) in roomList" :key="idx">
      <Room :room="room" />
    </li>
  </ul>
</template>

<script>
import Room from "../components/Room.vue";

export default {
  name: "List",
  components: {
    Room,
  },
  props: {
    roomList: {
      type: Array,
    },
  },
};
</script>

<style scoped>
article ul {
  list-style: none;
  display: flex;
  flex-wrap: wrap;
  margin-right: -20px;
}

article ul li {
  min-width: 200px;
  max-width: 300px;
  width: calc(24% - ((20px * 10) / 10));
  flex: 0 1 auto;
  margin: 0 20px 0 0;
  box-sizing: content-box;
}
.roomLi {
  --bg-color: #ffffff;
  --bg-color-light: #f1f7ff;
  transition: all 0.3s ease-out;
  border-radius: 10px;
}
.roomLi:hover {
  transform: translateY(-5px) scale(1.005) translateZ(0);
  /* background: rgba(255, 0, 0, 0.3); */
}
</style>