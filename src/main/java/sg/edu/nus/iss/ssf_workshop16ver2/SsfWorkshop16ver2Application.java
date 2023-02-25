package sg.edu.nus.iss.ssf_workshop16ver2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private Logger logger = Logger.getLogger(SsfWorkshop16ver2Application.class.getName());

	@Autowired
	private BoardGameRepo bgRepo;

	public static void main(String[] args) {
		SpringApplication.run(SsfWorkshop16ver2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// read game.json file and save to redis
	
		File jsonFile = new File("./src/main/resources/static/bgg/game.json"); // do not use \\ if deploy to Railway !!!!!

		String path = jsonFile.toPath().toAbsolutePath().toString();
		logger.log(Level.INFO, "++++++++++++++++ path is: " + path);

		if (jsonFile.exists()) {

			logger.log(Level.INFO, "++++++++++ file exist!!!!!!");

			Reader reader = new BufferedReader(new FileReader(jsonFile));
			JsonReader jsonReader= Json.createReader(reader);
			JsonArray jArray = jsonReader.readArray(); // readArray instead of readObject
			JsonObject json;
			for(int i = 0; i < jArray.size(); i++){

				json = jArray.getJsonObject(i);
				bgRepo.insertGame(json);
			}

			logger.log(Level.INFO, "++++++++++ game saved to Redis !!!!!!");

		}else{
			logger.log(Level.INFO, "++++++++++ File not found !!!!!!");
		}
	}

}
