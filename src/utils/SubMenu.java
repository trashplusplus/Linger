package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class SubMenu {
    private Map<Integer, SubMenuItem> allSubItems = new HashMap<>();
    private Map<Integer, Consumer<Void>> functionMap = new HashMap<>();
    private String subMenuTitle;
    private int index;


    public Map<Integer, Consumer<Void>> getFunctionMap(){
        return functionMap;
    }

    public SubMenu(int index, String subMenuTitle){
        this.index = index;
        this.subMenuTitle = subMenuTitle;
    }

    public void addSubItem(int index, String subMenuTitle, String prompt, Consumer<Void> func){
        SubMenuItem item = new SubMenuItem(index, subMenuTitle, prompt, func);
        allSubItems.put(index, item);
        functionMap.put(index, func);
    }


    public Map<Integer, SubMenuItem> getAllSubItems(){
        return allSubItems;
    }

    public SubMenuItem getSubItem(int index){
        return allSubItems.get(index);
    }

    public void showSubItem(int index){
        SubMenuItem item = allSubItems.get(index);
        System.out.println(item.getPrompt());
    }

    public String getSubMenuTitle(){
        return subMenuTitle;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }





}
