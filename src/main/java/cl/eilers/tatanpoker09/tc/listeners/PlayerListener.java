package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;

public class PlayerListener implements Listener{
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		switch(Minigame.getCurrentMinigame().getMap().getType()){
		case CIRCLE_OF_BOOM:
			Team.changeTeam(event.getPlayer(),Minigame.getObservers());	
			if(Team.getTeam("Survivors").getPlayers().size()==1){
				((CircleOfBoom)Minigame.getCurrentMinigame()).setWinner(Team.getTeam("Survivors").getPlayers().get(0));
			} else if (Team.getTeam("Survivors").getPlayers().size()==0){
				Minigame.getCurrentMinigame().setState(MatchState.FINISHED);
				Bukkit.broadcastMessage(ChatColor.GREEN+"El ganador es: "+ ((CircleOfBoom)Minigame.getCurrentMinigame()).getWinner().getDisplayName());
				Bukkit.broadcastMessage(ChatColor.GREEN+"Sobrevivió un total de: "+((CircleOfBoom)Minigame.getCurrentMinigame()).getRonda()+" rondas");
			}
			event.getPlayer().setGameMode(GameMode.CREATIVE);
			for(Player player : Minigame.getObservers().getPlayers()){
				event.getPlayer().showPlayer(player);
			}
			for(Team team : Minigame.getCurrentMinigame().getTeams()){
				if(!team.equals(Minigame.getObservers())){
					for(Player player : team.getPlayers()){
						player.hidePlayer(event.getPlayer());
					}
				}
			}
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
				event.setCancelled(true);
			} else if (!Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
				event.getItemDrop().remove();
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			Minigame.getObservers().addPlayer(event.getPlayer());
			event.getPlayer().teleport(Minigame.getObservers().getSpawn());
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
}