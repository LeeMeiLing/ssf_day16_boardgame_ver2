package sg.edu.nus.iss.ssf_workshop16ver2.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // GET /api/boardgame/<boardgame id>
    // return JSON or 404 with error object
    @GetMapping("/{gid}")
    public ResponseEntity<String> findGameById(@PathVariable String gid){

        Optional<String> opt = bgSvc.findGameById(gid);

        if ( opt.isPresent() ){
            return ResponseEntity.ok().body(opt.toString());
        } else {
            
            // create an error object
            JsonObject error = Json.createObjectBuilder()
                                    .add("error code", "404")
                                    .add("message","Game not found")
                                    .build();
            
            return ResponseEntity.status(404).body(error.toString());
        }
    
    }




}