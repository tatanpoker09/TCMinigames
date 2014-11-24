package cl.eilers.tatanpoker09.tc;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import cl.eilers.tatanpoker09.tc.commands.JugarCommand;
import cl.eilers.tatanpoker09.tc.commands.CambiarMapaCommand;
import cl.eilers.tatanpoker09.tc.commands.UnirCommand;
import cl.eilers.tatanpoker09.tc.listeners.BlockListener;
import cl.eilers.tatanpoker09.tc.listeners.CommandListener;
import cl.eilers.tatanpoker09.tc.listeners.EntityListener;
import cl.eilers.tatanpoker09.tc.listeners.PlayerListener;
import cl.eilers.tatanpoker09.tc.minigame.Map;
/*TODO
 * TEST MAP LOADING
 * TEST YML LOADING
 * TEST TEAM LOADING
 * ADD ID TO MINIGAME OBJECT, ID++ AFTER EACH TACO
 * START SCOREBOARD LOADING
 * GET DEFAULT MAPS FOR EACH GAMEMODE
 */
public class Main extends JavaPlugin{
	private static Plugin plugin;
	private static final File mapsDirectory = new File("TCMaps");
	public void onEnable(){
		getLogger().info("TCMinigames has been enabled!");
		if(getServer().getWorld("Lobby")!=null) generateLobby();
		if(!getMapsdirectory().exists()) getMapsdirectory().mkdir();
		Map.loadMaps();
		plugin = this;
		registerEvents(getPlugin(), new BlockListener(), new EntityListener(), new CommandListener(), new PlayerListener());
		getCommand("jugar").setExecutor(new JugarCommand());
		getCommand("cambiarmapa").setExecutor(new CambiarMapaCommand());
		getCommand("unir").setExecutor(new UnirCommand());
	}

	private void generateLobby() {
		Map.loadMap(Map.getMap("Lobby"));
	}

	public void onDisable(){
		getLogger().info("TCMinigames has been disabled!");
		plugin = null;
	}
	public static File getMapsdirectory() {
		return mapsDirectory;
	}
	public File getMapsDirectory(){
		return mapsDirectory;
	}
	public static void registerEvents(Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}
	public static Plugin getPlugin() {
		return plugin;
	}
}