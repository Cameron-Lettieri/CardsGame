package coms362.cards.war;
import java.util.Random;

import coms362.cards.abstractcomp.Player;

public class WarPlayer implements Player {
	
	Random r = new Random();
	
	private boolean turn = false;
	
	private int score=0;
	private int playerNum = r.nextInt(10);
	
	@Override
	public int addToScore(int amount) {
		return score += amount;
	}

	@Override
	public int getPlayerNum() {
		return playerNum;
	}

	@Override
	public String getSocketId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean checkTurn( ) {
		return turn;
	}
	
	public void changeTurn(Boolean b) {
		if (b)
			turn = false;
		else
			turn = true;
	}

}
