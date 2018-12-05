package edu.neumont.csc110;

public class Player {
	final int MONEY_START_FINAL=1500;
	String Name;
	PieceNames Piece;
	int Money;
	int location;
	int numJailCards;
	int doubleCount;
	int houseCount;
	int hotelCount;
	int jailTurns;
	boolean inJail;

	//init the player
	public Player() {
		Name = "Default";
		Piece = PieceNames.DEFAULT;
		Money = 0;
		location = 0;
		numJailCards = 0;
		doubleCount = 0;
		houseCount = 0;
		hotelCount = 0;
		jailTurns = 0;
		inJail = false;
	}
	//init player dedicated class
	public void init(String playerName, PieceNames thePiece) {
		Name = playerName;
		Piece = thePiece;
		Money = MONEY_START_FINAL;
		location = 0;
		numJailCards = 0;
		doubleCount = 0;
		houseCount = 0;
		hotelCount = 0;
		jailTurns = 0;
		inJail = false;
	}

	//set player name
	public void setName(String playerName) {
		Name = playerName;
	}
	
	//set player piece
	public void setPeice(PieceNames thePiece) {
		Piece = thePiece;
	}
	
	//set player money.
	public void setMoney(int mon) {
		Money = mon;
	}

	//set the location of the player
	public void setLocation(int loc) {
		location = loc;
	}
	
	//Acquire a jail card
	public void getJailCard() {
		numJailCards++;
	}
	
	//use a jail card
	public void useJailCard() throws NoSuchFieldException {
		if (numJailCards == 0) {
			throw new NoSuchFieldException();
		}
		numJailCards--;
	}

	//return the name of the player
	public String getName() {
		return Name;
	}

	//get the piece the player has
	public PieceNames getPiece() {
		return Piece;
	}

	//return location of player
	public int getLocation() {
		return location;
	}
	
	//return money of the player
	public int getMoney() {
		return Money;
	}

	//return number of jail cards
	public int getNumJailCards() {
		return numJailCards;
	}
	
	//return a String Amalgamation of the player
	public String toString() {
		return "Player name:" + Name + "\n"
				+ "Money: " + Money + "\n"
				+ "Piece: " + Piece;
	}
	
	public enum PieceNames {
		TOPHAT, THIMBLE, IRON, SHOE, BATTLESHIP, DOG, CAT, WHEELBARROW, DEFAULT
	}
}