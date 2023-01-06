package coms362.cards.events.inbound;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.RulesDispatch;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.GameController;
import coms362.cards.model.PregameSetup;
import coms362.cards.socket.SocketMessage;

public class SelectWarEvent implements Event, EventFactory, SysEvent{

    public static final String kId = "SelectWarEvent";
    private String socket = "";
   
    public static Event createEvent(SocketMessage sktEvent) {
        return new SelectWarEvent(sktEvent);
    }
    
    public SelectWarEvent(SocketMessage e) {
    	this.socket = "" + e.getSocketId();
    }
    
    public Move dispatch(RulesDispatch rules, Table table, Player player) {
        return rules.apply(this, table, player);
    }

	@Override
	public void accept(GameController handler, PregameSetup game) {
		handler.apply(this, game);
	}
	
	public String getSocketId() {
	        return socket;
	    }

}
