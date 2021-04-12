package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.js.java.dto.RoomDto;
import io.openvidu.js.java.service.RoomService;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;

@RestController
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private RoomService roomService;

	// OpenVidu object as entrypoint of the SDK
	private OpenVidu openVidu;

	// Collection to pair session names and OpenVidu Session objects
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	// Collection to pair session names and tokens (the inner Map pairs tokens and
	// role associated)
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

	// URL where our OpenVidu server is listening
	private String OPENVIDU_URL;
	// Secret shared with our OpenVidu server
	private String SECRET;

	public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}

	@GetMapping("/maketoken/{session_id}")
	public ResponseEntity<Map<String, Object>> joinSession(@PathVariable String session_id) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String clientData = "123";
		System.out.println(session_id);
		System.out.println("Getting sessionId and token | {sessionName}={" + session_id + "}");

		// Role associated to this user
		OpenViduRole role = OpenViduRole.PUBLISHER;
		// Optional data to be passed to other users when this user connects to the
		// video-call. In this case, a JSON with the value we stored in the HttpSession
		// object on login
		String serverData = "{\"serverData\": \"" + "publisher" + "\"}";

		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data(serverData).build();

		if (this.mapSessions.get(session_id) != null) {
			// Session already exists
			System.out.println("Existing session " + session_id);
			try {

				// Generate a new token with the recently created connectionProperties
				String token = this.mapSessions.get(session_id).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionNamesTokens.get(session_id).put(token, role);

				// Add all the needed attributes to the template
				resultMap.put("sessionName", session_id);
				resultMap.put("token", token);
				resultMap.put("nickName", clientData);
				resultMap.put("userName", "publisher");
				resultMap.put("message", "로그인 성공");
				status = HttpStatus.ACCEPTED;

			} catch (Exception e) {
				// If error just return dashboard.html template
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} else {
			// New session
			System.out.println("New session " + session_id);
			try {

				// Create a new OpenVidu Session
				Session session = this.openVidu.createSession();
				// Generate a new token with the recently created connectionProperties
				String token = session.createConnection(connectionProperties).getToken();

				// Store the session and the token in our collections
				this.mapSessions.put(session_id, session);
				this.mapSessionNamesTokens.put(session_id, new ConcurrentHashMap<>());
				this.mapSessionNamesTokens.get(session_id).put(token, role);

				// Add all the needed attributes to the template
				resultMap.put("sessionName", session_id);
				resultMap.put("token", token);
				resultMap.put("nickName", clientData);
				resultMap.put("userName", "publisher");
				resultMap.put("message", "로그인 성공");
				status = HttpStatus.ACCEPTED;

			} catch (Exception e) {
				// If error just return dashboard.html template
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	public ResponseEntity<String> removeUser(@RequestBody RoomDto roomdto) throws Exception {

		String sessionName = String.valueOf(roomdto.getSession_id());
		String token = roomdto.getToken();
		String byegender = roomdto.getByegender();

		
		RoomDto sroom = roomService.selectroom(roomdto.getSession_id());

		if(sroom==null) {
			return new ResponseEntity<String>("FAIL",HttpStatus.OK);
		}
		
		if (byegender.equals("여자")) {
			sroom.setFemaleNum(sroom.getFemaleNum() - 1);
		} else {
			sroom.setMaleNum(sroom.getMaleNum() - 1);
		}
		
		roomService.updateRoom(sroom);

		if (sroom.getFemaleNum() == 0 && sroom.getMaleNum() == 0) {
			roomService.deleteroom(sroom.getSession_id());
		}

		// If the session exists
		if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

			// If the token exists
			if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
				// User left the session
				if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
					// Last user left: session must be removed
					this.mapSessions.remove(sessionName);
				}
				return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
			} else {
				// The TOKEN wasn't valid
				System.out.println("Problems in the app server: the TOKEN wasn't valid");
				return new ResponseEntity<>("INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>("INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}