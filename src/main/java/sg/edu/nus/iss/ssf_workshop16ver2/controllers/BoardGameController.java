package sg.edu.nus.iss.ssf_workshop16ver2.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssf_workshop16ver2.models.BoardGame;
import sg.edu.nus.iss.ssf_workshop16ver2.models.Response;
import sg.edu.nus.iss.ssf_workshop16ver2.services.BoardGameService;

@RestController
@RequestMapping(path = "/api/boardgame", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {

    @Autowired
    private BoardGameService bgSvc;

    // task 1
    // POST /api/boardgame
    // return 201 status code & { “insert_count”: 1, “id”: <Redis key> }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insert(@RequestBody String payload) {

        // convert payload into JsonObject
        JsonReader jReader = Json.createReader(new StringReader(payload));
        JsonObject json = jReader.readObject();

        // // read payload into game entity
        // BoardGame bg = BoardGame.createBoardGame(payload);

        // insert into database
        bgSvc.insertGame(json);

        // generate response { “insert_count”: 1, “id”: <Redis key> }
        JsonObject response = Response.getResponse(json.getInt("gid"));

        // return response
        return ResponseEntity.status(201).body(response.toString());

    }

    // GET /api/boardgame/<boardgame id>
    // return JSON or 404 with error object
    @GetMapping("/{gid}")
    public ResponseEntity<String> findGameById(@PathVariable String gid) {

        Optional<String> opt = bgSvc.findGameById(gid);

        if (opt.isPresent()) {
            return ResponseEntity.ok().body(opt.get()); // change from opt.toString to opt.get()
        } else {

            // create an error object
            JsonObject error = Json.createObjectBuilder()
                    .add("error code", "404")
                    .add("message", "Game not found")
                    .build();

            return ResponseEntity.status(404).body(error.toString());
        }

    }

    // PUT /api/boardgame/<boardgame id>
    // return 200 status code & { “update_count”: <count>, “id”: <Redis key> }
    // return 400 status code if boardgame not found
    // optional parameter "upsert" if =true, insert if the <boardgame id> does not
    // exist
    // assume "upsert" = false if not specified
    @PutMapping(path = "/{gid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateGame(@PathVariable String gid,
            @RequestParam(defaultValue = "false") String upsert, @RequestBody String payload) {

        System.out.println(">>>>>>>>>>>>>>>>>>>upsert: " + upsert); //debug

        // retrieve the game by Id
        Optional<String> opt = bgSvc.findGameById(gid);
        System.out.println(">>>>>>>>>>opt = " + opt);

        if (opt.isPresent()) {

            // // convert into game object
            // BoardGame.createBoardGame(opt.get());

            // convert payload into JsonObject
            JsonReader jReader = Json.createReader(new StringReader(payload));
            JsonObject json = jReader.readObject();

            // save into database
            bgSvc.insertGame(json);

            // generate response { update_count: <count> , “id”: <Redis key> }
            JsonObject response = Response.putResponse(json.getInt("gid"));

            // return response
            return ResponseEntity.status(200).body(response.toString());

        } else {

            // if upsert == true, insert game
            if (upsert.equalsIgnoreCase("true")) {

                // convert payload into JsonObject
                JsonReader jReader = Json.createReader(new StringReader(payload));
                JsonObject json = jReader.readObject();

                // save into database
                bgSvc.insertGame(json);

                // return getResponse instead of putResponse coz insert is performed
                // generate response { “insert_count”: 1, “id”: <Redis key> }
                JsonObject response = Response.getResponse(json.getInt("gid"));

                // return response
                return ResponseEntity.status(200).body(response.toString());


            // if upsert == false, return error object
            } else {

                // create an error object
                JsonObject error = Json.createObjectBuilder()
                        .add("error code", "400")
                        .add("message", "Game not found, update failed")
                        .build();

                return ResponseEntity.status(400).body(error.toString());

            }

        }

    }

}