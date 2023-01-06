package coms362.cards.fiftytwo;

import java.io.IOException;

import coms362.cards.events.inbound.EventUnmarshallers;
import coms362.cards.events.inbound.FourPlayerEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.ThreePlayerEvent;
import coms362.cards.events.inbound.TwoPlayerEvent;
import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Location;
import coms362.cards.model.Menu;
import coms362.cards.model.Quorum;
import coms362.cards.streams.InBoundQueue;

public class MultiplayerMenu extends Menu {
	private String TwoPlayerRemoteId;
	private String ThreePlayerRemoteId;
	private String FourPlayerRemoteId;

	public MultiplayerMenu() {

	}

	public void apply(PlayerView view, InBoundQueue inQ, int min, int max) {
		try {
			view.send(new HideButtonRemote(TwoPlayerRemoteId));
			view.send(new HideButtonRemote(ThreePlayerRemoteId));
			view.send(new HideButtonRemote(FourPlayerRemoteId));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Quorum q = new Quorum(min, max);
		String selection = "PU52MP";
		inQ.pushBack(new SelectGameEvent(selection, q));
	}

	public void apply(PlayerView view) {
		try {

			TwoPlayerButton twoPlayerButton = new TwoPlayerButton("2 Players", new Location(300, 50));
			this.TwoPlayerRemoteId = twoPlayerButton.getRemoteId();
			view.send(new CreateButtonRemote(twoPlayerButton));

			ThreePlayerButton threePlayerButton = new ThreePlayerButton("3 Players", new Location(300, 100));
			this.ThreePlayerRemoteId = threePlayerButton.getRemoteId();
			view.send(new CreateButtonRemote(threePlayerButton));

			FourPlayerButton fourPlayerButton = new FourPlayerButton("4 Players", new Location(300, 150));
			this.FourPlayerRemoteId = fourPlayerButton.getRemoteId();
			view.send(new CreateButtonRemote(fourPlayerButton));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		EventUnmarshallers handlers = EventUnmarshallers.getInstance();
		handlers.registerHandler(TwoPlayerEvent.kId, (Class) TwoPlayerEvent.class);
		handlers.registerHandler(ThreePlayerEvent.kId, (Class) ThreePlayerEvent.class);
		handlers.registerHandler(FourPlayerEvent.kId, (Class) FourPlayerEvent.class);
	}

}
