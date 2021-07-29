
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;

public class Solitaire {
    public static Deck deckPile;
    public static DiscardPile discardPile;
    public static ArrayList<TableauPile> tableau;
    public static ArrayList<SuitPile> suitPile;
    public static ArrayList<CardPile> allPiles;
    private JFrame game;

    public static void main(String args[]) {
        new Solitaire();
    }

    public Solitaire() {
        game = new SolitaireFrame();
        init();
        game.setVisible(true);
    }

    public void init() {
        allPiles = new ArrayList<CardPile>();
        suitPile = new ArrayList<SuitPile>();
        tableau = new ArrayList<TableauPile>();

        deckPile = new Deck(15, 45);
        allPiles.add(deckPile);
        discardPile = new DiscardPile(100, 45);
        allPiles.add(discardPile);
        for (int i = 0; i < 4; i++) {
            suitPile.add(new SuitPile(250 + (Card.width + 10) * i, 40));
            allPiles.add(suitPile.get(i));
        }

        for (int i = 0; i < 7; i++) {
            tableau.add(new TableauPile(15 + (Card.width + 5) * i, Card.height + 40, i + 1));
            allPiles.add(tableau.get(i));
        }

    }

    class SolitaireFrame extends JFrame {

        private class Mouse extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (int i = 0; i < 13; i++)
                    if (allPiles.get(i).includes(x, y)) {
                        allPiles.get(i).select();
                        repaint();
                    }

                if (checkWin())
                    JOptionPane.showMessageDialog(null, "You won!");
            }
        }

        public SolitaireFrame() {
            setSize(630, 600);
            setTitle("Solitaire");
            addMouseListener(new Mouse());
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
                allPiles.get(i).display(g);
            }
        }

        public boolean checkWin() {
            boolean win = false;
            for (int i = 0; i < 4; i++) {
                int size = suitPile.get(i).getSize();
                if (size == 13)
                    win = true;
                else {
                    win = false;
                    break;
                }
            }
            return win;
        }

    }
}
