import javax.swing.*;
import java.awt.*;

public class BlackJackMain {

    public static void main(String[] args) {
            BlackJackLogic game = new BlackJackLogic();
            game.startRound();

            BlackJackPanel panel = new BlackJackPanel(game);

            JButton hit   = new JButton("Hit");
            JButton stand = new JButton("Stand");
            JButton newR  = new JButton("New Round");

            //Hit
            hit.addActionListener(e -> {
                if (game.getPhase() != BlackJackLogic.Phase.ROUND_OVER) {
                    game.hit(game.getPlayersHand());
                    if (game.getPlayersHand().isBust()) {
                        hit.setEnabled(false);
                        stand.setEnabled(false);
                        game.stand();
                    }
                    panel.repaint();
                }
            });

            //Stand
            stand.addActionListener(e -> {
                if (game.getPhase() != BlackJackLogic.Phase.ROUND_OVER) {
                    game.stand();
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                    panel.repaint();
                }
            });

            //New Round
            newR.addActionListener(e -> {
                game.startRound();
                hit.setEnabled(true);
                stand.setEnabled(true);
                if(game.getResult() == BlackJackLogic.Result.PUSH) {
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                }
                panel.repaint();
            });

            JPanel buttons = new JPanel();
            buttons.add(hit);
            buttons.add(stand);
            buttons.add(newR);

            JFrame f = new JFrame("Black Jack");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(false);
            f.add(panel, BorderLayout.CENTER);
            f.add(buttons, BorderLayout.SOUTH);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        };
}