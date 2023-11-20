package utils;

import java.util.function.Consumer;

public class SubMenuItem {

    private String prompt;
    private String title;
    private int index;
    private Consumer<Void> callback;

    public Consumer<Void> getCallback(){
        return callback;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public SubMenuItem(int index, String title) {
        this.title = title;
        this.index = index;
    }

    public SubMenuItem(int index, String title, String prompt) {
        this.title = title;
        this.index = index;
        this.prompt = prompt;
    }

    public SubMenuItem(int index, String title, String prompt, Consumer<Void> callback) {
        this.title = title;
        this.prompt = prompt;
        this.index = index;
        this.callback = callback;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }




}
