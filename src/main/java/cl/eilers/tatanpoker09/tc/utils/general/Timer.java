package cl.eilers.tatanpoker09.tc.utils.general;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;

public class Timer {
	private static int taskToCancel;
	private static int countdown;
	public static void startTimer(int countdown) {
		Bukkit.getScheduler().cancelTask(taskToCancel);
		Timer.countdown = countdown;
		taskToCancel = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(Timer.countdown!=0){
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"Empezando la partida en... "+ChatColor.GREEN+ChatColor.BOLD+Timer.countdown);
					Timer.countdown--;
				} else {
					Bukkit.getScheduler().cancelTask(taskToCancel);
				}
			}
		}, 0L, 20L);
	}
	public static void cycleTimer(int countdown) {
		Bukkit.getScheduler().cancelTask(taskToCancel);
		Timer.countdown = countdown;
		taskToCancel = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(Timer.countdown!=0){
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"Cambiando al mapa: "+ChatColor.GREEN+ChatColor.BOLD+Minigame.getNextMap().getName()+ChatColor.RESET+ChatColor.DARK_GREEN+" en "+ChatColor.GREEN+ChatColor.BOLD+Timer.countdown);
					Timer.countdown--;
				} else {
					Main.setId(Main.getId()+1);
					Minigame.loadMinigame(Minigame.getNextMap(), Main.getId());
					Bukkit.getScheduler().cancelTask(taskToCancel);
				}
			}
		}, 0L, 20L);
	}
}