package coms362.cards.war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.CreatePileRemote;
import coms362.cards.events.remote.SetBottomPlayerTextRemote;
import coms362.cards.events.remote.SetGameTitleRemote;
import coms362.cards.events.remote.SetupTableRemote;
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.model.Card;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

public class WarMoveInit implements Move {

	private Table table;
	private Map<Integer, Player> players;
	private String title;

	public WarMoveInit(Map<Integer, Player> players, String game, Table table) {
		this.players = players;
		this.title = game;
		this.table = table;
	}

	public void apply(Table table) {
		Pile dropPile1 = new Pile(WarRules.P1PILE, new Location(300, 100));
		Pile dropPile2 = new Pile(WarRules.P2PILE, new Location(300, 5500));

		ArrayList<Card> deck = new ArrayList<Card>();
		// create a standard deck of 52 cards with random placements
		for (String suit : Card.suits) {
			for (int i = 1; i <= 13; i++) {
				Card card = new Card();
				card.setSuit(suit);
				card.setRank(i);
				deck.add(card);
			}
		}

		//Shuffle the cards
		Collections.shuffle(deck, new Random(58));
		for(int i = 0; i < 52; i++) 
		{
			//Adds cards from the shuffled array to player 1's pile
			Card card1 = deck.get(i++);
			card1.setX(300);
			card1.setY(550);
			dropPile1.addCard(card1);

			//Adds cards from the shuffled array to player 2's pile
			Card card2 = deck.get(i);
			card2.setX(300);
			card2.setY(100);
			dropPile2.addCard(card2);
			
		}
		
		table.addPile(dropPile1);
		table.addPile(dropPile2);
		table.addPile(new Pile(WarRules.PICKUP_PILE, new Location(300, 300), true));
		dropPile1.setFaceUp(false);
		dropPile2.setFaceUp(false);
		for (Player p : players.values()) {
			table.addToScore(p, 26);
		}

	}

	public void apply(ViewFacade view) {
		view.send(new SetupTableRemote());
		view.send(new SetGameTitleRemote(title));

		for (Player p : players.values()) {
			String role = (p.getPlayerNum() == 1) ? "Dealer" : "Player " + p.getPlayerNum();
			view.send(new SetBottomPlayerTextRemote(role, p));
		}

		view.send(new CreatePileRemote(table.getPile(WarRules.P1PILE)));
		view.send(new CreatePileRemote(table.getPile(WarRules.P2PILE)));

		view.send(new CreatePileRemote(table.getPile(WarRules.PICKUP_PILE)));
		
		P52DealButton dealButton = new P52DealButton("DEAL", new Location(50, 50));
		view.register(dealButton); // so we can find it later.
		view.send(new CreateButtonRemote(dealButton));
	}

}

