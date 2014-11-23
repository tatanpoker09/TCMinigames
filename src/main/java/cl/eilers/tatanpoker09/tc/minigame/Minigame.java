package cl.eilers.tatanpoker09.tc.minigame;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.minigame.keyquest.KeyQuest;
import cl.eilers.tatanpoker09.tc.minigame.wtm.WaterTheMonument;
import cl.eilers.tatanpoker09.tc.utils.general.ScoreboardUtils;

public class Minigame {
	private static Map nextMap;
	private Map map;
	private Calendar date;
	private ArrayList<Team> teams = new ArrayList<Team>();
	private MatchState state;
	private final static Team observers = new Team("Observers", ChatColor.AQUA);
	private static Minigame currentMinigame;
	
	public Minigame(Map map){
		Map.loadMap(map);
		teams.add(observers);
		this.map = map;
		date = Calendar.getInstance();
		setCurrentMinigame(this);
		state = MatchState.PREMATCH;
		getObservers().setSpawn(ScoreboardUtils.getLocation(getMap().getYmlConfig().getString("spawn"), this));
		for(String name : getMap().getYmlConfig().getConfigurationSection("teams").getKeys(false)){
			ChatColor color = ChatColor.valueOf(getMap().getYmlConfig().getString("teams."+name+".color"));
			addTeam(new Team(name, color));
		}
		for(Player player : Bukkit.getOnlinePlayers()) player.teleport(getObservers().getSpawn());
	}
	

	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public static Map getNextMap() {
		return nextMap;
	}
	public static void setNextMap(Map nextMap) {
		Minigame.nextMap = nextMap;
	}
	public Calendar getDate(){
		return date;
	}

	public static void loadMinigame(Map map){
		switch(map.getType()){
		case CIRCLE_OF_BOOM:
			new CircleOfBoom(map);
			break;
		case KEY_QUEST:
			new KeyQuest(map);
			break;
		case WATER_THE_MONUMENT:
			new WaterTheMonument(map);
			break;
		default:
			break;
		
		}
	}
	public static Team getObservers() {
		return observers;
	}

	public static Minigame getCurrentMinigame() {
		return currentMinigame;
	}

	public static void setCurrentMinigame(Minigame currentMinigame) {
		Minigame.currentMinigame = currentMinigame;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team teamToAdd) {
		teams.add(teamToAdd);
	}
	
	public int getPlayersPlaying(){
		int players = 0;
		for(Team team : teams){
			for(@SuppressWarnings("unused") Player player : team.getPlayers()){
				players++;
			}
		}
		return players;
	}

	public MatchState getState() {
		return state;
	}

	public void setState(MatchState state) {
		this.state = state;
	}
}
