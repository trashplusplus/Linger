package utils;

public interface Link {
    String open();
    String openDirect();
    String getPrefix();
    void setUsername(String username);
    String getProfile();
    String getUsername();
    void switchEnable();
    boolean getEnable();


}
