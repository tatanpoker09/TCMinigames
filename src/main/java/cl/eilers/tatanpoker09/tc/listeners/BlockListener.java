package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import cl.eilers.tatanpoker09.tc.minigame.Minigame;

public class BlockListener implements Listener{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
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
		event.setCancelled(true);
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
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
				event.setCancelled(true);
		}
	@EventHandler
	public void onTntExplode(EntityExplodeEvent event){
		if(Minigame.getCurrentMinigame()!=null){
			switch(Minigame.getCurrentMinigame().getMap().getType()){
			case CIRCLE_OF_BOOM:
				event.blockList().clear();
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
	public void onLiquidFlow(BlockFromToEvent event){
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
}
