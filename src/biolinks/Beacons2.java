package biolinks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import utils.BioLink;
import utils.ColorUtils;
import utils.FilterLinks;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class Beacons2 implements BioLink {

    private final String basic = "beacons.page";
    private String username;
    private String linkToParse;
    private FilterLinks filterLinks;


    public Beacons2(String username, FilterLinks filterLinks){
        this.username = username;
        this.linkToParse = "";
        this.filterLinks = filterLinks;
    }

    @Override
    public void parseOwnLink(boolean isOpen) {
        String userAgent = "Chrome";
        try{
            Desktop desktop = Desktop.getDesktop();
            Document doc = Jsoup.connect(linkToParse).userAgent(userAgent)
                    .header("authority", "www.google.com")
                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("accept-language", "en-US,en;q=0.9")
                    .header("cache-control", "max-age=0")
                    .header("Cookie", "__cf_bm=IJdSa.j6KUaE5HiDpLNuk7_xdCPk.O7z83MSYhY7QMc-1699721618-0-AbC4fVExDo1dQBOIOZLErHT0uMtN5fXzdto8ax82cBPRRuHhr0XYQjqGdbkHOOoarmm2chrAhIDFgPxNrp6DWbo=")
                    .get();
            Element scriptElement = doc.select("script[type=application/ld+json]").first();
            String jsonText = scriptElement.data();
            JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonObject();
            JsonArray socialLinks = jsonObject.getAsJsonArray("sameAs");

            for(int i = 0; i < socialLinks.size(); i++){
                String url = socialLinks.get(i).getAsString();
                for(String socialMediaLink: filterLinks.getAll()){
                    if(url.contains(socialMediaLink)){

                        System.out.println(String.format("[%s]: %s", ColorUtils.RED + socialMediaLink + ColorUtils.RESET, url));

                        if(isOpen) {
                            desktop.browse(new URI(url));
                        }
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setLinkToParse(String linkToParse) {
        this.linkToParse = linkToParse;
    }

    @Override
    public String getBasic() {
        return basic;
    }

    @Override
    public List<String> getAllLinks() {
        return null;
    }


}
