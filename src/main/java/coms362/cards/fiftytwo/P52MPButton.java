package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectMPEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52MPButton extends Button {
	public static final String kSelector = "mpButton";

	public P52MPButton(String label, Location location) {
		super(kSelector, SelectMPEvent.kId, label, location);
	}	

}
