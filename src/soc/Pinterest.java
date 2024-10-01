package soc;

import utils.Link;

public class Pinterest implements Link {
    private final String basic = "https://pinterest.com/search/users/?q=";
    private final String tag = "&rs=typed";
    private String username;
    private final String prefix = "[pint]: ";
    private boolean enable = true;

    @Override
    public String open() {
        return String.format("%s%s%s", basic, username, tag);
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
