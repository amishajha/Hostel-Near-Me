package com.example.roomfinder;

import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;

public class Post  {
    private String Homename;
    private String contacts;
    private String rooms;
    private String area;
    private String districts;
    private String images;
    private String prices;
    public Post(){

    }


    public void setHomename(String homename) {
        Homename = homename;
    }

    public String getHomename() {
        return Homename;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistricts() {
        return districts;
    }

    public Post(String homename, String contacts, String rooms, String area, String districts, String images, String prices) {
        super();
        Homename = homename;
        this.contacts = contacts;
        this.rooms = rooms;
        this.area = area;
        this.districts = districts;
        this.images = images;
        this.prices = prices;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
}
