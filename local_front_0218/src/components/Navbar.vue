<template>
  <div id="navbar">
    <b-navbar toggleable="lg">
      <!-- <b-navbar-brand @click="toMain" href="#"
        ><span id="ant">야할래</span></b-navbar-brand
      > -->
      <img
        id="logo"
        @click="gotoMain"
        src="../assets/img/logo.png"
        width="120px"
      />

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-nav-form id="searchArea" @keypress.enter="search">
        <vue-bootstrap-typeahead
          id="bootTypehead"
          :data="searchNm"
          v-model="searchWord"
          size="sm"
          placeholder="검색어를 입력하세요"
          @keypress.enter="search"
        />
        <div @click="search" id="seachIconArea">
          <b-icon id="searchIcon" aria-hidden="true" icon="search"></b-icon>
        </div>
      </b-nav-form>
      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item id="navIcon">
            <router-link :to="{ name: 'MakeRoom' }">
              <b-icon id="startRoom" icon="camera-video"></b-icon>
              <span id="tooltiptext">방 만들기</span>
            </router-link>
          </b-nav-item>
          <b-nav-item
            ><router-link :to="{ name: 'Notice' }"
              >공지사항</router-link
            ></b-nav-item
          >
          <b-nav-item
            ><router-link :to="{ name: 'FnQ' }">FnQ</router-link></b-nav-item
          >
          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em class="whitefont">마이페이지</em>
            </template>
            <!-- <a href="https://127.0.0.1:8000/admin/" class="ml-4"
              >관리자 페이지</a
            > -->
            <b-dropdown-item @click="gotoInfo" v-if="getAccessToken"
              ><b-icon
                class="colblack"
                icon="person-fill"
                aria-hidden="true"
              ></b-icon
              ><span class="colblack">내 정보</span></b-dropdown-item
            >
            <b-dropdown-item @click="logout" v-if="getAccessToken"
              ><b-icon class="colblack" icon="power" aria-hidden="true"></b-icon
              ><span class="colblack">로그아웃</span></b-dropdown-item
            >
            <b-dropdown-item href="#" v-if="!getAccessToken"
              ><router-link :to="{ name: 'Signup' }"
                ><b-icon class="colblack" icon="person-fill"></b-icon
                ><span class="colblack"> 회원가입</span></router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#" v-if="!getAccessToken"
              ><router-link :to="{ name: 'Login' }"
                ><b-icon
                  class="colblack"
                  icon="power"
                  aria-hidden="true"
                ></b-icon
                ><span class="colblack"> 로그인</span></router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view />
  </div>
</template>

<script>
import VueBootstrapTypeahead from "vue-bootstrap-typeahead";
import { mapGetters } from "vuex";
export default {
  name: "Navbar",
  computed: {
    ...mapGetters(["getAccessToken", "getUserId", "getUserName"]),
    searchNm: function () {
      const usernames = this.roomList2.map((room) => room.owner_id);
      const roomtitle = this.roomList2.map((room) => room.title);
      const sum = usernames.concat(roomtitle);
      return Array.from(new Set(sum));
    },
  },
  methods: {
    logout() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => this.$router.replace("/").catch(() => {}));
    },
    gotoInfo() {
      this.$router.push("/info");
    },
    gotoMakeroom() {
      this.$router.push("/makeroom");
    },
    search() {
      if (this.searchWord.trim()) {
        this.roomSearched = this.roomList2.filter((room) => {
          return (
            room.owner_id.includes(this.searchWord) ||
            room.title.includes(this.searchWord)
          );
        });
        this.$emit("search", this.roomSearched, this.searchWord);
      } else {
        alert("검색어를 입력하세요");
      }
    },
    gotoMain() {
      this.$router.go({ name: "Main" });
    },
  },
  components: {
    VueBootstrapTypeahead,
  },
  data: function () {
    return {
      searchWord: "",
      roomSearched: [],
    };
  },
  props: {
    roomList2: {
      type: Array,
    },
  },
};
</script>

<style scoped>
#navbar {
  width: 100vw;
  position: fixed;
  background: white;
  /* border-bottom: rgb(3, 3, 3) 0.1em solid; */
  z-index: 4;
  display: block;
  box-shadow: 0 0 8px 1px rgba(0, 0, 0, 0.15);
}
#ant {
  font-family: "East Sea Dokdo", cursive;
  color: rgb(0, 0, 0);
  font-size: 2rem;
}
.navbar-light .navbar-nav .nav-link:link,
.navbar-light .navbar-nav .nav-link:visited,
.navbar-light .navbar-nav .nav-link:hover {
  color: rgb(0, 0, 0);
  text-decoration: none;
}
router-link:link,
router-link:visited,
router-link:hover {
  color: rgb(0, 0, 0);
  font-style: none;
}
.whitefont {
  color: rgb(0, 0, 0);
  font-style: none;
}
.whitefont:hover {
  color: rgb(255, 0, 0);
  font-style: none;
}
div.list-group > a:hover {
  background-color: #f5f5f5;
  border: none;
  color: black;
}
.colblack {
  color: black;
}
router-link:hover {
  background-color: #ff0000;
}
#logo {
  margin: auto;
  padding: 0 0 0 32px;
}
#logo:hover {
  cursor: pointer;
}
#navIcon > a {
  padding: 11px 8px 8px 8px;
}
#navIcon {
  position: relative;
}
#startRoom:hover {
  color: #c82333;
}
#searchIcon {
  color: #c82333;
  margin-top: calc((1.5em + 0.5rem + 2px) * 0.2);
  margin-bottom: calc((1.5em + 0.5rem + 2px) * 0.2);
  font-size: calc((1.5em + 0.5rem + 2px) * 0.5);
}
#searchIcon:hover {
  color: #9c1c29;
  cursor: pointer;
}
#searchArea {
  padding-left: 83px;
}
.form-control {
  border-right: none;
}
#bootTypehead {
  width: 300px;
  border: 1px solid rgb(255, 0, 0.5);
  border-radius: 0.2rem;
}
#seachIconArea {
  border: 1px solid rgb(255, 0, 0.5);
  border-left: none;
  border-radius: 0.2rem;
  width: 40px;
  text-align: center;
  height: calc(1.5em + 0.5rem + 2px);
}

#startRoom + #tooltiptext {
  visibility: hidden;
  width: 80px;
  /* background-color: #ff0000;
  color: #f5f5f5; */
  background-color: #fff;
  color: #c82333;
  border: 1px solid #c82333;
  text-align: center;
  border-radius: 6px;
  padding: 5px 10px;
  position: absolute;
  z-index: 1;
  top: 40px;
  left: -20px;
}

#startRoom:hover + #tooltiptext {
  visibility: visible;
}
</style>
