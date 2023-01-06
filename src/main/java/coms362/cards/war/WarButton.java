package coms362.cards.war;

import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectWarEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class WarButton extends Button {
	public static final String kSelector = "WarButton";

	public WarButton(String label, Location location) {
		super(kSelector, SelectWarEvent.kId, label, location);
	}	

}
