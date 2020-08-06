import java.util.ArrayList;

public class InGame {

	public static void main(String[] args) {
		// Create Player
		ArrayList<Player> playerList = new ArrayList<>();

		GameFunction Function = new GameFunction();
		playerList = Function.UserInput(playerList);

		// Generate Uno Deck
		ArrayList<UnoDeck> cardDeck = new ArrayList<>();
		cardDeck = Function.genDeck();

		// Player played card
		ArrayList<UnoDeck> deckOnTable = new ArrayList<>();

		// Distribute Card
		while (playerList.get(playerList.size() - 1).getHandCard().size() != 7) {
			for (int i = 0; i < playerList.size(); i++) {
				playerList.get(i).getHandCard().add(cardDeck.get(0));
				cardDeck.remove(0);
			}
		}

		// clear console
		for (int i = 0; i < 1000; i++)
			System.out.println();

		System.out.println("Game Start");

		// initialize the first card
		while (cardDeck.get(0).getValue() == null) {
			cardDeck.add(cardDeck.get(0));
			cardDeck.remove(0);
		}
		deckOnTable.add(cardDeck.get(0));
		cardDeck.remove(0);

		// Game Start
		boolean reverse = false;
		boolean skipOne = false;
		int i = 0;
		int count = 0;
		String temp = "";
		playerList.get(0).setTurn(true);

		do {
			count = i % playerList.size();

			if (cardDeck.isEmpty())
				cardDeck = Function.shuffleCard(cardDeck, deckOnTable);

			playerList = Function.playerTurn(playerList, cardDeck, count, deckOnTable, skipOne, reverse);

			if (playerList.get(count).getHandCard().isEmpty()) {
				playerList.get(count).setWin(true);
				temp = playerList.get(count).getName();
				break;
			}
			i++;
		} while (playerList.get(count).isWin() == false);

		System.out.println("Game Finished");
		System.out.println(temp + " won");
	}
}