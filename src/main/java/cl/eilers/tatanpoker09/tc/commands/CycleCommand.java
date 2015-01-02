package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;
import cl.eilers.tatanpoker09.tc.utils.general.Timer;

public class CycleCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		int delay = 15;
		if(args.length>0){
			if(TatanUtils.isNumeric(args[0])){
				delay = Integer.parseInt(args[0]);
			} else {
				sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames] "+ChatColor.RED+"Se esperaba un número, se recibió una String.");
				return false;
			}
		}
		Timer.cycleTimer(delay);
		return true;
	}
}
