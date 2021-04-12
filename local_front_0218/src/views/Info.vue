<template>
  <div id="Info">
    <Navbar2 />
    <div id="Infobox">
      <div id="infoContent">
        <h2 id="updateTxt">회원 정보 수정</h2>
        <div class="infoContent">
          <b-form-group label="아이디" label-for="userid">
            <b-form-input
              id="userid"
              ref="userid"
              readonly
              v-model="user.userid"
              placeholder="아이디를 입력해주세요"
            ></b-form-input>
          </b-form-group>

          <b-form-group label="비밀번호" label-for="userpwd">
            <b-form-input
              id="userpwd"
              ref="userpwd"
              v-model="user.userpwd"
              placeholder="사용하실 비밀번호를 입력해주세요"
              type="password"
            ></b-form-input>
          </b-form-group>

          <b-form-group label="비밀번호 확인" label-for="userpwdconfirm">
            <!-- @keypress.enter="signup" -->
            <b-form-input
              id="userpwdconfirm"
              ref="userpwdconfirm"
              v-model="user.userpwdconfirm"
              placeholder="비밀번호를 다시 입력해주세요"
              type="password"
            ></b-form-input>
          </b-form-group>

          <b-form-group label="이름" label-for="username">
            <b-form-input
              id="username"
              ref="username"
              v-model="user.username"
              placeholder="회원님의 이름을 입력해주세요"
            ></b-form-input>
          </b-form-group>
          <b-form-group label="성별" label-for="gender">
            <b-form-input
              id="gender"
              ref="gender"
              v-model="user.gender"
              placeholder="회원님의 성별이 표시됩니다"
              readonly
            ></b-form-input>
          </b-form-group>
          <b-form-group label="나이" label-for="ages">
            <b-form-input
              id="ages"
              ref="ages"
              v-model="user.ages"
              placeholder="회원님의 나이가 표시됩니다"
              readonly
            ></b-form-input>
          </b-form-group>
          <b-form-group label="이메일" label-for="email">
            <b-form-input
              id="email"
              ref="email"
              readonly
              v-model="user.email"
              placeholder="사용하시는 이메일이 표시됩니다"
            ></b-form-input>
          </b-form-group>

          <div id="introGroup">
            <div id="introduction">
              <b-form-group
                label="간단한 자기소개"
                label-for="self-introduction"
              >
                <b-form-textarea
                  rows="6"
                  id="self-introduction"
                  ref="introduce"
                  v-model="user.introduce"
                  placeholder="간단한 자기소개를 입력해주세요"
                ></b-form-textarea>
              </b-form-group>
            </div>
            <div id="profileLabel">
              <b-form-group
                label="프로필 사진"
                label-for="profile"
                enctype="multipart/form-data"
              >
                <input type="file" id="upload" @change="onFileSelected" />
              </b-form-group>
              <img
                @click="imgClicked"
                class="profileImg"
                v-if="!user.profileImg"
                src="../assets/img/default2.png"
                width="100%"
                height="100%"
              />
              <img
                @click="imgClicked"
                class="profileImg"
                v-if="user.profileImg"
                width="100%"
                height="100%"
                :src="user.profileImg"
              />
            </div>
          </div>
        </div>
        <div>
          <b-button id="updateBtn" variant="danger" @click="updateCheck"
            >수정</b-button
          >
          <p id="deleteBtn" @click="memberDelete">회원탈퇴</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import Navbar2 from "@/components/Navbar2.vue";
export default {
  components: {
    Navbar2,
  },
  data() {
    return {
      user: {},
      checkimg: "",
    };
  },
  created() {
    axios
      .post(`https://localhost:5000/user/info`)
      .then((response) => {
        this.user = response.data.user;
        this.checkimg = response.data.user.profileImg;
        if (this.user.profileImg === "") {
          this.showImg();
        }
      })
      .catch(() => {
        this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
      });
  },
  methods: {
    showImg() {
      this.user.profileImg =
        "https://localhost:5000/imgs/getByteImage/" + this.user.userid;
    },
    onFileSelected(event) {
      const file = event.target.files[0];
      this.user.profileImg = URL.createObjectURL(file);
    },
    imgClicked() {
      const uploadBtn = document.querySelector("#upload");
      uploadBtn.click();
    },
    memberDelete() {
      this.$store
        .dispatch("MEMBERDELETE", this.user)
        .catch(({ message }) => (this.msg = message));
    },
    memberUpdate() {
      this.$store
        .dispatch("MEMBERUPDATE", this.user)
        .catch(({ message }) => (this.msg = message));
    },
    updateCheck: function () {
      let err = true;
      let msg = "";
      !this.user.userpwdconfirm &&
        ((msg = "비밀번호를 확인해 주세요"),
        (err = false),
        this.$refs.userpwdconfirm.focus());
      err &&
        !this.user.username &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.user.introduce &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.introduce.focus());
      if (!err) alert(msg);
      else this.pwdConfirmer();
    },
    pwdConfirmer: function () {
      if (this.user.userpwd == this.user.userpwdconfirm) {
        this.memberUpdate();
        if (document.getElementById("upload").files[0]) {
          if (this.checkimg.includes("kakao")) {
            const fd = new FormData();
            fd.append("userid", this.user.userid);
            fd.append("upload", document.getElementById("upload").files[0]);
            axios
              .post(`https://localhost:5000/imgs/saveImage`, fd)
              .then((response) => {
                console.log(response.data);
              });
          } else {
            const fd = new FormData();
            fd.append("upload", document.getElementById("upload").files[0]);
            axios
              .put(
                `https://localhost:5000/imgs/updateImage/${this.user.userid}`,
                fd,
              )
              .then((response) => {
                console.log(response.data);
              });
          }
        }
        this.$router.push({ path: "/main" });
      } else alert("비밀번호와 비밀번호 확인란이 다릅니다.");
    },
  },
};
</script>

<style scoped>
h1 {
  font-family: yanoljaBold;
}

#Info {
  display: relative;
}
#Infobox {
  display: flex;
  justify-content: center;
  align-items: center;
}
#infoContent {
  margin-top: 3%;
  height: 70%;
  width: 30%;
}
#profileLabel {
  width: 30%;
  height: 100%;
}
#upload {
  display: none;
}
#introGroup {
  display: flex;
  justify-content: center;
  margin-bottom: 16px;
}
#introduction {
  width: 70%;
}
#updateTxt {
  text-align: center;
  margin-top: 60px;
}
#updateBtn {
  width: 100%;
}
#userid {
  width: 100%;
}

.form-control {
  display: inline;
}
#profileLabel img {
  border-radius: 0.25rem;
  border: 1px solid #ced4da;
  padding: 0;
  height: 174px;
}
#deleteBtn {
  color: grey;
}
</style>
