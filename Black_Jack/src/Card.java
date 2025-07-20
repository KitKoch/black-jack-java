public final class Card {

    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };
    public enum Rank {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10);

        private final int value;
        Rank(int v) {
            value = v;
        }
        public int getValue() {
            return value;
        }
    }
    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }

    public boolean isAce() {
        return rank == Rank.ACE;
    }

    public String imageResourcePath() {
        return "./cards/" + rank.name() + "-" + suit.name() + ".png";
    }

    @Override
    public String toString() {
        return rank +  "-" + suit;
    }
    @Override
    public boolean equals(Object obj) {
        return (this == obj) || (obj instanceof Card card && card.rank == rank && card.suit == suit);
    }

}
