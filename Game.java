package edu.neumont.csc110;

import java.io.IOException;
import java.util.Random;
import edu.neumont.csc110.Player.PieceNames;
//import interfaces.ConsoleUI;

public class Game {
	
	Random rnd = new Random();
	
	ChanceCard chanceCard = new ChanceCard();
	CommunityChestCard chestCards = new CommunityChestCard();
	BoardSpace[] GameBoard = new BoardSpace[40];
	
	Player[] players = new Player[8];
	public int playerCount = 0;
	public int turn = 0;
	public boolean chestJailCard = false;
	public boolean chanceJailCard = false;
	
	//Start up menu. Ask to play new game or quit
	public void printStartMenu() throws IOException, NoSuchFieldException {
		String[] menuOptions = new String[2];
		menuOptions[0] = "Quit Game";
		menuOptions[1] = "New Game";
		int selection = ConsoleUI.promptForMenuSelection(menuOptions, false);
		switch(selection) {
		case 0:
			System.out.println("Thanks for playing");
			break;
		case 1:
			game();
			break;
		default:
			System.out.println("That's not an option");
		}
	}
	
	//initialized everything and starts the game
	public void game() throws IOException, NoSuchFieldException {
		boolean play = true;
		while(play) {
			initSpaces();
			initChestCards();
			initChanceCards();
			askForPlayers();
			run();
			play = ConsoleUI.promptForBool("Do you want to play again? Y/N", "Y", "N");
		}	
	}
	
	//The part that loops. Prints who's turn it is and how much money they have
	public void run() throws NoSuchFieldException, IOException {
		boolean gameOver = false;
		while(!gameOver) {
			System.out.println("");
			System.out.println(players[turn].Name + ", your turn" );
			System.out.println("You have $" + players[turn].Money);
			if(players[turn].inJail == true) {
				jailedAction();
			}
			else {
				roll();
				players[turn].doubleCount = 0;
			}
			turn++;
			if(turn == playerCount) {
				turn = 0;
			}
		}
	}

	//Choices for how to get out of jail
	private void jailedAction() throws IOException {
		System.out.println(players[turn].Name + ", you're in jail.");
		String[] jailOptions = new String[4];
		jailOptions[0] = "Roll for double";
		jailOptions[1] = "Use 'Get out of Jail Free' card";
		jailOptions[2] = "Purchase card from another player";
		jailOptions[3] = "Pay fine of $50";
		if(players[turn].jailTurns == 3) {
			System.out.println("Pay $50 to get out of jail");
		}
		else {
			int selection = ConsoleUI.promptForMenuSelection(jailOptions, false);
			switch(selection) {
			case 0:
				int dice1 = rnd.nextInt(6);
				int dice2 = rnd.nextInt(6);
				System.out.println("You rolled " + dice1 + " and " + dice2);
				if(dice1 == dice2) {
					int roll = dice1+dice2;
					moveToSpace(roll);
					players[turn].inJail = false;
					players[turn].jailTurns = 0;
				}
				else {
					players[turn].jailTurns++;
				}
				break;
			case 1:
				
				try {
					players[turn].useJailCard();
					players[turn].inJail = false;
					players[turn].jailTurns = 0;
				} 
				catch (NoSuchFieldException noJailCard) {
					System.out.println("You don't have a 'Get Out of Jail Free' card");
					jailedAction();
				}
				break;
			case 2:
				if(chanceJailCard == true || chestJailCard == true) {
					for(int i=0;i<playerCount;i++) {
						if(players[i].numJailCards > 0) {
							boolean sell = ConsoleUI.promptForBool(players[i].Name + ", do you want to sell your card? Y/N", "Y", "N");
							if(sell) {
								int price = ConsoleUI.promptForInt(players[i].Name + ", what is the price?", 0, Integer.MAX_VALUE);
								boolean buy = ConsoleUI.promptForBool(players[turn].Name + ", will you buy the card for $" + price + "? Y/N", "Y", "N");
								if(buy) {
									players[turn].setMoney(players[turn].Money-price);
									players[i].setMoney(players[i].Money+price);
									players[turn].inJail = false;
									players[turn].jailTurns = 0;
								}
							}
							else {
								System.out.println(players[i].Name + " does not wish to sell their card.");
								jailedAction();
							}
						}
						else {
							System.out.println(players[i].Name + " does not have a card.");
							jailedAction();
						}
					}
				}
				break;
			case 3:
				if(players[turn].jailTurns != 0) {
					players[turn].setMoney(players[turn].Money-50);
					players[turn].inJail = false;
					players[turn].jailTurns = 0;
				}
				else {
					System.out.println("You cannot pay your way out of jail on the first turn");
					jailedAction();
				}
			}
		}
	}

	//rolls dice at beginning of turn. Calls moveToSpace and SpaceAction
	private void roll() throws IOException {
		int dice1 = rnd.nextInt(6)+1;
		int dice2 = rnd.nextInt(6)+1;
		if(dice1 == dice2) {
			players[turn].doubleCount++;
		}
		System.out.println("You rolled " + dice1 + " and " + dice2 + " for " + (dice1+dice2));
		int roll = dice1+dice2;
		moveToSpace(roll);
		spaceAction();
		if(dice1 == dice2 && players[turn].doubleCount < 2) {
			System.out.println(players[turn].Name + " rolls again");
			roll();
		}
	}
	
	//determines which space player is on and does action accordingly 
	private void spaceAction() throws IOException {
		if(players[turn].location == 7 || players[turn].location == 22 || players[turn].location == 30) {
			chanceCards();
		}
		else if(players[turn].location == 2 || players[turn].location == 17 || players[turn].location == 33) {
			chestCards();
		}
		else if(players[turn].location == 4) {
			players[turn].setMoney(players[turn].Money-200);
		}
		else if(players[turn].location == 10 || players[turn].location == 20 || players[turn].location == 0) {
			//nothing
		}
		else if(players[turn].location == 38) {
			players[turn].setMoney(players[turn].Money-100);
		}
		else {
			printGameMenu();
		}
	}

	//when on property that can be bought, options come up
	private void printGameMenu() throws IOException {
		int price = 0;
		if(players[turn].location != 10) {
			String[] options = new String[6];
			options[0] = "More Info on Property";
			options[1] = "Buy Property";
			options[2] = "Mortage";
			options[3] = "Buy House";
			options[4] = "Buy Hotel";
			options[5] = "Pay Rent";
			int selection = ConsoleUI.promptForMenuSelection(options, false);
			switch(selection) {
			case 0:
				GameBoard[players[turn].location].getPrice();
				printGameMenu();
				break;
			case 1:
				if(GameBoard[players[turn].location].getOwned()) {
					System.out.println("Someone owns this spot");
					rent();
				}
				else {
					GameBoard[players[turn].location].buySpace(players[turn]);
					price = GameBoard[players[turn].location].getPrice();
					players[turn].setMoney(players[turn].Money - price);
					System.out.println("You bought this space");
				}
				break;
			case 2:
				if(GameBoard[players[turn].location].getOwned()) {
					Player owner = GameBoard[players[turn].location].getOwner();
					if(owner == players[turn]) {
						GameBoard[players[turn].location].morgage();				
					}
				}
				else {
					System.out.println("You don't own this property");
					printGameMenu();
				}
				break;
			case 3:
				if(GameBoard[players[turn].location].getOwned()) {
					System.out.println("You don't own this property");
					rent();
				}
				else {
					Player owner = GameBoard[players[turn].location].getOwner();
					if(owner == players[turn]) {
						GameBoard[players[turn].location].buyHouse();
						price = GameBoard[players[turn].location].getBuildingPrice();
						players[turn].setMoney(players[turn].Money - price);
						players[turn].houseCount++;
					}
					else {
						System.out.println("You don't own this property");
						printGameMenu();
					}
				}
				break;
			case 4:
				if(GameBoard[players[turn].location].getOwned()) {
					System.out.println("You don't own this property");
					rent();
				}
				else {
					Player owner = GameBoard[players[turn].location].getOwner();
					if(owner == players[turn]) {
						GameBoard[players[turn].location].buyHotel();
						price = GameBoard[players[turn].location].getBuildingPrice();
						players[turn].setMoney(players[turn].Money - price);
						players[turn].houseCount++;				
					}
					else {
						System.out.println("You don't own this property");
						printGameMenu();
					}
				}
				break;
			case 5:
				rent();
				break;
			default:
				System.out.println("Not an option");
			}
		}
	}

	//when landed on property that's owned, pays owner rent
	private void rent() {
		players[turn].setMoney(players[turn].Money - GameBoard[players[turn].location].getRent());
		Player owner = GameBoard[players[turn].location].getOwner();
		try {
		owner.setMoney(
				owner.getMoney() - 
				GameBoard[players[turn].location].getRent());}
		catch(NullPointerException e) {
			System.out.println("You cannot pay Rent This turn.");
		}
		System.out.println("You payed " + GameBoard[players[turn].location].getRent());
	}

	//moves player space by space according to roll. Checks for instances to be sent to jail
	private void moveToSpace(int roll) {
		for(int i=0;i<roll;i++) {
			players[turn].location++;
			if(players[turn].location == 40) {
				players[turn].location = 0;
				System.out.println("You passed 'Go' and collected $200");
				players[turn].setMoney(players[turn].Money+200);
			}
		}
		System.out.println("You landed on " + GameBoard[players[turn].location].getName());
		if(players[turn].doubleCount == 3) {
			System.out.println("Sent to Jail. To many doubles.");
			players[turn].inJail = true;
			players[turn].location = 10;
			players[turn].doubleCount = 0;
		}
		else if(players[turn].location == 30) {
			players[turn].location = 10;
			players[turn].inJail = true;
		}
	}
	
	//Location set to space passed in
	private void jumpToSpace(int space) {
		players[turn].location = space;
	}
	
	
	
	
	
	
	//Randomly picks a chance card
	public void chanceCards() throws IOException {
		int draw = rnd.nextInt(16);
		System.out.println(chanceCard.chanceCards[draw]);
		switch(draw) {
		case 0:
			//Advance to Go
			players[turn].setMoney(players[turn].Money+200);
			jumpToSpace(0);
			break;
		case 1:
			//Advace to Illinois Ave.
			jumpToSpace(24);
			spaceAction();
			break;
		case 2:
			//Advance to St. Charles Ave.
			jumpToSpace(11);
			spaceAction();
			break;
		case 3:
			//Advance to nearest utility
			if(players[turn].location<12 || players[turn].location > 28) {
				players[turn].location = 12;
			}
			else {
				players[turn].location = 28;
			}
			System.out.println("You landed on " + GameBoard[players[turn].location].getName());
			spaceAction();
			break;
		case 4:
			//Advance to nearest Railroad
			if(players[turn].location<5 || players[turn].location>35) {
				players[turn].location = 5;
			}
			else if(players[turn].location<15 && players[turn].location > 5) {
				players[turn].location = 15;
			}
			else if(players[turn].location<25 && players[turn].location>15) {
				players[turn].location = 25;
			}
			else if(players[turn].location<35 && players[turn].location > 25) {
				players[turn].location = 35;
			}
			System.out.println("You landed on " + GameBoard[players[turn].location].getName());
			spaceAction();
			break;
		case 5:
			//Receive $50
			players[turn].setMoney(players[turn].Money + 50);
			break;
		case 6:
			//Get out of jail free
			if(chanceJailCard == false) {
				players[turn].getJailCard();
				chanceJailCard = true;
			}
			else {
				chanceCards();
			}
			break;
		case 7:
			//go back three spaces
			players[turn].setLocation(players[turn].location-3);
			spaceAction();
			break;
		case 8:
			//Go to jail
			players[turn].inJail = true;
			jumpToSpace(10);
		case 9:
			//Pay $25 per house and $100 per hotel
			players[turn].setMoney(players[turn].Money+(25 * players[turn].houseCount)+(100 * players[turn].hotelCount));
			break;
		case 10:
			//Pay $15
			players[turn].setMoney(players[turn].Money-15);
			break;
		case 11:
			//Advance Reading Railroad
			jumpToSpace(5);
			break;
		case 12:
			//Advance to Boardwalk
			jumpToSpace(39);
			spaceAction();
			break;
		case 13:
			//Pay each player $50
			for(int i=0;i<playerCount-1;i++) {
				players[i].setMoney(players[turn].Money+25);
			}
			players[turn].setMoney(players[turn].Money-(players.length*50));
			break;
		case 14:
			//Recieve $150
			players[turn].setMoney(players[turn].Money+150);
			break;
		case 15:
			//Collect $100
			players[turn].setMoney(players[turn].Money+100);
		}
	}
	
	//randomly picks a community chest card
	public void chestCards() {
		int draw = rnd.nextInt(17);
		System.out.println(chestCards.communityChestCard[draw]);
		switch(draw) {
		case 0:
			//Advance to Go
			players[turn].setMoney(players[turn].Money+200);
			jumpToSpace(0);
			break;
		case 1:
			//Collect $200
			players[turn].setMoney(players[turn].Money+200);
			break;
		case 2:
			//Pay $50
			players[turn].setMoney(players[turn].Money-50);
			break;
		case 3:
			//Get $50
			players[turn].setMoney(players[turn].Money+50);
			break;
		case 4:
			//Get out of jail free
			if(chestJailCard == false) {
				players[turn].getJailCard();
				chestJailCard = true;
			}
			else {
				chestCards();
			}
			break;
		case 5:
			//go to jail
			players[turn].inJail = true;
			System.out.println("You landed on " + GameBoard[players[turn].location].getName());
			jumpToSpace(10);
			break;
		case 6:
			//Collect $50 from each player
			for(int i=0;i<playerCount;i++) {
				players[i].setMoney(players[turn].Money-50);
			}
			players[turn].setMoney(players[turn].Money+(players.length*50));
			break;
		case 7:
			//Recieve $100
			players[turn].setMoney(players[turn].Money+100);
			break;
		case 8:
			//Collect $20
			players[turn].setMoney(players[turn].Money+20);
			break;
		case 9:
			//Collect $10 from each player
			for(int i=0;i<playerCount;i++) {
				players[i].setMoney(players[turn].Money-5);
			}
			players[turn].setMoney(players[turn].Money+(playerCount*10));
			break;
		case 10:
			//Collect $100
			players[turn].setMoney(players[turn].Money+100);
			break;
		case 11:
			//Pay $50
			players[turn].setMoney(players[turn].Money-50);
			break;
		case 12:
			//Pay $50
			players[turn].setMoney(players[turn].Money-50);
			break;
		case 13:
			//Recieve $25
			players[turn].setMoney(players[turn].Money+25);
			break;
		case 14:
			//Pay $40 per house and $115 per hotel
			players[turn].setMoney(players[turn].Money+(45 * players[turn].houseCount)+(115 * players[turn].hotelCount));
			break;
		case 15:
			//Collect $10
			players[turn].setMoney(players[turn].Money+10);
			break;
		case 16:
			//Collect $1000
			players[turn].setMoney(players[turn].Money+1000);
		}
	}
	
	//initialize community chest cards
	private void initChestCards() {
		chestCards.communityChestCard[0] = "Advance to 'Go'";
		chestCards.communityChestCard[1] = "Bank error in your favor. Collect $200";
		chestCards.communityChestCard[2] = "Doctor's fee. Pay $50";
		chestCards.communityChestCard[3] = "From sale of stock you get $50";
		chestCards.communityChestCard[4] = "Get out of Jail Free";
		chestCards.communityChestCard[5] = "Go to Jail";
		chestCards.communityChestCard[6] = "Grand Opera Night. Collect $50 from each player for opening night seats";
		chestCards.communityChestCard[7] = "Holiday Fund matures. Receive $100";
		chestCards.communityChestCard[8] = "Income tax refund. Collect $20";
		chestCards.communityChestCard[9] = "It's your birthday. Collect $10 from each player";
		chestCards.communityChestCard[10] = "Life insurance matures. Collect $100";
		chestCards.communityChestCard[11] = "Hospital fees. Pay $50";
		chestCards.communityChestCard[12] = "School fees. Pay $50";
		chestCards.communityChestCard[13] = "Receive $25 consultancy fee";
		chestCards.communityChestCard[14] = "You are assessed for street repairs";
		chestCards.communityChestCard[15] = "You have won second prize in a beauty contest. Collect $10";
		chestCards.communityChestCard[16] = "You inherit $1000";
	}	
	
	//initialize chance cards
	private void initChanceCards() {
		chanceCard.chanceCards[0] = "Advance to 'Go'";
		chanceCard.chanceCards[1] = "Advance to Illinois Ave.";
		chanceCard.chanceCards[2] = "Advance to St. Charles Place";
		chanceCard.chanceCards[3] = "Advance token to nearest Utility. If unowned, you may buy. If owned, throw a dice and pay owner a total 10 times the amount thrown";
		chanceCard.chanceCards[4] = "Advance token to nearest Railroad. If unowned, you may buy. If owned, pay double the rental";
		chanceCard.chanceCards[5] = "Bank pays you dividend of $50";
		chanceCard.chanceCards[6] = "Get out of Jail Free";
		chanceCard.chanceCards[7] = "Go back three spaces";
		chanceCard.chanceCards[8] = "Go to Jail";
		chanceCard.chanceCards[9] = "Make general repairs on all your property. For each house pay $25. For each hotel, pay $100";
		chanceCard.chanceCards[10] = "Pay poor tax of $15";
		chanceCard.chanceCards[11] = "Take a trip to Reading Railroad";
		chanceCard.chanceCards[12] = "Take a walk on the Boardwalk. Advance to Boardwalk";
		chanceCard.chanceCards[13] = "You have been elected Chairman of the Board. Pay each player $50";
		chanceCard.chanceCards[14] = "Your building loan matures. Receive $150";
		chanceCard.chanceCards[15] = "You have won a crossword competition. Collect $100";
	}
	
	//initialize all players
	private void askForPlayers() throws IOException {
		playerCount = ConsoleUI.promptForInt("How many people are playing?", 2, 8);
		for(int i=0;i<playerCount;i++) {
			players[i] = new Player();
			String playerName = ConsoleUI.promptForInput("What is your name?", false);
			//players[i].init(playerName, PieceNames.BATTLESHIP);
			int piece= rnd.nextInt(7)+1;
			switch(piece) {
			case 1:
				players[i].init(playerName, PieceNames.TOPHAT);
			case 2:
				players[i].init(playerName, PieceNames.THIMBLE);
			case 3:
				players[i].init(playerName, PieceNames.IRON);
			case 4:
				players[i].init(playerName, PieceNames.SHOE);
			case 5:
				players[i].init(playerName, PieceNames.BATTLESHIP);
			case 6:
				players[i].init(playerName, PieceNames.DOG);
			case 7:
				players[i].init(playerName, PieceNames.CAT);
			case 8:
				players[i].init(playerName, PieceNames.WHEELBARROW);
			default:
				players[i].init(playerName, PieceNames.DEFAULT);
			}
		}
	}
	
	//initialize all board spaces
	private void initSpaces() {
		GameBoard[0] = new UniqueSpace(0);
		GameBoard[1] = new PropSpace("Mediterranean Avenue", 60, 2, 10, 30, 90, 160, 200, 30, 50);
		GameBoard[2] = new UniqueSpace(1);
		GameBoard[3] = new PropSpace("Baltic Avenue", 60, 4, 20, 60, 180, 320, 450, 30, 50);
		GameBoard[4] = new UniqueSpace(6);
		GameBoard[5] = new RailSpace("Reading Railroad", 200);
		GameBoard[6] = new PropSpace("Oriental Avenue", 100, 6, 30, 90, 270, 400, 550, 50, 50);
		GameBoard[7] = new UniqueSpace(2);
		GameBoard[8] = new PropSpace("Vermont Avenue", 100, 6, 30, 90, 270, 400, 550, 50, 50);
		GameBoard[9] = new PropSpace("Connecticut Avenue", 120, 8, 40, 100, 300, 450, 600, 60, 50);
		GameBoard[10] = new UniqueSpace(3);
		GameBoard[11] = new PropSpace("St. Charles Place", 140, 10, 50, 150, 450, 625, 750, 70, 100);
		GameBoard[12] = new UtilSpace("Electric Company", 150);
		GameBoard[13] = new PropSpace("States Avenue", 140, 10, 50, 150, 450, 625, 750, 70, 100);
		GameBoard[14] = new PropSpace("Virginia Avenue", 160, 12, 60, 180, 500, 700, 900, 80, 100);
		GameBoard[15] = new RailSpace("Pennsylvania Railroad", 200);
		GameBoard[16] = new PropSpace("St. James Place", 180, 14, 70, 200, 550, 750, 950, 90, 100);
		GameBoard[17] = new UniqueSpace(1);
		GameBoard[18] = new PropSpace("Tennessee Avenue", 180, 14, 70, 200, 550, 750, 950, 90, 100);
		GameBoard[19] = new PropSpace("New York Avenue", 200, 16, 80, 220, 600, 800, 1000, 100, 100);
		GameBoard[20] = new UniqueSpace(4);
		GameBoard[21] = new PropSpace("Kentucky Avenue", 220, 18, 90, 250, 700, 875, 1050, 110, 150);
		GameBoard[22] = new UniqueSpace(2);
		GameBoard[23] = new PropSpace("Indiana Avenue", 220, 18, 90, 250, 700, 875, 1050, 110, 150);
		GameBoard[24] = new PropSpace("Illinois Avenue", 240, 20, 100, 300, 750, 925, 1100, 120, 150);
		GameBoard[25] = new RailSpace("B. & O. Railroad", 200);
		GameBoard[26] = new PropSpace("Atlantic Avenue", 260, 22, 110, 330, 800, 975, 1150, 130, 150);
		GameBoard[27] = new PropSpace("Ventnor Avenue", 260, 22, 110, 330, 800, 975, 1150, 130, 150);
		GameBoard[28] = new UtilSpace("Water Works", 150);
		GameBoard[29] = new PropSpace("Marvin Gardens", 280, 24, 120, 360, 850, 1025, 1200, 140, 150);
		GameBoard[30] = new UniqueSpace(5);
		GameBoard[31] = new PropSpace("Pacific Avenue", 300, 26, 130, 390, 900, 1100, 1275, 150, 200);
		GameBoard[32] = new PropSpace("North Carolina Avenue", 300, 26, 130, 390, 900, 1100, 1275, 150, 200);
		GameBoard[33] = new UniqueSpace(1);
		GameBoard[34] = new PropSpace("Pennsylvania Avenue", 320, 28, 150, 450, 1000, 1200, 1400, 160, 200);
		GameBoard[35] = new RailSpace("Short Line", 200);
		GameBoard[36] = new UniqueSpace(2);
		GameBoard[37] = new PropSpace("Park Place", 350, 35, 175, 500, 1100, 1300, 1500, 175, 200);
		GameBoard[38] = new UniqueSpace(7);
		GameBoard[39] = new PropSpace("BoardWalk", 400, 50, 200, 600, 1400, 1700, 2000, 200, 200);
	}
}