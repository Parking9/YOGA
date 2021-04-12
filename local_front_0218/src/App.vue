<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
      isLogin: false,
    };
  },
};
</script>

<style>
@font-face {
  font-family: "Blacksword";
  src: url("assets/fonts/Blacksword.woff") format("woff2");
}
@font-face {
  font-family: "yanoljaBold";
  src: url("assets/fonts/yanoljaBold.woff") format("woff2");
}
@font-face {
  font-family: "yanoljaRegular";
  src: url("assets/fonts/yanoljaRegular.woff") format("woff2");
}

*{
  font-family: "yanoljaRegular";
  margin: 0;
  padding: 0;
}

h1,
h2{
  font-family: 'yanoljaBold' !important;
}


a:hover{
  text-decoration: none !important;
}
a{
  color:black !important;
}
input::-webkit-input-placeholder{
  opacity: 0.3;
}
input::-moz-placeholder{
  opacity: 0.3;
}
input:-ms-input-placeholder{
  opacity: 0.3;
}
input:-moz-placeholder{
  opacity: 0.3;
}
input::placeholder{
  opacity: 0.3;
}
p{
  margin: 0 !important;
}
.modal-header{
    padding-left: 38%!important;
}
</style>
