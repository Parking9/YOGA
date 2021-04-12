<template>
  <div id="Main">
    <Navbar :roomList2="roomList2" @search="searched" />
    <aside>
      <ul>
        <li class="asideTheme">
          <h3>
            <a
              ><b-icon id="themeIcon" class="asideIcon" icon="play-btn"></b-icon
              ><span class="asideItem">테마</span></a
            >
            <button type="button">
              <b-icon
                :class="[{ active2: isActive }, { active3: !isActive }]"
                @click="btnClicked"
                icon="chevron-up"
              ></b-icon>
            </button>
          </h3>
          <ul id="themeUl" :class="{ active: isActive }">
            <li @click="selectTheme(0)" :class="{ selected: selectedTheme[0] }">
              전체
            </li>
            <li @click="selectTheme(1)" :class="{ selected: selectedTheme[1] }">
              남여
            </li>
            <li @click="selectTheme(2)" :class="{ selected: selectedTheme[2] }">
              응답하라
            </li>
            <li @click="selectTheme(3)" :class="{ selected: selectedTheme[3] }">
              EDM
            </li>
            <li @click="selectTheme(4)" :class="{ selected: selectedTheme[4] }">
              힙합
            </li>
            <li @click="selectTheme(5)" :class="{ selected: selectedTheme[5] }">
              회식
            </li>
            <li @click="selectTheme(6)" :class="{ selected: selectedTheme[6] }">
              술방
            </li>
            <li @click="selectTheme(7)" :class="{ selected: selectedTheme[7] }">
              밥먹자
            </li>
            <li @click="selectTheme(8)" :class="{ selected: selectedTheme[8] }">
              기타
            </li>
          </ul>
        </li>
        <li style="margin-top: 9px" class="asideTheme">
          <h3>
            <a
              ><b-icon id="myIcon" class="asideIcon" icon="sliders"></b-icon
              ><span class="asideItem">필터</span></a
            >
            <button type="button">
              <b-icon
                id="chevupIcon"
                :class="[{ active5: isActive2 }, { active6: !isActive2 }]"
                @click="btnClicked2"
                icon="chevron-up"
              ></b-icon>
            </button>
          </h3>
          <div id="filterAside" :class="{ active4: isActive2 }">
            <ul>
              <p class="filterIndex">테마</p>
              <li>
                <input v-model="filter1[0]" type="checkbox" id="theme1" />
                <label for="theme1">전체</label>
              </li>
              <li>
                <input v-model="filter1[1]" type="checkbox" id="theme2" />
                <label for="theme2">남여</label>
              </li>
              <li>
                <input v-model="filter1[2]" type="checkbox" id="theme3" />
                <label for="theme3">응답하라</label>
              </li>
              <li>
                <input v-model="filter1[3]" type="checkbox" id="theme4" />
                <label for="theme4">EDM</label>
              </li>
              <li>
                <input v-model="filter1[4]" type="checkbox" id="theme5" />
                <label for="theme5">힙합</label>
              </li>
              <li>
                <input v-model="filter1[5]" type="checkbox" id="theme6" />
                <label for="theme6">회식</label>
              </li>
              <li>
                <input v-model="filter1[6]" type="checkbox" id="theme7" />
                <label for="theme7">술방</label>
              </li>
              <li>
                <input v-model="filter1[7]" type="checkbox" id="theme8" />
                <label for="theme8">밥먹자</label>
              </li>
              <li>
                <input v-model="filter1[8]" type="checkbox" id="theme8" />
                <label for="theme9">기타</label>
              </li>
            </ul>
            <ul>
              <p class="filterIndex">남자</p>
              <li>
                <input v-model="filter2[0]" type="checkbox" id="boyAll" />
                <label for="boyAll">없음</label>
              </li>
              <li>
                <input v-model="filter2[1]" type="checkbox" id="boy1" />
                <label for="boy1">1 명</label>
              </li>
              <li>
                <input v-model="filter2[2]" type="checkbox" id="boy2" />
                <label for="boy2">2 명 </label>
              </li>
              <li>
                <input v-model="filter2[3]" type="checkbox" id="boy3" />
                <label for="boy3">3 명 이상</label>
              </li>
              <li>
                <input v-model="filter2[4]" type="checkbox" id="boy4" />
                <label for="boy4">상관 없음</label>
              </li>
            </ul>
            <ul>
              <p class="filterIndex">여자</p>
              <li>
                <input v-model="filter3[0]" type="checkbox" id="girlAll" />
                <label for="girlAll">없음</label>
              </li>
              <li>
                <input v-model="filter3[1]" type="checkbox" id="girl1" />
                <label for="girl1">1 명</label>
              </li>
              <li>
                <input v-model="filter3[2]" type="checkbox" id="girl2" />
                <label for="girl2">2 명 </label>
              </li>
              <li>
                <input v-model="filter3[3]" type="checkbox" id="girl3" />
                <label for="girl3">3 명 이상</label>
              </li>
              <li>
                <input v-model="filter3[4]" type="checkbox" id="girl4" />
                <label for="girl4">상관 없음</label>
              </li>
            </ul>
            <div id="applyBtn">
              <b-button block variant="danger" @click="filterApply"
                >필터 적용하기</b-button
              >
            </div>
          </div>
        </li>
      </ul>
    </aside>
    <main>
      <!-- <header>
        <img height="128px" width="1614px" src="../assets/img/hip3.png" alt="">
        <h1 id='headertitle'>힙합</h1>
      </header> -->
      <article>
        <h1 id="tag">전체</h1>
        <span id="resetbtn" @click="resetList" v-if="reset">초기화</span>
        <List :roomList="roomList" />
      </article>
    </main>

    <footer>
      <div style="text-align: center; margin: 8px 0 4px 0">
        <img
          style="text-align: center"
          id="logo"
          src="../assets/img/logo3.png"
          width="80px"
        />
      </div>
      <p class="footerContent">
        2021년 최강 륙조_ 이경섭 안준웅 최준호 신재복 박철완
      </p>
      <p class="footerContent">
        Copyright 2021 ~ by SSAFY RUKJO. All Rights Reserved.
      </p>
    </footer>
  </div>
</template>

<script>
import Navbar from "../components/Navbar.vue";
import List from "../components/List.vue";
import axios from "axios";

export default {
  name: "Main",
  components: {
    Navbar,
    List,
  },
  methods: {
    btnClicked: function () {
      this.isActive = !this.isActive;
    },
    btnClicked2: function () {
      this.isActive2 = !this.isActive2;
    },
    selectTheme: function (num) {
      let newA = [false, false, false, false, false, false, false, false];
      newA.splice(num, 0, true);
      this.selectedTheme = newA;
      this.idx = num;
      if (num === 0) {
        this.roomList = this.roomList2;
      } else {
        const filtered = this.roomList2.filter((room) => {
          return room.theme === this.themeList[num];
        });
        this.roomList = filtered;
      }
      // background
      const article = document.querySelector("article");
      if (this.idx === 0) {
        article.setAttribute("style", "background-color : none;");
      } else if (this.idx === 1) {
        article.setAttribute(
          "style",
          "background-color : rgba(255, 0, 0, 0.05);",
        );
      } else if (this.idx === 2) {
        article.setAttribute(
          "style",
          "background-color : rgba(25, 81, 144, 0.08);",
        );
      } else if (this.idx === 3) {
        article.setAttribute(
          "style",
          "background-color : rgba(177, 179, 179, 0.25);",
        );
      } else if (this.idx === 4) {
        article.setAttribute(
          "style",
          "background-color : rgba(120, 0, 189, 0.03);",
        );
      } else if (this.idx === 5) {
        article.setAttribute(
          "style",
          "background-color : rgba(203, 206, 145, 0.127);",
        );
      } else if (this.idx === 6) {
        article.setAttribute(
          "style",
          "background-color : rgba(255, 128, 65, 0.05);",
        );
      } else if (this.idx === 7) {
        article.setAttribute(
          "style",
          "background-color : rgba(16, 24, 32, 0.1);",
        );
      } else if (this.idx === 8) {
        article.setAttribute(
          "style",
          "background-color : rgba(253, 209, 14, 0.05);",
        );
      } else {
        article.setAttribute("style", "background-color : none;");
      }

      this.filter1 = [
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
      ];
      this.filter2 = [false, false, false, false, false];
      this.filter3 = [false, false, false, false, false];
      this.isActive2 = true;
      const tag = document.querySelector("#tag");
      tag.innerText = `${this.iconList[this.idx]}${this.themeList[this.idx]}${
        this.iconList[this.idx]
      }`;
      this.reset = false;
    },
    filterApply: function () {
      if (Array.from(new Set(this.filter1)).indexOf(true) === -1) {
        alert("테마를 선택해주세요");
      } else if (Array.from(new Set(this.filter2)).indexOf(true) === -1) {
        alert("남자 인원을 선택해주세요");
      } else if (Array.from(new Set(this.filter3)).indexOf(true) === -1) {
        alert("여자 인원을 선택해주세요");
      } else {
        this.idx = 9;
        const tList = [];
        let filtered1 = [];
        if (this.filter1[0] === false) {
          for (let i = 1; i < 9; i++) {
            if (this.filter1[i] === true) {
              tList.push(this.themeList[i]);
            }
          }
          filtered1 = this.roomList2.filter((room) => {
            return tList.includes(room.theme);
          });
        } else {
          filtered1 = this.roomList2;
        }
        let filtered2_1 = [];
        let filtered2_2 = [];
        let filtered2_3 = [];
        let filtered2_4 = [];
        let Filtered2 = [];
        if (this.filter2[4] === true) {
          Filtered2 = filtered1;
        } else {
          if (this.filter2[0] === true) {
            filtered2_1 = filtered1.filter((room) => {
              return room.maleNum === 0;
            });
          }
          if (this.filter2[1] === true) {
            filtered2_2 = filtered1.filter((room) => {
              return room.maleNum === 1;
            });
          }
          if (this.filter2[2] === true) {
            filtered2_3 = filtered1.filter((room) => {
              return room.maleNum === 2;
            });
          }
          if (this.filter2[3] === true) {
            filtered2_4 = filtered1.filter((room) => {
              return room.maleNum >= 3;
            });
          }
          let filtered2 = [
            ...filtered2_1,
            ...filtered2_2,
            ...filtered2_3,
            ...filtered2_4,
          ];
          Filtered2 = Array.from(new Set(filtered2));
        }
        let filtered3_1 = [];
        let filtered3_2 = [];
        let filtered3_3 = [];
        let filtered3_4 = [];
        let Filtered3 = [];
        if (this.filter3[4] === true) {
          Filtered3 = Filtered2;
        } else {
          if (this.filter3[0] === true) {
            filtered3_1 = Filtered2.filter((room) => {
              return room.femaleNum === 0;
            });
          }
          if (this.filter3[1] === true) {
            filtered3_2 = Filtered2.filter((room) => {
              return room.femaleNum === 1;
            });
          }
          if (this.filter3[2] === true) {
            filtered3_3 = Filtered2.filter((room) => {
              return room.femaleNum === 2;
            });
          }
          if (this.filter3[3] === true) {
            filtered3_4 = Filtered2.filter((room) => {
              return room.femaleNum >= 3;
            });
          }
          let filtered3 = [
            ...filtered3_1,
            ...filtered3_2,
            ...filtered3_3,
            ...filtered3_4,
          ];
          Filtered3 = Array.from(new Set(filtered3));
        }
        this.roomList = Filtered3;
        const tag = document.querySelector("#tag");
        if (Filtered3.length === 0) {
          tag.innerText = "❌필터링 결과가 존재하지 않습니다❌";
        } else {
          tag.innerText = "필터링 결과";
        }
        this.selectedTheme = [
          true,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
        ];
        this.reset = true;
      }
    },
    searched: function (arr, str) {
      this.roomList = arr;
      const tag = document.querySelector("#tag");
      if (arr.length === 0) {
        tag.innerText = `❌ '${str}'에 대한 검색 결과가 존재하지 않습니다❌`;
      } else {
        tag.innerText = `'${str}'에 대한 검색 결과`;
      }
      this.selectedTheme = [
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
      ];
      this.filter1 = [
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
      ];
      this.filter2 = [false, false, false, false, false];
      this.filter3 = [false, false, false, false, false];
      this.isActive2 = true;
      this.reset = true;
    },
    resetList: function () {
      this.$router.go("/main");
    },
  },
  data: function () {
    return {
      themeList: [
        "전체",
        "남여",
        "응답하라",
        "EDM",
        "힙합",
        "회식",
        "술방",
        "밥먹자",
        "기타",
        "필터링",
      ],
      idx: 0,
      isActive: false,
      isActive2: true,
      selectedTheme: [
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
      ],
      filter1: [false, false, false, false, false, false, false, false, false],
      filter2: [false, false, false, false, false],
      filter3: [false, false, false, false, false],
      roomList: [],
      roomList2: [],
      reset: false,
      iconList: ["", "💕", "💬", "🔥", "🤘🏿", "👔", "🍻", "🍴", "😀", ""],
    };
  },
  created: function () {
    axios
      .post(`https://localhost:5000/room/showroom`)
      .then((res) => {
        this.roomList2 = res.data;
        this.roomList = this.roomList2;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
p {
  margin: 0;
}
#Main {
  display: inline-block;
  position: relative;
  width: 100%;
  height: auto;
  overflow-x: hidden;
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
#Main::-webkit-scrollbar {
  display: none;
}

header {
  padding-top: 16px;
  padding: 0px 46px 0 46px;
}

aside {
  position: fixed;
  width: 197px;
  height: 100%;
  z-index: 3;
  color: #4d4d4d;
  background-color: #f7f7f7;
  overflow-x: hidden;
  padding-top: 70px;
}

main {
  display: block;
  padding-left: 197px;
  padding-top: 66px;
  z-index: 2;
  width: 100%;
  height: 150%;
}

footer {
  width: 100%;
  height: 10%;
  display: block;
  position: relative;
  padding-left: 197px;
  margin: 0;
  z-index: 2;
  border-top: 1px solid #e6e6e6;
}
header {
  max-height: 10%;
  border: none;
}
article {
  padding: 32px 46px;
}
article ul {
  min-height: 66vh;
}
button {
  border: none;
  background: none;
}
.active {
  display: none;
}
.active2 {
  transform: rotate(-180deg);
  transition: 0.3s ease;
  color: black;
}
.active3 {
  transition: 0.3s ease;
  color: #acacac;
}
.active4 {
  display: none;
}
.active5 {
  transform: rotate(-180deg);
  transition: 0.3s ease;
  color: black;
}
.active6 {
  transition: 0.3s ease;
  color: #acacac;
}
.asideItem {
  font-size: 1.6rem;
  color: #4d4d4d;
}
h3 {
  padding-left: 1px;
}
h3 a {
  padding-left: 25px;
  font-size: 15px;
  cursor: inherit;
}
h3 button {
  position: absolute;
  top: 13px;
  left: 160px;
  font-size: 10px;
}
.asideIcon {
  margin-right: 10px;
  margin-bottom: 1px;
  color: #c82333;
  font-size: 20px;
}
aside ul li ul {
  list-style: none;
  padding: 0 47px 17px 52px;
}
aside ul li ul li:hover {
  cursor: pointer;
  color: #c82333;
}
aside ul > .asideTheme {
  border-bottom: 1px solid #ebe4e3;
}

aside ul li {
  position: relative;
}
.selected {
  color: #c82333;
}
header {
  border-bottom: 1px solid #e6e6e6;
}
/* #themeIcon:hover {
  color: #c82333;
}
#myIcon:hover {
  color: rgb(214, 193, 3);
} */
#filterAside > ul > li > label {
  margin-bottom: 2px;
}
#filterAside > ul > li > input {
  margin-right: 8px;
  padding-top: 4px;
}
.footerContent {
  color: #acacac;
  text-align: center;
}
#footerh4 {
  margin-bottom: 0;
}
#applyBtn {
  text-align: center;
}
input:checked + label {
  color: #c82333;
}
.filterIndex {
  font-size: 21px;
}
#tag {
  display: inline;
}
#resetbtn {
  color: #c82333;
  margin-left: 15px;
  font-size: 1.4rem;
  border: 1px solid #c82333;
  border-radius: 20px;
  padding: 4px 7px;
}
#resetbtn:hover {
  cursor: pointer;
  background-color: #c82333;
  color: white;
}
#themeUl li {
  font-size: 18px;
}
#headertitle {
  position: absolute;
  top: 110px;
  left: 950px;
}
</style>
