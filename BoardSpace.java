package edu.neumont.csc110;

public abstract class BoardSpace {
	public BoardSpace(String name, int purchasePrice, int baseRent, int oneHouseRent, int twoHouseRent,
			int threeHouseRent, int fourHouseRent, int hotelRent, int houseCost, int morgageCost) {
	}

	public void init(String name, int purchasePrice, int baseRent, int oneHouseRent, int twoHouseRent,
			int threeHouseRent, int fourHouseRent, int hotelRent, int houseCost, int morgageCost) {
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
}
