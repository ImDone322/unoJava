import java.util.*;

public class GameFunction {

	// select number of player, assign player name
	public ArrayList<Player> UserInput(ArrayList<Player> player) {
		Scanner input = new Scanner(System.in);
		int n = 0;
		do {
			System.out.print("How many player: ");
			try {
				n = Integer.parseInt(input.nextLine());
				if (n < 2 || n > 4)
					System.out.println("ERROR !!! 2 TO 4 PLAYERS ONLY !!!");
			} catch (Exception ex) {
				System.out.println("INVALID INPUT !!! PRESS ENTER TO CONTINUE.");
				input.nextLine();
			}
		} while (n < 2 || n > 4);

		System.out.println(n + " players have been created ");

		int i = 1;
		do {
			System.out.print("Player " + i + " name: ");
			String str = input.nextLine();

			Player newPlayer = new Player(str);
			player.add(newPlayer);
			n--;
			i++;
		} while (n != 0);
		return player;
	}

	// Generate Deck
	public ArrayList<UnoDeck> genDeck() {
		ArrayList<UnoDeck> unoDeck = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			UnoDeck uno = new UnoDeck("Red ", Integer.toString(i), null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 10; i++) {
			UnoDeck uno = new UnoDeck("Blue ", Integer.toString(i), null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 10; i++) {
			UnoDeck uno = new UnoDeck("Green ", Integer.toString(i), null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 10; i++) {
			UnoDeck uno = new UnoDeck("Yellow ", Integer.toString(i), null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 2; i++) {
			UnoDeck uno = new UnoDeck("Wild ", null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 2; i++) {
			UnoDeck uno = new UnoDeck("Wild +4 ", null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 2; i++) {
			UnoDeck uno = new UnoDeck("Wild Random ", null);
			unoDeck.add(uno);
		}
		for (int i = 0; i < 4; i++) {
			String str = "";
			if (i == 0)
				str = "Red ";
			else if (i == 1)
				str = "Blue ";
			else if (i == 2)
				str = "Green ";
			else if (i == 3)
				str = "Yellow ";
			UnoDeck uno = new UnoDeck(str, null, "Reverse");
			UnoDeck uno2 = new UnoDeck(str, null, "Skip");
			UnoDeck uno3 = new UnoDeck(str, null, "+2");
			unoDeck.add(uno);
			unoDeck.add(uno2);
			unoDeck.add(uno3);
		}

		// shuffle the deck
		Collections.shuffle(unoDeck);

		return unoDeck;
	}

	// In Game Function
	public ArrayList<Player> playerTurn(ArrayList<Player> player, ArrayList<UnoDeck> cardDeck, int count,
			ArrayList<UnoDeck> deckOnTable, boolean skipOne, boolean reverse) {
		Scanner input = new Scanner(System.in);
		count = count % player.size();

		Collections.sort(player.get(count).getHandCard());

		// clear console
		for (int i = 0; i < 1000; i++)
			System.out.println();

		do {
			if (player.get(count).getTurn() == false) {
				player.get((count + 1) % player.size()).setTurn(true);
				return player;
			}
			if (player.get(count).getChallenge() == true) {
				wildChallenge(player, deckOnTable, cardDeck, count);
				player.get(count).setChallenge(false);
			}
			if (player.get(count).getTurn() == false)
				break;

			// Display on field card
			if (deckOnTable.get(deckOnTable.size() - 1).getValue() != null)
				System.out.println("On field card: " + deckOnTable.get(deckOnTable.size() - 1).getColor()
						+ deckOnTable.get(deckOnTable.size() - 1).getValue());
			else if (deckOnTable.get(deckOnTable.size() - 1).getActionCard() != null)
				System.out.println("On field card: " + deckOnTable.get(deckOnTable.size() - 1).getColor()
						+ deckOnTable.get(deckOnTable.size() - 1).getActionCard());
			else if (deckOnTable.get(deckOnTable.size() - 1).getWild() != null) {
				System.out.println("On field card: " + deckOnTable.get(deckOnTable.size() - 1).getWild()
						+ deckOnTable.get(deckOnTable.size() - 1).getColor());
			}

			System.out.println(player.get(count).getName() + " turn!");

			// display player hand Card
			for (int i = 0; i < player.get(count).getHandCard().size(); i++) {
				if (player.get(count).getHandCard().get(i).getValue() != null)
					System.out.println("Card " + (i + 1) + ": " + player.get(count).getHandCard().get(i).getColor()
							+ player.get(count).getHandCard().get(i).getValue());
				else if (player.get(count).getHandCard().get(i).getActionCard() != null)
					System.out.println("Card " + (i + 1) + ": " + player.get(count).getHandCard().get(i).getColor()
							+ player.get(count).getHandCard().get(i).getActionCard());
				else
					System.out.println("Card " + (i + 1) + ": " + player.get(count).getHandCard().get(i).getWild());
			}

			int userInput = 0;

			do {
				System.out.println();
				System.out.println("1. Select your card by entering the initial number.");
				System.out.println("2. Enter 99 for drawing a card.");
				System.out.print("Action: ");
				try {
					userInput = Integer.parseInt(input.nextLine()) - 1;
					if (userInput != 98 && userInput >= player.get(count).getHandCard().size() || userInput < 0)
						System.out.println("PLEASE ENTER THE CORRECT NUMBER !!!");
				} catch (Exception ex) {
					System.out.println("INVALID INPUT !!! PRESS ENTER TO CONTINUE.");
					input.nextLine();
				}
			} while (userInput != 98 && userInput >= player.get(count).getHandCard().size() || userInput < 0);

			if (userInput == 98) {
				try {
					player.get(count).getHandCard().add(cardDeck.get(0));
					cardDeck.remove(0);
					int temp = (count + 1) % player.size();
					player.get(temp).setTurn(true);
					player.get(count).setTurn(false);
				} catch (Exception ex) {
					System.out.println("Insufficient Card");
				}

			} else {
				if (checkValid(player, count, deckOnTable, userInput) == false) {
					System.out.println("PLEASE FOLLOW THE RULE !!!");
					return playerTurn(player, cardDeck, count, deckOnTable, skipOne, reverse);
				} else {
					if (player.get(count).getHandCard().get(userInput).getWild() == "Wild ") {
						wildCard(player, cardDeck, count, deckOnTable, userInput);
						returnFunction(player, deckOnTable, count, userInput);
					} else if (player.get(count).getHandCard().get(userInput).getWild() == "Wild +4 ") {
						int temp = (count + 1) % player.size();
						if (cardDeck.size() < 4) {
							cardDeck = shuffleCard(cardDeck, deckOnTable);
							wildCard(player, cardDeck, count, deckOnTable, userInput);
							player.get(temp).setChallenge(true);
							returnFunction(player, deckOnTable, count, userInput);

						} else {
							wildCard(player, cardDeck, count, deckOnTable, userInput);
							player.get(temp).setChallenge(true);
							returnFunction(player, deckOnTable, count, userInput);
						}
					} else if (player.get(count).getHandCard().get(userInput).getWild() == "Wild Random ") {
						wildRandom(player, cardDeck, count, deckOnTable, userInput);
						returnFunction(player, deckOnTable, count, userInput);
					} else if (player.get(count).getHandCard().get(userInput).getActionCard() == "Skip") {
						skipOne = true;
						skipPlayer(player, skipOne, count, deckOnTable, userInput);
					} else if (player.get(count).getHandCard().get(userInput).getActionCard() == "+2") {
						if (cardDeck.size() < 2) {
							cardDeck = shuffleCard(cardDeck, deckOnTable);

							int temp = (count + 1) % player.size();
							for (int i = 0; i < 2; i++) {
								player.get(temp).getHandCard().add(cardDeck.get(0));
								cardDeck.remove(0);
							}
							skipOne = true;
							skipPlayer(player, skipOne, count, deckOnTable, userInput);
						} else {
							int temp = (count + 1) % player.size();
							for (int i = 0; i < 2; i++) {
								player.get(temp).getHandCard().add(cardDeck.get(0));
								cardDeck.remove(0);
							}
							skipOne = true;
							skipPlayer(player, skipOne, count, deckOnTable, userInput);
						}

					} else if (player.get(count).getHandCard().get(userInput).getActionCard() == "Reverse") {
						reverse = true;
						reversePlayerList(player, reverse, count, deckOnTable, userInput);
					} else {
						returnFunction(player, deckOnTable, count, userInput);
					}
				}
			}
		} while (player.get(count).getTurn() == true);
		return player;

	}

	// Check the validation for deckOnTable and player option
	public static Boolean checkValid(ArrayList<Player> player, int count, ArrayList<UnoDeck> deckOnTable,
			int userInput) {

		if (deckOnTable.get(deckOnTable.size() - 1).getColor() != null && deckOnTable.get(deckOnTable.size() - 1)
				.getColor().equals(player.get(count).getHandCard().get(userInput).getColor()))
			return true;
		else if (deckOnTable.get(deckOnTable.size() - 1).getValue() != null && deckOnTable.get(deckOnTable.size() - 1)
				.getValue().equals(player.get(count).getHandCard().get(userInput).getValue()))
			return true;
		else if (deckOnTable.get(deckOnTable.size() - 1).getActionCard() != null
				&& deckOnTable.get(deckOnTable.size() - 1).getActionCard()
						.equals(player.get(count).getHandCard().get(userInput).getActionCard()))
			return true;
		else if (player.get(count).getHandCard().get(userInput).getWild() == "Wild "
				|| player.get(count).getHandCard().get(userInput).getWild() == "Wild +4 "
				|| player.get(count).getHandCard().get(userInput).getWild() == "Wild Random ")
			return true;

		return false;
	}

	// skip player
	public ArrayList<Player> skipPlayer(ArrayList<Player> player, boolean skipOne, int count,
			ArrayList<UnoDeck> deckOnTable, int userInput) {
		int temp = (count + 1) % player.size();
		int temp2 = (count + 2) % player.size();
		if (skipOne == true) {
			player.get(count).setTurn(false);
			player.get(temp2).setTurn(true);
		} else {
			player.get(count).setTurn(false);
			player.get(temp).setTurn(true);
		}
		deckOnTable.add(player.get(count).getHandCard().get(userInput));
		player.get(count).getHandCard().remove(userInput);

		for (int i = 0; i < 1000; i++)
			System.out.println();
		return player;
	}

	public ArrayList<Player> reversePlayerList(ArrayList<Player> player, boolean reverse, int count,
			ArrayList<UnoDeck> deckOnTable, int userInput) {
		int temp = (count + 1) % player.size();
		int temp2 = (((count - 1) % player.size()) + player.size()) % player.size();
		int temp3 = (count + 2) % player.size();

		if (reverse == true) {
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
			player.get(count).getHandCard().remove(userInput);
			player.get(count).setTurn(false);
			Collections.reverse(player);

			if (player.size() == 3 && count == 1)
				player.get(temp).setTurn(true);
			else if (player.size() == 3 && count == 2)
				player.get(temp2).setTurn(true);
			else if (player.size() == 4 && count == 1)
				player.get(temp3).setTurn(true);
			else if (player.size() == 4 && count == 3)
				player.get(temp3).setTurn(true);
			else
				player.get(count).setTurn(true);
		}
		for (int i = 0; i < 1000; i++)
			System.out.println();
		return player;
	}

	// switch player turn
	public ArrayList<Player> returnFunction(ArrayList<Player> player, ArrayList<UnoDeck> deckOnTable, int count,
			int userInput) {
		int temp = (count + 1) % player.size();

		deckOnTable.add(player.get(count).getHandCard().get(userInput));
		player.get(count).getHandCard().remove(userInput);
		player.get(count).setTurn(false);
		player.get(temp).setTurn(true);

		for (int i = 0; i < 1000; i++)
			System.out.println();
		return player;
	}

	public ArrayList<Player> wildRandom(ArrayList<Player> player, ArrayList<UnoDeck> cardDeck, int count,
			ArrayList<UnoDeck> deckOnTable, int userInput) {
		int random = 0;
		random = randomNum(1, 4);

		if (random == 1) {
			player.get(count).getHandCard().get(userInput).setWild("Red ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (random == 2) {
			player.get(count).getHandCard().get(userInput).setWild("Green ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (random == 3) {
			player.get(count).getHandCard().get(userInput).setWild("Blue ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (random == 4) {
			player.get(count).getHandCard().get(userInput).setWild("Yellow ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		}

		return player;
	}

	public static int randomNum(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public ArrayList<Player> wildCard(ArrayList<Player> player, ArrayList<UnoDeck> cardDeck, int count,
			ArrayList<UnoDeck> deckOnTable, int userInput) {

		Scanner input = new Scanner(System.in);
		int temp = (count + 1) % player.size();
		int option = 0;

		do {
			System.out.println("Choose a color: 1. Red | 2. Green | 3. Blue | 4. Yellow");
			System.out.println("Option: ");
			try {
				option = Integer.parseInt(input.nextLine());
				if (option < 1 || option > 4)
					System.out.println("ERROR !!!");
			} catch (Exception ex) {
				System.out.println("INVALID INPUT !!! PRESS ENTER TO CONTINUE.");
				input.nextLine();
			}
		} while (option < 1 || option > 4);

		if (option == 1) {
			player.get(count).getHandCard().get(userInput).setWild("Red ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (option == 2) {
			player.get(count).getHandCard().get(userInput).setWild("Green ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (option == 3) {
			player.get(count).getHandCard().get(userInput).setWild("Blue ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		} else if (option == 4) {
			player.get(count).getHandCard().get(userInput).setWild("Yellow ");
			deckOnTable.add(player.get(count).getHandCard().get(userInput));
		}

		player.get(temp).setTurn(false);

		return player;
	}

	public ArrayList<Player> wildChallenge(ArrayList<Player> player, ArrayList<UnoDeck> deckOnTable,
			ArrayList<UnoDeck> cardDeck, int count) {
		Scanner scanner = new Scanner(System.in);

		boolean winChallenge = false;
		String[] color = { "Red ", "Green ", "Blue ", "Yellow " };
		List<String> colorList = Arrays.asList(color);
		Collections.shuffle(colorList);

		int userInput = 0;
		int temp = (((count - 1) % player.size()) + player.size()) % player.size();
		int temp2 = (count + 1) % player.size();

		do {
			System.out.println(player.get(temp).getName() + " played a Wild +4 card, do you think "
					+ player.get(temp).getName() + " has a " + colorList.get(0) + "card?");
			System.out.println("1.Yes +6/0 | 2.No +4");
			System.out.println("Option: ");
			try {
				userInput = scanner.nextInt();
				if (userInput != 1 && userInput != 2)
					System.out.println("INVALID INPUT !!!");
			} catch (Exception ex) {
				System.out.println("INVALID INPUT !!! PRESS ENTER TO CONTINUE.");
				scanner.nextLine();
			}
		} while (userInput != 1 && userInput != 2);

		if (userInput == 1) {
			for (int i = 0; i < player.get(temp).getHandCard().size() - 1; i++) {
				if (player.get(temp).getHandCard().get(i).getColor() == colorList.get(0)) {
					winChallenge = true;
					break;
				} else
					winChallenge = false;
			}
			if (winChallenge == true) {
				for (int i = 0; i < 4; i++) {
					player.get(temp).getHandCard().add(cardDeck.get(0));
					cardDeck.remove(0);
				}
			} else if (winChallenge == false) {
				for (int i = 0; i < 6; i++) {
					player.get(count).getHandCard().add(cardDeck.get(0));
					cardDeck.remove(0);
				}
				player.get(count).setTurn(false);
				player.get(temp2).setTurn(true);
			}
		} else if (userInput == 2) {
			for (int i = 0; i < 4; i++) {
				player.get(count).getHandCard().add(cardDeck.get(0));
				cardDeck.remove(0);
			}
			player.get(count).setTurn(false);
			player.get(temp2).setTurn(true);
		}
		for (int i = 0; i < 1000; i++)
			System.out.println();
		return player;
	}

	public ArrayList<UnoDeck> shuffleCard(ArrayList<UnoDeck> cardDeck, ArrayList<UnoDeck> deckOnTable) {
		System.out.println("Deck finished, reshuffling");
		try {
			for (int j = 0; j < deckOnTable.size() - 1; j++) {
				if (cardDeck.get(0).getWild() == "Wild " || cardDeck.get(0).getWild() == "Wild +4 ")
					cardDeck.get(0).setWild(null);
				cardDeck.add(deckOnTable.get(0));
				deckOnTable.remove(0);
			}
		} catch (Exception ex) {
			System.out.println("Not Enough Card For Reshuffling");
		}
		Collections.shuffle(cardDeck);
		System.out.println("reshuffling done");

		return cardDeck;
	}
}
