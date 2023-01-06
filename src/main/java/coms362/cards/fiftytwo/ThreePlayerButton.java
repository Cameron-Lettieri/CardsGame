package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.ThreePlayerEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class ThreePlayerButton extends Button {
	public static final String kSelector = "ThreePlayerButton";

	public ThreePlayerButton(String label, Location location) {
		super(kSelector, ThreePlayerEvent.kId, label, location);
	}	

}
