package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.tc.utils.general.TatanUtils;
import cl.eilers.tatanpoker09.tc.utils.general.Timer;

public class CycleCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(TatanUtils.isNumeric(args[0])){
			Timer.cycleTimer(Integer.parseInt(args[0]));
			return true;
		}

		return false;
	}
}