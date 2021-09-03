package fr.picresenar.esquisse3D;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;

public class Players {

	private MainEsquisse main;


	public Players(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
		public boolean setNewPlayer(UUID player, CommandSender sender) {
			
		if(player==null)return false;
		
		switch(main.etape) {
			case LOBBY:
			case STARTING:
				if(!main.getPlayers().contains(player)) {
					if(main.getPlayers().toArray().length+1<main.Jmax) {
						main.getPlayers().add(player);
						main.getNonPlayers().remove(player);
						if(main.getSpecs().contains(player)) main.getSpecs().remove(player);
						if (sender!=null&&Bukkit.getPlayer(player)!=null) sender.sendMessage("�a[Succ�s] "+Bukkit.getPlayer(player).getName()+" a bien �t� rajout�(e)");
						for(UUID pl:main.getPlayers()) {
							if(Bukkit.getPlayer(player)!=null)Bukkit.getPlayer(pl).sendMessage("�b[Esquiss� 3D] �3"+Bukkit.getPlayer(player).getName()+"�b vient de rejoindre la partie �d("+ main.getPlayers().toArray().length+"/"+main.Jmax+")");
						}
						return true;
	
					}else {
						if (sender!=null) sender.sendMessage("��4[Erreur] �cLa partie est compl�te");
						if(Bukkit.getPlayer(player)!=null)Bukkit.getPlayer(player).sendMessage("�cD�sol� la partie d'Esquiss� 3D est compl�te...");
						return false;
					}
				}else {
					if (sender!=null&&Bukkit.getPlayer(player)!=null) sender.sendMessage("�4[Erreur]�d"+Bukkit.getPlayer(player).getName()+"�c est d�j� dans la liste des joueurs");
					return false;
				}
		default:
				sender.sendMessage("�4[Erreur] �cImpossible de rajouter un joueur apr�s le d�marrage de la partie");
			return false;
		}
	}
	
	public boolean RemovePlayer(UUID player, CommandSender sender) {
		switch(main.etape) {
		case LOBBY:
		case STARTING:
			if(main.getPlayers().contains(player)) {
				main.getPlayers().remove(player);
				main.getNonPlayers().add(player);
				if (sender!=null &&Bukkit.getPlayer(player)!=null) sender.sendMessage("�a[Succ�s] �3"+Bukkit.getPlayer(player).getName()+" a bien �t� supprim�(e)");
				if(Bukkit.getPlayer(player)!=null)Bukkit.getPlayer(player).sendMessage("�4[Esquiss� 3D] �cVous avez �t� retir� des participants");
				for(UUID pl:main.getPlayers()) {
					if(main.etape==ListeEtapes.LOBBY||Bukkit.getPlayer(player)!=null)Bukkit.getPlayer(pl).sendMessage("�4[Esquiss� 3D] �3"+Bukkit.getPlayer(player).getName()+"�c vient de quitter la partie �d("+ main.getPlayers().toArray().length+"/"+main.Jmax+")");
	//				if(Bukkit.getPlayer(player)!=null)System.out.println("�6"+Bukkit.getPlayer(player).getName()+"�e vient de quitter la partie de Esquiss� 3D ");
				}
				return true;
			}else {
				if (sender!=null||Bukkit.getPlayer(player)!=null) sender.sendMessage("�4[Erreur] �3"+Bukkit.getPlayer(player).getName()+" �cn'est pas dans la liste des joueurs");
				return false;
			}
		case ENDING:
			if(main.getPlayers().contains(player)) {
				main.getPlayers().remove(player);
			}
			
			return true;
	default:
		if (sender!=null) {
			sender.sendMessage("�4[Erreur] �cImpossible de supprimer un joueur apr�s le d�marrage de la partie");
		}else {
			main.getPlayers().remove(player);
			main.getNonPlayers().add(player);
		}
		return false;
	}
	}
	
	public void RemoveAllPlayer() {
		main.getNonPlayers().addAll(main.players);
		main.getPlayers().removeAll(main.players);
	}
	

}
