package soc;

import utils.ColorUtils;
import utils.Link;


public class Twitter implements Link {

    private final String basic = "https://twitter.com/search?q=";
    private final String userFilter = "&f=user";
    private final String profile = "https://twitter.com/";
    private final String prefix = "[x]: ";
    private String username;


    public Twitter(){

    }


    @Override
    public String open() {

        return String.format("%s%s%s", basic, username, userFilter);
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
