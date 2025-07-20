public class BlackJackLogic {

    public enum Phase { PLAYER_TURN, DEALER_TURN, ROUND_OVER }
    public enum Result { PLAYER_BUST, DEALER_BUST, PLAYER_WIN, DEALER_WIN, PUSH }

    private final Deck deck = new Deck();
    private final Hand dealersHand = new Hand();

    private final Hand playersHand = new Hand();
    private final Hand splitHand = new Hand();

    private Phase phase;
    private Result result;

    public BlackJackLogic(){
        startRound();
    }

    public Deck getDeck() {
        return deck;
    }
    public Hand getPlayersHand() {
        return playersHand;
    }
    public Hand getDealersHand() {
        return dealersHand;
    }
    public Hand getSplitHand() {
        return splitHand;
    }
    public Phase getPhase() {
        return phase;
    }
    public Result getResult() {
        return result;
    }

    public void startRound() {
        //Preset
        deck.reset();
        dealersHand.cards().clear();
        playersHand.cards().clear();
        splitHand.cards().clear();
        result = null;

        //Initial 2 cards each
        playersHand.add(deck.dealCard());
        dealersHand.add(deck.dealCard());
        playersHand.add(deck.dealCard());
        dealersHand.add(deck.dealCard());

        phase = Phase.PLAYER_TURN;


        if(playersHand.isBlackJack()) {
            stand();
        }
    }

    public void hit(Hand hand) {
        if(phase != Phase.PLAYER_TURN) {
            return;
        }
        hand.add(deck.dealCard());
        if(hand.isBust()) {
            result = Result.PLAYER_BUST;
            phase = Phase.ROUND_OVER;
        }
    }

    public void stand() {
        if(phase != Phase.PLAYER_TURN) {
            return;
        }
        phase = Phase.DEALER_TURN;
        while(dealersHand.getValue() < 17) {
            dealersHand.add(deck.dealCard());
        }
        if(dealersHand.isBust()) {
            result = Result.DEALER_BUST;
        } else if(dealersHand.getValue() > playersHand.getValue()) {
            result = Result.DEALER_WIN;
        } else if(dealersHand.getValue() < playersHand.getValue()) {
            result = Result.PLAYER_WIN;
        } else {
            result = Result.PUSH;
        }
        phase = Phase.ROUND_OVER;
    }

    public String outcomeText() {
        String status = switch (phase) {
            case PLAYER_TURN -> "Player's turn";
            case DEALER_TURN -> "Dealer's turn";
            case ROUND_OVER -> switch (result) {
                case PLAYER_BUST -> "You busted at " + playersHand.getValue();
                case DEALER_BUST -> "Dealer busted at " + dealersHand.getValue();
                case PLAYER_WIN -> "You won at " + playersHand.getValue();
                case DEALER_WIN -> "Dealer won at " + dealersHand.getValue();
                case PUSH -> "Push (tie) at " + playersHand.getValue();
            };
        };
        return status;
    }
}
