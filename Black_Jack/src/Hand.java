import java.util.*;

public final class Hand {

    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        cards.add(card);
    }

    public List<Card> cards() {
        return cards;
    }

    public int getValue() {
        int sum = 0;
        int aceCount = 0;
        for(Card card : cards) {
            sum += card.getRank().getValue();
            if(card.isAce()) {
                aceCount++;
            }
        }
        while(sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }

    public boolean isBust() {
        return getValue() > 21;
    }

    public boolean isBlackJack() {
        return (cards.size() == 2) && (getValue() == 21);
    }

    @Override
    public String toString() {
        String deck = "";
        for(Card card : cards) {
            deck += card.toString() + " ";
        }
        return deck;
    }
}
