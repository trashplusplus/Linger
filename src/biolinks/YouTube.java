package biolinks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.BioLink;
import utils.ColorUtils;
import utils.FilterLinks;
import utils.FilterUtils;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class YouTube implements BioLink {
    private final String basic = "youtube.com";
    private String username;
    private String linkToParse;
    private FilterLinks filterLinks;
    private boolean enabled = true;

    public YouTube(String username, FilterLinks filterLinks){
        this.username = username;
        this.filterLinks = filterLinks;
    }

    public void switchEnabled(){
        enabled = !enabled;
    }




    @Override
    public void parseOwnLink(boolean isOpen) {
        try{
            Desktop desktop = Desktop.getDesktop();
            Document doc = Jsoup.connect(linkToParse).get();
            Element scriptElement = doc.select("script[type=application/ld+json]").first();

            if (scriptElement != null) {
                // Получаем текст JSON
                String jsonText = scriptElement.html();

                // Парсим JSON
                JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonObject();

                // Переходим к нужному объекту itemListElement
                JsonArray itemList = jsonObject.getAsJsonArray("itemListElement");
                if (itemList != null && itemList.size() > 0) {
                    JsonObject firstItem = itemList.get(0).getAsJsonObject();
                    JsonObject item = firstItem.getAsJsonObject("item");

                    // Получаем значение поля "name"
                    String name = item.get("name").getAsString();
                    System.out.println(ColorUtils.RED + String.format("[%s]: %s", "YouTube", name) + ColorUtils.RESET);
                } else {
                   // System.out.println("Элемент itemListElement не найден или пуст.");
                }
            } else {
                //System.out.println("Скрипт с типом application/ld+json не найден.");
            }



            String jsonText = scriptElement.data();
            JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonObject();
            JsonArray socialLinks = jsonObject.getAsJsonArray("sameAs");

            //System.out.println(scriptElement);


            /*
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
             */

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
