package sg.edu.nus.iss.ssf_workshop16ver2.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssf_workshop16ver2.models.InsertResponse;
import sg.edu.nus.iss.ssf_workshop16ver2.services.BoardGameService;

@RestController
@RequestMapping(path="/api/boardgame", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {

    @Autowired
    private BoardGameService bgSvc;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insert(@RequestBody String payload){

        // convert payload into JsonObject
        JsonReader jReader = Json.createReader(new StringReader(payload));
        JsonObject json = jReader.readObject();

        // // read payload into game entity
        // BoardGame bg = BoardGame.createBoardGame(payload);

        // insert into database
        bgSvc.insertGame(json);
        
        // generate response { “insert_count”: 1, “id”: <Redis key> }
        JsonObject response = InsertResponse.getResponse(json.getInt("gid"));

        // return response
        return ResponseEntity.status(201).body(response.toString());

    }
}
