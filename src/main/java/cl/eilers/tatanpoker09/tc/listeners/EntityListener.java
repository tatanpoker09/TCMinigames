package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;

public class EntityListener implements Listener {
	@EventHandler
	public void cancelMobSpawning(CreatureSpawnEvent event){
		if(event.getSpawnReason().equals(SpawnReason.NATURAL)){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void observerDamagesEvent(EntityDamageByEntityEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			if(event.getDamager() instanceof Player){
				if(Team.getTeam((Player)event.getDamager()).equals(Minigame.getObservers())){
					event.setCancelled(true);
				}
			}
		}
	}
}