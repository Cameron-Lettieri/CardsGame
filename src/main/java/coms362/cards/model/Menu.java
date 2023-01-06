package coms362.cards.model;

import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.SetGameTitleRemote;

public class Menu {
    
	
	public static void changeTitle(Table table, ViewFacade view) {
		view.send(new SetGameTitleRemote("Cards 362"));	
	}
}

