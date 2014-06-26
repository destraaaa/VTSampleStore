package com.destra.vtdummy.model;


// Kelas untuk menyimpan struktur data "Schedule"
public class Item {

	private int id;
    private int resGambar;
    private String namaTempat;
    private Integer harga;

    public Item() {
    }

    public Item(int id, int prop1, String prop2, int prop3) {
    	this.id = id;
		this.resGambar = prop1;
		this.namaTempat = prop2;
		this.harga = prop3;
    }

    public int getGambar() {
    	return resGambar;
    }

    public String getNama() {
    	return namaTempat;
    }

    public Integer getHarga() {
    	return harga;
    }
    
    public int getId() {
    	return id;
    }
}