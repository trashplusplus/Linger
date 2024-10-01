
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static LinkGrabber linkGrabber;
    private static UserScanner userScanner;
    private static int select = 0;
    private static Scanner scanner;
    private static boolean isStarted = true;
    private static File file = new File("");
    private static File out = new File("");
    private static LinkFilterChain filterChain;
    private static FilterLinks filterLinks;
    public static void main(String args[]) throws FileNotFoundException {

        filterLinks = new FilterLinks();
        filterChain = new LinkFilterChain("", filterLinks);
      linkGrabber = new LinkGrabber("", filterChain);


        if(args.length == 0){
        System.out.println(linkGrabber.getBanner());
        System.out.println(ColorUtils.YELLOW + "Default clipboard link: " + linkGrabber.getCustomClipboard() + ColorUtils.RESET);

        scanner = new Scanner(System.in);

        Menu mainMenu = new Menu();
        mainMenu.addItem(1,"Search", search -> linkGrabber.search());

        mainMenu.addItem(2,"Settings",
                new SubMenuItem(1, "OpenMode",
                        "[OpenMode automatically opens links in the browser]" + ColorUtils.RESET, f -> linkGrabber.switchOpenMode()),
                new SubMenuItem(2, "ClipboardMode",
                        "[ClipboardMode copies TikTok link]" + ColorUtils.RESET, f -> linkGrabber.switchClipboardMode()),
                new SubMenuItem(3, "Goal",
                        "[Here you can set your goal per hour]" + ColorUtils.RESET, f -> linkGrabber.setCreatorGoal(scanner.nextInt())),
                new SubMenuItem(4, "Switch TikTok", "[TikTok status]: " + !linkGrabber.getTiktokInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getTiktokInstance())),
                new SubMenuItem(5, "Switch Instagram", "[Instagram status]: " + !linkGrabber.getInstagramInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getInstagramInstance())),
                new SubMenuItem(6, "Switch YouTube", "[YouTube status]: " + !linkGrabber.getYouTubeInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getYouTubeInstance())),
                new SubMenuItem(7, "Switch Twitter", "[Twitter status]: " + !linkGrabber.getTwitterInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getTwitterInstance())),
                new SubMenuItem(8, "Switch Facebook", "[Facebook status]: " + !linkGrabber.getFacebookInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getFacebookInstance())),
                new SubMenuItem(9, "Switch Snapchat", "[Snapchat status]: " + !linkGrabber.getSnapchatInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getSnapchatInstance())),
                new SubMenuItem(10, "Switch Twitch", "[Twitch status]: " + !linkGrabber.getTwitchInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getTwitchInstance())),
                new SubMenuItem(11, "Switch Pinterest", "[Pinterest status]: " + !linkGrabber.getPinterestInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getPinterestInstance())),
                new SubMenuItem(12, "Switch CreatorIQ", "[CreatorIQ status]: " + !linkGrabber.getCreatorIQInstance().getEnable(), f -> linkGrabber.switchEnable(linkGrabber.getCreatorIQInstance()))
        );

        mainMenu.addItem(3,"Info",
                new SubMenuItem(1,"Author", "Created by @trashplusplus/0x3\nhttps://github.com/trashplusplus"),
               // new SubMenuItem(2,"Version", "Linger version " + linkGrabber.getVersion() + linkGrabber.latestUpdates()),
                new SubMenuItem(2,"How to use", "1. Select [Search] and put username in the field\n2. Linger automatically will find all accounts belongs to placed username")
                );

        //mainMenu.addItem(4,"404 Scrapper", start -> userScanner.start());
        //mainMenu.addItem(5,"Bio Scrapper", start -> userScanner.scanBioLinks());

        mainMenu.addItem(4,"Use Cookie", shutdown -> linkGrabber.setCookieOption());
        mainMenu.addItem(5, "Setup Clipboard", shutdown -> linkGrabber.setCustomClipboard());
        mainMenu.addItem(6,"Shutdown", shutdown -> Main.dropLinger());
        while(isStarted) {
            mainMenu.showItems();
            //1
            try{
                select = scanner.nextInt();
            }catch (Exception e){
                System.out.println(ColorUtils.YELLOW + "Sad Meow :'(" + ColorUtils.RESET);
                e.printStackTrace();
            }
            SubMenu ourSubMenu = null;
            try{
                ourSubMenu = mainMenu.getSubMenu(select);
                Consumer<Void> function = mainMenu.getFunctionMap().get(select);
                if (function != null && ourSubMenu.getFunctionMap() != null) {
                    function.accept(null);
                }

                mainMenu.showSubMenu(select);
                //2
                if(mainMenu.getSubMenu(select).getAllSubItems().size() != 0) {
                    select = scanner.nextInt();
                }else{
                    continue;
                }
                    try {
                        SubMenuItem subItem = ourSubMenu.getSubItem(select);
                        Consumer<Void> function2 = subItem.getCallback();
                        if (function2 != null && subItem != null) {
                            function2.accept(null);
                        }

                        System.out.println(ColorUtils.YELLOW + subItem.getPrompt() + ColorUtils.RESET);

                    } catch (Exception e) {
                        System.out.println(ColorUtils.YELLOW + "WRONG" + ColorUtils.RESET);
                        continue;
                    }

            }catch (Exception e){
                System.out.println(ColorUtils.YELLOW + "Wrong parameter :(" + ColorUtils.RESET);
                //e.printStackTrace();
            }




        }
        }else{
            //args
            for (int i = 0; i < args.length; i++) {
                //System.out.println("Arg[" + i + "]: " + args[i]);
                if (args[i].equals("-singer")) {
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        String filePath = args[i + 1];
                        try {
                            userScanner = new UserScanner(file, out, linkGrabber.getTiktokInstance(), filterChain, filterLinks);
                            userScanner.setFilename(new File(filePath));
                            System.out.println(linkGrabber.getBannerSinger());
                            File input = new File(filePath);
                            File folderPath = input.getParentFile();
                            userScanner.setFileout(new File(folderPath, "singerOut.txt"));
                            userScanner.start();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("After tag -singer you should define .txt with list of creators");
                    }
                }
            }
        }

    }

    public static void dropLinger(){
        linkGrabber.shutdown();
        System.exit(0);

    }
}