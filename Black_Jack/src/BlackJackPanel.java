import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

public class BlackJackPanel extends JPanel {

    private final BlackJackLogic game;
    private static final Map<String, Image> IMAGE_CACHE = new HashMap<>();
    private final int cardW = 110, cardH = 154, gap = 5, startX = 20, dealerY = 20, playerY = 420;

    public BlackJackPanel(BlackJackLogic game) {
        this.game = game;
        setBackground(new Color(53,101,77));
        setPreferredSize(new Dimension(735, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dealer
        Hand dealer = game.getDealersHand();
        for (int i = 0; i < dealer.cards().size(); i++) {
            Card card = dealer.cards().get(i);
            boolean hide = (i == 1) && game.getPhase() == BlackJackLogic.Phase.PLAYER_TURN;
            drawCard(g2, card, startX + i * (cardW + gap), dealerY, cardW, cardH, hide);
        }

        // Player
        Hand player = game.getPlayersHand();
        for (int i = 0; i < player.cards().size(); i++) {
            Card card = player.cards().get(i);
            drawCard(g2, card, startX + (cardW + gap) * i, playerY, cardW, cardH, false);
        }

        // Player - Split
        Hand playerSplit = game.getSplitHand();
        for (int i = 0; i < playerSplit.cards().size(); i++) {
            Card card = playerSplit.cards().get(i);
            drawCard(g2, card, startX + (cardW + gap) * i, playerY, cardW, cardH, false);
        }

        // Text
        g2.setColor(Color.WHITE);
        if (game.getPhase() == BlackJackLogic.Phase.ROUND_OVER) {
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            String msg = game.outcomeText();
            g2.drawString(msg, 240, 300);
        } else {
            g2.setFont(new Font("Arial", Font.PLAIN, 18));
            g2.drawString("Your total: " + game.getPlayersHand().getValue(), startX, playerY - 15);
        }
    }

    private void drawCard(Graphics2D g2, Card c, int x, int y, int w, int h, boolean hide) {
        Image img = hide ? getBackImage() : getCardImage(c);
        if(img != null) {
            g2.drawImage(img, x, y, cardW, cardH, this);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, cardW, cardH, 16, 16);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, y, cardW, cardH, 16, 16);
            if(!hide) {
                g2.drawString("?", x + w/2 - 5, y + h/2);
            }
        }
    }

    private Image getCardImage(Card card) {
        String key = card.getRank().name() + "-" + card.getSuit().name();
        Image img = IMAGE_CACHE.get(key);
        if (img == null) {
            img = loadImage(card.imageResourcePath());   // your helper that reads the PNG
            IMAGE_CACHE.put(key, img);                   // save for next time
        }
        return img;
    }

    private Image getBackImage() {
        Image img = IMAGE_CACHE.get("BACK");
        if (img == null) {
            img = loadImage("/cards/BACK.png");
            IMAGE_CACHE.put("BACK", img);
        }
        return img;
    }

    private Image loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            return null;
        }
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }
}