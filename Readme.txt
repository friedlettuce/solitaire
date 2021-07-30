Solitaire Game
Group Members: Gam San, Cullen Hemsouvanh, Garret Anderson

\/Classes\/
Card:
    Card object to keep image data, rank, and suit informations

CardPiles:
    The Parent Class for all the piles

Deck:
	An initializing deck that ends up with 24 as a Hand Pile in 
    traditional game rule

WastePile:
    Flipped card from the Deck

Foundation:
    Four piles that you can stack from Ace to King in order with the same suits

TableauPile:
	7 piles
	Formation: left to right
		start with placing 7:
		place first card face up
		place the rest to the end face down
		repeat, each time starting one right until done



Solitaire:
    Outer class to start the game

Game:
    Inner class of Solitaire that extends JFrame to display the graphics
    and where it checks for the clicks and update the game state.

\/Gameplay & GUI Setup\/
The game is set up so that you can either move a single card
or if you're trying to move a tableau pile, you can move all
stacks of facedUp cards

When the piles are clicked, the foundation pile will take priority
then the single card move, then whole stack of card will be moved.
Once the cards are in foundation pile, it can not be moved back.

PLAY
- Cards in tableau stacked on next highest card of alternate color
- Once card moved, if top card face down, face up
- Ace needed to start foundation pile, has to follow suit Ace to King

- If no cards played flip one from waste
- Stack cards on top of each other until no more in waste
- Start Deck over from the flipped cards
- If space opens up in tableau, can fill with a king
- Wins once suit sequences are accomplished

Sources
http://www.it.uu.se/edu/course/homepage/oop/ht00/Local/Budd_Java/ftp.cs.orst.edu/pub/budd/java/solitaire/solit.pdf

Used source as concept basis for program structure and functionality.
