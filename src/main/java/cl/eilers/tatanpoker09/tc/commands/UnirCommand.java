/**
 * 
 */
package cl.eilers.tatanpoker09.tc.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.MapType;
import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.minigame.cob.Survivor;

public class UnirCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player playerSender = (Player)sender;
		if(Minigame.getCurrentMinigame()!=null){
			if(Minigame.getCurrentMinigame().getMap().getType().equals(MapType.CIRCLE_OF_BOOM)){
				if(Minigame.getCurrentMinigame().getState().equals(MatchState.STARTED)){
					sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames]"+ChatColor.RED+"No puedes unirte en este momento.");
				} else {
					if(args.length==0){
						ArrayList<Team> teamsInGame = new ArrayList<Team>();
						for(Team team : Minigame.getCurrentMinigame().getTeams()){
							if(!team.equals(Minigame.getObservers())){
								teamsInGame.add(team);
							}
						}
						Team shortest = teamsInGame.get(0);
						for(Team team : teamsInGame) {
							if (team.getPlayers().size() < shortest.getPlayers().size()) {
								shortest = team;
							}
						}
						if(shortest.getPlayers().size()>=shortest.getMaxCapacity()){
							sender.sendMessage(ChatColor.RED+"Este equipo está lleno.");
						} else {				
							if(!Team.getTeam(playerSender).equals(Minigame.getObservers())){
								((CircleOfBoom)Minigame.getCurrentMinigame()).getSurvivors().remove(Survivor.getSurvivor(playerSender));
							}
							Team.getTeam(playerSender).remove(playerSender);
							shortest.addPlayer(playerSender);
							return true;
						}
					} else {
						Team teamToJoin = Team.getTeam(args[0]);
						if(teamToJoin==null){
							playerSender.sendMessage(ChatColor.RED+ "No se ha encontrado un equipo con ese nombre");
						} else {
							if(teamToJoin.getPlayers().size()>=teamToJoin.getMaxCapacity()){
								sender.sendMessage(ChatColor.RED+"Este equipo está lleno.");
								return false;
							}
							((CircleOfBoom)Minigame.getCurrentMinigame()).getSurvivors().remove(Survivor.getSurvivor(playerSender));
							Team.getTeam(playerSender).remove(playerSender);
							teamToJoin.addPlayer(playerSender);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
