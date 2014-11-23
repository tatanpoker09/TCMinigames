package cl.eilers.tatanpoker09.tc.utils.cob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;

public class COBUtils {
	private static CircleOfBoom circle;
	private static int taskId;

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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			int min = (getCircle().getRadious()/5);
			int max = (int)(getCircle().getRadious()/1.5);
			@Override
			public void run() {
				if(Math.random()>0.05){
					List<Block> originalBlocks = getCircle().getBlocksInCircle();
					int rand = randInt(min, max);
					int x = 0;
					while(x < rand){
						int tntBlock = randInt(0, getCircle().getBlocksInCircle().size());
						getCircle().getBlocksInCircle().get(tntBlock);
						getCircle().getBlocksInCircle().remove(tntBlock);
					}
					getCircle().setBlocksInCircle(originalBlocks);
				} else {
					tntBarrage(COBUtils.circle);
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
			World world = COBUtils.getCircle().getMap().getWorld();
			@Override
			public void run() {
				if(distance>COBUtils.getCircle().getRadious()){
					 world.spawn(new Location(world, x+distance, COBUtils.getCircle().getCenter().getY()+20, z), TNTPrimed.class);
					world.spawn(new Location(world, x-distance, COBUtils.getCircle().getCenter().getY()+20, z), TNTPrimed.class);
					world.spawn(new Location(world, x, COBUtils.getCircle().getCenter().getY()+20, +distance), TNTPrimed.class);
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
