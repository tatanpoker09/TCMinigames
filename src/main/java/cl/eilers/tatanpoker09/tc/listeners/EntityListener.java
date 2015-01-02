package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;

import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;

public class EntityListener implements Listener {
	@EventHandler
	public void cancelMobSpawning(CreatureSpawnEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				event.setCancelled(true);
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
	@EventHandler
	public void observerDamagesEvent(EntityDamageByEntityEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(event.getDamager() instanceof Player){
					if(Team.getTeam((Player)event.getDamager()).equals(Minigame.getObservers())){
						event.setCancelled(true);
					} else {
						event.setDamage(0);
					}
				}
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


	@EventHandler
	public void itemKillEvent(ItemDespawnEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
					event.setCancelled(true);
				}
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
}