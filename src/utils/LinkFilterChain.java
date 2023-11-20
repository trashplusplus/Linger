package utils;

import biolinks.Beacons;
import biolinks.Linktree;

import java.util.ArrayList;

public class LinkFilterChain{

    private ArrayList<BioLink> bioLinks;
    private String username;
    private FilterLinks filterLinks;


    public LinkFilterChain(String username, FilterLinks filterLinks){
        this.username = username;
        this.filterLinks = filterLinks;
        bioLinks = new ArrayList<>();
        bioLinks.add(new Linktree(username, filterLinks));
        bioLinks.add(new Beacons(username, filterLinks));

        //TODO
        //biolinks.add(new Heyname(username));
    }

    public FilterLinks getFilterLinks(){
        return filterLinks;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public ArrayList<BioLink> getBiolinks(){
        return bioLinks;
    }


}
