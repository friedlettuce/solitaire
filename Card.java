import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.*;

public class Card {
	public final static int width = 50;
	public final static int height = 70;
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

		URL url = getClass().getResource("/images/2h.gif");
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
		return Color.yellow;
	}

	public void draw(Graphics g, int x, int y) {
		// String names[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J",
		// "Q", "K" };

		g.clearRect(x, y, width, height);
		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);

		g.setColor(color());
		if (faceUp()) {
			g.drawImage(image, x, y, 20, 60, null);

			// g.drawString(names[rank()], x + 3, y + 15);

			// if (suit() == heart) {
			// g.drawLine(x + 25, y + 30, x + 35, y + 20);
			// g.drawLine(x + 35, y + 20, x + 45, y + 30);
			// g.drawLine(x + 45, y + 30, x + 25, y + 60);
			// g.drawLine(x + 25, y + 60, x + 5, y + 30);
			// g.drawLine(x + 5, y + 30, x + 15, y + 20);
			// g.drawLine(x + 15, y + 20, x + 25, y + 30);
			// } else if (suit() == spade) {
			// g.drawLine(x + 25, y + 20, x + 40, y + 50);
			// g.drawLine(x + 40, y + 50, x + 10, y + 50);
			// g.drawLine(x + 10, y + 50, x + 25, y + 20);
			// g.drawLine(x + 23, y + 45, x + 20, y + 60);
			// g.drawLine(x + 20, y + 60, x + 30, y + 60);
			// g.drawLine(x + 30, y + 60, x + 27, y + 45);
			// } else if (suit() == diamond) {
			// g.drawLine(x + 25, y + 20, x + 40, y + 40);
			// g.drawLine(x + 40, y + 40, x + 25, y + 60);
			// g.drawLine(x + 25, y + 60, x + 10, y + 40);
			// g.drawLine(x + 10, y + 40, x + 25, y + 20);
			// } else if (suit() == club) {
			// g.drawOval(x + 20, y + 25, 10, 10);
			// g.drawOval(x + 25, y + 35, 10, 10);
			// g.drawOval(x + 15, y + 35, 10, 10);
			// g.drawLine(x + 23, y + 45, x + 20, y + 55);
			// g.drawLine(x + 20, y + 55, x + 30, y + 55);
			// g.drawLine(x + 30, y + 55, x + 27, y + 45);
			// }

		} else {
			g.drawImage(back, x, y, 20, 60, null);

			// g.drawLine(x + 15, y + 5, x + 15, y + 65);
			// g.drawLine(x + 35, y + 5, x + 35, y + 65);
			// g.drawLine(x + 5, y + 20, x + 45, y + 20);
			// g.drawLine(x + 5, y + 35, x + 45, y + 35);
			// g.drawLine(x + 5, y + 50, x + 45, y + 50);

		}
	}
};
