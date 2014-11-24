package cl.eilers.tatanpoker09.tc.minigame.cob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import cl.eilers.tatanpoker09.tc.minigame.Map;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.utils.cob.COBUtils;
import cl.eilers.tatanpoker09.tc.utils.general.ScoreboardUtils;
import cl.eilers.tatanpoker09.tc.utils.general.Timer;

public class CircleOfBoom extends Minigame{

	private List<Location> spawnPoints = new ArrayList<Location>();
	private ArrayList<Survivor> survivors = new ArrayList<Survivor>();
	private int radius;
	private Location center;
	private List<Block> blocksInCircle = new ArrayList<Block>();

	public CircleOfBoom(Map map) {
		super(map);
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
		setRadious(getMap().getYmlConfig().getInt("radius"));
		center = ScoreboardUtils.getLocation(getMap().getYmlConfig().getString("center"), this);
		for(String node : getMap().getYmlConfig().getConfigurationSection("spawnpoints").getKeys(true)){
			addSpawnPoints(ScoreboardUtils.getLocation(node, this));
		}
		System.out.println(getTeams().size());
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

}