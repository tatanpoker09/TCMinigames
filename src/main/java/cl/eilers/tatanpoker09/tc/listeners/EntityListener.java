package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityListener implements Listener {
	public void cancelMobSpawning(CreatureSpawnEvent event){
		if(event.getSpawnReason().equals(SpawnReason.NATURAL)){
			event.setCancelled(true);
		}
	}
}
