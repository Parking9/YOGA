<template>
  <div id="Home">
    <div id="carousel" class="col-xs-12 col-sm-8 col-md-8 col-lg-8 col-xl-8">
      <b-carousel
        :interval="6000"
        id="carousel-fade"
        style="text-shadow: 0px 0px 2px #000; margin-left: 35px"
        fade
      >
        <b-carousel-slide :img-src="require('../assets/img/image1.jpg')">
          <div class="introbox" id="firstbox">
            <h2 class="intro">비대면 시대</h2>
            <p class="intro2">우리가 웃고 즐기는 새로운 놀이방식</p>
          </div>
        </b-carousel-slide>
        <b-carousel-slide :img-src="require('../assets/img/image2.jpg')">
          <div class="introbox">
            <h2 class="intro">남녀모두 꿈꿔왔던</h2>
            <h4 class="intro2">자유로운 만남</h4>
          </div>
        </b-carousel-slide>
        <b-carousel-slide :img-src="require('../assets/img/image3.jpg')">
          <div class="introbox">
            <h2 class="intro">요즘 우리가 가는곳,</h2>
            <h4 class="intro2">YOGA에서 시작합니다.</h4>
          </div>
        </b-carousel-slide>
      </b-carousel>
    </div>
    <div id="loginP" class="col-xs-12 col-sm-4 col-md-4 col-lg-4 col-xl-4">
      <Login />
    </div>
  </div>
</template>

<script>
import Login from "./Login.vue";
import { mapState } from 'vuex'

export default {
  name: "Home",
  components: {
    Login,
  },
  created: function(){
    if(this.accessToken){
      this.$router.push('/main')
    }
  },
  computed: {
    ...mapState([
        'accessToken'
    ]),
  },
};
</script>

<style>
#Home {
  display: flex;
  flex-wrap: wrap;
}
#carousel {
  width: 100%;
  height: 90vh;
  margin: auto;
  margin-left: 0;
}
.carousel img {
  width: 100%;
  height: 100vh;
}

#loginP {
  height: 100vh;
}
#Home .carousel,
#Home .carousel-inner,
#Home .carousel-item,
#Home .carousel-item::before {
  background-repeat: no-repeat;
  background-position: center;
  background-repeat: no-repeat;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
}

#Home .carousel-item::before {
  content: "";
  background-color: rgba(0, 0, 0, 0.85);
  opacity: 0.9;
}

/* 데스크탑 */
@media (min-width: 1280px) {
  #Home .carousel-control-prev,
  #Home .carousel-control-next {
    width: 5%;
  }
}
/* 데스크탑 */
@media (min-width: 1024px) {
  #Home .carousel-control-prev,
  #Home .carousel-control-next {
    width: 5%;
  }
}
/* 노트북 */
@media (max-width: 992px) {
  a {
    font-size: 90%;
  }
}
/* 타블렛 */
@media (max-width: 768px) {
}

/* 모바일 */
@media (max-height: 576px) {
}

.introbox {
  position: absolute;
  display: inline-block;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  margin: 0;
  padding: 5% 10%;
  border: 1px solid rgba(255, 255, 255, 0.493);
}

.carousel-caption {
  top: 0;
  left: 0;
}

.intro {
  position: relative;
  font-size: 4rem;
  white-space: nowrap;
  color: transparent;
  animation: fadein 1s;
  animation-delay: 1s;
  animation-fill-mode: forwards;
}
@keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
    color: white;
  }
}
.intro2 {
  white-space: nowrap;
  word-break: break-all;
  color: transparent;
  font-size: 2rem;
  animation: fadein 1s;
  animation-delay: 2s;
  animation-fill-mode: forwards;
}
@keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
    color: white;
  }
}
</style>

