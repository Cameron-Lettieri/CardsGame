package coms362.cards.war;

import java.io.IOException;

import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Quorum;
import coms362.cards.streams.InBoundQueue;

public class War2Player {

	public void apply(PlayerView view, InBoundQueue inQ, int min, int max) {
//		try {
//			view.send(new HideButtonRemote(TwoPlayerRemoteId));
//			view.send(new HideButtonRemote(ThreePlayerRemoteId));
//			view.send(new HideButtonRemote(FourPlayerRemoteId));
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

		Quorum q = new Quorum(min, max);
		String selection = "WAR";
		inQ.pushBack(new SelectGameEvent(selection, q));
	}
}
