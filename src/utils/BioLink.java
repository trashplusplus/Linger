package utils;

import java.util.List;

public interface BioLink {

    void parseOwnLink(boolean isOpen);
    void setLinkToParse(String linkToParse);
    String getBasic();
    List<String> getAllLinks();

}
