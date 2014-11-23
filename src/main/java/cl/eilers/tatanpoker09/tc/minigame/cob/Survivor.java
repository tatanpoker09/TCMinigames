package cl.eilers.tatanpoker09.tc.minigame.cob;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Survivor{
	private Player player;
	private Location spawnPoint;
	
	public Survivor(Player player){
		this.player = player;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Location getSpawnPoint() {
		return spawnPoint;
	}
	public void setSpawnPoint(Location spawnPoint) {
		this.spawnPoint = spawnPoint;
	}
}
