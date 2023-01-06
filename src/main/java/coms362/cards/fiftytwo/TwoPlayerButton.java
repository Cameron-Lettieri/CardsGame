package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.TwoPlayerEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class TwoPlayerButton extends Button {
	public static final String kSelector = "TwoPlayerButton";

	public TwoPlayerButton(String label, Location location) {
		super(kSelector, TwoPlayerEvent.kId, label, location);
	}	

}
