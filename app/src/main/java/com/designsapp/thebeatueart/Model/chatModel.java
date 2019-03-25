package com.designsapp.thebeatueart.Model;

public class chatModel {
    private String title,chat_id,creator,image;
    private int forHow;

    public chatModel(String mtitle, int forHow){
        this.title = mtitle;
        this.forHow = forHow;
    }
    public chatModel(String mtitle, int forHow,String image){
        this.title = mtitle;
        this.forHow = forHow;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public chatModel(String text, String chat_id, String creator, int forHow) {
        this.chat_id = chat_id;
        this.title = text;
        this.creator = creator;
        this.forHow = forHow;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getCreator() {
        return creator;
    }


    public String getTitle() {
        return title;
    }

    public int getForHow() {
        return forHow;
    }
}
