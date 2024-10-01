package soc;

import utils.ColorUtils;
import utils.Link;



public class Facebook implements Link {

    private final String basic = "https://www.facebook.com/search/top?q=";
    private final String profile = "https://www.facebook.com/";
    private String username;
    private final String prefix = "[fb]: ";
    private boolean enable = true;
    public Facebook(){

    }


    @Override
    public String open() {

        return String.format("%s%s", basic, username);

    }

    @Override
    public String openDirect() {
        return String.format("%s%s", profile, username);
    }

    @Override
    public String getPrefix() {
        return ColorUtils.BLUE + prefix + ColorUtils.RESET;
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
