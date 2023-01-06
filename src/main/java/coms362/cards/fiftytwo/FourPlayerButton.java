package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.FourPlayerEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class FourPlayerButton extends Button {
	public static final String kSelector = "FourPlayerButton";

	public FourPlayerButton(String label, Location location) {
		super(kSelector, FourPlayerEvent.kId, label, location);
	}	

}
