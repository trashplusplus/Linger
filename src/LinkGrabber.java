
import soc.*;
import utils.BioLink;
import utils.ColorUtils;
import utils.Link;
import utils.LinkFilterChain;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LinkGrabber {
    private static final String TT_NOT_FOUND = "tt is not found";
    private final String VERSION = "1.1.3";
    private boolean isStarted = true;
    private int creatorCounter = 0;
    private int creatorGoal = 20;

    //settings
    private boolean openMode = true;
    private boolean clipboardMode = true;

    private Scanner textInput;

    //socs
    private TikTok tikTok;
    private YouTube youTube = new YouTube();
    private Instagram instagram = new Instagram();
    private Twitter twitter = new Twitter();
    private Facebook facebook = new Facebook();
    private CreatorIQ creatorIQ = new CreatorIQ();

    private ArrayList<Link> options = new ArrayList<>();
    private String username = "";

    private Clipboard clipboard;
    private StringSelection selection;

    public LinkGrabber(String username, LinkFilterChain filterChain) throws FileNotFoundException {
        textInput = new Scanner(System.in);
        tikTok = new TikTok(filterChain);
        addOption(tikTok);
        addOption(creatorIQ);
        addOption(youTube);
        addOption(instagram);
        addOption(twitter);
        addOption(facebook);

        this.username = username;
        isStarted = true;

    }

    public void init(){
        for(Link link: options){
            link.setUsername(username);
        }
        //openAllLinks();
    }

    public String getVersion(){
        return VERSION;
    }


    public void switchOpenMode(){
        openMode = !openMode;
        System.out.println(ColorUtils.BLUE + "OpenMode: " + openMode + ColorUtils.RESET);
    }
    public void setCreatorGoal(int creatorGoal){

            this.creatorGoal = creatorGoal;
            if(creatorGoal == 777){
                System.out.println(ColorUtils.RED + "[Easter Egg Manager \uD83E\uDD5A]: Wish you luck dear user! " + ColorUtils.RESET);
            }
            System.out.println(ColorUtils.YELLOW + "New Goal: " + creatorGoal + ColorUtils.RESET);

    }

    public void switchClipboardMode(){
        clipboardMode = !clipboardMode;
        System.out.println(ColorUtils.BLUE + "ClipboardMode: " + clipboardMode + ColorUtils.RESET);
    }



    public boolean isOpenMode(){
        return openMode;
    }


    public void openAllLinks(){
        try {
            Desktop desktop = Desktop.getDesktop();

            for (Link link: options) {

                desktop.browse(new URI(link.open()));


                //additional link for found instagram link
                if(link instanceof TikTok && ((TikTok) link).getExpectedInstagram() != ""){
                    desktop.browse(new URI(instagram.getBasic() + ((TikTok) link).getExpectedInstagram()));
                    ((TikTok) link).setExpectedInstagram("");
                }

                Thread.sleep(900);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Error]: idk what the fuck it is | -_(O_o)_- ");
        }
        creatorIQ.setSocialAccountId("");
    }

    public TikTok getTiktokInstance(){
        return tikTok;
    }


    public void shutdown(){
       // isStarted = false;
        System.out.println(String.format("[%s...]", ColorUtils.YELLOW + "stopping" + ColorUtils.RESET));

    }

    public boolean isTrue(){
        return isStarted;
    }

    private void addOption(Link link){
        options.add(link);
    }

    public void showAll(){
        StringBuffer links = new StringBuffer();

        for(Link link: options){
            if(!(link instanceof TikTok)) {
                links.append(link.getPrefix() + link.open() + "\n");
            }
        }
        links.append("======================");
        System.out.println(links);
    }

    public void showUsernameProgress(){
        System.out.println(String.format("[%s] loading...", ColorUtils.YELLOW + username + ColorUtils.RESET));
    }

    public void setUsername(String username){
        this.username = username;
    }



    public boolean isExist(String profile, String username) {

        boolean result = true;


        try {
            URL urlToCheck = new URL(profile + username);
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = (HttpURLConnection) urlToCheck.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_NOT_FOUND){
                result = false;
            }
        }catch (Exception e){
            //e.printStackTrace();
            System.err.println("Something went wrong {>_<}");
        }

        return result;
    }

    private void setClipboard(String url){
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        selection = new StringSelection(url);
        clipboard.setContents(selection, null);
        System.out.println(ColorUtils.YELLOW + "[TikTok copied to clipboard]" + ColorUtils.RESET);
    }

    public String getBanner(){
        StringBuffer sb = new StringBuffer();
        sb.append(ColorUtils.RED + "================================================" + ColorUtils.RESET);
        sb.append(ColorUtils.RED +"\n ██▓     ██▓ ███▄    █   ▄████ ▓█████  ██▀███  \n" +
                "▓██▒    ▓██▒ ██ ▀█   █  ██▒ ▀█▒▓█   ▀ ▓██ ▒ ██▒\n" +
                "▒██░    ▒██▒▓██  ▀█ ██▒▒██░▄▄▄░▒███   ▓██ ░▄█ ▒\n" +
                "▒██░    ░██░▓██▒  ▐▌██▒░▓█  ██▓▒▓█  ▄ ▒██▀▀█▄  \n" +
                "░██████▒░██░▒██░   ▓██░░▒▓███▀▒░▒████▒░██▓ ▒██▒\n" +
                "░ ▒░▓  ░░▓  ░ ▒░   ▒ ▒  ░▒   ▒ ░░ ▒░ ░░ ▒▓ ░▒▓░\n" +
                "░ ░ ▒  ░ ▒ ░░ ░░   ░ ▒░  ░   ░  ░ ░  ░  ░▒ ░ ▒░\n" +
                "  ░ ░    ▒ ░   ░   ░ ░ ░ ░   ░    ░     ░░   ░ \n" +
                "    ░  ░ ░           ░       ░    ░  ░   ░     \n");
        sb.append(ColorUtils.RED + "CreatorIQ Data Cleanup               V. " + getVersion() + ColorUtils.RESET + "\n");
        sb.append(ColorUtils.RED + "================================================" + ColorUtils.RESET);

        return sb.toString();
    }

    public String getBannerSinger(){
        StringBuffer sb = new StringBuffer();
        sb.append(ColorUtils.BLUE + "================================================" + ColorUtils.RESET);
        sb.append(ColorUtils.BLUE + "\n ██████  ██▓ ███▄    █   ▄████ ▓█████  ██▀███  \n" +
                "▒██    ▒ ▓██▒ ██ ▀█   █  ██▒ ▀█▒▓█   ▀ ▓██ ▒ ██▒\n" +
                "░ ▓██▄   ▒██▒▓██  ▀█ ██▒▒██░▄▄▄░▒███   ▓██ ░▄█ ▒\n" +
                "  ▒   ██▒░██░▓██▒  ▐▌██▒░▓█  ██▓▒▓█  ▄ ▒██▀▀█▄  \n" +
                "▒██████▒▒░██░▒██░   ▓██░░▒▓███▀▒░▒████▒░██▓ ▒██▒\n" +
                "▒ ▒▓▒ ▒ ░░▓  ░ ▒░   ▒ ▒  ░▒   ▒ ░░ ▒░ ░░ ▒▓ ░▒▓░\n" +
                "░ ░▒  ░ ░ ▒ ░░ ░░   ░ ▒░  ░   ░  ░ ░  ░  ░▒ ░ ▒░\n" +
                "░  ░  ░   ▒ ░   ░   ░ ░ ░ ░   ░    ░     ░░   ░ \n" +
                "      ░   ░           ░       ░    ░  ░   ░     \n");
        sb.append(ColorUtils.BLUE + "CreatorIQ Data Cleanup               V. " + getVersion() + ColorUtils.RESET + "\n");
        sb.append(ColorUtils.BLUE + "================================================" + ColorUtils.RESET);
        return sb.toString();
    }

    public void search(){
        while(isTrue()) {
            System.out.println(String.format("[" + ColorUtils.GREEN + "%s/%s" + ColorUtils.RESET + "]", creatorCounter, creatorGoal));
            creatorCounter++;
            if (creatorCounter > creatorGoal) {
                System.out.println(ColorUtils.GREEN + "Goal Achieved!" + ColorUtils.RESET);
                creatorGoal+=5;
            }
            System.out.println(ColorUtils.MAGENTA + "[username]: " + ColorUtils.RESET);


            String input = textInput.nextLine();
            if(input.startsWith("https://") || input.startsWith("http://")){
                boolean isFound = false;
                for(BioLink bioLink: tikTok.getFilterChain().getBiolinks()){
                    if(input.contains(bioLink.getBasic())) {
                        System.out.println(ColorUtils.MAGENTA + "[link]: " + ColorUtils.MAGENTA + bioLink.getBasic());
                        bioLink.setLinkToParse(input);
                        bioLink.parseOwnLink(openMode);
                        isFound = true;
                    }
                }
                if(!isFound){
                    System.out.println(ColorUtils.RED + "[Link isn't belong to filter] " + ColorUtils.RESET);
                }
            }else {


                setUsername(input);
                init();
                showUsernameProgress();

                if (openMode) {
                    openAllLinks();
                }

                if (clipboardMode) {
                    //here always true, fix this and refactor in future
                    if (isExist(tikTok.getProfile(), tikTok.getUsername())) {
                        //fix here
                        //weak stats detector
                        setClipboard(tikTok.openDirect());
                    } else {
                        //never happened
                        setClipboard(TT_NOT_FOUND);
                    }
                }

                //showUsernameProgress();
                if (!openMode) {
                    //showAll();
                    tikTok.parseDescription(tikTok.getProfile(), tikTok.getUsername(), true);
                    tikTok.parseBioLink(tikTok.getProfile() + tikTok.getUsername(), tikTok.getFilterChain(), tikTok.getUsername(), false);
                }
            }
        }
    }

    public String latestUpdates(){
        StringBuffer updates = new StringBuffer();
        updates.append("\n");
        updates.append("- Bypass blocking of beacons.ai");
        updates.append("- Possibility to scrap biolinks");
        //updates.append("- New Mode: 404 Scrapper\n");
        //updates.append("- New Mode: Bio Scrapper\n");

        return updates.toString();

    }

}
