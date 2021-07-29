import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.*;

public class Card {

    // Suits
    final public static int heart = 0;
    final public static int spade = 1;
    final public static int diamond = 2;
    final public static int club = 3;

    // Image for the cards
    BufferedImage image;
    BufferedImage back;

    // The height and width of the card graphics
    final public static int width = 70;
    final public static int height = 100;

    // Values of the card
    private boolean faceUp;
    private int r;
    private int s;

    // Constructor
    Card(int suit, int rank) {
        s = suit;
        r = rank;
        faceUp = false;

        // Information for the picture

        // query for the retrieving image
        String ranks[] = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };
        String suits[] = { "h", "s", "d", "c" };

        // get path
        URL url = getClass().getResource("/images/" + ranks[r] + suits[s] + ".gif");
        URL backUrl = getClass().getResource("/images/b.gif");

        // set image to path
        try {
            image = ImageIO.read(url);
            back = ImageIO.read(backUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // card ranks
    public int rank() {
        return r;
    }

    // card suit
    public int suit() {
        return s;
    }

    // face up true or false
    public boolean faceUp() {
        return faceUp;
    }

    // flip the card
    public void flip() {
        faceUp = !faceUp;
    }

    // get color of the card
    public Color color() {
        if (faceUp()) {
            if (suit() == club || suit() == spade) {
                return Color.black;
            } else
                return Color.red;
        }
        return Color.white;
    }

    // draw the image
    public void draw(Graphics g, int x, int y) {
        if (faceUp()) {
            g.drawImage(image, x, y + 20, width, height, null);

        } else {
            g.drawImage(back, x, y + 20, width, height, null);

        }
    }
};
