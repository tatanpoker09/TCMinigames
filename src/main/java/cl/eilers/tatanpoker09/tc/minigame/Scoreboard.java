package cl.eilers.tatanpoker09.tc.minigame;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboard {
	private static final ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Objective objective;

	public static void loadScoreboard(Minigame minigame) {
		minigame.setBoard(manager.getNewScoreboard());
		switch (Minigame.getCurrentMinigame().getMap().getType()) {
		case CIRCLE_OF_BOOM:
			Objective rondas = minigame.getBoard().registerNewObjective("Rondas", "dummy");
			rondas.setDisplaySlot(DisplaySlot.SIDEBAR);
			break;
		case WATER_THE_MONUMENT:
			
			break;
		case KEY_QUEST:
			
			break;
		default:
			break;
		}
	}

	public ScoreboardManager getManager() {
		return manager;
	}

	public static Objective getObjective() {
		return objective;
	}
	public static void setObjective(Objective objective){
		Scoreboard.objective = objective;
	}
}
