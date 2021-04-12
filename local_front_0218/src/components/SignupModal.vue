<template>
  <div id="SignupModal2">
    <div id="Signupbox2">
      <div id="signupContent2">
        <div class="signupContent2">
          <h2 id="signupTxt"></h2>
          <div class="signupProcess">
            <h4>1. 카카오 계정 연동</h4>
            <p>
              요가는 카카오 계정을 통해 회원의 정보를 수집합니다. 하단의 '카카오
              로그인' 버튼을 클릭해주세요.
            </p>
          </div>
          <div align="center" class="mt-2">
            <img
              id="kakaoImg"
              src="../assets/img/kakao_login_medium_wide.png"
              width="250px"
              @click="logInKakao"
            />
          </div>
          <div class="signupProcess">
            <h4>2. 추가 개인정보 입력</h4>
            <p>요가에서 사용하고 싶은 회원님의 계정 정보를 입력해주세요.</p>
          </div>
          <b-form-group label="아이디" label-for="userid">
            <b-form-input
              id="userid"
              ref="userid"
              v-model="user.userid"
              placeholder="아이디를 입력해주세요"
            >
            </b-form-input>
            <b-button
              variant="outline-danger"
              id="checkBtn"
              @click="overlapCheck"
              >아이디 중복 체크</b-button
            >
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
              v-model="user.email"
              placeholder="사용하시는 이메일이 표시됩니다"
              readonly
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
              <b-form-group label="프로필 사진" label-for="profile">
                <input
                  type="file"
                  id="upload"
                  accept="image/*"
                  enctype="multipart/form-data"
                  @change="onFileSelected"
                />
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
        <div class="signupProcess">
          <h4>3. 회원가입 완료</h4>
          <p>입력한 개인정보는 마이페이지를 통해 언제든 수정 가능합니다</p>
        </div>
        <div>
          <b-button variant="danger" id="signupBtn" @click="signupCheck"
            >회원가입 완료</b-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "SignupModal",
  data: function () {
    return {
      user: {
        manager: 0,
        userid: "",
        username: "",
        userpwd: "",
        userpwdconfirm: "",
        ages: "",
        gender: "",
        email: "",
        profileImg: "",
        introduce: "",
        session_id: -1,
      },
      message: "",
      cnt: -1,
    };
  },
  created() {
    let code = this.$route.query.code;
    if (code != null || code != undefined) {
      axios
        .post(`https://localhost:5000/kakao/login/request`, code)
        .then((response) => {
          this.user.email = response.data["email"];
          this.user.ages = response.data["ages"];
          this.user.gender = response.data["gender"];
          this.user.username = response.data["nickname"];
          this.user.profileImg = response.data["profileImage"];
        });
    }
  },
  methods: {
    onFileSelected(event) {
      const file = event.target.files[0];
      this.user.profileImg = URL.createObjectURL(file);
      console.log(file);
    },
    imgClicked() {
      const uploadBtn = document.querySelector("#upload");
      uploadBtn.click();
    },
    overlapCheck: function () {
      axios
        .get(`https://localhost:5000/user/overlap/${this.user.userid}`)
        .then((response) => {
          this.cnt = response.data["cnt"];
          if (this.cnt == 0) {
            alert("사용할 수 있는 아이디입니다.");
          } else {
            alert("중복된 아이디입니다. 수정해 주세요!!");
          }
        });
    },
    signupCheck: function () {
      let err = true;
      let msg = "";
      !this.user.userid &&
        ((msg = "아이디를 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err && this.cnt == -1 && ((msg = "중복 체크해주세요!!"), (err = false));
      err &&
        this.cnt > 0 &&
        ((msg = "중복된 아이디입니다. 수정해 주세요!!"), (err = false));
      err &&
        !this.user.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());
      err &&
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
        ((msg = "자기소개를 작성해주세요"),
        (err = false),
        this.$refs.introduce.focus());
      err &&
        !this.user.email &&
        ((msg = "카카오 로그인으로 정보를 가지고 오세요"),
        (err = false),
        this.$refs.email.focus());
      if (!err) {
        alert(msg);
      } else {
        this.pwdConfirmer();
        this.$bvModal.hide("modal-1");
      }
    },
    pwdConfirmer: function () {
      if (this.user.userpwd == this.user.userpwdconfirm) {
        this.$store
          .dispatch("SIGNUP", this.user)
          .catch(({ message }) => (this.msg = message));
        const fd = new FormData();
        if (document.getElementById("upload").files[0]) {
          fd.append("userid", this.user.userid);
          fd.append("upload", document.getElementById("upload").files[0]);
          axios
            .post(`https://localhost:5000/imgs/saveImage`, fd)
            .then((response) => {
              console.log(response.data);
            });
        }
      } else alert("비밀번호와 비밀번호 확인란이 다릅니다.");
    },
    logInKakao() {
      window.Kakao.Auth.authorize({
        //현재 url 찾아야해
        redirectUri: `http://localhost:8080`,
      });
    },
  },
};
</script>

<style scoped>
h1 {
  font-family: yanoljaBold;
}
.profileImg {
  padding: 8%;
}
#Signup2 {
  display: relative;
}
#Signupbox {
  display: flex;
  justify-content: center;
  align-items: center;
}
#signupContent {
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
#signupTxt {
  text-align: center;
  margin-top: 60px;
}
#signupBtn {
  width: 100%;
}
#userid {
  width: 75%;
}
#checkBtn {
  width: 25%;
  height: 100%;
}

.form-control {
  display: inline;
}
#profileLabel img {
  border-radius: 0.25rem;
  border: 1px solid #ced4da;
  padding: 0;
  height: 177px;
}
#__BVID__19 {
  margin: 0;
}
#__BVID__21 {
  margin-bottom: 8px;
}
#profileLabel :nth-child(1) {
  margin: 0 0 8px 0;
}
.signupProcess {
  margin-top: 40px;
  margin-bottom: 20px;
}
#kakaoImg:hover {
  cursor: pointer;
}
</style>