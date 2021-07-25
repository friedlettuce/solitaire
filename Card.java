import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class Card extends JPanel {
    private int suit;
    private int rank;

    // more private variables on the card

    // graphic components
    ImageIcon image;
    Point imageCorner;
    Point prevPt;

    public Card(int suit, int rank) {
        // needs the values of the card

        // TODO: the card object

        // graphic components
        image = new ImageIcon("cards/diamond_king.png");
        imageCorner = new Point(0, 0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    // Card mechanics, and object retrieval

    // Cards Component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            Point currentPt = e.getPoint();
            imageCorner.translate((int) (currentPt.getX() - prevPt.getX()), (int) (currentPt.getY() - prevPt.getY()));
            prevPt = currentPt;
            repaint();
        }
    }

}
