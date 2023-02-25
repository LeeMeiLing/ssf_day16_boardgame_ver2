package sg.edu.nus.iss.ssf_workshop16ver2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.ssf_workshop16ver2.repositories.BoardGameRepo;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepo bgRepo;

    // this method currently used for insert & update game
    public Boolean insertGame(JsonObject json){

        return bgRepo.insertGame(json);
        
    }

    public Optional<String> findGameById(String gid){

        return bgRepo.findGameById(gid);
        
    }

}
