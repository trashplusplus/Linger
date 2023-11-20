package utils;

import exceptions.SubMenuException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Menu {
    private Map<Integer, SubMenu> allItems = new HashMap<>();
    private Map<Integer, Consumer<Void>> functionMap = new HashMap<>();

    public Map<Integer, Consumer<Void>> getFunctionMap(){
        return functionMap;
    }

    public Menu(){

    }

    public void addItem(int index, String title, Consumer<Void> func){
        SubMenu subMenu = new SubMenu(index, title);
        subMenu.setIndex(index);
        allItems.put(index, subMenu);
        functionMap.put(index, func);
    }

    //for multiply items
    public void addItem(int index, String title, SubMenuItem ... subMenuItems){
        SubMenu subMenu = new SubMenu(index, title);
        subMenu.setIndex(index);

        for(SubMenuItem item: subMenuItems){
            subMenu.addSubItem(item.getIndex(), item.getTitle(), item.getPrompt(), item.getCallback());
        }



        allItems.put(index, subMenu);
    }

    public SubMenu getSubMenu(int index) {
        return allItems.get(index);
    }



    public void showSubMenu(int index){
        StringBuffer sb = new StringBuffer();
        int localIndex = 0;
        sb.append(ColorUtils.RED);
        SubMenu subMenu = allItems.get(index);
        for(SubMenuItem item: subMenu.getAllSubItems().values()){
            sb.append(String.format("[%d]. %s", item.getIndex(), item.getTitle()));
            localIndex++;
            if(localIndex < subMenu.getAllSubItems().size()){
                sb.append("\n");
            }
        }

        sb.append(ColorUtils.RESET);
        System.out.println(sb.toString());
    }

    public void showItems(){
        StringBuffer sb = new StringBuffer();
        int localIndex = 0;
        sb.append(ColorUtils.MAGENTA);
        for(Map.Entry<Integer, SubMenu> entry : allItems.entrySet()){
            SubMenu item = entry.getValue();
            int index = entry.getKey();
            sb.append(String.format("[%d]. %s", index, item.getSubMenuTitle()));
            localIndex++;
            if(localIndex < allItems.size()){
                sb.append("\n");
            }


        }

        sb.append(ColorUtils.RESET);
        System.out.println(sb.toString());
    }


    public void addItem(int number, String title, SubMenu ... subMenus){

    }

}
