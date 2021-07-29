import java.awt.*;
import java.util.Vector;
import java.util.Collections;

public class CardPile {
    protected int x;
    protected int y;
    protected Vector<Card> thePile;

    CardPile(int x, int y) {
        this.x = x;
        this.y = y;
        thePile = new Vector<Card>();
    }

    public final Card top() {
        return (Card) thePile.get(thePile.size() - 1);
    }

    public final int getSize() {
        return thePile.size();
    }

    public final boolean isEmpty() {
        return thePile.isEmpty();
    }

    public final Card getCard(int index) {
        return thePile.get(index);
    }

    public final Card draw() {
        try {
            return (Card) thePile.remove(thePile.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean includes(int xBound, int yBound) {
        return (x <= xBound && xBound <= x + Card.width && y <= yBound && yBound <= y + Card.height);
    }

    public void select() {

    }

    public void addCard(Card aCard) {
        thePile.add(aCard);
    }

    public void display(Graphics g) {
        g.setColor(Color.red);
        if (isEmpty()) {
            g.drawRect(x, y, Card.width, Card.height);
        } else
            top().draw(g, x, y - 20);
    }

    public boolean moveable(Card aCard) {
        return false;
    }
}

class SuitPile extends CardPile {
    SuitPile(int x, int y) {
        super(x, y);
    }

    public boolean moveable(Card card) {
        if (isEmpty()) {
            return card.rank() == 0;
        }
        Card topCard = top();
        return (card.suit() == topCard.suit()) && (card.rank() == 1 + topCard.rank());
    }
}

class Deck extends CardPile {
    Deck(int x, int y) {
        super(x, y);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 12; j++)
                addCard(new Card(i, j));

        Collections.shuffle(thePile);
    }

    public void select() {
        if (isEmpty()) {
            if (!Solitaire.discardPile.isEmpty()) {
                while (!Solitaire.discardPile.isEmpty()) {
                    addCard(Solitaire.discardPile.draw());
                }

                for (Card cards : thePile) {
                    cards.flip();
                }
                Collections.shuffle(thePile);

            } else
                return;
        }
        Solitaire.discardPile.addCard(draw());
    }
}

class DiscardPile extends CardPile {
    DiscardPile(int x, int y) {
        super(x, y);
    }

    public void addCard(Card aCard) {
        if (!aCard.faceUp())
            aCard.flip();

        super.addCard(aCard);
    }

    public void select() {

        if (!isEmpty()) {
            Card topCard = draw();
            for (int i = 0; i < 4; i++)
                if (Solitaire.suitPile.get(i).moveable(topCard)) {
                    Solitaire.suitPile.get(i).addCard(topCard);
                    return;
                }
            for (int i = 0; i < 7; i++)
                if (Solitaire.tableau.get(i).moveable(topCard)) {
                    Solitaire.tableau.get(i).addCard(topCard);
                    return;
                }
            addCard(topCard);
        }
    }

}

class TableauPile extends CardPile {
    protected Card breakPoint;

    TableauPile(int x, int y, int c) {
        super(x, y);
        for (int i = 0; i < c; i++) {
            Card draw = Solitaire.deckPile.draw();
            addCard(draw);
            if (i == c - 1)
                breakPoint = draw;
        }
        top().flip();
    }

    public boolean moveable(Card card) {

        if (isEmpty()) {
            if (card.rank() == 12)
                return true;
            else
                return false;
        }
        Card top = top();

        boolean retVal = (card.color() != top.color()) && (card.rank() == top.rank() - 1);
        return retVal;
    }

    public boolean bottomMove(Card breaker) {
        System.out.println("this pile is: " + isEmpty());
        if (isEmpty()) {
            if (breaker.rank() == 12)
                return true;
            else
                return false;
        }

        Card top = top();

        boolean retVal = false;
        if (breakPoint != breaker) {
            retVal = (breaker.color() != top.color()) && (breaker.rank() == top.rank() - 1);
            System.out.println("Pile with " + (breaker.rank() + 1) + " moveable to top card with " + (top.rank() + 1)
                    + "is " + retVal);
            System.out.println("Pile Moveable: " + retVal);
        }
        return retVal;
    }

    public boolean includes(int xBound, int yBound) {
        return (x <= xBound && xBound <= x + Card.width && y <= yBound);
    }

    public void display(Graphics g) {
        int offset = y;
        for (int i = 0; i < thePile.size(); i++) {
            Card aCard = (Card) thePile.get(i);
            aCard.draw(g, x + 10, offset);
            offset += 30;
        }
    }

    public void select() {
        System.out.println(breakPoint.rank());
        if (isEmpty()) {
            return;
        }

        Card topCard = top();
        if (!topCard.faceUp()) {
            topCard.flip();
            breakPoint = topCard;
            return;
        }

        boolean allFaceUp = true;
        for (Card card : thePile) {
            if (card.faceUp() == false) {
                allFaceUp = false;
                break;
            }
        }
        if (allFaceUp)
            breakPoint = getCard(0);

        // topCard = draw();
        for (int i = 0; i < 4; i++)
            if (Solitaire.suitPile.get(i).moveable(topCard)) {
                Solitaire.suitPile.get(i).addCard(draw());
                return;
            }

        for (int i = 0; i < 7; i++) {
            if (Solitaire.tableau.get(i).moveable(topCard)) {
                Solitaire.tableau.get(i).addCard(draw());
                return;
            } else if (Solitaire.tableau.get(i).bottomMove(this.breakPoint)) {
                Solitaire.tableau.get(i).addPile(getBottomPile());
                return;
            }
        }
        // addCard(topCard);
    }

    public void addPile(Vector<Card> pile) {
        for (Card card : pile) {
            addCard(card);
        }
    }

    public Vector<Card> getBottomPile() {
        Vector<Card> faceDowns = new Vector<Card>();
        Vector<Card> faceUps = new Vector<Card>();

        for (Card card : thePile) {
            if (card.faceUp() == false)
                faceDowns.add(card);
            else
                faceUps.add(card);
        }

        thePile.removeAllElements();
        thePile = new Vector<Card>(faceDowns);

        return faceUps;

    }

}
