package coms362.cards.war;

import java.io.IOException;

import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.events.remote.SetGameTitleRemote;
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Location;
import coms362.cards.model.Menu;

public class WarMenu extends Menu {
	private String dealButton;

	public WarMenu() {

	}

	public void hideAll(PlayerView view) {
		try {
			view.send(new HideButtonRemote(dealButton));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void createButtons(PlayerView view) {
		try {
			P52DealButton dealButton = new P52DealButton("DEAL", new Location(50, 50));
			this.dealButton = dealButton.getRemoteId();
			view.send(new CreateButtonRemote(dealButton));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void changeTitle(PlayerView view) throws IOException {
		view.send(new SetGameTitleRemote("War"));	
	}
	
}
