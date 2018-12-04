package edu.neumont.csc110;

public class Player {
	final int MONEY_START_FINAL=1500;
	String Name;
	PieceNames Piece;
	int Money;
	int location;
	int numJailCards;
	int doubleCount;
	boolean inJail;

	public Player() {
		Name = "Default";
		Piece = PieceNames.DEFAULT;
		Money = 0;
		location = 0;
		numJailCards = 0;
		doubleCount = 0;
		inJail = false;
	}

	public void init(String playerName, PieceNames thePiece) {
		Name = playerName;
		Piece = thePiece;
		Money = MONEY_START_FINAL;
		location = 0;
		numJailCards = 0;
		doubleCount = 0;
		inJail = false;
	}

	public void setName(String playerName) {
		Name = playerName;
	}

	public void setPeice(PieceNames thePiece) {
		Piece = thePiece;
	}

	public void setMoney(int mon) {
		Money = mon;
	}

	public void setLocation(int loc) {
		location = loc;
	}

	public void getJailCard() {
		numJailCards++;
	}

	public void useJailCard() throws NoSuchFieldException {
		if (numJailCards == 0) {
			throw new NoSuchFieldException();
		}
		numJailCards--;
	}

	public String getName() {
		return Name;
	}

	public PieceNames getPiece() {
		return Piece;
	}

	public int getLocation() {
		return location;
	}

	public int getMoney() {
		return Money;
	}

	public int getNumJailCards() {
		return numJailCards;
	}

	public String toString() {
		return "Player name:" + Name + "\n"
				+ "Money: " + Money + "\n"
				+ "Piece: " + Piece;
	}
	
	public enum PieceNames {
		TOPHAT, THIMBLE, IRON, SHOE, BATTLESHIP, DOG, CAT, WHEELBARROW, DEFAULT
	}
}