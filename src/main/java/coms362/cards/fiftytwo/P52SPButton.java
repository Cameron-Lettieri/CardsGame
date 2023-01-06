package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52SPButton extends Button {
	public static final String kSelector = "spButton";

	public P52SPButton(String label, Location location) {
		super(kSelector, SelectSPEvent.kId, label, location);
	}	

}
