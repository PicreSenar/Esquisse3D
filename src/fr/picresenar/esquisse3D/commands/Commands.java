package fr.picresenar.esquisse3D.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.picresenar.esquisse3D.MainEsquisse;
import fr.picresenar.esquisse3D.Players;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;
import fr.picresenar.esquisse3D.etapes.MainEtapes;

public class Commands implements CommandExecutor {
	
	@SuppressWarnings("unused")
	public MainEsquisse main;

	public Commands(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	private Players pl = new Players(main);

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if(sender instanceof Player) {
			Player player=(Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("adminE3D")) {
				
			

				ItemStack LivreAdmin = new ItemStack(Material.BOOK);
				ItemMeta LivreAdminM = LivreAdmin.getItemMeta();
				
				LivreAdminM.setDisplayName("§cOutil admin E3D");
				LivreAdminM.setLore(Arrays.asList("Différentes commandes liées au Esquissé 3D","A utiliser avec précaution","Appartient à " + player.getDisplayName()));
				
				LivreAdmin.setItemMeta(LivreAdminM);
				
				player.getInventory().setItemInHand(LivreAdmin);		
				//player.updateInventory();
				
				player.sendMessage("§2[Esquissé 3D] §aTu es maintenant admin du Esquissé 3D");
				
				return true;
			}
			
		}
		
		if(cmd.getName().equalsIgnoreCase("startE3D")) {
			
			MainEtapes ME= new MainEtapes(this.main);

			if(main.etape==ListeEtapes.LOBBY) {
				ME.CheckDemarrage();
			}else {
				if(sender instanceof Player)sender.sendMessage("§4[Erreur] §c La partie est déjà commencée");
			}
		}
		
		
		
		if(cmd.getName().equalsIgnoreCase("addPlayerE3D")||cmd.getName().equalsIgnoreCase("removeplayerE3D")) {
			
			if(args.length==0) {
				sender.sendMessage("§4[Erreur] §cLa commande doit avoir le format §o§d/addPlayerE3D <Player_name>");
				return false;
			}else if(args.length>=2) {
				sender.sendMessage("§4[Erreur] §cLe format du nom du joueur est invalide §l(!Ne pas utiliser d'espace!)");
				return false;
			}else if(Bukkit.getPlayer(args[0])!=null) {
				if(cmd.getName().equalsIgnoreCase("addPlayerE3D")) {
					boolean isCreated=pl.setNewPlayer(Bukkit.getPlayer(args[0]).getUniqueId(),sender);
					return isCreated;
				}else if(cmd.getName().equalsIgnoreCase("removeplayerE3D")) {
					boolean isDeleted=pl.RemovePlayer(Bukkit.getPlayer(args[0]).getUniqueId(),sender);
					return isDeleted;
				}
				
			}else {
				String wrongname = args[0];
				sender.sendMessage("§4[Erreur] §cLe joueur §3" + wrongname + "§c n'existe pas ou n'est pas en ligne");
				return false;
			}
		}
			
		
		return false;
	}

}
