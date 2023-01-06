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

public class WarCardMover implements Move {

	private Card c;
	private Player p;
	private Pile fromPile;
	private Pile toPile;
	
	@Override
	public void apply(Table table) {
		c.setFaceUp(true);
		table.removeFromPile(P52Rules.DROP_PILE, c);
		table.addToPile(P52Rules.PICKUP_PILE, c);
		table.addToScore(p, 1);
		
	}

	@Override
	public void apply(ViewFacade views) {
		c.setFaceUp(true);
		views.send(new HideCardRemote(c));
		views.send(new RemoveFromPileRemote(fromPile, c));
		views.send(new AddToPileTopRemote(toPile, c));
		views.send(new ShowCardRemote(c));
		views.send(new ShowPlayerScore(p, null));
	}

}
