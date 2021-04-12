<template>
  <div id="intoRoom">
    <div id="session" v-if="session">
      <div :class="[{ fullscreen: chatToggle }, 'left_layout']">
        <div id="session-title">
          <h1>{{ mySessionId }}</h1>

          <button
            id="iconBtn"
            style="color: black"
            :class="{ chatOff: !chatToggle }"
            @click="chatToggles"
          >
            <b-icon
              style="background-color: #f8f9fa; padding: 8px 8px; width: 60px"
              icon="chat-left-text"
            ></b-icon>
          </button>
        </div>
        <div class="roomContents">
          <div class="cameraContainer" v-if="accepted">
            <user-video
              class="userVideo"
              v-for="share in ssubscribers"
              :key="share.stream.connection.connectionId"
              :stream-manager="share"
              @click.native="updateMainVideoStreamManager(share)"
            />
            <user-video
              class="userVideo"
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />
            <user-video
              class="userVideo"
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click.native="updateMainVideoStreamManager(sub)"
            >
            </user-video>
          </div>
          <div class="cameraContainer" v-else>
            <div class="waitroom">
              <p>
                방 소개 :
                <span id="waitroom_roomname">{{ this.room.introduce }}</span
                ><br />
                방장이 수락하기 전까진 채팅만 가능합니다. 수락이후 더 재밌게 놀
                수 있습니다.
              </p>
            </div>

            <user-video
              class="userVideo"
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />
          </div>
        </div>
      </div>
      <!-- 왼쪽 layout endline -->

      <div :class="[{ chatOff: chatToggle }, 'right_layout']">
        <div>
          <div id="chatContainer">
            <div style="display: flex; justify-content: space-between">
              <h1>채팅</h1>
              <button
                style="color: red"
                :class="{ chatOff: chatToggle }"
                @click="chatToggles"
              >
                <b-icon
                  style="
                    padding: 4px 0;
                    color: white;
                    width: 60px;
                    height: 40px;
                  "
                  icon="arrow-right-square"
                ></b-icon>
              </button>
            </div>
            <div class="message-wrap" id="mydiv">
              <p
                class="chatlist"
                v-for="(msg, index) in messageList"
                :key="index"
                v-text="msg.message"
              ></p>
            </div>

            <div id="messageInput">
              <select v-model="t" class="chatselect">
                <option :value="undefined">전체</option>
                <option :value="this.publisher.stream.connection">
                  {{
                    JSON.parse(
                      this.publisher.stream.connection.data.split("%/%")[0],
                    ).clientData
                  }}
                </option>
                <option
                  v-for="(sub, index) in subscribers"
                  :key="index"
                  :value="sub.stream.connection"
                  v-text="
                    JSON.parse(sub.stream.connection.data.split('%/%')[0])
                      .clientData
                  "
                ></option>
              </select>
              <input
                placeholder="대화를 시작해보세요..!"
                id="chatInput"
                v-model="message"
                @keydown.enter="sendMessage"
                @keyup.enter="scrolldown"
              />
              <button
                class="btn btn-outline-light"
                @click="sendMessage"
                style="margin: 3.5% 0 0 6%; height: 30px; line-height: normal"
              >
                보내기
              </button>
            </div>
            <div class="chatmenu">
              <!-- 화면 on / off -->
              <div v-if="showScreen" class="chatmenu_on">
                <b-iconstack
                  variant="dark"
                  class="icons fontRed"
                  font-scale="4"
                  @click="changecam"
                  v-on:click="showScreen = !showScreen"
                >
                  <b-icon stacked icon="camera-video-off" scale="0.45"></b-icon>
                </b-iconstack>
                <span class="tooltiptext">카메라</span>
              </div>
              <div v-else class="chatmenu_off">
                <b-iconstack
                  variant="dark"
                  class="icons fontGreen"
                  font-scale="4"
                  @click="changecam"
                  v-on:click="showScreen = !showScreen"
                >
                  <b-icon stacked icon="camera-video" scale="0.45"></b-icon>
                </b-iconstack>
                <span class="tooltiptext">카메라</span>
              </div>

              <!-- 음량 on / off -->
              <div v-if="muteChange" class="chatmenu_on">
                <b-iconstack
                  variant="dark"
                  class="icons fontRed"
                  font-scale="4"
                  @click="changemute"
                  v-on:click="muteChange = !muteChange"
                >
                  <b-icon stacked icon="mic" scale="0.5"></b-icon>
                </b-iconstack>
                <span class="tooltiptext">마이크</span>
              </div>
              <div v-else class="chatmenu_off">
                <b-iconstack
                  variant="dark"
                  class="icons fontGreen"
                  font-scale="4"
                  @click="changemute"
                  v-on:click="muteChange = !muteChange"
                >
                  <b-icon stacked icon="mic-mute" scale="0.5"></b-icon>
                </b-iconstack>
                <span class="tooltiptext">마이크</span>
              </div>

              <!-- 화면공유 on / off -->
              <div v-if="show" v-show="accepted" class="chatmenu_on">
                <b-iconstack
                  class="icons"
                  font-scale="4"
                  @click="screenShare"
                  v-on:click="show = !show"
                >
                  <b-icon
                    stacked
                    icon="share-fill"
                    variant="dark"
                    scale="0.4"
                  ></b-icon>
                </b-iconstack>
                <span class="tooltiptext">화면공유</span>
              </div>
              <div v-else class="chatmenu_off">
                <b-iconstack
                  class="icons"
                  font-scale="4"
                  @click="stopScreenShare"
                  v-on:click="show = !show"
                >
                  <b-icon
                    stacked
                    icon="share"
                    variant="dark"
                    scale="0.4"
                  ></b-icon>
                </b-iconstack>
                <span class="tooltiptext">화면공유</span>
              </div>

              <!-- 게임 메뉴 열기 -->
              <div class="chatmenu_game" v-show="accepted">
                <div :class="{ toggle: toggleGames }" id="backg2">
                  <b-icon scale="2" icon="caret-down-fill"></b-icon>
                </div>
                <div :class="{ toggle: toggleGames }" id="games">
                  <ul>
                    <li v-if="this.mafia !== ''">
                      <span
                        v-if="this.user.manager === 1"
                        class="mafiagames"
                        @click="findMafia"
                      >
                        마피아 잡기
                      </span>
                      <span
                        v-if="this.mafia === this.myUserName"
                        class="mafiagames"
                        @click="killCitizen"
                        style="color: red"
                      >
                        / 시민 죽이기
                      </span>
                    </li>
                    <li v-if="this.mafia === ''">
                      <span class="gameContent" @click="mafiaGame"
                        >마피아 게임</span
                      >
                    </li>
                    <li>
                      <span class="gameContent" @click="liarGame"
                        >라이어 게임</span
                      >
                    </li>
                    <li>
                      <span class="gameContent" @click="getTopic(0)"
                        >순한 대화 주제 랜덤 뽑기</span
                      >
                    </li>
                    <li>
                      <span class="gameContent" @click="getTopic(1)"
                        >매운 대화 주제 랜덤 뽑기</span
                      >
                    </li>
                    <li>
                      <span class="gameContent" @click="getRandomGame"
                        >게임 랜덤 추천</span
                      >
                    </li>
                  </ul>
                </div>
                <b-iconstack @click="showGames" class="icons" font-scale="4">
                  <b-icon
                    stacked
                    icon="joystick"
                    variant="dark"
                    scale="0.4"
                  ></b-icon>
                </b-iconstack>
                <span class="tooltiptext">게임</span>
              </div>

              <!-- 방장이 화면 열어주기 -->
              <div class="chatmenu_off" v-show="this.user.manager === 1">
                <b-iconstack class="icons" font-scale="4" @click="showCam">
                  <b-icon
                    stacked
                    icon="person-check"
                    variant="dark"
                    scale="0.5"
                  ></b-icon>
                </b-iconstack>
                <span class="tooltiptext">입장 수락</span>
              </div>

              <!-- 강퇴 -->
              <div class="chatmenu_on" v-show="this.user.manager === 1">
                <b-iconstack class="icons" font-scale="4" @click="kickUser">
                  <b-icon
                    stacked
                    icon="emoji-dizzy"
                    variant="dark"
                    scale="0.5"
                  ></b-icon>
                </b-iconstack>
                <span class="tooltiptext">강퇴</span>
              </div>

              <!-- 방 나가기 -->
              <div class="chatmenu_on" id="goOutIcon">
                <b-iconstack
                  class="icons"
                  font-scale="4"
                  @click="$bvModal.show('bv-modal-example')"
                >
                  <b-icon
                    stacked
                    icon="door-open"
                    variant="dark"
                    scale="0.5"
                  ></b-icon>
                </b-iconstack>
                <b-modal id="bv-modal-example" hide-footer>
                  <div class="d-block text-center">
                    <h4>
                      실수로 누르신 건 아니죠? <br />
                      정말 나가시나요?
                    </h4>
                  </div>
                  <b-button
                    id="goOutBtn"
                    class="mt-3"
                    block
                    @click="leaveSession"
                    >나가기</b-button
                  >
                  <b-button
                    class="mt-3"
                    id="goBackBtn"
                    block
                    @click="$bvModal.hide('bv-modal-example')"
                    >돌아가기</b-button
                  >
                </b-modal>
                <span class="tooltiptext">나가기</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 오른쪽 layout endline -->
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../components/UserVideo";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "intoRoom",
  components: {
    UserVideo,
  },
  data() {
    return {
      outClicked: false,
      chatToggle: true,
      toggleGames: false,
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      token: "",

      sOV: undefined,
      ssession: undefined,
      smainStreamManager: undefined,
      spublisher: undefined,
      ssubscribers: [],
      screensharetoken: "",

      checkscreenshare: false,
      checkmute: true,
      checkcam: true,

      mySessionId: "Room",
      myUserName: "player" + Math.floor(Math.random() * 100),

      user: {},
      room: {},
      session_id: null,

      // chat
      messageList: [],
      message: "",
      to: "",
      t: undefined,

      //toggle
      show: true,
      showScreen: true,
      muteChange: true,
      showChatroom: true,

      //camera
      camera_size: "",

      //game
      //라이어게임
      liar: "",

      //마피아 게임
      leftCitizen: 0,
      mafia: "",
      mafiaMission: [
        "박수 5번 치기",
        "손가락 하트 2번 만들기",
        "코 3번 만지기",
        "앞머리 뒤로 넘기기",
        "2번 일어나기",
        "뒤돌아보기",
      ],

      //방장권한
      accepted: false,
    };
  },

  created() {
    // created가 아니라 허용할 때로 변경
    this.joinSession(this.$route.params.session_id);
    this.session_id = this.$route.params.session_id;

    this.protectfromreload;
    //버튼 눌러서 상대 들어오게 만들기
    this.getAccepted();
    //버튼으로 상대 내보내기
    this.getLeave();

    //mafia
    this.setMafia();
    this.getMafia();
    this.deadCitizen();
    this.getEndMafia();
  },

  methods: {
    protectfromreload() {
      history.pushState(null, null, location.href);
      window.onpopstate = function () {
        history.go(1);
      };
    },

    // game
    //게임 추천
    getRandomGame() {
      axios.get(`https://localhost:5000/games/recommend`).then((response) => {
        const rGame = response.data;
        this.publisher.session.signal({
          data: JSON.stringify({
            message: "=====당신이 뽑은 게임은! =====",
          }),
          type: "chat",
        });

        this.publisher.session.signal({
          data: JSON.stringify({
            message: rGame,
          }),
          type: "chat",
        });
      });
    },

    // 라이어게임
    liarGame() {
      let ran = Math.floor(
        (Math.random() * 100) % (this.subscribers.length + 1),
      );

      axios.get(`https://localhost:5000/games/liar`).then((response) => {
        this.liar = response.data;

        this.publisher.session.signal({
          data: JSON.stringify(
            (data = {
              //message: "여러분 중 한명은 거짓말을 하고 있습니다. 5분 동안 서로 질문을 돌아가며 하며 힌트를 얻어 범인을 찾으세요! 범인은 힌트를 잘 듣고 대상이 무엇인지 맞추세요!",
              message: "=====liar game 시작!=====",
            }),
          ),
          type: "chat",
        });

        for (let index = 0; index <= this.subscribers.length; index++) {
          var msg =
            index === ran
              ? "당신은 'liar' 입니다."
              : "당신은 '" + this.liar + "' 입니다.";
          var data = {
            message: msg,
          };

          var t =
            index === this.subscribers.length
              ? this.publisher.stream.connection
              : this.subscribers[index].stream.connection;
          this.publisher.session.signal({
            data: JSON.stringify(data),
            to: [t],
            type: "chat",
          });
        }
      });
    },

    //주제 선정 게임
    getTopic(level) {
      axios
        .get(`https://localhost:5000/games/topic/${level}`)
        .then((response) => {
          const topic = response.data;
          console.log(topic);
          this.publisher.session.signal({
            data: JSON.stringify({
              message: "=====당신이 뽑은 주제는=====",
            }),
            type: "chat",
          });

          this.publisher.session.signal({
            data: JSON.stringify({
              message: topic,
            }),
            type: "chat",
          });
        });
    },

    //마피아 게임
    //마피아 세팅
    setMafia() {
      this.session.on("signal:setMafia", (event) => {
        const data = JSON.parse(event.data);
        this.mafia = data.mafia;
        this.leftCitizen = data.leftCitizen;
      });
    },

    endMafia() {
      this.publisher.session.signal({
        type: "endMafia",
      });
    },

    getEndMafia() {
      this.session.on("signal:endMafia", (event) => {
        console.log(event);
        this.mafia = "";
      });
    },

    mafiaGame() {
      let ran = Math.floor(
        (Math.random() * 100) % (this.subscribers.length + 1),
      );

      let missionRan = Math.floor(
        (Math.random() * 100) % this.mafiaMission.length,
      );

      this.mafia =
        ran === this.subscribers.length
          ? JSON.parse(this.publisher.stream.connection.data.split("%/%")[0])
              .clientData
          : JSON.parse(
              this.subscribers[ran].stream.connection.data.split("%/%")[0],
            ).clientData;
      this.leftCitizen = this.subscribers.length;

      this.publisher.session.signal({
        data: JSON.stringify(
          (data = {
            message: "=====마피아 게임 시작!=====",
          }),
        ),
        type: "chat",
      });

      this.publisher.session.signal({
        data: JSON.stringify(
          (data = {
            mafia: this.mafia,
            leftCitizen: this.leftCitizen,
          }),
        ),
        type: "setMafia",
      });

      //역할분배
      for (let index = 0; index <= this.subscribers.length; index++) {
        var msg =
          index === ran
            ? "당신은 '마피아' 입니다. 당신의 미션은 " +
              this.mafiaMission[missionRan] +
              "입니다."
            : "당신은 '시민' 입니다.";
        var data = {
          message: msg + "토론 시작",
        };

        var t =
          index === this.subscribers.length
            ? this.publisher.stream.connection
            : this.subscribers[index].stream.connection;

        this.publisher.session.signal({
          data: JSON.stringify(data),
          to: [t],
          type: "chat",
        });
      }
    },

    //마피아 잡기 버튼
    findMafia() {
      this.leftCitizen = this.leftCitizen - 1;
      const data = {
        mafia: JSON.parse(this.t.data.split("%/%")[0]).clientData,
      };
      this.publisher.session.signal({
        data: JSON.stringify(data),
        //to : [this.publisher.stream.connection],
        type: "mafia",
      });
    },
    //마피아 잡기 버튼 - get
    getMafia() {
      this.session.on("signal:mafia", (event) => {
        const data = JSON.parse(event.data);
        //마피아면
        if (data.mafia === this.mafia) {
          this.endMafia();
          this.publisher.session.signal({
            data: JSON.stringify({
              message: "마피아가 잡혔습니다. 시민 승!",
            }),
            to: [this.publisher.stream.connection],
            type: "chat",
          });
        }
        //시민이면
        else {
          this.publisher.session.signal({
            data: JSON.stringify({
              message: data.mafia + "은(는) 시민입니다.",
            }),
            to: [this.publisher.stream.connection],
            type: "chat",
          });

          //시민이 잡혀서 시민 숫자 === 마피아 숫자
          if (this.leftCitizen <= 1) {
            this.endMafia();
            this.publisher.session.signal({
              data: JSON.stringify({
                message: "마피아 승리",
              }),
              //to: [this.publisher.stream.connection],
              type: "chat",
            });
          }
        }
      });
    },

    //시민 죽이기 버튼
    killCitizen() {
      this.leftCitizen = this.leftCitizen - 1;
      const data = {
        citizen: JSON.parse(this.t.data.split("%/%")[0]).clientData,
        leftCitizen: this.leftCitizen,
      };
      this.publisher.session.signal({
        data: JSON.stringify(data),
        //to : [this.publisher.stream.connection],
        type: "kill",
      });
    },

    deadCitizen() {
      this.session.on("signal:kill", (event) => {
        const data = JSON.parse(event.data);
        this.leftCitizen = data.leftCitizen;
        let msg = "아침이 밝았습니다. \n" + data.citizen + "이 죽었습니다.";
        if (this.leftCitizen <= 1) {
          this.endMafia();
          msg = msg + "마피아 승리";
        }
        this.publisher.session.signal({
          data: JSON.stringify({
            message: msg,
          }),
          to: [this.publisher.stream.connection],
          type: "chat",
        });
      });
    },

    // chat

    scrolldown() {
      var objDiv = document.getElementById("mydiv");
      objDiv.scrollTop = objDiv.scrollHeight;
    },

    sendMessage() {
      console.log(this.leftCitizen);
      console.log(this.user);
      // if (this.props.user && this.message) {
      if (this.message) {
        let message = this.message.replace(/ +(?= )/g, "");
        if (message !== "" && message !== " ") {
          const data = {
            message: this.myUserName + " : " + message,
            nickname: this.myUserName,
            streamId: this.publisher.stream.streamId,
          };
          if (this.t) {
            this.publisher.session.signal({
              data: JSON.stringify(data),
              to: [this.t, this.publisher.stream.connection],
              type: "chat",
            });
          } else {
            this.publisher.session.signal({
              data: JSON.stringify(data),
              type: "chat",
            });
          }
        }
      }
      this.message = "";
    },

    //허용

    showCam() {
      this.publisher.session.signal({
        data: JSON.stringify({
          message:
            JSON.parse(this.t.data.split("%/%")[0]).clientData +
            "님이 입장했습니다.",
        }),
        type: "chat",
      });

      const data = {
        accept: true,
      };
      this.publisher.session.signal({
        data: JSON.stringify(data),
        to: [this.t],
        type: "allow",
      });
    },

    getAccepted() {
      this.session.on("signal:allow", (event) => {
        console.log(event);
        const data = JSON.parse(event.data);
        this.accepted = data.accept;
        console.log("dddddddddddd");
        console.log(this.accepted);
      });
    },

    //추방
    kickUser() {
      const data = {
        leave: true,
      };
      this.publisher.session.signal({
        data: JSON.stringify(data),
        to: [this.t],
        type: "kick",
      });
    },

    getLeave() {
      this.session.on("signal:kick", (event) => {
        console.log(event);
        this.leaveSession();
      });
    },

    memberUpdate() {
      this.$store
        .dispatch("MEMBERUPDATE", this.user)
        .catch(({ message }) => (this.msg = message));
    },

    joinSession(session_id) {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        if (subscriber.stream.typeOfVideo !== "CUSTOM")
          this.subscribers.push(subscriber);
        else {
          this.checkscreenshare = true;
          this.ssubscribers.push(subscriber);
        }
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        const subscriber = this.session.subscribe(stream);
        if (subscriber.stream.typeOfVideo === "CUSTOM") {
          this.ssubscribers = [];
          this.checkscreenshare = false;
        }
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      axios // add
        .post(`https://localhost:5000/user/info`)
        .then((response) => {
          this.myUserName = response.data.user.username;
          axios // add
            .get(`https://localhost:5000/room/selectroom/${session_id}`)
            .then((res) => {
              if (res.data.message === "FAIL") {
                alert(
                  "새로고침하여, 이 방은 더 이상 유효하지 않습니다. 나가주세요.",
                );
                if (this.session) this.session.disconnect();
                this.session = undefined;
                this.mainStreamManager = undefined;
                this.publisher = undefined;
                this.subscribers = [];
                this.OV = undefined;
                this.$router.push({ name: "Main" });
              } else {
                this.room = res.data.room;
                this.mySessionId =
                  "[" + this.room.theme + "]" + this.room.title;
                this.user = response.data.user;
                this.user.session_id = session_id;
                if (this.user.username === this.room.owner_id) {
                  this.user.manager = 1;
                  this.accepted = true;
                }
                this.memberUpdate(); // add
                if (this.user.gender === "남자") {
                  this.room.maleNum = this.room.maleNum + 1;
                } else {
                  this.room.femaleNum = this.room.femaleNum + 1;
                }
                axios // add
                  .put(
                    `https://localhost:5000/room/updateRoom/${session_id}`,
                    this.room,
                  )
                  .then((response) => {
                    console.log(response);
                  });
              }
            });
        })
        .catch(() => {
          this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
        });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      // this.getToken(this.mySessionId).then((token) => {
      axios
        .get(`https://localhost:5000/session/maketoken/${session_id}`)
        .then((response) => {
          this.token = response.data.token;

          this.session
            .connect(this.token, { clientData: this.myUserName })

            .then(() => {
              // --- Get your own camera stream with the desired properties ---

              let whole = {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                // insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              };

              let publisher = this.OV.initPublisher(undefined, whole);

              // let publisher = this.OV.initPublisher(undefined, {
              //   audioSource: undefined, // The source of audio. If undefined default microphone
              //   videoSource: undefined, // The source of video. If undefined default webcam
              //   publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              //   publishVideo: true, // Whether you want to start publishing with your video enabled or not
              //   resolution: "300x160", // The resolution of your video
              //   frameRate: 30, // The frame rate of your video
              //   insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              //   mirror: false, // Whether to mirror your local video or not
              // });

              this.mainStreamManager = publisher;
              this.publisher = publisher;

              // --- Publish your stream ---
              this.messageList = [];
              this.session.publish(this.publisher);
              // console.log("참여자 목록");
              // console.log(this.subscribers);
              //chat
              this.publisher.session.on("signal:chat", (event) => {
                const data = JSON.parse(event.data);
                this.messageList.push({
                  // connectionId: event.from.connectionId,
                  nickname: data.nickname,
                  message: data.message,
                });
              });
              //chat
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message,
              );
            });
        });
      // window.addEventListener("beforeunload", (event) => {
      //   event.returnValue = "경고";
      // });
      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.checkscreenshare) {
        this.stopScreenShare();
      }

      if (this.user.manager === 1) {
        this.publisher.session.signal({
          data: JSON.stringify({
            message: "방장이 나갔습니다. 몇몇 기능이 안될 수 있습니다.",
          }),
          type: "chat",
        });
      }

      if (this.session) this.session.disconnect();
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.accepted = false;
      axios // add
        .post(`https://localhost:5000/user/info`)
        .then((response) => {
          this.user = response.data.user;
          this.user.session_id = -1;
          this.user.manager = 0;
          this.memberUpdate();
        });
      axios
        .post(`https://localhost:5000/session/removeUser`, {
          session_id: this.session_id,
          token: this.token,
          byegender: this.user.gender,
        })
        .then((response) => {
          if (response.data === "FAIL") {
            window.removeEventListener("beforeunload", this.leaveSession);
            this.$router.push({ name: "Main" });
            location.reload();
          } else {
            this.$router.push({ name: "Main" });
            // window.removeEventListener("beforeunload", (event) => {
            //   event.returnValue = "경고";
            // });
            window.removeEventListener("beforeunload", this.leaveSession);
            window.removeEventListener("beforeunload", this.protectfromreload);
            location.reload();
          }
        });
    },

    screenShare() {
      // --- Get an OpenVidu object ---
      this.sOV = new OpenVidu();

      // --- Init a session ---
      this.ssession = this.sOV.initSession();

      axios
        .get(`https://localhost:5000/session/maketoken/${this.session_id}`)
        .then((response) => {
          this.screensharetoken = response.data.token;

          this.ssession
            .connect(this.screensharetoken, { clientData: this.myUserName })
            .then(() => {
              let mediaDevices = navigator.mediaDevices;
              var displayMediaOptions = {
                video: {
                  cursor: "always",
                },
                audio: true,
              };
              mediaDevices
                .getDisplayMedia(displayMediaOptions)
                .then((screenStream) => {
                  let screenVideoTrack = screenStream.getVideoTracks()[0];
                  let screenAudioTrack = screenStream.getAudioTracks()[0];

                  // const videoSource =
                  //   navigator.userAgent.indexOf("Firefox") !== -1
                  //     ? "window"
                  //     : "screen";

                  let publisher = this.OV.initPublisher(
                    undefined,
                    {
                      audioSource: screenAudioTrack,
                      videoSource: screenVideoTrack,
                      // audioSource: "system",
                      // videoSource: videoSource,
                      publishAudio: true,
                      publishVideo: true,
                      resolution: "200x145", // The resolution of your video
                      frameRate: 30, // The frame rate of your video
                      insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                      mirror: false,
                    },
                    (error) => {
                      if (
                        error &&
                        error.name === "SCREEN_EXTENSION_NOT_INSTALLED"
                      ) {
                        alert("Your browser should INSTALL SCREEN_EXTENSION");
                      } else if (
                        error &&
                        error.name === "SCREEN_SHARING_NOT_SUPPORTED"
                      ) {
                        alert("Your browser does not support screen sharing");
                      } else if (
                        error &&
                        error.name === "SCREEN_EXTENSION_DISABLED"
                      ) {
                        alert("You need to enable screen sharing extension");
                      } else if (
                        error &&
                        error.name === "SCREEN_CAPTURE_DENIED"
                      ) {
                        alert(
                          "You need to choose a window or application to share",
                        );
                      }
                    },
                  );
                  this.smainStreamManager = publisher;
                  this.spublisher = publisher;

                  // --- Publish your stream ---
                  this.ssession.publish(this.spublisher);

                  //this.shareScreenShare();
                });
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message,
              );
            });
        });

      window.addEventListener("beforeunload", this.stopScreenShare);
    },

    // shareScreenShare() {
    //   const data = {
    //     spublisher: this.spublisher,
    //   };

    //   this.publisher.session.signal({
    //     data: JSON.stringify(data),
    //     type: "screenshare",
    //   });

    //   console.log(this.this.spublisher);
    // },

    // getScreenShare() {
    //   this.publisher.session.on("signal:screenshare", (event) => {
    //     //this.checkscreenshare = event.data.check;
    //     console.log('ㅅ비자ㅓㅏㅣㅅㅈㅄ')
    //     console.log(event);
    //     console.log('ㅅ비자ㅓㅏㅣㅅㅈㅄ')
    //     console.log(this.checkscreenshare);
    //     this.changeScreenShare();
    //   });
    // },

    // changeScreenShare() {
    //   this.checkscreenshare = false;
    // },

    stopScreenShare() {
      // const data = {
      //   check: false,
      // };

      // this.publisher.session.signal({
      //   data: JSON.stringify(data),
      //   type: "screenshare",
      // });

      if (this.ssession) this.ssession.disconnect();

      this.ssession = undefined;
      this.smainStreamManager = undefined;
      this.spublisher = undefined;
      this.ssubscribers = [];
      this.sOV = undefined;
      //this.checkscreenshare = !this.checkscreenshare;

      axios
        .post(`https://localhost:5000/session/removeUser`, {
          session_id: this.session_id,
          token: this.screensharetoken,
        })
        .then((response) => {
          console.log(response);
          window.removeEventListener("beforeunload", this.stopScreenShare);
        });
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    changemute() {
      if (this.checkmute) {
        this.checkmute = false;
      } else {
        this.checkmute = true;
      }
      console.log(this.checkmute);
      this.mainStreamManager.publishAudio(this.checkmute);
    },
    changecam() {
      if (this.checkcam) {
        this.checkcam = false;
      } else {
        this.checkcam = true;
      }
      console.log(this.checkcam);
      this.mainStreamManager.publishVideo(this.checkcam);
    },
    showGames() {
      this.toggleGames = !this.toggleGames;
    },
    chatToggles() {
      this.chatToggle = !this.chatToggle;
    },
  },
  updated: function () {
    const videos = window.document.querySelectorAll(".userVideo");
    let videoL = `${videos.length}`;
    const mysetsize = function (wid, hei) {
      videos.forEach((video) => {
        video.setAttribute(
          "style",
          `width : ${wid} !important; height : ${hei} !important;`,
        );
      });
    };
    const mysetsize2 = function (wid, hei, wid2, hei2) {
      videos.forEach((video) => {
        video.setAttribute(
          "style",
          `width : ${wid2} !important; height : ${hei2} !important;`,
        );
      });
      videos[0].setAttribute(
        "style",
        `width : ${wid} !important; height : ${hei} !important; margin-bottom : 20px;`,
      );
    };

    // const container = window.document.querySelector(".cameraContainer");
    // const myflexwrap = function(wrap) {
    //   container.setAttribute("style", `height : ${wrap} !important;`);
    // };
    if (this.checkscreenshare) {
      if (this.chatToggle) {
        if (videoL === "2") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "3") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "4") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "5") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "6") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "7") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else if (videoL === "8") {
          mysetsize2("1440px", "400px", "190px", "190px");
        } else {
          mysetsize2("1440px", "400px", "190px", "190px");
        }
      } else {
        if (videoL === "2") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "3") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "4") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "5") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "6") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "7") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else if (videoL === "8") {
          mysetsize2("1000px", "400px", "130px", "130px");
        } else {
          mysetsize2("1000px", "400px", "130px", "130px");
        }
      }
    } else {
      if (this.chatToggle) {
        if (videoL === "1") {
          mysetsize("1070px", "554px");
        } else if (videoL === "2") {
          mysetsize("500px", "554px");
        } else if (videoL === "3") {
          mysetsize("500px", "270px");
        } else if (videoL === "4") {
          mysetsize("500px", "270px");
        } else if (videoL === "5") {
          mysetsize("420px", "270px");
        } else if (videoL === "6") {
          mysetsize("420px", "270px");
        } else if (videoL === "7") {
          mysetsize("300px", "270px");
        } else {
          mysetsize("300px", "270px");
        }
      } else {
        if (videoL === "1") {
          mysetsize("966px", "600px");
        } else if (videoL === "2") {
          mysetsize("966px", "300px");
        } else if (videoL === "3") {
          mysetsize("400px", "300px");
        } else if (videoL === "4") {
          mysetsize("400px", "300px");
        } else if (videoL === "5") {
          mysetsize("300px", "300px");
        } else if (videoL === "6") {
          mysetsize("300px", "300px");
        } else if (videoL === "7") {
          mysetsize("210px", "300px");
        } else {
          mysetsize("210px", "300px");
        }
      }
    }
  },
};
</script>

<style>
#intoRoom {
  /* background-color: rgb(1, 22, 39); */
  background-color: rgb(30, 30, 30);
  color: white;
}

#session {
  /* display: grid;

  grid-template-columns: 70% 30%;

  grid-template-rows: 100%; */
  width: 100vw;
  height: 100vh;
  display: flex;

  overflow: hidden;
}
#session > h1 {
  display: inline;
}

#video-container {
  width: 100%;
}
#session-title {
  height: 5%;
  margin: 1% 0 0 15%;
  font-size: 40px;
  display: flex;
  justify-content: space-between;
}
.left_layout {
  border-right: 1px solid white;
  width: 70%;
}
.right_layout {
  width: 30%;
}
.my_video {
  float: left;
  margin: 0 0 2% 0;
  width: 40%;
  border-bottom: thick double white;
}

.other_videos {
  float: left;
  text-align: center;
  /* border: 2px solid white; */
}

.video_menu {
  float: right;
  width: 20%;
  position: absolute;
  top: 16%;
  left: 28%;
  height: 15%;
}

.right_layout {
}

#chatContainer {
  height: 100%;
  width: 80%;
  margin: 1% auto 0 auto;
}

.message-wrap {
  height: 68vh;
  border: 1px solid grey;
  width: 100%;
  overflow: scroll;
  overflow-x: hidden;
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.message-wrap::-webkit-scrollbar {
  display: none;
}

.chatlist {
  margin: 8px 0 0 8px !important;
  font-size: 16px;
}

.video_test {
  display: flex;
  flex-wrap: wrap;
  padding: 1% 5% 5% 5%;
}
.video_test > div {
  overflow: hidden;
  height: auto;
  width: 100%;
}
#messageInput {
  float: left;
  width: 100%;
  height: 40px;
  margin: 20px 0 0 0;
}

.chatselect {
  float: left;
  width: 100%;
  height: 25px;
}

#chatInput {
  float: left;
  margin: 4% 0 0 0;
  width: 78%;
  height: 25px;
}
#sendButton {
  float: left;
  width: 18%;
  margin: 10px 0 0 5px;
  height: 20px;
  color: white;
}
.roomContents {
  height: 95%;
  overflow-y: scroll;
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.roomContents::-webkit-scrollbar {
  display: none;
}

#buttonLeaveSession {
  margin: 0 0 6% 5%;
}

#buttonLeaveSession:hover {
  opacity: 0.8;
  color: black;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 1s;
}
.v-leave-active {
  position: absolute;
}
.v-enter,
.v-leave-to {
  opacity: 0;
}

.chatmenu {
  /* background: rgb(247, 247, 247); */
  background: rgb(30, 30, 30);
  position: absolute;
  right: 1px;
  bottom: 1px;
  width: 30%;
  border-left: 1px solid white;
  padding-left: 16px;

  height: 10%;
}

.chatmenu_on svg:hover g {
  color: red;
}

.chatmenu_off svg:hover g {
  color: green;
}

.chatmenu_game svg:hover g {
  color: rgba(145, 145, 11, 0.562);
}

.chatmenu_on,
.chatmenu_off,
.chatmenu_game {
  width: 12%;
  float: left;
}

.chatmenu_game {
  position: relative;
}

#games {
  position: absolute;
  top: -182px;
  left: -39px;
  background-color: rgb(218 218 218);
  color: black;
  border-radius: 20px;
  padding-top: 16px;
  padding-left: 10px;
  visibility: hidden;
}
#games > ul {
  list-style: none;
}
#games > ul > li {
  height: 30px;
  width: 130px;
  text-decoration: rgba(255, 0, 38, 0.404);
  text-decoration-line: underline;
  /* border-bottom: 1px solid rgb(201, 201, 201); */
}
#backg2 {
  visibility: hidden;
  position: absolute;
  top: -5px;
  left: 19px;
  color: rgb(218 218 218);
}
.gameContent:hover {
  cursor: pointer;
}
.toggle {
  visibility: visible !important;
}
.cameraContainer {
  width: 100%;
  height: 673px;
  max-width: 100%;
  display: flex;
  flex-flow: row wrap;
  padding: 5% 5%;
  justify-content: center;
  align-items: center;
}
.cameraContainer > div {
  position: relative;
  overflow: hidden;
  height: auto;
}
.userVideo {
  border: none;
}
video {
  width: 100%;
  height: 100%;
}
video + div > p {
  color: rgba(0, 255, 0, 1);
}
video + div {
  position: absolute;
  height: 30px;
  top: 0;
  color: rgba(0, 255, 0, 1);
  font-size: 1.5rem;
  left: 50%;
  transform: translate(-50%, 0);
  font-size: 2rem;
  text-align: center;
  width: 100%;
  visibility: hidden;
}
video:hover + div {
  visibility: visible;
  animation: fadein 0.6s;
  width: 100%;
}

.waitroom {
  border: 1px solid white;
  font-size: 20px;
  margin: 0 0 5% 0;
  width: 100%;
  text-align: center;
}

#waitroom_roomname {
  color: rgb(197, 245, 197);
}

.chatOff {
  display: none;
  animation: fadein 0.6s;
}
.fullscreen {
  width: 100% !important;
}
/* .fontGreen {
  color: green;
}
.fontRed {
  color: red;
} */
.icons {
  margin: 1% 0 0 2px;
  width: 90%;
}
.chatmenu > div {
  position: relative;
  box-shadow: 1px 1px 3px 1px #dadce0;
  margin: 5px 3px 0 3px;
  border-radius: 10px;
  background-color: rgb(247, 247, 247);
  width: 12%;
  height: 60px;
}

.chatmenu > div:hover {
  cursor: pointer;
}
.chatmenu > div:active {
  transform: translateY(2px);
  box-shadow: 1px 1px 3px 1px #505050 inset;
  /* background-color: rgba(221, 221, 221, 0.185); */
  background-color: rgb(226, 226, 226);
}
.chatmenu > div:active .tooltiptext {
  visibility: hidden;
}

.iconExplain {
  color: black;
  font-size: 8px;
  top: 55px;
  left: 88px;
  position: absolute;
}
.tooltip {
  position: relative;
}

.chatmenu div svg:hover + span {
  visibility: visible;
}

/* .chatmenu > div > svg > g:hover svg > .tooltiptext {
  visibility: visible;
} */
.tooltiptext {
  visibility: hidden;
  width: 44px;
  height: 25px;
  background-color: rgb(255, 0, 40, 0.87);
  color: black;
  text-align: center;
  padding: 2px 0;
  border-radius: 6px;
  font-size: 13px;
  position: absolute;
  z-index: 1;
  bottom: 83%;
  left: 119%;
  margin-left: -60px;
}

.tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: rgb(255, 0, 40, 0.87) transparent transparent transparent;
}
#goBackBtn {
  background-color: #212529c2;
  border: none;
  width: 71%;
  margin: auto;
  color: white;
}
#goOutBtn {
  background-color: rgba(236, 47, 76, 0.87);
  color: white;
  border: none;
  width: 71%;
  margin: auto;
}
</style>