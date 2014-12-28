package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.tc.minigame.Map;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;

public class CambiarMapaCommand implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length>0){
			Map mapToPlay = null;
			String argsConcatenated = TatanUtils.concatenateArgs(args);
			for(Map map : Map.getMaps()){
				if(map.getName().toLowerCase().startsWith(argsConcatenated.toLowerCase())){
					mapToPlay = map;
				}
			}
			if(mapToPlay==null){
				sender.sendMessage(ChatColor.RED+"No se encontr� un mapa con ese nombre.");
				return false;
			} else {
				sender.sendMessage(ChatColor.GREEN+"Se ha seleccionado: "+ChatColor.DARK_GREEN+mapToPlay.getName());
				Minigame.setNextMap(mapToPlay);
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames] "+ChatColor.RED+"Debes especificar el mapa.");
		}
		return false;
	}
}
