package cl.eilers.tatanpoker09.tc.minigame;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
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
	private static Team observers;
	private static Minigame currentMinigame;
	private int id;
	private World world;
	private ArrayList<SpecialItem> specialItems = new ArrayList<SpecialItem>();
	private org.bukkit.scoreboard.Scoreboard board;

	public Minigame(Map map, int id){
		observers = new Team("Observers", ChatColor.AQUA);
		setCurrentMinigame(this);
		this.id = id;
		Map.loadMap(map, id);
		teams.add(observers);
		this.map = map;
		date = Calendar.getInstance();
		state = MatchState.PREMATCH;
		getObservers().setSpawn(ScoreboardUtils.getLocation(getMap().getYmlConfig().getString("spawn"), this));
		for(String name : getMap().getYmlConfig().getConfigurationSection("teams").getKeys(false)){
			int maxCapacity = 43526;
			if(getMap().getYmlConfig().get("teams."+name+".maxcapacity")!=null){
				maxCapacity = this.getMap().getYmlConfig().getInt("teams."+name+".maxcapacity");
			}
			if(maxCapacity == 0){
				maxCapacity = 999;
			}
			ChatColor color = ChatColor.valueOf(getMap().getYmlConfig().getString("teams."+name+".color"));
			if(maxCapacity!=43526){
				addTeam(new Team(maxCapacity, name, color));
			} else {
				addTeam(new Team(name, color));
			}
		}
		Scoreboard.loadScoreboard(this);
		for(Player player : Bukkit.getOnlinePlayers()){
			player.teleport(getObservers().getSpawn());
			player.setGameMode(GameMode.CREATIVE);
			getObservers().addPlayer(player);
		}
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

	public static void loadMinigame(Map map, int id){
		switch(map.getType()){
		case CIRCLE_OF_BOOM:
			new CircleOfBoom(map, id);
			break;
		case KEY_QUEST:
			new KeyQuest(map, id);
			break;
		case WATER_THE_MONUMENT:
			new WaterTheMonument(map, id);
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
			if(!team.equals(getObservers())){
				for(@SuppressWarnings("unused") Player player : team.getPlayers()){
					players++;
				}
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setWorld(World world) {
		this.world = world;
	}
	public World getWorld(){
		return world;
	}


	public org.bukkit.scoreboard.Scoreboard getBoard() {
		return board;
	}


	public void setBoard(org.bukkit.scoreboard.Scoreboard scoreboard) {
		this.board = scoreboard;
	}

	public ArrayList<SpecialItem> getSpecialItems() {
		return specialItems;
	}


	public void addSpecialItems(SpecialItem specialItem) {
		specialItems.add(specialItem);
	}
}
