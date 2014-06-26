package com.destra.vtdummy.model;

// Constructor, getter, setter, untuk dipakai oleh adapter
public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    // Constructor Type #1: Empty
    public NavDrawerItem() {
    }

    // Constructor Type #2: Title & Icon
    public NavDrawerItem(String title, int icon) {
	this.title = title;
	this.icon = icon;
    }

    // Constructor Type #3: Title, Icon, IsCounterVisible, Count
    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
	this.title = title;
	this.icon = icon;
	this.isCounterVisible = isCounterVisible;
	this.count = count;
    }

    // Constructor Type #4: Title, Icon, Text in Counters
    public NavDrawerItem(String title, int icon, String count) {
	this.title = title;
	this.icon = icon;
	if (count.equals(""))
	    this.count = count;
    }

    // Setter Getters
    public String getTitle() {
	return this.title;
    }

    public int getIcon() {
	return this.icon;
    }

    public String getCount() {
	return this.count;
    }

    public boolean getCounterVisibility() {
	return this.isCounterVisible;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public void setIcon(int icon) {
	this.icon = icon;
    }

    public void setCount(String count) {
	this.count = count;
    }

    public void setCounterVisibility(boolean isCounterVisible) {
	this.isCounterVisible = isCounterVisible;
    }
}