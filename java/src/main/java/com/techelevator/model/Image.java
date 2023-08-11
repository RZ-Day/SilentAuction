package com.techelevator.model;

public class Image {
    private byte[] imageData;

    public Image(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
