package coms362.cards.war;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.AddToPileTopRemote;
import coms362.cards.events.remote.HideCardRemote;
import coms362.cards.events.remote.RemoveFromPileRemote;
import coms362.cards.events.remote.ShowCardRemote;
import coms362.cards.events.remote.ShowPlayerScore;
import coms362.cards.fiftytwo.P52Rules;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class WarCardMove implements Move{
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RED = "\u001B[31m";
	
	private Card c;
	private Player p;
	private Pile fromPile;
	private Pile toPile;
	
	private int moveCount = 0;
	
	public WarCardMove(Card c, Player p, Pile fromPile, Pile toPile) {
		this.c = c;
		this.p = p;
		this.fromPile = fromPile;
		this.toPile = toPile;
	}
	
	
	@Override
	public void apply(Table table) {
		System.out.println(ANSI_CYAN + "Card Y:    " + c.getY() + ANSI_RESET);
		if (moveCount % 3 == 0)
		{
//			if (c.getY() != 100) {
				table.removeFromPile(WarRules.P1PILE, c);
				table.addToPile(WarRules.PICKUP_PILE, c);
				//table.addToScore(p, 1);
//			}
		} else if (moveCount % 3 == 1){
//			if (c.getY() != 550) {
				table.removeFromPile(WarRules.P2PILE, c);
				table.addToPile(WarRules.PICKUP_PILE, c);
//				table.addToScore(p, 1);
//			}
		} else {
			table.removeFromPile(WarRules.PICKUP_PILE, c);
			table.addToPile(WarRules.P1PILE, c);
//			WarRules.P1PILE.setFaceUp(False);
			table.removeFromPile(WarRules.PICKUP_PILE, c);
			table.addToPile(WarRules.P1PILE, c);
			table.addToScore(p, 2);
			

		}
		
		moveCount++;
		
//		table.removeFromPile(WarRules.P1PILE, c);
//		table.addToPile(WarRules.PICKUP_PILE, c);
//		table.addToScore(p, 1);
	}

	@Override
	public void apply(ViewFacade view) {
		view.send(new HideCardRemote(c));
		view.send(new RemoveFromPileRemote(fromPile, c));
		view.send(new AddToPileTopRemote(toPile, c));
		view.send(new ShowCardRemote(c));
		view.send(new ShowPlayerScore(p, null));
	}

}
