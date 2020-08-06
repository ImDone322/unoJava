import java.util.Comparator;

public class UnoDeck implements Comparable<UnoDeck> {

	private String color;
	private String value;
	private String wild;
	private String actionCard;

	public UnoDeck() {
	}

	public UnoDeck(String color, String value, String actionCard) {
		this.color = color;
		this.value = value;
		this.actionCard = actionCard;
	}

	public UnoDeck(String wild, String color) {
		this.wild = wild;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public String getValue() {
		return value;
	}

	public String getWild() {
		return wild;
	}

	public void setWild(String color) {
		this.color = color;
	}

	public String getActionCard() {
		return actionCard;
	}

	@Override
	public int compareTo(UnoDeck sortCard) {
//		if (sortCard.getColor() == null)
//			return (this.getColor() == null) ? 0 : -1;
//		if (this.getColor() == null)
//			return 1;
//
//		return sortCard.getColor().compareTo(this.getColor());

		if (sortCard.getColor() == null)
			return (this.getColor() == null) ? 0 : -1;
		if (this.getColor() == null)
			return 1;

		if (sortCard.getColor().compareTo(this.getColor()) == 0) {
			if (sortCard.getValue() == null)
				return (this.getValue() == null) ? 0 : -1;
			if (this.getValue() == null)
				return 1;
			return sortCard.getValue().compareTo(this.getValue());
		}

		return sortCard.getColor().compareTo(this.getColor());
	}

}
