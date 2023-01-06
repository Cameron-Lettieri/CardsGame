package coms362.cards.fiftytwo;

import java.io.IOException;

import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Location;
import coms362.cards.model.Menu;
import coms362.cards.war.WarButton;

public class GameSelectMenu extends Menu {
	private String P52buttonRemoteId;
	private String WarbuttonRemoteId;

	public GameSelectMenu() {

	}

	public void hideAll(PlayerView view) {
		try {
			view.send(new HideButtonRemote(P52buttonRemoteId));
			view.send(new HideButtonRemote(WarbuttonRemoteId));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void createButtons(PlayerView view) {
		try {
			P52button P52button = new P52button("Pickup 52", new Location(300, 50));
			this.P52buttonRemoteId = P52button.getRemoteId();
			view.send(new CreateButtonRemote(P52button));

			WarButton Warbutton = new WarButton("War", new Location(300, 100));
			this.WarbuttonRemoteId = Warbutton.getRemoteId();
			view.send(new CreateButtonRemote(Warbutton));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
