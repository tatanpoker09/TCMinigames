package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.SpecialItem;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;

public class PlayerListener implements Listener{
	private static Player player;
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				event.setRespawnLocation(Minigame.getObservers().getSpawn());
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;

			}
		}
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		((Player)event.getEntity()).setGameMode(GameMode.CREATIVE);
		((Player)event.getEntity()).getInventory().clear();
		Team.changeTeam(((Player)event.getEntity()),Minigame.getObservers());	
		if(Team.getTeam("Survivors").getPlayers().size()==1){
			((CircleOfBoom)Minigame.getCurrentMinigame()).setWinner(Team.getTeam("Survivors").getPlayers().get(0));
		} else if (Team.getTeam("Survivors").getPlayers().size()==0){
			Minigame.getCurrentMinigame().setState(MatchState.FINISHED);
			Bukkit.broadcastMessage(ChatColor.GREEN+"El ganador es: "+ ((CircleOfBoom)Minigame.getCurrentMinigame()).getWinner().getDisplayName());
			Bukkit.broadcastMessage(ChatColor.GREEN+"Sobrevivió un total de: "+((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda()+" rondas");
			for(SpecialItem si : Minigame.getCurrentMinigame().getSpecialItems()){
				for(Item item : si.getItems()){
					item.remove();
				}
				si.getHologram().delete();
			}
		}
		for(Player player : Minigame.getObservers().getPlayers()){
			((Player)event.getEntity()).showPlayer(player);
		}
		for(Team team : Minigame.getCurrentMinigame().getTeams()){
			if(!team.equals(Minigame.getObservers())){
				for(Player player : team.getPlayers()){
					player.hidePlayer(((Player)event.getEntity()));
				}
			}
		}
	}
	
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(Team.getTeam((Player)event.getEntity()).equals(Minigame.getObservers())){
					event.setCancelled(true);
				}
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}
	@EventHandler
	public void onProjectileThrow(ProjectileLaunchEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(event.getEntity().getShooter() instanceof Player){
					if(Team.getTeam((Player)event.getEntity().getShooter()).equals(Minigame.getObservers())){
						event.setCancelled(true);	
					} else {
						if(!Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
							event.setCancelled(true);
						}
					}
				}
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				event.setCancelled(true);
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				event.setCancelled(true);
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(((CircleOfBoom)Minigame.getCurrentMinigame()).isCorrer()){
					((Player) event.getEntity()).setFoodLevel(20);
				} else {
					((Player) event.getEntity()).setFoodLevel(6);
				}
				event.setCancelled(true);
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}
	@EventHandler
	public void onHealthChange(EntityRegainHealthEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(event.getEntity() instanceof Player){
					if(((CircleOfBoom)Minigame.getCurrentMinigame()).isUhc()){
						event.setCancelled(true);
					}
				}
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			if(Team.getTeam(event.getPlayer()).equals(Minigame.getObservers())){
				event.getItemDrop().remove();
			} else if (!Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
				event.getItemDrop().remove();
			}
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			player = event.getPlayer();
			player.setLevel(0);
			Minigame.getObservers().addPlayer(event.getPlayer());
			event.getPlayer().teleport(Minigame.getObservers().getSpawn());
			new BukkitRunnable() {

				@Override
				public void run() {
					for(PotionEffect pe : player.getActivePotionEffects()){
						player.removePotionEffect(pe.getType());
					}
				}
			}.runTaskLaterAsynchronously(Main.getPlugin(), TatanUtils.secondsToLong(1));
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:


				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;

			}
		}
	}
	@EventHandler
	public void onPotionDrink(PlayerItemConsumeEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(Team.getTeam(event.getPlayer()).equals(Minigame.getObservers())){
					event.setCancelled(true);
				} else {
					if(!Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){

					}
				}
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onPlayerPickUpItem(PlayerPickupItemEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				if(!Team.getTeam(event.getPlayer()).equals(Minigame.getObservers())){
					for(SpecialItem si : Minigame.getCurrentMinigame().getSpecialItems()){
						if(si.getItems().contains(event.getItem())){
							for(Item item : si.getItems()){
								item.remove();
							}si.getHologram().delete();
							switch(si.getType()){
							case HEALTH_BOOST:
								int totalHealth = (int)event.getPlayer().getHealth()+6;
								if(totalHealth>20)	totalHealth = 20;
								event.getPlayer().setHealth(totalHealth);
								event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 5L, 5L);
								event.setCancelled(true);
								event.getItem().remove();
								break;
							case KB_STICK:
								break;
							case ONE_USE_STONE_SWORD:
								break;
							case PUNCH_BOW:
								break;
							default:
								break;
							}
							Minigame.getCurrentMinigame().getSpecialItems().remove(si);
							break;
						}
					}
				} else {
					event.setCancelled(true);
				}
				break;
			case KEY_QUEST:
				break;
			case WATER_THE_MONUMENT:
				break;
			default:
				break;

			}
		}
	}
	public static Player getPlayer() {
		return player;
	}
	public static void setPlayer(Player player) {
		PlayerListener.player = player;
	}
}