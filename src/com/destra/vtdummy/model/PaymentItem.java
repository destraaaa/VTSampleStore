package com.destra.vtdummy.model;


// Kelas untuk menyimpan struktur data "Schedule"
public class PaymentItem {

    private int resGambar;
    private String nama;

    public PaymentItem() {
    }

    public PaymentItem(int prop1, String prop2) {
		this.resGambar = prop1;
		this.nama = prop2;
    }

    public int getGambar() {
    	return resGambar;
    }

    public String getNama() {
    	return nama;
    }
}