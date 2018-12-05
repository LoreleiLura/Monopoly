package edu.neumont.csc110;

import java.util.Random;

public class UtilSpace extends BoardSpace {
	public String name;
	public int price;
	public Player owner;
	public boolean isOwned = false;

	Random rand = new Random();

	//init utilSpace
	public UtilSpace(String Name, int Price) {
		name = Name;
		price = Price;
	}

	//init utilSpace dedicated class
	public void init(String Name, int Price) {
		name = Name;
		price = Price;
	}
	
	//set name of the space
	public void setName(String Name) {
		name = Name;
	}

	//set the price of the space
	public void setPrice(int Price) {
		price = Price;
	}
	
	//return if the space is flagged as owned
	public boolean getOwned() {
		return isOwned;
	}
	
	//set the owner. also flags as purchased when called
	public void setOwner(Player i) {
		owner = i;
		isOwned=true;
	}

	//returns the rent value of the space
	public int getRent(boolean otherOwned) {
		int dice = (rand.nextInt(6)+1)+(rand.nextInt(6)+1);
		if (otherOwned) {
			return dice*10;
		} else {
			return dice*4;
		}
	}
	
	//returns a toString of the space.
	public String toString() {
		return name +" is owned by "+owner.getName()+".";
	}
}