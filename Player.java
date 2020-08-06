import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<UnoDeck> handCard;
	private boolean turn;
	private boolean challenge;
	private boolean win;

	public Player() {

	}

	public Player(String name) {
		this.name = name;
		handCard = new ArrayList<>();
	}

	public Player(ArrayList<UnoDeck> handCard) {
		this.handCard = handCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<UnoDeck> getHandCard() {
		return handCard;
	}

	public void setHandCard(ArrayList<UnoDeck> handCard) {
		this.handCard = handCard;
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public boolean getChallenge() {
		return challenge;
	}

	public void setChallenge(boolean challenge) {
		this.challenge = challenge;
	}
	
	public boolean isWin() {
		win = false;
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
}