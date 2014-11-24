package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.Map;
import cl.eilers.tatanpoker09.tc.utils.general.FileUtils;

public class CommandListener implements Listener{
	@EventHandler
	public void onServerStop(PlayerCommandPreprocessEvent event){
		if(event.getMessage().equals("/stop")){
			for(Map map : Map.getMaps()){
				World world = map.getWorld();
				if(world!=null){
					if(world.getName().equalsIgnoreCase("lobby")){
						Main.getPlugin().getLogger().info("Found important map, leaving it be.");
					} else {
						for(Player player : Bukkit.getOnlinePlayers()) player.kickPlayer("Server closed.");
						Bukkit.unloadWorld(world, true);
						FileUtils.delete(world.getWorldFolder());
					}
				}
			}
		}
	}
}
