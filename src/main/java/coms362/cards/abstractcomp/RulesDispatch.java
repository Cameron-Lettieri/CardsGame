package coms362.cards.abstractcomp;

import coms362.cards.events.inbound.CardEvent;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.EndPlayEvent;
import coms362.cards.events.inbound.FourPlayerEvent;
import coms362.cards.events.inbound.GameRestartEvent;
import coms362.cards.events.inbound.InitGameEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SelectWarEvent;
import coms362.cards.events.inbound.NewPartyEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectMPEvent;
import coms362.cards.events.inbound.SelectP52Event;
import coms362.cards.events.inbound.SetQuorumEvent;
import coms362.cards.events.inbound.ThreePlayerEvent;
import coms362.cards.events.inbound.TwoPlayerEvent;

/**
 * Part of the double dispatch mechanism we use to recover concrete type information from Event
 * subclasses.
 * 
 * This declaration segregates the double dispatch portion of the rules api from the unrelated (and
 * more game independent) methods.
 * 
 * Since we anticipate that a material number of input events will be reusable across games,
 * defining a separate interface for these methods allows us to reuse those events without change,
 * even though the ultimate implementor (Rules) will change with each game.
 * 
 * The base implementation (which for every one of these methods just throws an "unimplemented"
 * exception) must also be expanded each time a new Event is added. However the combination
 * insulates existing game-specific rules objects from needing to be changes and allows each new
 * game-specific rules object to define methods for only the events it requires.
 * 
 * @author Robert Ward
 */
public interface RulesDispatch {

    public Move apply(CardEvent e, Table table, Player player);

    public Move apply(SelectSPEvent e, Table table, Player player);	
    
    public Move apply(SelectMPEvent e, Table table, Player player);
    
    public Move apply(DealEvent e, Table table, Player player);

    public Move apply(EndPlayEvent e, Table table, Player player);

    public Move apply(InitGameEvent e, Table table, Player player);

    public Move apply(SelectGameEvent e, Table table, Player player);

    public Move apply(GameRestartEvent e, Table table, Player player);

    public Move apply(NewPartyEvent e, Table table, Player player);

    public Move apply(ConnectEvent e, Table table, Player player);

    public Move apply(SetQuorumEvent e, Table table, Player player);
    
    public Move apply(TwoPlayerEvent e, Table table, Player player);
    
    public Move apply(ThreePlayerEvent e, Table table, Player player);
    
    public Move apply(FourPlayerEvent e, Table table, Player player);

	public Move apply(SelectP52Event e, Table table, Player player);
	
	public Move apply(SelectWarEvent e, Table table, Player player);


}
