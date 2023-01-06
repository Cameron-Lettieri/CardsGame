package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectP52Event;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52button extends Button {
	public static final String kSelector = "P52Button";

	public P52button(String label, Location location) {
		super(kSelector, SelectP52Event.kId, label, location);
	}	

}
