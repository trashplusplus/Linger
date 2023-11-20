package soc;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.Link;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class CreatorIQ implements Link {

    private final String basic = "https://api.creatoriq.com/api/discovery/accountInfo?source=link&link=https://tiktok.com/@";
    private final String profile = "";
    private final String prefix = "[CreatorIQ]: ";
    private String username;
    private String jwtToken;
    private BufferedReader jwtReader;
    private String filename = "C:\\Users\\Admin\\Desktop\\jwt.txt";
    private String socialAccountId;

    public CreatorIQ() {
        /*
        try{
            jwtReader = new BufferedReader(new FileReader(filename));
            jwtToken = jwtReader.readLine();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(jwtReader != null){
                    jwtReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        socialAccountId = "";

         */
    }

    public void parseSocialID(){
        /*
        try{
            //открываем api с токеном
            Connection connection = Jsoup.connect(basic + username);
            connection.header("Cookie", jwtToken);
            Document doc = connection.ignoreContentType(true).get();
            //получаем json
            JsonElement rootElement = JsonParser.parseString(doc.text());
            //парсим ключ href
            String href = rootElement.getAsJsonObject().getAsJsonObject("AccountInfo")
                    .getAsJsonObject("account").get("href").getAsString();
            //извлекаем id
            String[] partsOfUrl = href.split("/");
            this.socialAccountId = partsOfUrl[partsOfUrl.length-1];
            //устанавливаем id
            System.out.println("id: " + socialAccountId);
        }catch (Exception e){
            e.printStackTrace();
        }

         */
    }

    @Override
    public String open() {
        //parseSocialID(); //finding id
        String jsonParameters = String.format("{\"filters\":[{\"field\":\"KeywordSearchBar\",\"value\":\"%s\"}]}", "https://tiktok.com/@" + username);
        String encodedParams = URLEncoder.encode(jsonParameters, StandardCharsets.UTF_8);
        //println for debug
        //System.out.println("https://app.creatoriq.com/#discovery/" + encodedParams);
        return "https://app.creatoriq.com/#discovery/" + encodedParams;

    }

    public void setSocialAccountId(String socialAccountId){
        this.socialAccountId = socialAccountId;
    }

    @Override
    public String openDirect() {
        return null;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getProfile() {
        return profile;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
