package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.Map;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.utils.general.FileUtils;

public class CommandListener implements Listener{
	@EventHandler
	public void onServerStop(PlayerCommandPreprocessEvent event){
		if(event.getMessage().equals("/stop")){
			World world = Minigame.getCurrentMinigame().getWorld();
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
	@EventHandler
	public void onServerStop(ServerCommandEvent event){
		if(event.getCommand().equals("stop")){
			for(Player player : Bukkit.getOnlinePlayers()) player.kickPlayer("Server closed.");
			for(World world : Bukkit.getWorlds()){
				for(Map map : Map.getMaps()){
					if(world.getName().startsWith(map.getName())){
						Bukkit.unloadWorld(world, true);
						FileUtils.delete(world.getWorldFolder());
					}
				}
			}
		}
	}
}