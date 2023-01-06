package coms362.cards.fiftytwo;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateCardRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.events.remote.UpdateCardRemote;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class P52SelectMove implements Move {
    private Table table;

    public P52SelectMove(Table table, Player player) {
        this.table = table;
    }

    public void apply(Table table) {
        // TODO Auto-generated method stub

    }

    public void apply(ViewFacade views) {

        try {
            String remoteId = views.getRemoteId(P52SPButton.kSelector);
            views.send(new HideButtonRemote(remoteId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

