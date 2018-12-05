package edu.neumont.csc110;

public class UniqueSpace extends BoardSpace {
	public int identity;

	public UniqueSpace(int identifyer) {
		identity = identifyer;
	}

	public int getSpace() {
		return identity;
	}

	public String toString() {
		switch (identity) {
		default:
			return "This shouldnt appear. Why'd you get this?";
		case 0:
			return "GO! The first tile in the game. Pass or land here and get 200$";
		case 1:
			return "Community Chest! Draw a card from the Community Chest pile! What could it be?!";
		case 2:
			return "Chance Card! Draw a card from the Chance Card pile! What could it be?!";
		case 3:
			return "Jail! You're either in here for some heinous crime or you're just visiting! Lets hope its the latter.";
		case 4:
			return "Free Parking! In some rulesets of the game landing here earns you any money that is lost by cards or paid via spaces."
					+ " This is not one of those versions";
		case 5:
			return "Yikes the cops got ya. Go right to jail! Maybe next time don't commit tax fraud?";
		case 6:
			return "Income Tax! Pay 200$ if you land here!";
		case 7:
			return "Luxary Tax! Those fur coats and diamond rings dont pay for themselves! Pay 100$ here! (if only...)";
		}
	}
	
	public String getName() {
		switch (identity) {
		default:
			return "This shouldnt appear. Why'd you get this?";
		case 0:
			return "GO! The first tile in the game. Pass or land here and get 200$";
		case 1:
			return "Community Chest! Draw a card from the Community Chest pile! What could it be?!";
		case 2:
			return "Chance Card! Draw a card from the Chance Card pile! What could it be?!";
		case 3:
			return "Jail! You're either in here for some heinous crime or you're just visiting! Lets hope its the latter.";
		case 4:
			return "Free Parking! In some rulesets of the game landing here earns you any money that is lost by cards or paid via spaces."
					+ " This is not one of those versions";
		case 5:
			return "Yikes the cops got ya. Go right to jail! Maybe next time don't commit tax fraud?";
		case 6:
			return "Income Tax! Pay 200$ if you land here!";
		case 7:
			return "Luxary Tax! Those fur coats and diamond rings dont pay for themselves! Pay 100$ here! (if only...)";
		}

	}
}