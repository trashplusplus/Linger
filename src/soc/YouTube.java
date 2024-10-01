package soc;

import utils.ColorUtils;
import utils.Link;



public class YouTube implements Link {

    private final String basic = "https://www.youtube.com/results?search_query=";
    private final String userFilter = "&sp=EgIQAg%253D%253D";
    private final String profile = "https://www.youtube.com/@";
    private String username;
    private String prefix = "[yt]: ";
    private boolean enable = true;

    public YouTube(){

    }



    @Override
    public String open() {
        return String.format("%s%s%s",basic, username, userFilter);
    }

    @Override
    public String openDirect() {
        return String.format("%s%s",profile, username);
    }

    @Override
    public String getPrefix() {
        return ColorUtils.RED + prefix + ColorUtils.RESET;
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
