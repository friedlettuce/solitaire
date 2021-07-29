import java.awt.*;
import java.util.Vector;
import java.util.Collections;

// Card Pile and its children classes

public class Pile {
    // position of the pile
    protected int x;
    protected int y;

    // card of the pile
    protected Vector<Card> mPile;

    Pile(int x, int y) {
        // set postion and allocate for vector of cards
        this.x = x;
        this.y = y;
        mPile = new Vector<Card>();
    }

    // return the top card (visually the last card)
    public final Card top() {
        return mPile.get(mPile.size() - 1);
    }

    // return the number of cards in the pile
    public final int getSize() {
        return mPile.size();
    }

    // return if the pile is empty or not
    public final boolean isEmpty() {
        return mPile.isEmpty();
    }

    // get card at index index from vector of cards (Pile)
    public final Card getCard(int index) {
        return mPile.get(index);
    }

    // draw the top card (bottom most card visually)
    public final Card draw() {
        try {
            return (Card) mPile.remove(mPile.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    // check if the click x and y is in the bound of the pile
    public boolean withinBound(int xBound, int yBound) {
        return (x <= xBound && xBound <= x + Card.width && y <= yBound && yBound <= y + Card.height);
    }

    // inherit method for the children
    public void click() {
    }

    // add card to the top or end of the vector
    public void addCard(Card card) {
        mPile.add(card);
    }

    // display the pile
    public void display(Graphics g) {
        g.setColor(Color.red); // empty pile
        if (isEmpty()) {
            g.drawRect(x, y, Card.width, Card.height);
        } else
            top().draw(g, x, y - 20);
    }

    // inherrit method for children class
    public boolean moveable(Card card) {
        return false;
    }
}

class Foundation extends Pile {
    Foundation(int x, int y) {
        super(x, y);
    }

    // moveable to one of the foundation pile?
    public boolean moveable(Card card) {
        if (isEmpty()) {
            return card.rank() == 0; // Ace
        }

        return (card.suit() == top().suit()) && (card.rank() == 1 + top().rank());
    }
}

// The deck starts with 52 and ends with 24
class Deck extends Pile {

    // insert card and shuffle them
    Deck(int x, int y) {
        super(x, y);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 12; j++)
                addCard(new Card(i, j));

        Collections.shuffle(mPile);
    }

    // on click on the bounds
    public void click() {
        if (isEmpty()) {
            // can only go to wastePile
            if (!Solitaire.wastePile.isEmpty()) {
                while (!Solitaire.wastePile.isEmpty()) {
                    addCard(Solitaire.wastePile.draw());
                }

                for (Card cards : mPile) {
                    cards.flip();
                }

                Collections.shuffle(mPile);

            } else
                return;
        }
        Solitaire.wastePile.addCard(draw());
    }
}

class WastePile extends Pile {
    WastePile(int x, int y) {
        super(x, y);
    }

    // add card and display them face up
    public void addCard(Card card) {
        if (!card.faceUp())
            card.flip();

        // add to the pile
        super.addCard(card);
    }

    // click within the pile
    public void click() {
        // non empty waste pile
        if (!isEmpty()) {
            // moveable to foundation pile first
            for (int i = 0; i < 4; i++)
                if (Solitaire.foundation.get(i).moveable(top())) {
                    Solitaire.foundation.get(i).addCard(draw());
                    return;
                }
            // moveable to tableau pile
            for (int i = 0; i < 7; i++)
                if (Solitaire.tableau.get(i).moveable(top())) {
                    Solitaire.tableau.get(i).addCard(draw());
                    return;
                }

        }
    }

}

// 7 Tableau piles
class TableauPile extends Pile {

    // last face up card
    protected Card breakPoint;

    TableauPile(int x, int y, int n) {
        super(x, y);
        // draw card from deck to the tableau
        for (int i = 0; i < n; i++) {
            Card draw = Solitaire.deckPile.draw();
            addCard(draw);
            if (i == n - 1)
                breakPoint = draw;
        }
        top().flip();
    }

    // check if it is moveable to the pile
    public boolean moveable(Card card) {

        // moveable when the first card is King
        if (isEmpty()) {
            if (card.rank() == 12)
                return true;
            else
                return false;
        }

        Card top = top();

        // color is different and lower than the top card
        boolean retVal = (card.color() != top.color()) && (card.rank() == top.rank() - 1);
        return retVal;
    }

    // are all face up cards movable
    public boolean bottomMove(Card breaker) {

        // Moveable if it's king
        if (isEmpty()) {
            if (breaker.rank() == 12)
                return true;
            else
                return false;
        }

        Card top = top();

        boolean retVal = false;
        // The pile is not itself
        if (breakPoint != breaker) {
            // color is different and lower than the top card
            retVal = (breaker.color() != top.color()) && (breaker.rank() == top.rank() - 1);
        }
        return retVal;
    }

    // The click is within bound
    public boolean withinBound(int xBound, int yBound) {
        return (x <= xBound && xBound <= x + Card.width && y <= yBound);
    }

    // display the pile
    public void display(Graphics g) {
        // offset between the cards
        int offset = y;

        // draw cards
        for (int i = 0; i < mPile.size(); i++) {
            Card aCard = (Card) mPile.get(i);
            aCard.draw(g, x + 10, offset);
            offset += 30;
        }
    }

    // pile on click
    public void click() {

        // break if pile is empty
        if (isEmpty()) {
            return;
        }

        // if the top card is not faced up, turn the card face up and end click
        Card topCard = top();
        if (!topCard.faceUp()) {
            topCard.flip();
            breakPoint = topCard;
            return;
        }

        // determine where the breakpoint where all the face up cards are
        boolean allFaceUp = true;
        for (Card card : mPile) {
            if (card.faceUp() == false) {
                allFaceUp = false;
                break;
            }
        }
        if (allFaceUp)
            breakPoint = getCard(0);

        // check if it's insertable on foundation piles if so execute and end
        for (int i = 0; i < 4; i++)
            if (Solitaire.foundation.get(i).moveable(topCard)) {
                Solitaire.foundation.get(i).addCard(draw());
                return;
            }

        // check if it's insertable on tableau piles if so execute and end
        for (int i = 0; i < 7; i++) {
            if (Solitaire.tableau.get(i).moveable(topCard)) { // one card move
                Solitaire.tableau.get(i).addCard(draw());
                return;
            } else if (Solitaire.tableau.get(i).bottomMove(this.breakPoint)) { // all face up move
                Solitaire.tableau.get(i).addPile(getBottomPile());
                return;
            }
        }

    }

    // add the vector of card to mPile
    public void addPile(Vector<Card> pile) {
        for (Card card : pile) {
            addCard(card);
        }
    }

    // get all the face up cards and split it
    public Vector<Card> getBottomPile() {
        Vector<Card> faceDowns = new Vector<Card>();
        Vector<Card> faceUps = new Vector<Card>();

        for (Card card : mPile) {
            if (card.faceUp() == false)
                faceDowns.add(card);
            else
                faceUps.add(card);
        }

        mPile.removeAllElements();
        mPile = new Vector<Card>(faceDowns);

        return faceUps;

    }

}
