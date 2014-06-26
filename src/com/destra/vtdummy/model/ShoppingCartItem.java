package com.destra.vtdummy.model;

public class ShoppingCartItem {
	
	private Item item;
	private int quantity;
	
	public ShoppingCartItem(){
		
	}
	
	public ShoppingCartItem(Item it, int q){
		this.item = it;
		this.quantity = q;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}		
}
