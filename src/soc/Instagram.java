package soc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Link;

public class Instagram implements Link {

    private final String basic = "https://www.instagram.com/";

    public String getBasic() {
        return basic;
    }
    private boolean enable = true;

    private String username;
    private final String prefix = "[inst]: ";

    public Instagram(){

    }

    public void parseDescription(String profile, String username) {
        //Parsing of instagram description, not working now
        String url = profile + username;
        try {
            // Получаем документ страницы
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();
            System.out.println("DOC: " + doc);
            // Ищем тег meta с атрибутом property="og:description"
            Elements metaTag = doc.select("meta[property=og:description]");

            // Проверяем, найден ли мета-тег
            if (metaTag != null) {
                // Извлекаем значение атрибута content
                String instInfo = metaTag.attr("content");
                System.out.println("Описание профиля: " + instInfo);
            } else {
                System.out.println("Мета-тег с property=og:description не найден.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public String open() {
        //parseDescription(basic, username);
        return String.format("%s%s", basic, username);
    }

    @Override
    public String openDirect() {
        //unique
        return open();
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
        //no profile
        return basic;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void switchEnable() {
        this.enable = !enable;
    }

    @Override
    public boolean getEnable() {
        return enable;
    }
}
