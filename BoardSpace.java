package edu.neumont.csc110;

public abstract class BoardSpace {
	
	//Abstract Class BoardSpace used to declare

	public void init() {
	}

	public void buyHouse() {
	}

	public void buyHotel() {
	}

	public int morgage() {
		return 0;
	}

	public int getHouse() {
		return 0;
	}

	public boolean getHotel() {
		return false;
	}

	public int getBuildingPrice() {
		return 0;
	}

	public String getName() {
		return "";
	}

	public Player getOwner() {
		return null;
	}

	public boolean getMorgaged() {
		return false;
	}

	public boolean getMonopoly() {
		return false;
	}

	public int getPrice() {
		return 0;
	}

	public void payMorgage() {
	}

	public String toString() {
		return "";
	}

	public boolean getOwned() {return false;}

	public void buySpace(Player player) {
	}

	public int getRent() {
		return 0;
	}
}
