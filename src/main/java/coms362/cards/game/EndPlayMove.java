package coms362.cards.game;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;


public class EndPlayMove implements Move {

	public void apply(Table table) {
		table.setMatchOver(true);
	}

	public void apply(ViewFacade view) {	
	}
	
	@Override	
	public boolean isMatchEnd(){
		return true;
	}

}
