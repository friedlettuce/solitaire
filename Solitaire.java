public class Solitaire extends Applet 
{
  final static int no_card_piles  = 13;
  final static int no_suit_piles  = 4;
  final static int no_table_piles = 7;

  final static int topMargin      = 40;
  final static int leftMargin     = 5;
  final static int distTable      = 5;
  final static int distSuit       = 10;

  static DeckPile deckPile;
  static DiscardPile discardPile;
  static TablePile tableau[];
  static SuitPile suitPile[];
  static CardPile allPiles[];

  protected void initialize()
  {
    // first allocate the arrays
    allPiles = new CardPile[no_card_piles];
    suitPile = new SuitPile[no_suit_piles];
    tableau  = new TablePile[no_table_piles];

    // then fill them in
    int xDeck = leftMargin + (no_table_piles - 1) * (Card.width + distTable);
    allPiles[0] = deckPile = new DeckPile(xDeck, topMargin);
    allPiles[1] = discardPile = new DiscardPile(xDeck - Card.width - distSuit, 
						topMargin);

    for (int i = 0; i < no_suit_piles; i++)
      allPiles[2+i] = suitPile[i] =
	new SuitPile(leftMargin + (Card.width + distSuit) * i, topMargin);

    for (int i = 0; i < no_table_piles; i++)
      allPiles[6+i] = tableau[i] =
	new TablePile(leftMargin + (Card.width + distTable) * i, 
		      Card.height + distTable + topMargin, i+1); 

    showStatus("Welcome to Solitaire!");
  }

  protected boolean gameEnded()
  {
    if (!deckPile.empty())
      return false;

    if (!discardPile.empty())
      return false;

    for (int i = 0; i < no_table_piles; i++)
      if (!tableau[i].empty())
	return false;

    // if we reached this point then all piles are empty
    // notify the user in the status bar
    showStatus("Congratulations! You have won this game.");

    return true;
  }

  public void init() 
  {
    // Create a new game button
    Button b = new Button("New game");

    // Define, instantiate, and register a listener to handle button presses
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) 
	{  // start a new game
	  initialize();
	  repaint();
	}
    });

    // Add the button to the applet
    add(b);

    // Define, instantiate and register a MouseListener object.
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) 
	{
	  if (gameEnded())
	    return;
	  
	  showStatus("Neat click!");

	  int x = e.getX();
	  int y = e.getY(); 

	  for (int i = 0; i < no_card_piles; i++)
	    if (allPiles[i].includes(x, y))
	      {
		allPiles[i].select(x, y);
		repaint();
	      }
	} 
    }); 

    initialize();
  }

  public void paint(Graphics g) 
  {
    for (int i = 0; i < no_card_piles; i++)
      allPiles[i].display(g);
  }
}
