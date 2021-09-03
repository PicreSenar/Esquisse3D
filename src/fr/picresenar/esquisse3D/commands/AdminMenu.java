package fr.picresenar.esquisse3D.commands;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.picresenar.esquisse3D.MainEsquisse;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;

public class AdminMenu {

	public MainEsquisse main;
	

	public AdminMenu(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	
	
	public Inventory createInv() {
		
		Fonctions f = new Fonctions(main);
		
		Inventory inv = Bukkit.createInventory(null, 54,"§cMenu administrateur E3D");
		
		inv.setItem(0, f.getItem(Material.REDSTONE_BLOCK,"Démarrer la partie", null));
		inv.setItem(1, f.getItem(Material.BARRIER,"Stopper la partie", null));
		
		inv.setItem(3, f.getItem(Material.RED_MUSHROOM,"Changer d'étape(règles de constructions)", null));
		
		inv.setItem(5, f.getItem(Material.GUNPOWDER,"Supprimer tous les joueurs",null));
		inv.setItem(6, f.getItem(Material.BONE,"Retirer un joueur",null));
		inv.setItem(7, f.getItem(Material.EGG,"Ajouter un joueur",null));
		inv.setItem(8, f.getItem(Material.COOKED_CHICKEN,"Ajouter tous les joueurs en ligne",null));
		
		
		inv.setItem(19, f.getItem(Material.ENCHANTED_GOLDEN_APPLE,"Téléporter tous les joueurs en ligne au lobby",null));
		inv.setItem(28, f.getItem(Material.GOLDEN_APPLE,"Aller au Lobby du E3D",null));
		inv.setItem(37, f.getItem(Material.APPLE,"Téléporter un joueur au lobby du E3D",null));
		
		
		inv.setItem(39, f.getItem(Material.NETHER_STAR,"Urne précédente",null));
		
		inv.setItem(30, f.getItem(Material.NAME_TAG,"Première urne de choix de mot",null));
		
		inv.setItem(21, f.getItem(Material.NETHER_STAR,"Urne suivante",null));
		
		
		inv.setItem(33, f.getItem(Material.ITEM_FRAME,"Première salle de construction",null));
		
		inv.setItem(42, f.getItem(Material.NETHER_STAR,"Salle précédente X",null));
		
		inv.setItem(32, f.getItem(Material.NETHER_STAR,"Salle précédente Z",null));
		
		inv.setItem(24, f.getItem(Material.NETHER_STAR,"Salle suivante X",null));
		
		inv.setItem(34, f.getItem(Material.NETHER_STAR,"Salle suivante Z",null));
		
		inv.setItem(45, f.getItem(Material.CLOCK,"Finir le timer",null));
		inv.setItem(46, f.getItem(Material.CLOCK,"Enlever 1 minute au timer",null));
		inv.setItem(47, f.getItem(Material.CLOCK,"Ajouter 1 minute au timer",null));
		inv.setItem(48, f.getItem(Material.CLOCK,"Revenir au timer initial (= "+main.timermanches+" secondes)",null));
		
		
		inv.setItem(50, f.getItem(Material.GLOWSTONE,"Dessiner les titres",null));
		inv.setItem(51, f.getItem(Material.BRICK,"Réparer les murs",null));
		inv.setItem(52, f.getItem(Material.FLINT_AND_STEEL,"Effacer les constructions",null));
		inv.setItem(53, f.getItem(Material.TNT,"Tout effacer §c( /!\\ lags )",null));
		
		return inv;
	}
	
	
	public Inventory showListofPlayers(List<UUID> players,String titre) {
		
		Fonctions f = new Fonctions(main);
		
		Integer size =  Math.round(players.toArray().length/9)*9+9;
		
		if(players.toArray().length==0)return null;
		
		Inventory inv = Bukkit.createInventory(null, size,titre);
		
		Integer i=0;
		for(UUID currentP:players) {
			inv.setItem(i, f.getSkull(Bukkit.getPlayer(currentP).getDisplayName(), Bukkit.getPlayer(currentP).getDisplayName()));
			i++;
		}
		
				
		return inv;
	}
	
	
	public Inventory showListofEtapes(String titre) {
	
		Fonctions f = new Fonctions(main);
		
		Integer size =  Math.round(ListeEtapes.values().length/9)*9+9;
		
		if(ListeEtapes.values().length==0)return null;
		
		Inventory inv = Bukkit.createInventory(null, size,titre);
		
		Integer i=0;
		for(ListeEtapes etape:ListeEtapes.values()) {
			
			inv.setItem(i, f.getItem(Material.BOOK,etape.toString(),null));
			i++;
		}
		
				
		return inv;
	}
	
	public Inventory DeplacementDestruction() {
		Fonctions f = new Fonctions(main);
		
		Inventory inv = Bukkit.createInventory(null, 27,"§cMenu téléport E3D");
		
				
		inv.setItem(13, f.getItem(Material.ITEM_FRAME,"Voir les différentes salles",null));
		
		inv.setItem(22, f.getItem(Material.NETHER_STAR,"Salle précédente X",null));
		
		inv.setItem(12, f.getItem(Material.NETHER_STAR,"Salle précédente Z",null));
		
		inv.setItem(4, f.getItem(Material.NETHER_STAR,"Salle suivante X",null));
		
		inv.setItem(14, f.getItem(Material.NETHER_STAR,"Salle suivante Z",null));
		
		
		
		
		return inv;
	
	}	
	
	public Inventory SallesDestructions(Integer page) {
		Fonctions f = new Fonctions(main);
		
		Inventory inv = Bukkit.createInventory(null, 54,"§c[E3D] Se téléporter page"+(page+1));
		
		Integer i;
		Integer j;
		
		Integer maxi=0;
		
		Integer Drawer=0;
		Integer Guesser=0;
		
		Integer NbJoueurs = main.getPlayers().toArray().length;
		
		//Bukkit.broadcastMessage("" + NbJoueurs);
		
		if(page*6+5>NbJoueurs-1)maxi=NbJoueurs-1;
		else maxi=page*6+5;
		
		for(i=page*6;i<=maxi;i++) {
		
			for(j=1;j<=(NbJoueurs-NbJoueurs%2)/2;j++) {
				
				
				if(i-2*(j-1)-NbJoueurs%2>=NbJoueurs) {
					Drawer=i-2*(j-1)-NbJoueurs%2-NbJoueurs;
				}else if(i-2*(j-1)-NbJoueurs%2<0){
					Drawer=i-2*(j-1)-NbJoueurs%2+NbJoueurs;
				}else {
					Drawer=i-2*(j-1)-NbJoueurs%2;
				}
				
				if(i-2*(j-1)-NbJoueurs%2-1>=NbJoueurs) {
					Guesser=2*(j-1)-NbJoueurs%2-1-NbJoueurs;
				}else if(i-2*(j-1)-NbJoueurs%2-1<0){
					Guesser=i-2*(j-1)-NbJoueurs%2-1+NbJoueurs;
				}else {
					Guesser=i-2*(j-1)-NbJoueurs%2-1;
				}
				
			String Phrase1="§4Evolution du mot (proposé par "+main.NomJoueurs[i]+") : ";
			String Phrase2="§2"+main.RefCochons[i][0].getCustomName();
			String Phrase3="§a"+main.NomJoueurs[Drawer]+" a essayé de construire : §e"+main.RefCochons[i][(j-1)*2+1].getCustomName();
			String Phrase4="§a"+main.NomJoueurs[Guesser]+" a deviné : §5"+main.RefCochons[i][(j-1)*2+2].getCustomName();
				
			inv.setItem((i*9+j-1), f.getItem(Material.ITEM_FRAME,"Mot n°"+(i+1)+" / Dessin n°"+j,Arrays.asList(Phrase1,Phrase2,Phrase3,Phrase4)));
			

		
			}
		}
		
		
		if(6<main.Jtot)inv.setItem(53, f.getItem(Material.NETHER_STAR,"Page suivante",null));
		
		return inv;
	
	}	
	
	
	


}


//if(JoueurVisited-2*(DessinVisited)-decalage>=0) {
//	NomJoueurDraw=main.NomJoueurs[JoueurVisited-2*(DessinVisited)-decalage];
//}else {
//	NomJoueurDraw=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited)-decalage];
//}
//
//
//if(JoueurVisited-2*(DessinVisited)-decalage-1>=0) {
//	NomJoueurGuess=main.NomJoueurs[JoueurVisited-2*(DessinVisited)-decalage-1];
//}else {
//	NomJoueurGuess=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited)-decalage-1];
//}
//
//
////if(DessinVisited==0) {
////
////	NomJoueurGive=main.NomJoueurs[JoueurVisited];
////	
////}else if(JoueurVisited-2*(DessinVisited-1)-decalage>=0) {
////	
////	NomJoueurGive=main.NomJoueurs[JoueurVisited-2*(DessinVisited-1)-decalage];
////	
////}else {
////	NomJoueurGive=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited-1)-decalage];
////}
//
//for(UUID pl:main.getPlayers()) {
//	
//	if(Bukkit.getPlayer(pl)!=null) {
//	
//		if(i==0)for(UUID specs:main.getSpecs()) {
//			f.TP2Salle(main.JoueurVisite, main.DessinVisite, Bukkit.getPlayer(specs));
//			
//			Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Evolution du mot de §3"+main.NomJoueurs[JoueurVisited]+"§a - Salle"+(DessinVisited+1));		
//			if(DessinVisited==0)Bukkit.getPlayer(specs).sendMessage("§a - §3"+main.NomJoueurs[JoueurVisited]+"§a a donné le mot : "+main.RefCochons[JoueurVisited][0].getCustomName() );		
//			Bukkit.getPlayer(specs).sendMessage("§a - §3"+NomJoueurDraw+"§a a construit : §e"+main.RefCochons[JoueurVisited][1+2*DessinVisited].getCustomName());	
//			Bukkit.getPlayer(specs).sendMessage("§a - Et §3"+NomJoueurGuess+"§a a deviné : §5"+main.RefCochons[JoueurVisited][2*DessinVisited+2].getCustomName()+"\n\n");
