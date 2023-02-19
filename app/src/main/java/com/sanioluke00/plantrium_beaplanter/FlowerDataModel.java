package com.sanioluke00.plantrium_beaplanter;

public class FlowerDataModel {
    String flower_name, botanical_name;
    int image_res;

    public FlowerDataModel(String flower_name, String botanical_name, int image_res) {
        this.flower_name = flower_name;
        this.botanical_name = botanical_name;
        this.image_res = image_res;
    }

    public String getFlower_name() {
        return flower_name;
    }

    public void setFlower_name(String flower_name) {
        this.flower_name = flower_name;
    }

    public String getBotanical_name() {
        return botanical_name;
    }

    public void setBotanical_name(String botanical_name) {
        this.botanical_name = botanical_name;
    }

    public int getImage_res() {
        return image_res;
    }

    public void setImage_res(int image_res) {
        this.image_res = image_res;
    }
}
