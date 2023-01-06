package coms362.cards.app;

import java.io.IOException;
import java.util.Stack;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.Table;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.EndPlayEvent;
import coms362.cards.events.inbound.Event;
import coms362.cards.events.inbound.EventUnmarshallers;
import coms362.cards.events.inbound.FourPlayerEvent;
import coms362.cards.events.inbound.InvalidGameSelectionEvent;
import coms362.cards.events.inbound.NewPartyEvent;
import coms362.cards.events.inbound.SelectGameEvent;
import coms362.cards.events.inbound.SelectMPEvent;
import coms362.cards.events.inbound.SelectP52Event;
import coms362.cards.events.inbound.SelectSPEvent;
import coms362.cards.events.inbound.SelectWarEvent;
import coms362.cards.events.inbound.SetQuorumEvent;
import coms362.cards.events.inbound.SysEvent;
import coms362.cards.events.inbound.ThreePlayerEvent;
import coms362.cards.events.inbound.TwoPlayerEvent;
import coms362.cards.fiftytwo.GameSelectMenu;
import coms362.cards.fiftytwo.MainMenu;
import coms362.cards.fiftytwo.MultiplayerMenu;
import coms362.cards.game.PartyRole;
import coms362.cards.game.PlayerView;
import coms362.cards.model.PregameSetup;
import coms362.cards.model.Quorum;
import coms362.cards.streams.InBoundQueue;
import coms362.cards.streams.RemoteTableGateway;
import coms362.cards.war.War2Player;
import coms362.cards.war.WarGameFactory;
import coms362.cards.war.WarRules;

/**
 * This class is responsible for processing events involved in determining what
 * game is to be played. It runs in a context with a single default .
 * 
 * It defers events that can't be processed without access to concrete game
 * rules: for example how many players are required/allowed, and what position
 * and role each party will hold.
 * 
 * Currently events carrying this and related game/match information can be
 * submitted by the host on * the query string, for example:
 * 
 * ?host&player=1&min=1&max=2&game=PU52MP
 * 
 * The "host" parameter qualifies the connection to select game and specify game
 * "quorum" limits. In this case, the host would also be registered as player 1.
 * The "host" is <em>not</em> required to be a player in all games.
 * 
 * @author Robert Ward
 */
public class GameController {

	private InBoundQueue inQ;
	private RemoteTableGateway remote;
	private GameFactoryFactory abstractFactory;
	private MultiplayerMenu multiplayerMenu;
	private MainMenu mainMenu;
	private GameSelectMenu gameSelect;
	private War2Player req;

	Stack<Event> deferred = new Stack<Event>();
	PregameSetup game = new PregameSetup();

	public GameController(InBoundQueue inQ, RemoteTableGateway gateway, GameFactoryFactory gFFactory) {
		this.inQ = inQ;
		this.remote = gateway;
		this.abstractFactory = gFFactory;
		this.multiplayerMenu = new MultiplayerMenu();
		this.mainMenu = new MainMenu();
		this.gameSelect = new GameSelectMenu();
		this.req = new War2Player();

	}

	public void run() {

		System.out.println("Application Started");

		Event e = null;
		while (!game.isSelected()) {
			try {
				e = inQ.take();
				((SysEvent) e).accept(this, game);

			} catch (ExitTestException ex) {
				break;
			} catch (Exception ex) {
				System.out.println("GameController Exception " + ex.getMessage());
				System.out.println("Deferring unhandled event " + e.toString());
				ex.printStackTrace(System.err);
				// we can't do anything game specific until after we have seen SelectGame.
				deferred.add(e);
			}
			;
		}

		GameFactory factory = abstractFactory.getGameFactory(game.getSelection());
		//GameFactory factory = new WarGameFactory();
		Rules rules = factory.createRules();
		Table table = factory.createTable();
		inQ.pushBack(deferred);
		

		MatchController match = new MatchController(inQ, table, rules, remote, factory);
		match.start();
	}

	public void apply(SelectSPEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		mainMenu.hideAll(view);
		String selection = "PU52SP";
		inQ.pushBack(new SelectGameEvent(selection));
	}

	public void apply(ThreePlayerEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		multiplayerMenu.apply(view, inQ, 3, 3);
	}

	public void apply(FourPlayerEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		multiplayerMenu.apply(view, inQ, 4, 4);
	}

	public void apply(TwoPlayerEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		multiplayerMenu.apply(view, inQ, 2, 2);
	}

	public void apply(SelectMPEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		mainMenu.hideAll(view);
		multiplayerMenu.apply(view);
	}
	
	public void apply(SelectP52Event e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		gameSelect.hideAll(view);
		mainMenu.createButtons(view);
	}

	public void apply(SelectWarEvent e, PregameSetup game) {
		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		gameSelect.hideAll(view);
		//String selection = "WAR";
		//inQ.pushBack(new SelectGameEvent(selection));
		req.apply(view, inQ, 2, 2);
	}
	

	// TODO: make specific handlers for each match pre-req event.
	public void apply(ConnectEvent e, PregameSetup game) { // TODO Auto-generated method stub
		System.out.println("GameRules.apply " + e.getClass().getSimpleName());
		System.out.println(e.toString());

		PlayerView view = new PlayerView(0, e.getSocketId(), remote);
		if (e.getParam("host") != null) {
			gameSelect.createButtons(view);
		}

		EventUnmarshallers handlers = EventUnmarshallers.getInstance();
		handlers.registerHandler(SelectMPEvent.kId, (Class) SelectMPEvent.class);
		handlers.registerHandler(SelectSPEvent.kId, (Class) SelectSPEvent.class);
		handlers.registerHandler(SelectP52Event.kId, (Class) SelectP52Event.class);
		handlers.registerHandler(SelectWarEvent.kId, (Class) SelectWarEvent.class);


		String selection = "";
		if (e.getParam("host") != null) {
			if ((selection = e.getParam("game")) != null) {
				inQ.pushBack(new SelectGameEvent(selection, e));
			}
		}

		String pnum = null;
		if ((pnum = e.getParam("player")) != null) {
			pnum = (pnum.isEmpty() ? "1" : pnum);
			deferred.push(new NewPartyEvent(PartyRole.player, pnum, e.getSocketId()));
		}
	}

	public void apply(SelectGameEvent e, PregameSetup game) {
		String selected = "";
		if (abstractFactory.isValidSelection(selected = e.getSelection())) {
			game.setSelected(selected);
			// without other information force to a maximum of 4
			// To give the rules a chance to supply game specific limits.
			Quorum pushQ = (e.hasQuorum()) ? e.getQuorum() : new Quorum(1, 1);
			deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
		} else {
			// we need to inform the alleged host now
			System.out.format("GameController. SelectGame : %s is not a supported game.", selected);
			inQ.pushBack(new InvalidGameSelectionEvent(selected));
		}

	}

	public void apply(InvalidGameSelectionEvent e, PregameSetup game) {
		System.out.println("InvalidGameSelection Event");
		try {
			remote.send(new SystemStatus(e.getMsg()), "default");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void apply(NewPartyEvent e, PregameSetup game) {
		deferred.add(e);
	}

	// should not normally be processed from game controller.
	public void apply(EndPlayEvent endPlay, PregameSetup game2) {
		throw new ExitTestException("Exit on EndPlay Event");
	}


}
