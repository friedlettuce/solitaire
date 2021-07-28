
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Graphics;

public class Solitaire {
    static public DeckPile deckPile;
    static public DiscardPile discardPile;
    static public TableauPile tableau[];
    static public SuitPile suitPile[];
    static public CardPile allPiles[];
    private JFrame window;

    public static void main(String args[]) {
        Solitaire world = new Solitaire();
    }

    public Solitaire() {
        window = new SolitaireFrame();
        init();
        window.setVisible(true);
    }

    public void init() {
        allPiles = new CardPile[13];
        suitPile = new SuitPile[4];
        tableau = new TableauPile[7];

        allPiles[0] = deckPile = new DeckPile(15, 25);
        allPiles[1] = discardPile = new DiscardPile(100, 45);
        for (int i = 0; i < 4; i++)
            allPiles[2 + i] = suitPile[i] = new SuitPile(250 + (Card.width + 10) * i, 40);

        for (int i = 0; i < 7; i++)
            allPiles[6 + i] = tableau[i] = new TableauPile(15 + (Card.width + 5) * i, Card.height + 40, i + 1);

    }

    class SolitaireFrame extends JFrame {

        private class MouseKeeper extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (int i = 0; i < 13; i++)
                    if (allPiles[i].includes(x, y)) {
                        allPiles[i].select(x, y);
                        repaint();
                    }
            }
        }

        public SolitaireFrame() {
            setSize(630, 600);
            setTitle("Solitaire");
            addMouseListener(new MouseKeeper());
            JButton restartButton = new JButton("New Game");
            restartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    init();
                    repaint();
                }
            });

            JPanel panel = new JPanel();

            add("South", panel);

            panel.add(restartButton);

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            panel.add(closeButton);
        }

        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < 13; i++) {
                allPiles[i].display(g);
            }
        }

    }
}