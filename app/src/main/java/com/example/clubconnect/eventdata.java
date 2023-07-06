package com.example.clubconnect;

import java.util.List;

public class eventdata {
    private List<String> imageUrls;
    private String itemName;
    private String itemDesc;
    private String itemId;

    public eventdata(List<String> imageUrls, String itemName, String itemDesc, String itemId) {
        this.imageUrls = imageUrls;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemId = itemId;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemId() {
        return itemId;
    }
}


