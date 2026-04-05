package com.aitarrot.model;

public class TarotCard {
    private String id;
    private String nameKo;
    private String imagePath;
    private boolean reversed;
    private String position;

    public TarotCard(String id, String nameKo) {
        this.id = id;
        this.nameKo = nameKo;
        this.imagePath = "/images/cards/" + id + ".jpg";
    }

    public String getId() { return id; }
    public String getNameKo() { return nameKo; }
    public String getImagePath() { return imagePath; }
    public boolean isReversed() { return reversed; }
    public void setReversed(boolean reversed) { this.reversed = reversed; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getOrientationKo() { return reversed ? "역방향" : "정방향"; }
}
