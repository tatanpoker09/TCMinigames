package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.minigame.cob.Survivor;

public class JugarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Minigame.getCurrentMinigame().setState(MatchState.STARTED);
		switch(Minigame.getCurrentMinigame().getMap().getType()){
		case CIRCLE_OF_BOOM:
			CircleOfBoom minigame = (CircleOfBoom)Minigame.getCurrentMinigame();
			
			for(Survivor survivor : minigame.getSurvivors()){
				survivor.setSpawnPoint(minigame.getSpawnPoints().get(0));
				survivor.getPlayer().teleport(survivor.getSpawnPoint());
				minigame.getSpawnPoints().remove(0);
			}
			minigame.startMatch();
			break;
		case KEY_QUEST:
			break;
		case WATER_THE_MONUMENT:
			break;
		default:
			break;
		
		}
		return false;
	}

}
