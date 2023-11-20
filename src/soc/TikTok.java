package soc;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import dao.EmailDAO;
import dao.LinkDAO;
import jdk.jshell.spi.ExecutionControl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.reactive.function.client.WebClient;
import utils.*;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TikTok implements Link {

    private String expectedInstagram = "";

    public void setExpectedInstagram(String expectedInstagram) {
        this.expectedInstagram = expectedInstagram;
    }

    public String getExpectedInstagram() {
        return expectedInstagram;
    }



    private final String basic = "https://www.tiktok.com/search/user?q=";
    private final String profile = "https://tiktok.com/@";
    private final String prefix = "[tiktok]: ";
    private String followersDesc;
    private String username;
    //private WebClient.Builder builder;
    private Document doc;
    private Element metaDesc;
    private boolean failureAcc;
    LinkFilterChain filterChain;


    public TikTok(LinkFilterChain filterChain){
        //builder = WebClient.builder();
        this.filterChain = filterChain;
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver\\chromedriver-win64\\chromedriver.exe");

    }

    public boolean getFailureAcc(){
        return failureAcc;
    }

    public void parseDescription(String profile, String username, boolean isPrint){
        String url = profile + username;
        String notFound = "[Linger]: Account is not found";
        failureAcc = false;
        try {
            Document doc = Jsoup.connect(url).get();
            Element metaDescription = doc.select("script[type=application/json]").last();
            String description = "No Description.";
            String followerCount = "";
            String nickname = "";
            String jsonText = metaDescription.data();
            //nickname
            Pattern nicknamePattern = Pattern.compile("\"nickname\":\"(.*?)\"");
            Matcher nicknameMatcher = nicknamePattern.matcher(jsonText);
            //desc
            Pattern descriptionPattern = Pattern.compile("\"signature\":\"(.*?)\"");
            Matcher descriptionMatcher = descriptionPattern.matcher(jsonText);
            //followers
            Pattern followerPattern = Pattern.compile("\"followerCount\":\\s*(\\d+)");
            Matcher followerMatcher = followerPattern.matcher(jsonText);

            if(nicknameMatcher.find()) {
                nickname = nicknameMatcher.group(1);
            }

            if(followerMatcher.find()) {
                followerCount = followerMatcher.group(1);
            }

            if(descriptionMatcher.find()) {
                description = descriptionMatcher.group(1).replace("\\n", "");
            }

            if(description.isEmpty()){
                description = "No Description.";
            }

                if(nickname != "") {
                    if(isPrint) {
                        System.out.println(ColorUtils.YELLOW + "================================");

                        System.out.println("Name: " + nickname);
                        System.out.println("Followers: " + followerCount);
                        System.out.println("Description: " + description);

                        // Проверяем наличие ключевых слов
                        String extractedInst = extractInstagramUsername(description);
                        if (extractedInst != "") {
                            System.out.println(ColorUtils.CYAN + "Instagram: @" + extractedInst + ColorUtils.RESET);
                            expectedInstagram = extractedInst;
                        }
                        System.out.println(ColorUtils.YELLOW + "================================" + ColorUtils.RESET);
                    }
                }else {
                    if(isPrint) {
                        System.out.println(ColorUtils.RED + notFound + ColorUtils.RESET);
                    }
                    failureAcc = true;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String parseAndGetSearchUser(String url){
        String result = "";
        System.out.println(String.format("[%sLinger%s]: searching...", ColorUtils.RED, ColorUtils.RESET));
        System.out.println("url: " + url);

        try{
            Document document = Jsoup.connect(url).get();
            Element firstUser = document.getElementById("search_user-item-user-link-0");
            if(firstUser != null){

                String href = firstUser.attr("href");
                result = firstUser.text();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            return result;
        }


    }


    //TODO
    //1. parsing link
    //2. create filter
    //3. add to filter, such as linktree, beacons, heylink, barcodi

    public LinkFilterChain getFilterChain(){
        return filterChain;
    }

    public void parseBioLink(String tiktokUrl, LinkFilterChain filterChain, String creator, boolean isOpen){
        //detecting linktr.ee it the Tiktok.com
        try{
            Desktop desktop = Desktop.getDesktop();
            Document doc = Jsoup.connect(tiktokUrl).get();





            Element metaDescription = doc.select("script[type=application/json]").last();
            String jsonText = metaDescription.data();

            Pattern bioPattern = Pattern.compile("\"bioLink\":\\{\"link\":\"(.*?)\"");
            Matcher bioMatcher = bioPattern.matcher(jsonText);



            if(bioMatcher.find()){
                String href = bioMatcher.group(1);
                String formatLink = decodeAndFormatLink(href);
                //System.out.println("href: " + formatLink);

                for(BioLink link: filterChain.getBiolinks()){
                    if (formatLink.contains(link.getBasic())) {
                        System.out.println(ColorUtils.MAGENTA + "[Link]: " + ColorUtils.MAGENTA + formatLink );
                        link.setLinkToParse(formatLink);
                        //System.out.println(ColorUtils.BLUE + "[" + link.getBasic() + "] processing..." + ColorUtils.RESET);
                        link.parseOwnLink(isOpen);
                        //LinkDAO.saveLink(creator, extractedLinkFromHref);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String decodeAndFormatLink(String input) {
        // Проверяем, начинается ли текст с "href:"
        if (input.startsWith("href: ")) {
            // Убираем "href:" из текста
            input = input.substring(6);
        }

        // Декодируем текст, заменяя \\uXXXX на соответствующие символы
        try {
            input = URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        // Убираем кодирование слеша и заменяем его на обычный слеш
        input = input.replace("\\u002F", "/").toLowerCase();

        // Проверяем, начинается ли текст с "http://" или "https://", и если нет, добавляем "https://"
        if (!input.startsWith("http://") && !input.startsWith("https://")) {
            input = "https://" + input;
        }

        return input;
    }



    private void parseEmail(String content){
        String emailRegex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}\\b";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(content);

        while(emailMatcher.find()){
            System.out.println(ColorUtils.CYAN + "[Email Detector]: " + emailMatcher.group() + ColorUtils.RESET);
            EmailDAO.saveEmailToDatabase(emailMatcher.group());
            //Sender.sendTelegramMessage("-", "501446180", emailMatcher.group());
        }
    }


    private String extractInstagramUsername(String text) {
        Pattern pattern = Pattern.compile("(?i)(?<!\\w)(IG:?|inst:|instagram:)\\s*([^\\s]+)|(?<=[\\s:>)])@([^\\s]+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String username1 = matcher.group(2);
            String username2 = matcher.group(3);

            if (username1 != null) {
                username1 = removeTrailingDot(username1);
                return username1;
            } else if (username2 != null) {
                username2 = removeTrailingDot(username2);
                return username2;
            }
        }
        return "";
    }

    private String removeTrailingDot(String username) {
        // Проверка наличия точки в конце имени пользователя и ее удаление
        if (username.endsWith(".")) {
            return username.substring(0, username.length() - 1);
        }
        return username;
    }


    @Override
    public String open() {
        //possibility to connect gptAPI, and extracting info
        parseDescription(profile, username, true);
        parseBioLink(profile + username, filterChain, username, true);
        return String.format("%s%s", basic, username);
    }

    @Override
    public String openDirect() {
        return String.format("%s%s", profile, username);
    }

    @Override
    public String getPrefix() {
        return ColorUtils.BLACK + prefix + ColorUtils.RESET;
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
