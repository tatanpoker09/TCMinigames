package cl.eilers.tatanpoker09.tc.minigame.cob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.Map;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.utils.cob.COBUtils;
import cl.eilers.tatanpoker09.tc.utils.general.ScoreboardUtils;
import cl.eilers.tatanpoker09.tc.utils.general.Timer;

public class CircleOfBoom extends Minigame{

	private List<Location> spawnPoints = new ArrayList<Location>();
	private ArrayList<Survivor> survivors = new ArrayList<Survivor>();
	private int radius;
	private Location center;
	private List<Block> blocksInCircle = new ArrayList<Block>();
	private boolean correr;
	private int vida;
	private Player winner;
	private boolean uhc;
	private int ronda;
	private boolean spawnPointCapacity;

	public CircleOfBoom(Map map, int id) {
		super(map, id);
		getSurvivors().clear();
		loadYML();
		setBlocksInCircle(COBUtils.loadBlocks(center, radius));
	}
	public ArrayList<Survivor> getSurvivors() {
		return survivors;
	}
	public void addSurvivor(Survivor survivor){
		getSurvivors().add(survivor);
	}

	public void loadYML(){
		YamlConfiguration ymlFile = getMap().getYmlConfig();
		setRadious(ymlFile.getInt("radius"));
		center = ScoreboardUtils.getLocation(ymlFile.getString("center"), this);
		if(ymlFile.get("spawnpoints")!=null){
			for(String node : ymlFile.getConfigurationSection("spawnpoints").getKeys(true)){
				String location = ymlFile.getString("spawnpoints."+node);
				addSpawnPoints(ScoreboardUtils.getLocation(location, this));
			}
		}
		if(ymlFile.get("correr")!=null){
			setCorrer(ymlFile.getBoolean("correr"));
		} else {
			setCorrer(true);
		}
		if(ymlFile.get("spawnpointcapacity")!=null){
			setSpawnPointCapacity(ymlFile.getBoolean("spawnpointcapacity"));
		} else {
			setSpawnPointCapacity(false);
		}
		if(ymlFile.get("vida")!=null){
			setVida(ymlFile.getInt("vida"));
		} else {
			vida = 5;
		}
		if(ymlFile.get("uhc")!=null){
			setUhc(ymlFile.getBoolean("uhc"));
		}
		if(isSpawnPointCapacity()){
			Team.getTeam("Survivors").setMaxCapacity(spawnPoints.size());
		} else {
			if(ymlFile.getInt("maxcapacity")==0){
				Team.getTeam("Survivors").setMaxCapacity(999);
			} else {
				Team.getTeam("Survivors").setMaxCapacity(ymlFile.getInt("maxcapacity"));
			}
		}
	}

	public int getRadious() {
		return radius;
	}
	public void setRadious(int radius) {
		this.radius = radius;
	}
	public Location getCenter() {
		return center;
	}
	public void setCenter(Location center) {
		this.center = center;
	}

	public void startMatch(){
		Timer.startTimer(10);
		COBUtils.startTNT(this);
	}

	public List<Location> getSpawnPoints() {
		return spawnPoints;
	}
	public List<Block> getBlocksInCircle() {
		return blocksInCircle;
	}
	public void setBlocksInCircle(List<Block> blocksInCircle) {
		this.blocksInCircle = blocksInCircle;
	}
	public void setSpawnPoints(List<Location> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}
	public void addSpawnPoints(Location spawnPoint){
		spawnPoints.add(spawnPoint);
	}
	public void setSurvivors(ArrayList<Survivor> survivors) {
		this.survivors = survivors;
	}
	public boolean isCorrer() {
		return correr;
	}
	public void setCorrer(boolean correr) {
		this.correr = correr;
	}
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	public boolean isUhc() {
		return uhc;
	}
	public void setUhc(boolean uhc) {
		this.uhc = uhc;
	}
	public int getRonda() {
		return ronda;
	}
	public void addRonda(int ronda) {
		this.ronda = this.ronda + ronda;
	}
	public boolean isSpawnPointCapacity() {
		return spawnPointCapacity;
	}
	public void setSpawnPointCapacity(boolean spawnPointCapacity) {
		this.spawnPointCapacity = spawnPointCapacity;
	}
}