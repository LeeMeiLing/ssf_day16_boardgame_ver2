package sg.edu.nus.iss.ssf_workshop16ver2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssf_workshop16ver2.repositories.BoardGameRepo;

@SpringBootApplication
public class SsfWorkshop16ver2Application implements CommandLineRunner{

	@Autowired
	private BoardGameRepo bgRepo;

	public static void main(String[] args) {
		SpringApplication.run(SsfWorkshop16ver2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// read game.json file and save to redis
	
		File jsonFile = new File("src\\main\\resources\\static\\bgg\\game.json");
		if (jsonFile.exists()) {

			Reader reader = new BufferedReader(new FileReader(jsonFile));
			JsonReader jsonReader= Json.createReader(reader);
			JsonArray jArray = jsonReader.readArray(); // readArray instead of readObject
			JsonObject json;
			for(int i = 0; i < jArray.size(); i++){

				json = jArray.getJsonObject(i);
				bgRepo.insertGame(json);
				
			}

		}else{
			System.out.println("File not found");
		}
	}

}
