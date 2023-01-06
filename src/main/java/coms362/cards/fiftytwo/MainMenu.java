package coms362.cards.fiftytwo;

import java.io.IOException;

import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Location;
import coms362.cards.model.Menu;

public class MainMenu extends Menu {
	private String MPbuttonRemoteId;
	private String SPbuttonRemoteId;

	public MainMenu() {

	}

	public void hideAll(PlayerView view) {
		try {
			view.send(new HideButtonRemote(MPbuttonRemoteId));
			view.send(new HideButtonRemote(SPbuttonRemoteId));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void createButtons(PlayerView view) {
		try {
			P52SPButton SPbutton = new P52SPButton("Pickup 52 SP", new Location(300, 50));
			this.SPbuttonRemoteId = SPbutton.getRemoteId();
			view.send(new CreateButtonRemote(SPbutton));

			P52MPButton MPbutton = new P52MPButton("Pickup 52 MP", new Location(300, 100));
			this.MPbuttonRemoteId = MPbutton.getRemoteId();
			view.send(new CreateButtonRemote(MPbutton));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
