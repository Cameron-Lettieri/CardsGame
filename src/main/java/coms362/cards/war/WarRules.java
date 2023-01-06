package coms362.cards.war;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.RulesDispatch;
import coms362.cards.abstractcomp.RulesDispatchBase;
import coms362.cards.abstractcomp.Table;
import coms362.cards.events.inbound.CardEvent;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.Event;
import coms362.cards.events.inbound.EventUnmarshallers;
import coms362.cards.events.inbound.GameRestartEvent;
import coms362.cards.events.inbound.InitGameEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SetQuorumEvent;
import coms362.cards.fiftytwo.P52CardMove;
import coms362.cards.fiftytwo.P52DealMove;
import coms362.cards.fiftytwo.P52InitMove;
import coms362.cards.fiftytwo.P52SelectMove;
import coms362.cards.game.CreatePlayerMove;
import coms362.cards.game.DoNothingMove;
import coms362.cards.game.PartyRole;
import coms362.cards.game.SetQuorumMove;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;
import coms362.cards.events.inbound.NewPartyEvent;
import coms362.cards.events.inbound.SelectMPEvent;


public class WarRules extends RulesDispatchBase
implements Rules, RulesDispatch {
    private int moveCount = 0;
	
    public static final String P1PILE = "p1pile";
    public static final String P2PILE = "p2pile";
    public static final String PICKUP_PILE = "discardPile";
    
    public boolean playerTurn = false;
	
	public WarRules() {
		registerEvents();
	}
	 
	public Move eval(Event nextE, Table table, Player player) {
		return nextE.dispatch(this, table, player);
		
	}
	
	
	public Move apply(CardEvent e, Table table, Player player){	
//		Pile fromPile = table.getPile(P1PILE);
//		Pile toPile = table.getPile(PICKUP_PILE1);
//		Card c = fromPile.getCard(e.getId());
		Pile fromPile = null;
		Pile toPile = null;
		Card c = null;
		
		if (moveCount % 3 == 0) {
			fromPile = table.getPile(P1PILE);
			toPile = table.getPile(PICKUP_PILE);
			c = fromPile.getCard(e.getId());
			table.addToScore(player, -1);
//			if (c.getY() == 550) {
//				return new DoNothingMove();
//			}
		} else if (moveCount % 3 == 1) {
			fromPile = table.getPile(P2PILE);
			toPile = table.getPile(PICKUP_PILE);
			c = fromPile.getCard(e.getId());
			table.addToScore(player, -1);
//			if (c.getY() == 100) {
//				return new DoNothingMove();
//			}
		} else {
			fromPile = table.getPile(PICKUP_PILE);
			toPile = table.getPile(P1PILE);
			c = fromPile.getCard(e.getId());
		}
//		Pile fromPile = table.getPile(P1PILE);
//		Pile toPile = table.getPile(PICKUP_PILE);
//		Card c = fromPile.getCard(e.getId());
		
		moveCount++;
		System.out.println("Move Count: " + moveCount + "\n\n\n\n\n\n\n");
		if (c == null) {
			return new DoNothingMove();
		}
		return new WarCardMove(c, player, fromPile, toPile);	
	}
	
	
	//Set the title to War
	public Move apply(InitGameEvent e, Table table, Player player){
		return new WarMoveInit(table.getPlayerMap(), "War", table);
	}
	

	
	public Move apply(DealEvent e, Table table, Player player){
		return new WarDealMove(table, player);
	}
	
	public Move apply(SelectSPEvent e, Table table, Player player) {
		return new P52SelectMove(table, player);
	}
	
	
	public Move apply(NewPartyEvent e, Table table, Player player){
		if (e.getRole() == PartyRole.player){
			return new CreatePlayerMove( e.getPosition(), e.getSocketId());
		}
		return new DoNothingMove();
	}
	
	public Move apply(SetQuorumEvent e, Table table, Player player){
		return new SetQuorumMove(e.getQuorum());
	}
	
	public Move apply(ConnectEvent e, Table table, Player player){
		Move rval = new DoNothingMove(); 
		System.out.println("Rules apply ConnectEvent "+e);
		if (! table.getQuorum().exceeds(table.getPlayers().size()+1)){
			if (e.getRole() == PartyRole.player){				
				rval =  new CreatePlayerMove( e.getPosition(), e.getSocketId());
			}			
		}
		System.out.println("PickupRules connectHandler rval = "+rval);
		return rval;
	}
	
	
	
	public void warTurn()
	{
		if(playerTurn) {
			
		}
	}

	/**
	 * We rely on Rules to register the appropriate input events with
	 * the unmarshaller. This avoids excessive complexity in the 
	 * abstract factory and there is a natural dependency between 
	 * the rules and the game input events.  
	 */
	private void registerEvents() {
		EventUnmarshallers handlers = EventUnmarshallers.getInstance();
		handlers.registerHandler(InitGameEvent.kId, (Class) InitGameEvent.class); 
		handlers.registerHandler(DealEvent.kId, (Class) DealEvent.class); 
		handlers.registerHandler(CardEvent.kId, (Class) CardEvent.class); 
		handlers.registerHandler(GameRestartEvent.kId, (Class) GameRestartEvent.class); 
		handlers.registerHandler(NewPartyEvent.kId, (Class) NewPartyEvent.class);
		handlers.registerHandler(SelectSPEvent.kId, (Class) SelectSPEvent.class);
		handlers.registerHandler(SelectMPEvent.kId, (Class) SelectMPEvent.class);
	}
}
