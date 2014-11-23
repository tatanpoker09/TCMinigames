/**
 * 
 */
package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.MapType;
import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;

public class UnirCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player playerSender = (Player)sender;
		if(Minigame.getCurrentMinigame().getMap().getType().equals(MapType.CIRCLE_OF_BOOM)){
			if(Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
				sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames]"+ChatColor.RED+"Esta partida ya ha empezado.");
			}
		}
		if(args.length>0){
			Team shortest = Minigame.getCurrentMinigame().getTeams().get(0);
			for(Team team : Minigame.getCurrentMinigame().getTeams()) {
			    if (team.getPlayers().size() < shortest.getPlayers().size()) {
			        shortest = team;
			    }
			}
			shortest.addPlayer(playerSender);
		} else {
			Team teamToJoin = Team.getTeam(args[0]);
			if(teamToJoin==null){
				playerSender.sendMessage(ChatColor.RED+ "No se ha encontrado un equipo con ese nombre");
			} else {
				teamToJoin.addPlayer(playerSender);
				return true;
			}
		}
		return false;
	}

}
