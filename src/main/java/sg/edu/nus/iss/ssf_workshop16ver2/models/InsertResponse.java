package sg.edu.nus.iss.ssf_workshop16ver2.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class InsertResponse {
    
    private static Integer insert_count = 1;
    private String id;

    // { “insert_count”: 1, “id”: <Redis key> }
    public static JsonObject getResponse(Integer gid){

        JsonObject json = Json.createObjectBuilder()
			.add("insert_count", insert_count)
			.add("id", gid.toString())
			.build();
            
        return json;
    }

    public Integer getInsert_count() {
        return insert_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
