package cl.eilers.tatanpoker09.tc.minigame;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.minigame.cob.Survivor;

public class Team {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int maxCapacity;
	private String name;
	private ChatColor color;
	private Location spawn;
	
	public Team(int maxCapacity, String name, ChatColor color){
		getPlayers().clear();
		this.setMaxCapacity(maxCapacity);
		this.setName(name);
		this.setColor(color);
	}

	public Team(String name, ChatColor color) {
		getPlayers().clear();
		this.setMaxCapacity(999);
		this.setName(name);
		this.setColor(color);
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void addPlayer(Player player){
		player.sendMessage(ChatColor.GREEN+"Has entrado a: "+this.getColor()+this.getName());
		players.add(player);
		if(Minigame.getCurrentMinigame().getMap().getType().equals(MapType.CIRCLE_OF_BOOM) && !this.equals(Minigame.getObservers())){
			((CircleOfBoom)Minigame.getCurrentMinigame()).addSurvivor(new Survivor(player));
		}
	}
	
	public static Team getTeam(String name){
		for(Team team : Minigame.getCurrentMinigame().getTeams()){
			if(team.getName().toLowerCase().startsWith(name.toLowerCase())){
				return team;
			}
		}
		return null;
	}

	public void remove(Player player) {
		getPlayers().remove(player);
	}
	public static Team getTeam(Player player){
		Team playerTeam = null;
		for(Team team : Minigame.getCurrentMinigame().getTeams()){
			if(team.getPlayers().contains(player)){
				playerTeam = team;
			}
		}
		return playerTeam;
	}
	public static void changeTeam(Player player, Team destination){
		Team teamToLeave = getTeam(player);
		if(teamToLeave!=null){
			teamToLeave.remove(player);
		}
		if(Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED) && !player.isDead()){
			player.setHealth(0);
		}
		destination.addPlayer(player);
	}

	public void clear() {
		for(Player player : this.getPlayers()){
			this.remove(player);
		}
	}
}
