package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;

public class DebugTeamsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		for(Team team : Minigame.getCurrentMinigame().getTeams()){
			sender.sendMessage(team.getName()+": "+team.getPlayers().size());
			sender.sendMessage(""+team.getMaxCapacity());
		}
		return true;
	}
}