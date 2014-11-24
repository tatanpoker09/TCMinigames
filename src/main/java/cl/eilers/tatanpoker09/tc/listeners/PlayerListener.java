package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;

public class PlayerListener implements Listener{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		switch(Minigame.getCurrentMinigame().getMap().getType()){
		case CIRCLE_OF_BOOM:
			Team.changeTeam((Player)event.getEntity(),Minigame.getObservers());	
			
			break;
		case KEY_QUEST:
			break;
		case WATER_THE_MONUMENT:
			break;
		default:
			break;
		
		}
	}
}
