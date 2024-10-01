package soc;


import dao.EmailDAO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import utils.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
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
    private String prefix = "[tiktok]: ";
    private String followersDesc;
    private String username;
    //private WebClient.Builder builder;
    private Document doc;
    private Element metaDesc;
    private boolean failureAcc;
    LinkFilterChain filterChain;
    private String cookie;
    private boolean enable = true;


    public TikTok(LinkFilterChain filterChain, String cookie){
        //builder = WebClient.builder();
        this.filterChain = filterChain;
        this.cookie = cookie;
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver\\chromedriver-win64\\chromedriver.exe");

    }

    public void setCookie(String cookie){
        this.cookie = cookie;
    }


    public boolean getFailureAcc(){
        return failureAcc;
    }

    public void parseDescription(String profile, String username, boolean isPrint){
        String url = profile + username;
        String notFound = "[Tiktok]: Account is not found";
        failureAcc = false;
        try {
            Document doc = Jsoup.connect(url).get();

            if(!cookie.isEmpty()){
                doc = Jsoup.connect(url).header("Cookie", cookie).get();
                String shortCookie;
                if(cookie.length() > 64){
                    shortCookie = cookie.substring(0, 64);
                }else{
                    shortCookie = cookie.substring(0, cookie.length() / 2);
                }

                System.out.println(ColorUtils.CYAN + "[Cookie]: " + shortCookie + "..." + ColorUtils.RESET);
            }



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
                //james fix todo
                description = descriptionMatcher.group(1).replace("\\n", " ");
                description = description.replace("\\u002F", "/");
            }

            if(description.isEmpty()){
                description = "No Description.";
            }

                if(nickname != "") {
                    if(isPrint) {
                        System.out.println(ColorUtils.YELLOW + "================================");

                        System.out.println("Name: " + nickname);

                        if(isFamousRegex(nickname)){
                            parseBirthday(nickname);
                        }

                        System.out.println("Followers: " + followerCount);
                        System.out.println("Description: " + description);

                        // Проверяем наличие ключевых слов
                        String extractedInst = removeDoubleDog(extractInstagramUsername(description));
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
        //Pattern pattern = Pattern.compile("(?i)(?<!\\w)(IG:?|inst:|instagram:|instagram|insta:)\\s*([^\\s]+)|(?<=[\\s:>)])@([^\\s]+)");
        Pattern pattern = Pattern.compile("(?i)(?<!\\w)(IG:?|inst:|instagram:|instagram|insta:)[\\s\\/\\\\]*([^\\s\\/\\\\]+)|(?<=[\\s:>)])@([^\\s\\/\\\\]+)\n");
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

    private String removeDoubleDog(String input){
        return input.replace("@", "");
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

    public String noParseOpen(){
        return String.format("%s%s", basic, username);
    }
    //famousbirthdays scrapping
    public void parseBirthday(String name){
        //create link to scrap
        String url = "https://www.famousbirthdays.com/people/";
        String[] name_surname = name.split(" ");
        url += name_surname[0].toLowerCase(Locale.ROOT) + "-" + name_surname[1].toLowerCase(Locale.ROOT) + ".html";

        //scrapping age and birthdate
        try{
            Document doc = Jsoup.connect(url).get();
            Element spanBirthday = doc.select("div.bio-module__person-attributes").first();
            StringBuilder result = new StringBuilder();
            if(spanBirthday != null) {
                String birthdayText = spanBirthday.text();
                System.out.println(ColorUtils.CYAN + "[Warning]: Don't use this info for CreatorIQ" + ColorUtils.RESET);
                System.out.println(ColorUtils.BLUE + "Additional Info: " + "[" + birthdayText + "]" + ColorUtils.YELLOW);
            }


        }catch (Exception e){
            System.out.println("Additional Info: Not Found");
            //e.printStackTrace();
        }


    }

    public boolean isFamousRegex(String name){
        String regex = "^[A-Z][a-zA-Z']* [A-Z][a-zA-Z']*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()){
            return true;
        }
        return false;
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

    @Override
    public void switchEnable() {
        this.enable = !enable;
    }

    @Override
    public boolean getEnable() {
        return enable;
    }


}
