package biolinks;

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
import java.util.ArrayList;
import java.util.List;


public class Linktree2 implements BioLink {

    private final String basic = "linktree.com";
    private String username;
    private String linkToParse;
    private FilterLinks filterLinks;

    public Linktree2(String username, FilterLinks filterLinks){
        this.username = username;
        this.linkToParse = "";
        this.filterLinks = filterLinks;


    }

    @Override
    public void setLinkToParse(String linkToParse){
        this.linkToParse = linkToParse;
    }


    @Override
    public void parseOwnLink(boolean isOpen) {

        try{
            Desktop desktop = Desktop.getDesktop();
            Document doc = Jsoup.connect(linkToParse).get();
            Elements elements = doc.select("a");
            System.out.println(String.format("%s==================%s", ColorUtils.RED, ColorUtils.RESET));
            for(Element element: elements){
                //cleaning links from linktree

                String href = element.attr("href");
                if (!FilterUtils.isYouTubeVideoLink(href)) {

                for(String socialMediaLink: filterLinks.getAll()) {
                    if (href.contains(socialMediaLink)) {
                        System.out.println(String.format("[%s]: %s", ColorUtils.RED + socialMediaLink + ColorUtils.RESET, href));
                        if(isOpen) {
                            desktop.browse(new URI(href));
                        }
                      }
                    }
                }

            }
            System.out.println(String.format("%s==================%s", ColorUtils.RED, ColorUtils.RESET));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getAllLinks(){
        List<String> links = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(linkToParse).get();
            Elements elements = doc.select("a");

            for(Element element: elements){
                //cleaning links from linktree
                String href = element.attr("href");
                if (!FilterUtils.isYouTubeVideoLink(href)) {
                    for(String socialMediaLink: filterLinks.getAll()) {
                        if (href.contains(socialMediaLink)) {
                            //System.out.println("Soc Media: " + socialMediaLink);
                            links.add(href);

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return links;
    }




    @Override
    public String getBasic() {
        return basic;
    }


}
