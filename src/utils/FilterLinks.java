package utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FilterLinks {

    //all possible links
    //here you can add filter
    //TODO
    //1. Create external txt to fill it with filters


    private ArrayList<String> filterLinks;

    public FilterLinks(){
        filterLinks = new ArrayList<>();
        filterLinks.add("youtube.com");
        filterLinks.add("instagram.com");
        filterLinks.add("twitter.com");
        filterLinks.add("facebook.com");
        //filterLinks.add("tiktok.com");
    }

    public ArrayList<String> getAll(){
        return filterLinks;
    }
}
