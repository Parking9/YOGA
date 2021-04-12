<template>
  <div id="MakeRoom">
    <div id="MakeRoombox">
      <div id="makeRoomContent">
        <div class="makeRoomContent">
          <div id="makeRoomTxt">
            <h2>방 만들기 💭</h2>
            <p id="makeRoomTxt2">만드는 방이 아래와 같이 생성됩니다.</p>
          </div>
          <div id="thumbnailLabel">
            <b-form-group label-for="thumbnail" enctype="multipart/form-data">
              <input type="file" id="upload" @change="onFileSelected" />
            </b-form-group>
            <img
              @click="imgClicked"
              class="thumbnail"
              v-if="!room.thumbnail"
              src="../assets/img/default.png"
              width="100%"
              height="100%"
            />
            <img
              @click="imgClicked"
              class="thumbnail"
              v-if="room.thumbnail"
              width="100%"
              height="100%"
              :src="room.thumbnail"
            />
            <div id="ratingBox">
              <b-form-group>
                <b-form-select
                  id="rate"
                  ref="rate"
                  @change="selectedChanged"
                  type="select"
                  v-model="room.rate"
                  :options="options3"
                >
                  <template #first>
                    <b-form-select-option :value="null" disabled
                      >참여 허용 여부를 선택해주세요</b-form-select-option
                    >
                  </template>
                </b-form-select>
              </b-form-group>
            </div>
          </div>
          <div id="titleBox">
            <div id="title">
              <b-form-group>
                <b-form-input
                  ref="title"
                  v-model="room.title"
                  placeholder="방 이름을 설정해 주세요"
                >
                </b-form-input>
              </b-form-group>
            </div>
            <div id="checkBtn">
              <b-button variant="danger" @click="overlapCheck"
                >중복 체크</b-button
              >
            </div>
          </div>
          <div id="themeBox">
            <div id="themeBox2">
              <b-form-group>
                <b-form-select
                  id="theme"
                  ref="theme"
                  v-model="room.theme"
                  :options="options1"
                  @change="selectedChanged2"
                >
                  <template #first>
                    <b-form-select-option :value="null" disabled
                      >테마를 선택해주세요</b-form-select-option
                    >
                  </template>
                </b-form-select>
              </b-form-group>
              <div id="nameArea">
                <span>{{ this.user.username }}님의 방</span>
              </div>
            </div>
            <div style="display: inline">
              <div v-if="this.user.gender === '남자'">
                <span id="people">👧🏻 0명</span>
                <span id="people">👦🏻 1명</span>
              </div>
              <div v-else>
                <span id="people">👧🏻 1명</span>
                <span id="people">👦🏻 0명</span>
              </div>
            </div>
          </div>
          <!-- <div>
            <b-form-group label='최대 인원 설정' label-for="maxNum">
              <b-form-select
                id="maxNum"
                ref="maxNum"
                v-model="room.maxNum"
                :options="options4"
                @change="selectedChanged3"
              >
                  <template #first>
                    <b-form-select-option :value="null" disabled
                      >최대 인원을 선택해주세요</b-form-select-option
                    >
                  </template>
              </b-form-select>
            </b-form-group>
          </div> -->
          <div id="introGroup">
            <div id="introduction">
              <b-form-group label="간단 소개" label-for="room-introduction">
                <b-form-textarea
                  rows="6"
                  id="room-introduction"
                  ref="introduce"
                  placeholder="만드실 방에 대한 소개를 해주세요"
                  v-model="room.introduce"
                ></b-form-textarea>
              </b-form-group>
            </div>
          </div>
        </div>
        <div>
          <b-button variant="danger" id="makeRoomBtn" @click="makeroomCheck"
            >완료</b-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "MakeRoom",
  data: function () {
    return {
      room: {
        owner_id: "",
        title: "",
        theme: null,
        introduce: "",
        thumbnail: "",
        rate: null,
        maxNum: 0,
        token: "",
      },
      user: {},
      session_id: null,
      message: "",
      cnt: -1,
      options1: [
        { value: "남여", text: "💕남여" },
        { value: "응답하라", text: "💬응답하라" },
        { value: "EDM", text: "🔥EDM" },
        { value: "힙합", text: "🤘🏿힙합" },
        { value: "회식", text: "👔회식" },
        { value: "술방", text: "🍻술방" },
        { value: "밥먹자", text: "🍴밥먹자" },
        { value: "기타", text: "😀기타" },
      ],
      options3: [
        { value: 1, text: "입장 허용" },
        { value: 2, text: "입장 불가" },
      ],
      // options4: [
      //   { value: 2, text: "2명" },
      //   { value: 3, text: "3명" },
      //   { value: 4, text: "4명" },
      //   { value: 5, text: "5명" },
      //   { value: 6, text: "6명" },
      //   { value: 7, text: "7명" },
      //   { value: 8, text: "8명" },
      // ],
    };
  },
  created() {
    axios
      .post(`https://localhost:5000/user/info`)
      .then((response) => {
        this.room.owner_id = response.data.user.username;
        this.user = response.data.user;
      })
      .catch(() => {
        this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
      });
  },
  methods: {
    onFileSelected(event) {
      const file = event.target.files[0];
      this.room.thumbnail = URL.createObjectURL(file);
      console.log(file);
    },
    imgClicked() {
      const uploadBtn = document.querySelector("#upload");
      uploadBtn.click();
    },
    overlapCheck: function () {
      axios
        .get(`https://localhost:5000/room/overlap/${this.room.title}`)
        .then((response) => {
          this.cnt = response.data["cnt"];
          if (this.cnt == 0) {
            alert("사용할 수 있는 방 제목입니다.");
          } else {
            alert("중복된 방 제목입니다. 수정해 주세요!!");
          }
        });
    },
    makeroomCheck: function () {
      let err = true;
      let msg = "";
      !this.room.title &&
        ((msg = "방 제목을 입력해주세요"),
        (err = false),
        this.$refs.title.focus());
      err && this.cnt == -1 && ((msg = "중복 체크해주세요!!"), (err = false));
      err &&
        this.cnt > 0 &&
        ((msg = "중복된 방제목입니다. 수정해 주세요!!"), (err = false));
      err &&
        !this.room.theme &&
        ((msg = "테마를 선택해주세요"),
        (err = false),
        this.$refs.theme.focus());
      err &&
        !this.room.rate &&
        ((msg = "참여 허용 여부를 설정해주세요"),
        (err = false),
        this.$refs.rate.focus());
      err &&
        !this.room.introduce &&
        ((msg = "방 소개를 작성해주세요"),
        (err = false),
        this.$refs.introduce.focus());
      if (!err) alert(msg);
      else {
        axios
          .post(`https://localhost:5000/room/makeroom`, this.room)
          .then((response) => {
            alert(response.data["message"]);
            this.session_id = response.data["session_id"];

            if (document.getElementById("upload").files[0]) {
              const fd = new FormData();
              fd.append("session_id", this.session_id);
              fd.append("upload", document.getElementById("upload").files[0]);
              axios
                .post(`https://localhost:5000/imgs/saveThumbnail`, fd)
                .then((response) => {
                  console.log(response.data);
                });
            }
            this.$router.push({
              name: "IntoRoom",
              params: { session_id: this.session_id },
            });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    selectedChanged: function () {
      const a = document.querySelector("#rate");
      a.setAttribute("style", "color : black");
    },
    selectedChanged2: function () {
      const a = document.querySelector("#theme");
      a.setAttribute("style", "color : black");
    },
    // selectedChanged3 : function(){
    //   const a = document.querySelector('#maxNum')
    //   a.setAttribute('style', 'color : black')
    // },
  },
};
</script>

<style scoped>
h1 {
  font-family: yanoljaBold;
}
.thumbnail {
  padding: 8%;
}
#MakeRoom {
  display: relative;
}
#MakeRoombox {
  display: flex;
  justify-content: center;
  align-items: center;
}
#makeRoomContent {
  height: 70%;
  width: 30%;
}
@media (max-width: 1005px) {
  #makeRoomContent {
    height: 100%;
    width: 80%;
  }
  #checkBtn > button {
    font-size: 100%;
  }
}
#makeRoomTxt2 {
  color: #8e9297;
}
#thumbnailLabel {
  width: 100%;
  height: 100%;
  position: relative;
}
#upload {
  display: none;
}
#introGroup {
  display: flex;
  justify-content: center;
  margin-top: 25px;
  margin-bottom: 8px;
}
#introduction {
  width: 100%;
}
#makeRoomTxt {
  text-align: center;
  margin-top: 60px;
  margin-bottom: 30px;
}
#makeRoomBtn {
  width: 100%;
  margin-bottom: 30px;
}

#idSection {
  display: inline;
  max-width: 100%;
}
.form-control {
  display: inline;
}
#thumbnailLabel img {
  border-radius: 0.25rem;
  border: 1px solid #ced4da;
  padding: 0;
  height: 100%;
}
#__BVID__19 {
  margin: 0;
}
#__BVID__21 {
  margin-bottom: 8px;
}
#thumbnailLabel :nth-child(1) {
  margin: 0 0 8px 0;
}
#title {
  width: 70%;
}
#checkBtn {
  width: 20%;
  height: 38px;
}
#checkBtn > button {
  width: 100%;
  height: 100%;
}

@media (max-width: 1290px) {
  #checkBtn > button {
    font-size: 75%;
  }
}

#ratingBox {
  position: absolute;
  top: 0%;
  width: auto;
}
#titleBox {
  margin-top: 2%;
  margin-bottom: 2%;
}

option:first-of-type {
  display: none;
}
input::placeholder {
  color: #dc3545;
}
#rate,
#theme,
#maxNum {
  color: #dc3545;
}
#theme {
  display: inline;
}
fieldset {
  margin: 0;
}
#themeBox {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#themeBox2 {
  display: flex;
  align-items: center;
}
#titleBox {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#nameArea {
  margin-left: 16px;
  font-size: 16px;
}
</style>
