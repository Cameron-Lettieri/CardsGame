package coms362.cards.war;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateCardRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.events.remote.UpdateCardRemote;
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class WarDealMove implements Move {
    private Table table;

    public WarDealMove(Table table, Player player) {
        this.table = table;
    }

    public void apply(Table table) {
        // TODO Auto-generated method stub

    }

    public void apply(ViewFacade views) {

        try {
            String remoteId = views.getRemoteId(P52DealButton.kSelector);
            views.send(new HideButtonRemote(remoteId));
            addCards(views, WarRules.P1PILE);
            addCards(views, WarRules.P2PILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Adds cards to their respective piles
    public void addCards(ViewFacade views, String pile) {
    	Pile local = table.getPile(pile);
        if (local == null) {
            return;
        }
        for (Card c : local.getCards()) {
            String outVal = "";
            views.send(new CreateCardRemote(c));
            views.send(new UpdateCardRemote(c));
            System.out.println(outVal);
        }
    }
}

