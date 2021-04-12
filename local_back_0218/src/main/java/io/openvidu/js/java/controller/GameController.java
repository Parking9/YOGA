package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.openvidu.js.java.service.GameService;

@CrossOrigin("*")
@RestController
@RequestMapping("/games")
public class GameController {

   @Autowired
   private GameService gameservice;
   
   @GetMapping("/liar")
   public ResponseEntity<String> liarGame () {
      int id = (int) (Math.random() * 21) + 1;
      String liar = gameservice.liarGame(id);
      return new ResponseEntity<String>(liar, HttpStatus.OK);
   }
   
   @GetMapping("/recommend")
   public ResponseEntity<String> recommendgame () {
      int id = (int) (Math.random() * 10) + 1;
      String rec = gameservice.recommend(id);
      return new ResponseEntity<String>(rec, HttpStatus.OK);
   }
   
   @GetMapping("/topic/{level}")
   public ResponseEntity<String> topicGame (@PathVariable int level) {

      Map<String, Object> hmap = new HashMap<String, Object>();
      hmap.put("level", level);
      if(level==0) {         
         hmap.put("id", (int) (Math.random() * 5) + 1);
      }else {
         hmap.put("id", (int) (Math.random() * 5) + 6);
      }
      String topic = gameservice.topicGame(hmap);
      
      return new ResponseEntity<String>(topic, HttpStatus.OK);
   }


   
}