package sg.edu.nus.iss.ssf_workshop16ver2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.ssf_workshop16ver2.repositories.BoardGameRepo;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepo bgRepo;

    public Boolean insertGame(JsonObject json){

        return bgRepo.insertGame(json);
        
    }

}
