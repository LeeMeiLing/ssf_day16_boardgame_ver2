package sg.edu.nus.iss.ssf_workshop16ver2.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class BoardGame {

    // "gid":1,
    // "name":"Die Macher",
    // "year":1986,
    // "ranking":223,
    // "users_rated":4777,
    // "url":"https://www.boardgamegeek.com/boardgame/1/die-macher",
    // "image":"https://cf.geekdo-images.com/micro/img/JFMB8ORpxWo-VfPrEePfMluBkGs=/fit-in/64x64/pic159509.jpg"

    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;

    public static BoardGame createBoardGame(String payload) {

        // convert payload into JsonObject
        JsonReader jReader = Json.createReader(new StringReader(payload));
        JsonObject json = jReader.readObject();

        // instantiate a baordgame
        BoardGame bg = new BoardGame();

        // extract json object & populate each field of boardgame
        bg.setGid(json.getInt("gid"));
        bg.setName(json.getString("name"));
        bg.setYear(json.getInt("year"));
        bg.setRanking(json.getInt("ranking"));
        bg.setUsers_rated(json.getInt("users_rated"));
        bg.setUrl(json.getString("url"));
        bg.setImage(json.getString("image"));

        return bg;

    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsers_rated() {
        return users_rated;
    }

    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
