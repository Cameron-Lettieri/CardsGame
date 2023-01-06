package coms362.cards.abstractcomp;

import coms362.cards.app.ExitTestException;
import coms362.cards.events.inbound.CardEvent;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.EndPlayEvent;
import coms362.cards.events.inbound.FourPlayerEvent;
import coms362.cards.events.inbound.GameRestartEvent;
import coms362.cards.events.inbound.InitGameEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.NewPartyEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectMPEvent;
import coms362.cards.events.inbound.SetQuorumEvent;
import coms362.cards.events.inbound.ThreePlayerEvent;
import coms362.cards.events.inbound.TwoPlayerEvent;

public class RulesDispatchBase implements RulesDispatch {

	@Override
	public Move apply(CardEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(DealEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(EndPlayEvent e, Table table, Player player) {
		throw new ExitTestException("Exit on EndPlay Event");
	}

	@Override
	public Move apply(InitGameEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(SelectGameEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(GameRestartEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(NewPartyEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(ConnectEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(SetQuorumEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(SelectSPEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(SelectMPEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(TwoPlayerEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(ThreePlayerEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(FourPlayerEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	

}
