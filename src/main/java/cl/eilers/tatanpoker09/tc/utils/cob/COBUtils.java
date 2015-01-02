package cl.eilers.tatanpoker09.tc.utils.cob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.SpecialItem;
import cl.eilers.tatanpoker09.tc.minigame.SpecialItems;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;

public class COBUtils {
	private static CircleOfBoom circle;
	private static int taskId;
	private static int tntId;
	private static long period;
	public static List<Block> loadBlocks(Location center, int radius){
		List<Block> blocksInCircle = new ArrayList<Block>();
		int radiusSquared = radius * radius;
		for(int x = -radius; x <= radius; x++) {
			for(int z = -radius; z <= radius; z++) {
				if( (x*x) + (z*z) <= radiusSquared) {
					blocksInCircle.add(center.getWorld().getBlockAt((int)center.getX()-1+x, (int)center.getY()+16, ((int)center.getZ()+z)));
				}
			}
		}
		return blocksInCircle;
	}

	public static void startTNT(CircleOfBoom circle){
		COBUtils.circle = circle;
		period = TatanUtils.secondsToLong(6);
		long delay = TatanUtils.secondsToLong(10);
		tntId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			int min = (getCircle().getRadious()/5);
			int max = (int)(getCircle().getRadious()/1.5);
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
					((CircleOfBoom)Minigame.getCurrentMinigame()).addRonda(1);
					for(Player player : Bukkit.getOnlinePlayers()){
						player.setLevel(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda());
						if(!Team.getTeam(player).equals(Minigame.getObservers())){
							Minigame.getCurrentMinigame().getBoard().getObjective("Rondas").getScore(player).setScore(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda());;
						}
					}
					for(SpecialItem si : Minigame.getCurrentMinigame().getSpecialItems()){
						for(Item item : si.getItems()){
							if(item.isDead()){
								si.getHologram().delete();
								break;
							}
						}
					}
					double random = randInt(1, 10);
					if(((CircleOfBoom)Minigame.getCurrentMinigame()).isUhc()){
						if(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda() % 5 == 0){
							int healthBoosts = randInt(1, 3);
							Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"[COB] "+ChatColor.GREEN+"Dropping "+ healthBoosts + " Health Boosts!");
							for(int repetition = 0; repetition<healthBoosts; repetition++){
								Location location = getCircle().getBlocksInCircle().get(randInt(0, getCircle().getBlocksInCircle().size()-1)).getLocation();
								location.setY(getCircle().getCenter().getY()+1);
								SpecialItems.createItem(location, SpecialItems.HEALTH_BOOST.getItems(), SpecialItems.HEALTH_BOOST);
							}
						}
					}
					if(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda() % 4 == 0){
						for(SpecialItems item : SpecialItems.values()){
							double randomNumber = randInt(1, 100);
							if(randomNumber<=item.getProbability()){
								Location location = getCircle().getBlocksInCircle().get(randInt(0, getCircle().getBlocksInCircle().size()-1)).getLocation();
								location.setY(getCircle().getCenter().getY()+1);
								SpecialItems.createItem(location, item.getItems(), item);
							}
						}
					}
					if(random!=1){
						List<Block> originalBlocks = getCircle().getBlocksInCircle();
						int rand = randInt(min, max);
						if(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda()>3){
							rand = rand*((int)((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda()/3);
						}
						int x = 0;
						while(x < rand){
							int tntBlock = randInt(0, getCircle().getBlocksInCircle().size()-1);
							TNTPrimed tnt = getCircle().getWorld().spawn(getCircle().getBlocksInCircle().get(tntBlock).getLocation(), TNTPrimed.class);
							tnt.setYield(3L);
							getCircle().getBlocksInCircle().remove(tntBlock);
							x++;
						}
						getCircle().setBlocksInCircle(originalBlocks);
					} else {
						tntBarrage(COBUtils.circle);
					}
				} else {
					if(Minigame.getCurrentMinigame().getState().equals(MatchState.FINISHED)){
						Bukkit.getScheduler().cancelTask(tntId);
					}
				}
			}
		}, delay, period);
	}

	private static void tntBarrage(CircleOfBoom circle){
		COBUtils.setCircle(circle);
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			int distance = 1;
			int x = (int)COBUtils.getCircle().getCenter().getX();
			int z = (int)COBUtils.getCircle().getCenter().getZ();
			World world = COBUtils.getCircle().getWorld();
			@Override
			public void run() {
				if(distance<COBUtils.getCircle().getRadious()){
					world.spawn(new Location(world, x+distance, COBUtils.getCircle().getCenter().getY()+20, z), TNTPrimed.class);
					world.spawn(new Location(world, x-distance, COBUtils.getCircle().getCenter().getY()+20, z), TNTPrimed.class);
					world.spawn(new Location(world, x, COBUtils.getCircle().getCenter().getY()+20, z+distance), TNTPrimed.class);
					world.spawn(new Location(world, x, COBUtils.getCircle().getCenter().getY()+20, z-distance), TNTPrimed.class);
				} else {
					Bukkit.getScheduler().cancelTask(taskId);
				}

				distance++;
			}
		}, 0L, 5L);
	}

	public static CircleOfBoom getCircle() {
		return circle;
	}

	public static void setCircle(CircleOfBoom circle) {
		COBUtils.circle = circle;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static long getPeriod() {
		return period;
	}

	public static void setPeriod(long period) {
		COBUtils.period = period;
	}
}
