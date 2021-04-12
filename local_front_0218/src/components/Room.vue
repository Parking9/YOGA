<template>
  <!-- <b-card
    id='Room'
    title='방입니다'
    img-src="../assets/img/default.png"
  >
    <b-card-text>
      방제목,
      참여자,
      닉네임
    </b-card-text>
  </b-card> -->
  <div id="Room">
    <div id="borderArea" :style="cssProps" @click="gotointoRoom(room)"></div>
    <div>
      <!-- <b-icon class='{rateOne : room.rating}' animation="throb" id='rateIcon' icon='record-fill'></b-icon> -->
      <p v-if="room.rate===1" id="rateIcon">{{ room.rating }}</p>
      <p v-else class='rateOne'>{{ room.rating }}</p>
      <img id="defImg" v-if="room.myThumbnail===false" src="../assets/img/default3.jpg" />
      <img id="defImg" v-else :src="room.myThumbnail" alt="" />
    </div>
    <div id="roomInfo">
      <h2>
        <a class="title" href="">{{ room.title }}</a>
      </h2>
      <div id="details">
        <div style="display: inline">
          <a href=""
            ><span id="ficon">{{ room.ficon }}</span
            ><span id="ftheme" :style="{ color: 'rgb(' + room.fstyle + ')' }">{{
              room.theme
            }}</span></a
          >
          <a href=""
            ><span>{{ room.owner_id }}</span></a
          >
        </div>
        <!-- <span><span id='people'>🙍🏻 ♀️</span>{{room.참여자}}명</span>
        <span><span id='people'>🙍🏻 ♂️</span>{{room.참여자}}명</span> -->
        <div style="display: inline">
          <span><span id="people">👧🏻</span>{{ room.femaleNum }}명</span>
          <span><span id="people">👦🏻</span>{{ room.maleNum }}명</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Room",
  props: {
    room: {
      type: Object,
    },
  },
  data: function () {
    return {
      check: {},
      myThumbnail:'',
    };
  },
  computed: {
    cssProps() {
      return {
        "--hover-border": `2px solid rgba(${this.room.fstyle},0.2)`,
        "--hover-background": `rgba(${this.room.fstyle},0.1)`,
      };
    },
  },
  created: function () {
    this.showThumbnail();
    if (this.room.theme === "남여") {
      this.room.fstyle = "255,0,0";
      this.room.ficon = "💕";
    } else if (this.room.theme == "응답하라") {
      this.room.fstyle = "128,128,128";
      this.room.ficon = "💬";
    } else if (this.room.theme == "EDM") {
      this.room.fstyle = "214, 193, 3";
      this.room.ficon = "🔥";
    } else if (this.room.theme == "힙합") {
      this.room.fstyle = "0,0,0";
      this.room.ficon = "🤘🏿";
    } else if (this.room.theme == "회식") {
      this.room.fstyle = "128,0,128";
      this.room.ficon = "👔";
    } else if (this.room.theme == "술방") {
      this.room.fstyle = "0,255,0";
      this.room.ficon = "🍻";
    } else if (this.room.theme == "밥먹자") {
      this.room.fstyle = "255,165,0";
      this.room.ficon = "🍴";
    } else {
      this.room.fstyle = "255,0,0";
      this.room.ficon = "😀";
    }
    if (this.room.rate === 1) {
      this.room.rating = "입장 환영";
    } else {
      this.room.rating = "입장 사절";
    }
  },
  methods: {
    showThumbnail() {
      if (this.room.thumbnail === "있음") {
        this.myThumbnail =
          "https://localhost:5000/imgs/getByteThumbnail/" +
          this.room.session_id;
      } else {
        this.myThumbnail = false;
      }
      this.room.myThumbnail = this.myThumbnail;
    },
    gotointoRoom: function (room) {
      axios // add
        .get(`https://localhost:5000/room/selectroom/${room.session_id}`)
        .then((res) => {
          this.check = res.data.room;
          console.log(this.check);
          if (this.check == null) {
            alert("존재하지 않는 방입니다.");
            location.reload();
          } else {
            this.$router.push({
              name: "IntoRoom",
              params: { session_id: this.room.session_id },
            });
          }
        });
    },
  },
  // mounted : function(){
  //   this.showThumbnail();
  // }
};
</script>

<style>
#defImg {
  border-radius: 10px;
  width: 100%;
  height: 175px;
  z-index: 2;
}

#Room {
  position: relative;
  margin: 30px 10px;
  background: none;
  z-index: 0;
  transition: transform 0.3s ease-out;
  --box-shadow-color: rgba(16, 65, 150, 0.48);
}
#Room:hover {
  /* box-shadow: 0 24px 36px rgba(255, 0, 0, 0.041), 0 24px 46px var(--box-shadow-color);
  background: rgba(255, 0, 0, 0); */
}

#roomInfo {
  margin-top: 11px;
  padding-left: 5px;
}

#Room div a img {
  border: 1px solid rgb(177, 176, 176);
}
.title {
  font-size: 20px;
  white-space: normal;
  word-break: break-all;
  text-overflow: ellipsis;
  line-height: 22px;
  -webkit-box-orient: vertical;
  font-weight: normal;
  text-decoration: none;
  letter-spacing: -0.75px;
  display: -webkit-box;
  overflow: hidden;
  width: 100%;
  max-height: 44px;
  -webkit-line-clamp: 2;
}
#details {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}
#details div > a > span {
  color: #c82333;
  font-size: 16px;
  line-height: 1.2;
  white-space: nowrap;
}

#details > span {
  margin-left: 5px;
  font-size: 15px;
  line-height: 1.2;
}
#people {
  margin: 0 2px;
}
#ficon {
  margin-right: 1px;
}
#ftheme {
  margin-right: 4px;
}
#borderArea {
  position: absolute;
  top: -5%;
  left: -5%;
  width: 110%;
  height: 110%;
  border-radius: 10px;
  z-index: 0;
}
#borderArea:hover {
  border: var(--hover-border);
  background: var(--hover-background);
  transition: all 0.3s ease-out;
}
#borderArea:hover + div #rateIcon {
  visibility: visible;
}
#rateIcon {
  visibility: hidden;
  position: absolute;
  top: 3%;
  left: 4%;
  color: rgb(43, 255, 0);
  border: 1px solid rgb(43, 255, 0);
  border-radius: 20px;
  padding: 2px 6px;
}
.rateOne {
  visibility: hidden;
  position: absolute;
  top: 3%;
  left: 4%;
  color: red;
  border: 1px solid red;
  border-radius: 20px;
  padding: 2px 6px;
}
#borderArea:hover + div .rateOne {
  visibility: visible;
}

</style>