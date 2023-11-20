package soc;

import utils.Link;

public class Instagram implements Link {

    private final String basic = "https://www.instagram.com/";

    public String getBasic() {
        return basic;
    }

    private String username;
    private final String prefix = "[inst]: ";

    public Instagram(){

    }



    @Override
    public String open() {
        return String.format("%s%s", basic, username);
    }

    @Override
    public String openDirect() {
        //unique
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
        //no profile
        return basic;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
