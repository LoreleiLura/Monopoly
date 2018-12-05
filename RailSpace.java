package edu.neumont.csc110;

import java.util.Random;

public class RailSpace extends BoardSpace {
	public String name;
	public int price;
	public Player owner;
	public boolean isOwned = false;

	Random rand = new Random();

	//init railspace
	public RailSpace(String Name, int Price) {
		name = Name;
		price = Price;
	}

	//init railspace dedicated class
	public void init(String Name, int Price) {
		name = Name;
		price = Price;
	}

	//set the name of the space
	public void setName(String Name) {
		name = Name;
	}

	//set the price of the space
	public void setPrice(int Price) {
		price = Price;
	}
	
	//set the owner of the space. also flags the space as owned
	public void setOwner(Player i) {
		owner = i;
		isOwned=true;
	}
	
	//returns if the space is owned
	public boolean getOwned() {
		return isOwned;
	}
	
	//returns the rent of the space
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
	
	//return the display name of the tile
	public String getName() {
		return name;
	}
	
	//provides an awful toString of the space
	public String toString() {
		return name + " is owned by " + owner.getName() + ".";
	}
}