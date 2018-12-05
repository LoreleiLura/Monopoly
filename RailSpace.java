package edu.neumont.csc110;

import java.util.Random;

public class RailSpace extends BoardSpace {
	public String name;
	public int price;
	public Player owner;
	public boolean isOwned = false;

	Random rand = new Random();

	public RailSpace(String Name, int Price) {
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

	public void setOwner(Player i) {
		owner = i;
		isOwned=true;
	}
	public boolean getOwned() {
		return isOwned;
	}
	public int getRent(int otherOwned) {
		if (otherOwned == 1) {
			return 50;
		} else if (otherOwned == 2) {
			return 100;
		} else if (otherOwned == 3) {
			return 200;
		} else if (otherOwned == 0) {
			return 25;
		} else {
			return 0;
		}
	}

	public String toString() {
		return name + " is owned by " + owner.getName() + ".";
	}
}
