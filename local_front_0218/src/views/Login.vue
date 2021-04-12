<template>
  <div id="loginPage">
    <div id="loginbox">
      <div id="login">
        <div id="h2div" class="col-12">
          <img id="loginTxt" src="../assets/img/logo.png" width="200px" />
        </div>
        <div>
          <b-form-group>
            <b-input-group class="mb-2">
              <b-input-group-prepend is-text>
                <b-icon icon="person-fill"></b-icon>
              </b-input-group-prepend>
              <b-form-input
                v-model="user.userid"
                type="text"
                placeholder="아이디를 입력해주세요"
              ></b-form-input>
            </b-input-group>
          </b-form-group>

          <b-form-group>
            <b-input-group class="mb-2">
              <b-input-group-prepend is-text>
                <b-icon icon="key"></b-icon>
              </b-input-group-prepend>
              <b-form-input
                v-model="user.userpwd"
                type="password"
                @keypress.enter="login"
                placeholder="비밀번호를 입력해주세요"
              ></b-form-input>
            </b-input-group>
          </b-form-group>
        </div>
        <div>
          <b-form-group>
            <b-form-checkbox
              id="remember"
              name="remember"
              v-model="check"
              checked-value="1"
              unchecked-value="0"
            >
              아이디 저장
            </b-form-checkbox>
          </b-form-group>
        </div>
        <div>
          <b-form-group label-for="input-lazy">
            <b-button @click="login" variant="danger" class="col-12"
              >로그인</b-button
            >
          </b-form-group>
        </div>
        <div class="pt-0">
          <p v-b-modal.modal-1 @click="modalShow = !modalShow">
            아직 회원이 아니신가요? 계정 만들기
          </p>
          <b-modal
            v-model="modalShow"
            id="modal-1"
            hide-footer
            centered
            title="🎉 환영합니다 🎉"
          >
            <SignupModal />
          </b-modal>
        </div>
        <div class="pt-0">
          <p v-b-modal.modal-2>비밀번호 찾기</p>
          <b-modal id="modal-2" hide-footer centered title="🔍 계정 정보 찾기">
            <FindModal />
          </b-modal>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SignupModal from "../components/SignupModal.vue";
import FindModal from "../components/FindModal.vue";

export default {
  name: "login",
  components: {
    SignupModal,
    FindModal,
  },
  data: function () {
    return {
      user: {
        userid: "",
        userpwd: "",
      },
      modalShow: false,
      check: false,
      message: "",
    };
  },
  created() {
    this.user.userid = localStorage.getItem("userid");
    this.user.userpwd = localStorage.getItem("userpwd");
    this.check = localStorage.getItem("check");

    let code = this.$route.query.code;
    console.log(code);
    if (code != null || code != undefined) {
      this.modalShow = true;
    }
  },

  methods: {
    login: function () {
      this.remember(this.check);
      // LOGIN 액션 실행
      // 서버와 통신(axios)을 해 토큰값을 얻어야 하므로 Actions를 호출.
      this.$store
        .dispatch("LOGIN", this.user)
        .then(() => console.log("로그인 성공"))
        .catch(({ message }) => (this.msg = message));
    },
    remember: function (check) {
      if (check == 1) {
        localStorage.setItem("userid", this.user.userid);
        localStorage.setItem("userpwd", this.user.userpwd);
        localStorage.setItem("check", check);
      } else {
        localStorage.removeItem("userid");
        localStorage.removeItem("userpwd");
        localStorage.setItem("check", 0);
      }
    },
  },
};
</script>

<style scoped>
#loginbox {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
#loginTxt {
  text-align: center;
  margin-bottom: 30px;
  font-size: 42px;
}
#login {
  box-sizing: content-box;
}
@media (max-width: 992px) {
}
/* 타블렛 */
@media (max-width: 768px) {
  #Home h2 {
    font-size: 28px;
  }
}
p {
  color: #c82333;
}
</style>