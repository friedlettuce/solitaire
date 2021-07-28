import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.*;

public class Card {
    public final static int width = 70;
    public final static int height = 100;
    public final static int heart = 0;
    public static int spade = 1;
    public final static int diamond = 2;
    public final static int club = 3;
    BufferedImage image;
    BufferedImage back;

    private boolean faceup;
    private int r;
    private int s;

    Card(int sv, int rv) {
        s = sv;
        r = rv;
        faceup = false;

        String ranks[] = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };
        String suits[] = { "h", "s", "d", "c" };
        System.out.println(ranks[r] + suits[s]);
        URL url = getClass().getResource("/images/" + ranks[r] + suits[s] + ".gif");
        URL backUrl = getClass().getResource("/images/b.gif");
        System.out.println(url);
        try {
            image = ImageIO.read(url);
            back = ImageIO.read(backUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int rank() {
        return r;
    }

    public int suit() {
        return s;
    }

    public boolean faceUp() {
        return faceup;
    }

    public void flip() {
        faceup = !faceup;
    }

    public Color color() {
        if (faceUp()) {
            if (suit() == heart || suit() == diamond) {
                return Color.red;
            } else
                return Color.black;
        }
        return null;
    }

    public void draw(Graphics g, int x, int y) {

        g.clearRect(x, y + 20, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y + 20, width, height);

        g.setColor(color());
        if (faceUp()) {
            g.drawImage(image, x, y + 20, width, height, null);

        } else {
            g.drawImage(back, x, y + 20, width, height, null);

        }
    }
};
