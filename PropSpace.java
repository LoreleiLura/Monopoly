package edu.neumont.csc110;

public class PropSpace extends BoardSpace {
	public String propName = "";
	public int Price = 0;
	public int houseNum = 0;
	public Player owner = null;
	public boolean hasHotel = false;
	public boolean monopoly = false;
	public boolean morgaged = false;
	public boolean isOwned = false;

	// rent Values:
	public int zeroHouse, oneHouse, twoHouse, threeHouse, fourHouse, hotel, morgage;

	// House/Hotel Price:
	public int housePrice;

	public PropSpace(String name, int purchasePrice, int baseRent, int oneHouseRent, int twoHouseRent,
			int threeHouseRent, int fourHouseRent, int hotelRent, int houseCost, int morgageCost) {
		// assign the property values
		propName = name;
		Price = purchasePrice;
		zeroHouse = baseRent;
		oneHouse = oneHouseRent;
		twoHouse = twoHouseRent;
		threeHouse = threeHouseRent;
		fourHouse = fourHouseRent;
		hotel = hotelRent;
		housePrice = houseCost;
		morgage = morgageCost;
	}

	public void init(String name, int purchasePrice, int baseRent, int oneHouseRent, int twoHouseRent,
			int threeHouseRent, int fourHouseRent, int hotelRent, int houseCost, int morgageCost) {
		// assign the property values
		propName = name;
		Price = purchasePrice;
		zeroHouse = baseRent;
		oneHouse = oneHouseRent;
		twoHouse = twoHouseRent;
		threeHouse = threeHouseRent;
		fourHouse = fourHouseRent;
		hotel = hotelRent;
		housePrice = houseCost;
		morgage = morgageCost;
	}

	public void buyHouse() {
		if (hasHotel == false) {
			if (houseNum == 4) {
				throw new IllegalStateException();
			} else {
				houseNum++;
			}
		} else {
			throw new IllegalStateException();
		}
	}

	public void buyHotel() {
		if (houseNum != 4) {
			throw new IllegalStateException();
		} else {
			hasHotel = true;
			houseNum = 0;
		}
	}

	public void buySpace(Player i) {
		owner = i;
	}

	public int morgage() {
		if (morgaged == false) {
			return morgage;
		} else {
			System.out.println("Property Already Morgaged");
			return 0;
		}
	}

	public int payMorgageCost() {
		return (int) (morgage * 1.1);
	}

	public int getBuildingCost() {
		return housePrice;
	}

	public int getRent() {
		if (hasHotel) {
			return hotel;
		}
		switch (houseNum) {
		case 0:
			if ((monopoly) && hasHotel == false) {
				return zeroHouse * 2;
			} else if (hasHotel == false) {
				return zeroHouse;
			} else if (hasHotel) {
				return hotel;
			}
		case 1:
			return oneHouse;
		case 2:
			return twoHouse;
		case 3:
			return threeHouse;
		case 4:
			return fourHouse;
		default:
			return 0;
		}
	}

	public int getHouse() {
		return houseNum;
	}

	public boolean getHotel() {
		return hasHotel;
	}

	public int getBuildingPrice() {
		return housePrice;
	}

	public String getName() {
		return propName;
	}

	public Player getOwner() {
		return owner;
	}

	public boolean getOwned() {
		return isOwned;
	}

	public boolean getMorgaged() {
		return morgaged;
	}

	public boolean getMonopoly() {
		return monopoly;
	}

	public int getPrice() {
		return Price;
	}

	public void payMorgage() {
		morgaged = false;
	}

	public String toString() {
		String returnString = "";
		boolean nameTooLong = false;
		returnString += "  _____________________     ____________________\n";
		returnString += "  |                   |     |                  | \n" + "  |";
		String propName = "park Place";
		if (propName.length() < 20) {
			returnString += propName;
			for (int i = propName.length(); i < 19; i++) {
				returnString += " ";
			}
			returnString += "|     |";
		} else {
			if (propName.lastIndexOf(" ") > -1) {
				nameTooLong = true;
				returnString += propName.substring(0, propName.lastIndexOf(" "));
				for (int i = propName.length(); i < 19; i++) {
					returnString += " ";
				}
				returnString += "|     |";
			}
		}
		int morgage = 50;
		String morgageString = "Morgage: " + morgage;
		returnString += morgageString;
		for (int i = morgageString.length(); i < 18; i++) {
			returnString += " ";
		}
		returnString += "|\n";

		return propName + " Costs $" + getRent() + " and has " + getHouse() + " houses.";
	}
}
