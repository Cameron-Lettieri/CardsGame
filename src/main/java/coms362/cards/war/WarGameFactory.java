package coms362.cards.war;

import coms362.cards.abstractcomp.Rules;
import coms362.cards.fiftytwo.P52MPGameFactory;

public class WarGameFactory extends P52MPGameFactory {

	@Override
	public Rules createRules() {
		return new WarRules();

	}

}