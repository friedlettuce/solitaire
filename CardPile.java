import java.awt.*;
import java.util.Stack;
import java.util.Random;
import java.util.EmptyStackException;
import java.util.Enumeration;

public class CardPile {
    protected int x;
    protected int y;
    protected Stack<Card> thePile;

    CardPile(int x, int y) {
        this.x = x;
        this.y = y;
        thePile = new Stack<Card>();
    }

    public final Card top() {
        return (Card) thePile.peek();
    }

    public final boolean isEmpty() {
        return thePile.empty();
    }

    public final Card pop() {
        try {
            return (Card) thePile.pop();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public boolean includes(int tx, int ty) {
        return x <= tx && tx <= x + Card.width && y <= ty && ty <= y + Card.height;
    }

    public void select(int tx, int ty) {

    }

    public void addCard(Card aCard) {
        thePile.push(aCard);
    }

    public void display(Graphics g) {
        g.setColor(Color.red);
        if (isEmpty()) {
            g.drawRect(x, y, Card.width, Card.height);
        } else
            top().draw(g, x, y - 20);
    }

    public boolean canTake(Card aCard) {
        return false;
    }
}

class SuitPile extends CardPile {
    SuitPile(int x, int y) {
        super(x, y);
    }

    public boolean canTake(Card aCard) {
        if (isEmpty()) {
            return aCard.rank() == 0;
        }
        Card topCard = top();
        return (aCard.suit() == topCard.suit()) && (aCard.rank() == 1 + topCard.rank());
    }
}

class DeckPile extends CardPile {
    DeckPile(int x, int y) {
        super(x, y);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 12; j++)
                addCard(new Card(i, j));

        Random random = new Random();
        for (int i = 0; i < 52; i++) {
            int j = Math.abs(random.nextInt()) % 52;

            Object temp = thePile.elementAt(i);
            thePile.setElementAt(thePile.elementAt(j), i);
            thePile.setElementAt((Card) temp, j);
        }
    }

    public void select(int tx, int ty) {
        if (isEmpty()) {
            return;
        }
        Solitaire.discardPile.addCard(pop());
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

    public void select(int tx, int ty) {
        if (isEmpty())
            return;
        Card topCard = pop();
        for (int i = 0; i < 4; i++)
            if (Solitaire.suitPile[i].canTake(topCard)) {
                Solitaire.suitPile[i].addCard(topCard);
                return;
            }
        for (int i = 0; i < 7; i++)
            if (Solitaire.tableau[i].canTake(topCard)) {
                Solitaire.tableau[i].addCard(topCard);
                return;
            }
        addCard(topCard);
    }
}

class TableauPile extends CardPile {
    TableauPile(int x, int y, int c) {
        super(x, y);
        for (int i = 0; i < c; i++)
            addCard(Solitaire.deckPile.pop());
        top().flip();
    }

    public boolean canTake(Card aCard) {
        if (isEmpty())
            return aCard.rank() == 12;
        Card topCard = top();
        return (aCard.color() != topCard.color()) && (aCard.rank() == topCard.rank() - 1);
    }

    public boolean includes(int tx, int ty) {
        return (x <= tx && tx <= x + Card.width && y <= ty);
    }

    public void display(Graphics g) {
        int localy = y;
        for (Enumeration<Card> e = thePile.elements(); e.hasMoreElements();) {
            Card aCard = (Card) e.nextElement();
            aCard.draw(g, x, localy);
            localy += 35;
        }
    }

    public void select(int tx, int ty) {
        if (isEmpty())
            return;

        Card topCard = top();
        if (!topCard.faceUp()) {
            topCard.flip();
            return;
        }

        topCard = pop();
        for (int i = 0; i < 4; i++)
            if (Solitaire.suitPile[i].canTake(topCard)) {
                Solitaire.suitPile[i].addCard(topCard);
                return;
            }

        for (int i = 0; i < 7; i++)
            if (Solitaire.tableau[i].canTake(topCard)) {
                Solitaire.tableau[i].addCard(topCard);
                return;
            }
        addCard(topCard);
        // get the cards for the build from the suit pile
    while (!isEmpty())
      {
	// stop if we reached a card that is face down
	if (!top().faceUp())
	  break;

	build.addCard(pop());
      }

    // We don't allow the user to play a King card
    // that is at the bottom of a table pile
    // to another table pile
    if (build.top().isKing() && isEmpty())
      {
	while (!build.isEmpty())
	  addCard(build.pop());
	return;
      }    

    // if we have to play only one card
    if (build.top() == topCard)
      {
	// put it back into the table pile
	addCard(build.pop());

	// we have already tried the suit piles
        // see if any other table pile can take card
        for (int i = 0; i < Solitaire.no_table_piles; i++)
          if (Solitaire.tableau[i].canTake(topCard))
            {
              Solitaire.tableau[i].addCard(pop());
              return;
            }
      }
    else // we got ourselves a build to play
      {
	topCard = build.top();

	// see if any other table pile can take this build
	for (int i = 0; i < Solitaire.no_table_piles; i++)
	  if (Solitaire.tableau[i].canTake(topCard))
	    {
	      while (!build.iEmpty())
		Solitaire.tableau[i].addCard(build.pop());
	      
	      return;
	    }
	
	// can't play the build?
	// then we must restore our pile
	while (!build.isEmpty())
	  addCard(build.pop());
    }

}
