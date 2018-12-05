package edu.neumont.csc110;

import java.util.Random;

public class UtilSpace extends BoardSpace {
	public String name;
	public int price;
	public Player owner;
	public boolean isOwned = false;

	Random rand = new Random();

	public UtilSpace(String Name, int Price) {
		name = Name;
		price = Price;
	}

	public void init(String Name, int Price) {
		name = Name;
		price = Price;
	}

	public void setName(String Name) {
		name = Name;
	}

	public void setPrice(int Price) {
		price = Price;
	}
	public boolean getOwned() {
		return isOwned;
	}
	public void setOwner(Player i) {
		owner = i;
		isOwned=true;
	}

	public int getRent(boolean otherOwned) {
		int dice = (rand.nextInt(6)+1)+(rand.nextInt(6)+1);
		if (otherOwned) {
			return dice*10;
		} else {
			return dice*4;
		}
	}
	
	public String toString() {
		return name +" is owned by "+owner.getName()+".";
	}
}
