import java.util.Stack;
import java.util.EmptyStackException;

public class CardPile
{
	protected int x;
	protected int y;
	protected Stack thePile;

	CardPile(int x1, int y1)
	{
		x = x1;
		y = y1;
		thePile = new Stack();
	}
	public final Card top()
	{
		return (Card)thePile.peek();
	}
	public final boolen isEmpty()
	{
		return thePile.empty();
	}
	public final Card pop()
	{
		try
		{
			return (Card)thePile.pop();
		}
		catch (EmptyStackException e) 
		{
			return null;
		}
	}
	public boolean includes(int tx, int ty)
	{
		return x <= tx && tx <= x + Card.width && y <= ty && ty <= y + Card.height;
	}
	public void select(int tx, int ty)
	{

	}
	public void addCard(Card aCard)
	{
		thePile.push(aCard);
	}
	public void display(Graphics g)
	{
		g.setColor(Color.blue);
		if (isEmpty())
		{
			g.drawRect(x, y, Card.width, Card.height);
		}
		else
			top().draw(g, x, y);
	}
	public boolean canTake(Card aCard)
	{
		return false;
	}

	class SuitPile extends CardPile
	{
		SuitPile(int x, int y)
		{
			super(x, y);
		}
		public boolean canTake(Card aCard)
		{
			if (isEmpty())
			{
				return aCard.rank() == 0;
			}
			Card topCard = top();
			return (aCard.suit() == topCard.suit()) && (aCard.rank() == 1 + topCard.rank());
		}
	}

	class DeckPile extends CardPile
	{
		DeckPile(int x, int y)
		{
			super(x, y);

			for (int i = 0; i < 4; i++)
				for (int j = 0; j <= 12; j++)
					addCard(new Card(i, j));

			Random generator = new Random();
			for (int i = 0, i < 52; i++)
			{
				int j = Math.abs(generator.nextInt()) % 52;

				Object temp = thePile.elementAt(i);
				thePile.setElementAt(thePile.elementAt(j), i);
				thePile.setElementAt(temp, j);
			}
		}
		public void select(int tx, int ty)
		{
			if (isEmpty())
			{
				return;
			}
			Solitaire.discardPile.addCard(pop());
		}
	}
	class DiscardPile extends CardPile
	{
		DiscardPile (int x, int y)
		{
			super (x, y);
		}
		
		public void addCard (Card aCard)
		{
			if (! aCard.faceUp())
			{
				aCard.flip();
			}
			super.addCard(aCard);
		}
		public void select (int tx, int ty)
		{
			if (isEmpty())
			{
				return;
			}
			Card topCard = pop();
			for (int i = 0; i < 4; i++)
				if (Solitaire.suitPile[i].canTake(topCard))
				{
					Solitaire.suitPile[i].addCard(topCard);
					return;
				}
			for (int i = 0; i < 7; i++)
				if (Solitaire.tableau[i].canTake(topCard))
				{
					Solitaire.tableau[i].addCard(topCard);
					return;
				}
			addCard(topCard);
		}
	}
	class TablePile extends CardPile
	{
		TablePile (int x, int y, int c)
		{
			super(x, y);
			for (int i = 0; i < c; i++)
			{
				addCard(Solitaire.deckPile.pop());
			}
			top().flip();
		}
		
		public boolean canTake (Card aCard)
		{
			if (isEmpty())
			{
				return aCard.rank() == 12;
			}
			Card topCard = top();
			return (aCard.color() != topCard.color()) && (aCard.rank() == topCard.rank() - 1);
		}
		
		public boolean includes (int tx, int ty)
		{
			return x <= tx && tx <= x + Card.width && y <= ty;
		}
		
		public void display (Graphics g)
		{
			int localy = y;
			for (Enumeration e = thePile.elements(); e.hashMoreElements();)
			{
				Card aCard = (Card) e.nextElement();
				aCard.draw (g, x, localy);
				localy += 35;
			}
		}
		
		public void select (int tx, int ty)
		{
			if (isEmpty())
			{
				return;
			}
			
			Card topCard = top();
			if (! topCard.faceUp())
			{
				topCard.flip();
				return;
			}
			
			topCard = pop();
			for (int i = 0; i < 4; i++)
				if (Solitaire.suitPile[i].canTake(topCard))
				{
					Solitaire.suitPile[i].addCard(topCard);
					return;
				}
			for (int i = 0; i < 7; i++)
				if (Solitaire.tableau[i].canTake(topCard))
				{
					Solitaire.tableau[i].addCard(topCard);
					return;
				}
			addCard(topCard);
		}
	}
};
