package soc;

import utils.Link;

public class Snapchat implements Link {
    private final String basic = "https://www.snapchat.com/explore/";

    public String getBasic() {
        return basic;
    }

    private String username;
    private final String prefix = "[snap]: ";
    private boolean enable = true;

    public Snapchat(){

    }

    @Override
    public String open() {
        return String.format("%s%s", basic, username);
    }

    @Override
    public String openDirect() {
        return open();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setUsername(String username) {
    this.username = username;
    }

    @Override
    public String getProfile() {
        return basic;
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
