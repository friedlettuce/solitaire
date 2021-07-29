
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;

public class Solitaire {

    // Static variables to share within this class and the CardPile class
    public static Deck deckPile;
    public static WastePile wastePile;
    public static ArrayList<TableauPile> tableau;
    public static ArrayList<Foundation> foundation;
    public static ArrayList<CardPile> totalPiles;
    private JFrame game;

    // Create an instance in main
    public static void main(String args[]) {
        new Solitaire();
    }

    // run the game
    public Solitaire() {
        game = new Game();
        initialize();
        game.setVisible(true);
    }

    public void initialize() {

        totalPiles = new ArrayList<CardPile>();
        foundation = new ArrayList<Foundation>();
        tableau = new ArrayList<TableauPile>();

        // set bounds for DeckPile and add it
        deckPile = new Deck(15, 45);
        totalPiles.add(deckPile);

        // set bounds to WastePile and add it
        wastePile = new WastePile(100, 45);
        totalPiles.add(wastePile);

        // place it in foundation
        for (int i = 0; i < 4; i++) {
            foundation.add(new Foundation(250 + (Card.width + 10) * i, 40));
            totalPiles.add(foundation.get(i));
        }

        // place it in tableau
        for (int i = 0; i < 7; i++) {
            tableau.add(new TableauPile(15 + (Card.width + 5) * i, Card.height + 40, i + 1));
            totalPiles.add(tableau.get(i));
        }

    }

    // Frame for the game
    class Game extends JFrame {

        // set up the frame
        public Game() {
            // resolution and the frame
            setSize(630, 800);
            setTitle("Solitaire");

            // add the listener for the clicks
            addMouseListener(new Mouse());

            // new game buton
            JButton restartButton = new JButton("New Game");
            restartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    initialize();
                    repaint();
                }
            });

            // panel for the buttons, new game and close
            JPanel panel = new JPanel();
            add("South", panel);
            panel.add(restartButton);

            // close button to close the game on click
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            panel.add(closeButton);
        }

        // mouse listener
        private class Mouse extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                int xPos = e.getX();
                int yPos = e.getY();

                // check for bounds with all the piles
                for (int i = 0; i < 13; i++)
                    if (totalPiles.get(i).withinBound(xPos, yPos)) {
                        totalPiles.get(i).click();
                        repaint();
                    }

                // check win everytime a card is moved, win message is displayed when the game
                // is won
                if (checkWin())
                    JOptionPane.showMessageDialog(null, "You won!");
            }
        }

        // show the graphics to the screen
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < 13; i++) {
                totalPiles.get(i).display(g);
            }
        }

        // check if the game is won
        public boolean checkWin() {
            boolean win = false;
            for (int i = 0; i < 4; i++) {
                int size = foundation.get(i).getSize();
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
