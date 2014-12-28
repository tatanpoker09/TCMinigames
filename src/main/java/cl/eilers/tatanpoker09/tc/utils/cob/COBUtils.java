package cl.eilers.tatanpoker09.tc.utils.cob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;

public class COBUtils {
	private static CircleOfBoom circle;
	private static int taskId;
	private static int tntId;
	public static List<Block> loadBlocks(Location center, int radius){
		List<Block> blocksInCircle = new ArrayList<Block>();
		int radiusSquared = radius * radius;
		for(int x = -radius; x <= radius; x++) {
			for(int z = -radius; z <= radius; z++) {
				if( (x*x) + (z*z) <= radiusSquared) {
					blocksInCircle.add(center.getWorld().getBlockAt((int)center.getX()-1+x, 100, ((int)center.getZ()+z)));
				}
			}
		}
		return blocksInCircle;
	}

	public static void startTNT(CircleOfBoom circle){
		COBUtils.circle = circle;
		long period = TatanUtils.secondsToLong(10);
		long delay = TatanUtils.secondsToLong(10);
		tntId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			int min = (getCircle().getRadious()/5);
			int max = (int)(getCircle().getRadious()/1.5);
			@Override
			public void run() {
				double random = Math.random();
				((CircleOfBoom)Minigame.getCurrentMinigame()).addRonda(1);
				if(((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda() % 5 == 0){
					int healthBoosts = randInt(1, 3);
							Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"[COB] "+ChatColor.GREEN+"Dropping "+ healthBoosts + "Health Boosts!");
					for(int repetition = 0; repetition<=healthBoosts; repetition++){
						Minigame.getCurrentMinigame().getWorld().dropItemNaturally(getCircle().getBlocksInCircle().get(randInt(0, getCircle().getBlocksInCircle().size()-1)).getLocation(),new ItemStack(Material.EMERALD_BLOCK));
					}
				}
				if(random>0.1){ //Mejorar esto
					List<Block> originalBlocks = getCircle().getBlocksInCircle();
					int rand = randInt(min, max);
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
				if(Minigame.getCurrentMinigame().getState().equals(MatchState.FINISHED)){
					Bukkit.getScheduler().cancelTask(tntId);
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
}
