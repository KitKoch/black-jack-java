import java.util.*;

public final class Deck {

    private final List<Card> cards = new ArrayList<>();
    public Deck(){
        reset();
    }

    public void reset() {
        cards.clear();
        for(int i = 0; i < Card.Suit.values().length; i++) {
            for(int j = 0; j < Card.Rank.values().length; j++) {
                Card.Suit suit = Card.Suit.values()[i];
                Card.Rank rank = Card.Rank.values()[j];
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.remove(cards.size()-1);
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
