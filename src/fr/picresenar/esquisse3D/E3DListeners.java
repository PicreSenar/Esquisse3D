package fr.picresenar.esquisse3D;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.picresenar.esquisse3D.commands.AdminMenu;
import fr.picresenar.esquisse3D.commands.Fonctions;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;
import fr.picresenar.esquisse3D.etapes.Builders;
import fr.picresenar.esquisse3D.etapes.MainEtapes;
import fr.picresenar.esquisse3D.etapes.Timer;





public class E3DListeners implements Listener {
	

	private MainEsquisse main;
	
	
	public E3DListeners(MainEsquisse mainEsquisse) {
		this.main=mainEsquisse;
	}


	public String AddOrDelete="";


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		if (main.isStarted==false) return;
		
		Player player = event.getPlayer();
		//Players p = new Players(main);
		Fonctions f=new Fonctions(main);

		//System.out.println("Connexion de "+player.getUniqueId());

		
		if(main.getPlayers().contains(player.getUniqueId())){
			
			player.sendMessage("�2[Esquiss� 3D] �aBon retour, voici une pomme, mange-l� pour revenir en jeu");
			player.getInventory().setItem(1,f.getItem(Material.GOLDEN_APPLE,"�1Revenir au Esquiss� 3D",null));
			
		}else if(!main.getNonPlayers().contains(player.getUniqueId())) {
			main.getNonPlayers().add(player.getUniqueId());
		}
		
	}
		
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
	
		Player player=event.getPlayer();
		Fonctions f=new Fonctions(main);
		
		if(main.getSpecs().contains(player.getUniqueId())) {
			f.TP2Lobby(player);
			player.setGameMode(Bukkit.getDefaultGameMode());
			main.getSpecs().remove(player.getUniqueId());
		}else if(main.getNonPlayers().contains(player.getUniqueId())) {
			main.getNonPlayers().remove(player.getUniqueId());
		}
	}
	
	@EventHandler
	public void isDamaged(EntityDamageByEntityEvent event) {
		
		if (main.isStarted==false) return;
		
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		
		
		if(entity.getType()==EntityType.PIG && damager instanceof Player && entity.getCustomName()!=null) {
			damager.sendMessage("�5Cochon : �d Oh baby don't hurt me! Don't hurt me! No more...");
			event.setCancelled(true);
		}
//			else if(entity.getType()==EntityType.PIG) {
//			damager.sendMessage("�dGRRUUUUUIIIIKKKK");
//		}
		
		
	}
	
	@EventHandler
	public void onDestroyed(BlockBreakEvent event) {
		
		if (main.isStarted==false) return;
		
		Player player = event.getPlayer();
		Block block= event.getBlock();
		Material material=block.getType();

		
		Fonctions f=new Fonctions(main);
		
		 
	 if(main.etape!=ListeEtapes.DESTRUCTION && f.isInE3DArea(block.getLocation()) && (material==Material.CLAY)) {
		 event.setCancelled(true);
		 player.sendMessage("�4[Esquiss� 3D] �cImpossible de casser ce bloc");
		 return;
	 }else if((main.etape==ListeEtapes.GUESSING||main.etape==ListeEtapes.REVEALING) && f.isInE3DArea(block.getLocation())&&main.getPlayers().contains(player.getUniqueId()))	{
		 event.setCancelled(true);
		 player.sendMessage("�4[Esquiss� 3D] �cNe d�truisez pas les oeuvres des autres...");
		 
	 }else if(f.isInE3DArea(block.getLocation()) && (material==Material.BEDROCK||material==Material.BARRIER)&&main.getPlayers().contains(player.getUniqueId())) {
		 event.setCancelled(true);
		 player.sendMessage("�4[Esquiss� 3D] �cImpossible de casser ce bloc");
	 }else if(main.etape==ListeEtapes.CHOSING && f.isInE3DArea(block.getLocation())&&main.getPlayers().contains(player.getUniqueId()))	{
		 event.setCancelled(true);
		 player.sendMessage("�4[Esquiss� 3D] �cVous ne pouvez rien casser ici...");
	 }else if(main.etape==ListeEtapes.DESTRUCTION && !f.isInE3DArea(block.getLocation())&&main.getPlayers().contains(player.getUniqueId())) {
		 event.setCancelled(true);
		 player.sendMessage("�4[Esquiss� 3D] �cVous ne pouvez d�truire que ce qui se trouve dans l'aire de jeu !");
	 }else if(block.getType()==Material.ANVIL && main.etape!=ListeEtapes.DESTRUCTION) {
		 for(Location loc:main.AnvilList) {
			 if(f.isBetweenLocations(block.getLocation(), loc, loc)) {
				 event.setCancelled(true);
				 player.sendMessage("�4[Esquiss� 3D] �cVous ne pouvez pas d�truire cette enclume...");

			 }
		 }
	 }
		
		
	}
	
		
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		Fonctions f=new Fonctions(main);
		//Material block=event.getMaterial();
		
		
		 AdminMenu adm=new AdminMenu(main);
		 MainEtapes me=new MainEtapes(main);
		
		if (player.getWorld()!=Bukkit.getWorld(main.world)) return;
		// System.out.println("Joueur: "+player+" / Action: "+action);
		 
		 
		 
		
		if(it == null) return;
		
		ItemMeta itM = it.getItemMeta();
		
		// System.out.println("Item: "+it.getType()+" / Nom: "+itM.getDisplayName());
			
		if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && itM.hasDisplayName()) {
			
			if(it.getType()==Material.BOOK  ) {
					
				if(itM.getDisplayName().equalsIgnoreCase("�cOutil admin E3D")) {
					
					Inventory inv=adm.createInv();
					player.openInventory(inv);
					
					return;
					
				}else if (itM.getDisplayName().equalsIgnoreCase("�dT�l�portation Esquiss� 3D")) {	
					

					Inventory inv=adm.DeplacementDestruction();
					player.openInventory(inv);
					
					return;
					
					
				}else if (itM.getDisplayName().equalsIgnoreCase("�dSalle E3D pr�c�dente")) {
					
					if(main.DessinVisite<=0) {
						if(main.JoueurVisite<=0) {
							player.sendMessage("�4[Esquiss� 3D] �cVous �tes d�j� dans la premi�re salle");
							return;
						}else {
							main.DessinVisite=(main.Jtot-main.Jtot%2)/2-1;
							main.JoueurVisite--;
						}
					}else {
						main.DessinVisite--;
					}
						
					player.getInventory().setHeldItemSlot(1);	
					
					me.Visite();
					return;
					
				}else if (itM.getDisplayName().equalsIgnoreCase("�dSalle E3D suivante")) {
					if(main.DessinVisite>=(main.Jtot-main.Jtot%2)/2-1) {
						if(main.JoueurVisite>=main.Jtot-1) {
							player.sendMessage("�4[Esquiss� 3D] �cVous �tes dans la derni�re salle");
							player.getInventory().setItem(4, f.getItem(Material.TNT, "�dPasser � l'�tape �4Destruction", null));
							player.updateInventory();
							return;
						}else {
							main.DessinVisite=0;
							main.JoueurVisite++;
						}
					}else {
						main.DessinVisite++;
					}
					
					player.getInventory().setHeldItemSlot(7);						
					
					me.Visite();
					return;

				}
			}else if(it.getType()==Material.TNT&&main.etape==ListeEtapes.REVEALING){
				if(itM.getDisplayName().equalsIgnoreCase("�dPasser � l'�tape �4Destruction")) {
					event.setCancelled(true);
				
					me.Destroy(false,null);
					return;
				}
//			}else if(itM.getDisplayName().equalsIgnoreCase("�1Revenir au Esquiss� 3D")) {
			}else if(it.getType()==Material.GOLDEN_APPLE) {

				if(main.getPlayers().contains(player.getUniqueId())) {
					
					player.getInventory().clear();
					
					switch(main.etape) {
					
					case LOBBY:
						f.TP2Lobby(player);
						player.sendMessage("�2[Esquiss� 3D] �aBon retour, nous t'attendions avant de commmencer!");
						player.getInventory().remove(it);
						break;
					case STARTING:
						f.TP2Lobby(player);
						for(UUID pl:main.getPlayers()) Bukkit.getPlayer(pl).sendMessage("�4[Esquiss� 3D]�3 "+ Bukkit.getPlayer(player.getUniqueId()).getName() + "�c est de retour juste � temps ! !");
						player.getInventory().remove(it);
						break;
						
					case CHOSING:
						player.sendMessage("�2[Esquiss� 3D] �a Retour dans l'�tape de choix...");
						player.setGameMode(GameMode.CREATIVE);
						player.getInventory().remove(it);
						me.ChoixdesMots(true, player);
						
						break;
						
					case DRAWING:
						player.sendMessage("�2[Esquiss� 3D] �a Retour dans l'�tape de construction...");
						player.setGameMode(GameMode.CREATIVE);
						
						me.Drawing(true, player);
						
						break;
						
					case GUESSING:
						player.sendMessage("�2[Esquiss� 3D] �a Retour dans l'�tape de devinette...");
						player.setGameMode(GameMode.CREATIVE);
						
						me.Guessing(true, player);
						
						break;

					case REVEALING:
						player.sendMessage("�2[Esquiss� 3D] �a Retour � l'�tape de r�v�lation...");
						player.setGameMode(GameMode.CREATIVE);
						
						me.Revealing(true, player);
						
						break;
					
					case DESTRUCTION:
						player.sendMessage("�2[Esquiss� 3D] �a Retour � l'�tape de destruction...");
						player.setGameMode(GameMode.CREATIVE);
						
						me.Destroy(true, player);
						
						break;
						
						
					default:
						player.sendMessage("�2[Esquiss� 3D] �a Un d�connexion ? Tout va bien ?");
						//player.setGameMode(GameMode.CREATIVE);
						player.getInventory().remove(it);
						
						break;			
					
	
					}
				}else {
					player.sendMessage("�2[Esquiss� 3D] �aTu ne peux plus utiliser cela !");
					player.getInventory().remove(it);
				}
			}
		}else if(action==Action.RIGHT_CLICK_BLOCK ) {
			Block block=event.getClickedBlock();
			if((it.getType()==main.MurMaterial||it.getType()==Material.BARRIER||it.getType()==Material.BEDROCK) && f.isInE3DArea(block.getLocation()) ) {
				event.setCancelled(true);
				player.sendMessage("�4[Esquiss� 3D] �cCe bloc est r�serv� aux parois, impossible de l'utiliser");
			}else if((main.etape==ListeEtapes.GUESSING||main.etape==ListeEtapes.CHOSING||main.etape==ListeEtapes.REVEALING)&& it.getType()!=Material.NAME_TAG&& f.isInE3DArea(block.getLocation())){
				event.setCancelled(true);
				player.sendMessage("�4[Esquiss� 3D] �cImpossible de construire pendant cette �tape");
			}

			
		}
			
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		InventoryView invv = event.getView();
		
		Player player =(Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		 Fonctions f= new Fonctions(main);
		 Builders b= new Builders(main);
		 AdminMenu adm=new AdminMenu(main);
		 Players p=new Players(main);
		 MainEtapes me=new MainEtapes(main);
		 
		 String invt=invv.getTitle();
		
		
		if (player.getWorld()!=Bukkit.getWorld(main.world)) return;
		
		
		if(current==null) return;
		
		ItemMeta currentM=current.getItemMeta();
		
		if(invt.equalsIgnoreCase("�cMenu administrateur E3D")||invt.equalsIgnoreCase("�cMenu t�l�port E3D")) {
			
			player.closeInventory();
			event.setCancelled(true);
						
			switch(current.getType()) {
			
				case REDSTONE_BLOCK:
					
					if(main.etape==ListeEtapes.LOBBY) {
						me.CheckDemarrage();
					}else {
						player.sendMessage("�4[Erreur] �c La partie est d�j� commenc�e");
					}
					break;
					
				case BARRIER:
				
					if(main.etape!=ListeEtapes.LOBBY) {
						me.ArretPartie();
					}else {
						player.sendMessage("�4[Erreur] �c La partie n'est pas demarr�e");
					}
					break;
				
				case ENCHANTED_GOLDEN_APPLE:
					
					for(Player pl:Bukkit.getOnlinePlayers())f.TP2Lobby(pl);
					break;
					
				case GOLDEN_APPLE:
					
						f.TP2Lobby(player);
						
					break;
					
					
				case APPLE:
					
					List<UUID> OnlinePlayers = new ArrayList<>();
					
					for(Player pl:Bukkit.getOnlinePlayers())OnlinePlayers.add(pl.getUniqueId());
					
					inv=adm.showListofPlayers(OnlinePlayers,"�1S�lectionner un joueur � TP");
										
					if(inv!=null) {
						player.openInventory(inv);
					}else {
						player.sendMessage("�4[Erreur] �cAucun joueur � t�l�porter");
					}
				
					break;
				
				case NAME_TAG:
					
					f.TP2FirstUrne(player);
					player.sendMessage(f.getSalleName(player.getLocation()));
					break;
					
				case ITEM_FRAME:
					
					if(invt.equalsIgnoreCase("�cMenu administrateur E3D")) {
						f.TP2FirstSalle(player);
						player.sendMessage(f.getSalleName(player.getLocation()));
					}else {
						inv=adm.SallesDestructions(0);
						player.openInventory(inv);
					}
					break;
					
				case NETHER_STAR:
					
					switch(currentM.getDisplayName()) {
					
					case "Urne suivante":
						if(f.TP2NextUrne(player, 1))player.sendMessage(f.getSalleName(player.getLocation()));
						break;
					case "Urne pr�c�dente":
						if(f.TP2NextUrne(player, -1))player.sendMessage(f.getSalleName(player.getLocation()));
						break;						
					case "Salle pr�c�dente X":
						if(f.TP2NextSalle(player, -1, 0))player.sendMessage(f.getSalleName(player.getLocation()));
						break;		
					case "Salle pr�c�dente Z":
						if(f.TP2NextSalle(player, 0,-1))player.sendMessage(f.getSalleName(player.getLocation()));
						break;		
					case "Salle suivante X":
						if(f.TP2NextSalle(player, 1, 0))player.sendMessage(f.getSalleName(player.getLocation()));
						break;		
					case "Salle suivante Z":
						if(f.TP2NextSalle(player, 0, 1))player.sendMessage(f.getSalleName(player.getLocation()));
						break;		
					default:break;
					}
					
					break;
					
				case FLINT_AND_STEEL:
					
					//Bukkit.broadcastMessage("�2[Info] �aR�initialisation de l'Esquiss� 3D, des lags peuvent appara�tre");
					
					b.ConstructionSalles(true,main.carapaceSalles);
					b.ConstructionUrnes(true);
					b.ConstructionPanneauxSalles(main.TextePanneauDraw);
					
					if(main.etape==ListeEtapes.DRAWING) {
						b.ConstructionPanneauxSalles(main.TextePanneauDraw);
					}else {
						b.ConstructionPanneauxSalles(main.TextePanneauGuess);
					}
					
					
					break;
					
				case GLOWSTONE:
					
					b.ConstruireDessin();
					
					break;
					
				case BRICK:
					
					main.destroy=false;
					b.ConstructionUrnes(false);
					b.ConstructionSalles(false,main.carapaceSalles);
					main.destroy=true;
					
					if(main.etape==ListeEtapes.DRAWING) {
						b.ConstructionPanneauxSalles(main.TextePanneauDraw);
					}else {
						b.ConstructionPanneauxSalles(main.TextePanneauGuess);
					}
					
					
					break;
				
				case GUNPOWDER:
					
					p.RemoveAllPlayer();
					
					break;
					
					
				case EGG:
					
					inv=adm.showListofPlayers(main.getNonPlayers(),"�1S�lectionner un joueur");
					
					AddOrDelete="ADD";
					
					if(inv!=null) {
						player.openInventory(inv);
					}else {
						player.sendMessage("�4[Erreur] �cAucun joueur � ajouter");
					}
				
					break;
					
				case COOKED_CHICKEN:
					
					for(Player pl:Bukkit.getOnlinePlayers())if(!main.getPlayers().contains(pl.getUniqueId()))p.setNewPlayer(pl.getUniqueId(), player);
					
					break;
				
				case TNT:
					
					Bukkit.broadcastMessage("�2[Esquiss� 3D] �aEffacement : �4/!\\ �ades lags peuvent appara�tre");
					b.EffacerTout();
					
					break;
				
				case BONE:
					
					inv=adm.showListofPlayers(main.getPlayers(),"�1S�lectionner un joueur");
					
					AddOrDelete="DELETE";
					
					if(inv!=null) {
						player.openInventory(inv);
					}else {
						player.sendMessage("�4[Erreur] �cAucun joueur � supprimer");
					}
				
					break;

					
				case RED_MUSHROOM:
					
					inv=adm.showListofEtapes("�1S�lectionner une �tape");
					
					player.openInventory(inv);
								
					break;
					
				case CLOCK:
					
									
					switch(currentM.getDisplayName()) {
					
					case "Finir le timer":
						main.timer=3.0;
						break;
					case "Enlever 1 minute au timer":
						if(main.timer>=60)main.timer-=60;
						else main.timer=0.0;
						break;
					case "Ajouter 1 minute au timer":
						main.timer+=60;
						break;
					default:break;
					
					}
					
					if (currentM.getDisplayName().equalsIgnoreCase("Revenir au timer initial (= "+main.timermanches+" secondes)")) {
						main.timer=main.timermanches;
					}
								
					
					
					break;
					
				default: break;
			
			
					
			
			}
			
			
			
		}else if(invt.equalsIgnoreCase("�1S�lectionner un joueur") && current.getType()==Material.PLAYER_HEAD) {
			
			player.closeInventory();
			
			if(AddOrDelete=="DELETE" ) {
				
				p.RemovePlayer(Bukkit.getPlayer(currentM.getDisplayName()).getUniqueId(), (CommandSender)player);
				
			}else if(AddOrDelete=="ADD") {
				p.setNewPlayer(Bukkit.getPlayer(currentM.getDisplayName()).getUniqueId(),(CommandSender)player);
			}
			
			
			
		}else if(invt.equalsIgnoreCase("�1S�lectionner un joueur � TP")) {
			player.closeInventory();			
			f.TP2Lobby((Player)Bukkit.getPlayer(currentM.getDisplayName()));
			
		}else if(invt.equalsIgnoreCase("�1S�lectionner une �tape")) {
			player.closeInventory();
			main.setState(ListeEtapes.valueOf(currentM.getDisplayName()));
			player.sendMessage("�2[Esquiss� 3D] �aEtape fix�e � : "+ListeEtapes.valueOf(currentM.getDisplayName()).toString());
			
		}else if(invt.equalsIgnoreCase("�c[E3D] Se t�l�porter page1")||invt.equalsIgnoreCase("�c[E3D] Se t�l�porter page2")) {
			player.closeInventory();
			
			if(current.getType()==Material.ITEM_FRAME) {
				Integer i;
				Integer j;
				
				for(i=0;i<=main.Jtot-1;i++) {
					
					for(j=1;j<=(main.Jtot-main.Jtot%2)/2;j++) {
						
						if(currentM.getDisplayName().equalsIgnoreCase("Mot n�"+(i+1)+" / Dessin n�"+j)) {
							f.TP2Salle(i, j-1, player);
						}
						
						
					}
				}
				
			}else if(current.getType()==Material.NETHER_STAR) {
				
				if(invt.equalsIgnoreCase("�c[E3D] Se t�l�porter page1"))inv=adm.SallesDestructions(1);
				else inv=adm.SallesDestructions(0);
				player.openInventory(inv);
				
				
			
			}
			
			
		}
		
		
		
		
	}
	
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		
		if(main.isStarted==false)return;
		
		Entity entity=event.getEntity();
		Fonctions f=new Fonctions(main);
		SpawnReason reason=event.getSpawnReason();

		Location entityL=entity.getLocation();
		
		
		
		//System.out.println("Apparition de " +entity.getType()+" par "+reason);

		if(f.isInE3DArea(entityL)) {
			
			
			if(reason==SpawnReason.NATURAL) {
				entity.remove();
			}else {
				entity.setGravity(false);
				
				if(entity instanceof LivingEntity) {
					
					LivingEntity le =(LivingEntity)entity;
					le.setAI(false);
					
				}
				//f.noAI(entity);
			}
			
			
			
			
		}

			
		
	}
	
	
	@EventHandler
	public void onExplosion(ExplosionPrimeEvent event) {
		
		if(main.isStarted==false)return;
		
		Entity entity=event.getEntity();
		Fonctions f=new Fonctions(main);

		Location entityL=entity.getLocation();
		
	
					
		
		if((f.isInE3DArea(entityL)&&main.etape!=ListeEtapes.DESTRUCTION)||main.autoriserTNT) {
			
			for(Player pl:Bukkit.getOnlinePlayers()) {
				if(pl!=null && entityL.distance(pl.getLocation())<=55) {
					pl.sendMessage("�4[Esquiss� 3D] �cD�sol�, pas d'explosion ici !");
				}
			}
			
			event.setCancelled(true);
			
			
		}
		
		
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		
		if(main.isStarted==false)return;	
		
		Entity entity=event.getRightClicked();
		Player player =(Player) event.getPlayer();
		ItemStack item = player.getItemInHand();
		ItemMeta itemM=item.getItemMeta();
//		MainEtapes me=new MainEtapes(main);
		

		
		Fonctions f=new Fonctions(main);
		
		if(entity.getType()==EntityType.PIG && item.getType()==Material.NAME_TAG && f.isInE3DArea(player.getLocation())) {
			
			if(entity.getCustomName()==null) {
				event.setCancelled(true);
				player.sendMessage("�4[Esquiss� 3D] �cImpossible de renommer les cochons de base");
			}else if(itemM.getDisplayName() == null){
				event.setCancelled(true);
				player.sendMessage("�4[Esquiss� 3D] �cRenommez le nametag dans l'enclume avant !");
			}else {
				
				switch(main.etape) {
				
				case CHOSING:
				case INITIALIZING:
					
					if(!main.getPlayersReady().contains(player.getUniqueId())) {
						main.getPlayersReady().add(player.getUniqueId());
						for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D]�3 "+player.getName()+"�a est pr�t �d("+main.getPlayersReady().toArray().length+"/"+main.Jtot+")");
					}
					
					if(main.getPlayersReady().toArray().length==main.Jtot) {
						for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D]�a Tout le monde est pr�t, passage � l'�tape suivante...");
						main.Round=1;
						
						entity.setCustomName(itemM.getDisplayName());
						
						Timer task = new Timer(main);
						main.timer=5.0;
						task.runTaskTimer(this.main, 0, 20);

					}
					
					break;
					
				case GUESSING:
					
					
					if(!main.getPlayersReady().contains(player.getUniqueId())) {
						main.getPlayersReady().add(player.getUniqueId());
						for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D]�3 "+player.getName()+"�a est pr�t �d("+main.getPlayersReady().toArray().length+"/"+main.Jtot+")");
						entity.setCustomName(itemM.getDisplayName());
					}
					
					if(main.getPlayersReady().toArray().length==main.Jtot) {
						for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D]�a Tout le monde est pr�t, passage � l'�tape suivante...");
						main.resetJoueursPrets();
						entity.setCustomName(itemM.getDisplayName());
						Timer task = new Timer(main);
						main.timer=5.0;
						task.runTaskTimer(this.main, 0, 20);
						
						
						
					}
					

					break;
				
					
				case DRAWING:
					event.setCancelled(true);
					player.sendMessage("�4[Esquiss� 3D] �cVous ne pouvez pas renommer votre cochon maintenant !");
					break;
					
				default:

				break;
				}
				
			}

		}if(entity.getType()==EntityType.PIG && item.getType()==Material.CARROT && f.isInE3DArea(player.getLocation())) {
			if(main.etape==ListeEtapes.DRAWING) {
				
				if(!main.getPlayersReady().contains(player.getUniqueId())) {
					main.getPlayersReady().add(player.getUniqueId());
					for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D] �3 "+player.getName()+" �aest pr�t �d("+main.getPlayersReady().toArray().length+"/"+main.Jtot+")");
				}
				
				if(main.getPlayersReady().toArray().length==main.Jtot) {
					for(UUID pl:main.getPlayers())if(Bukkit.getPlayer(pl)!=null)Bukkit.getPlayer(pl).sendMessage("�2[Esquiss� 3D] �a Tout le monde est pr�t, passage � l'�tape suivante...");
						main.timer=3.0;
//					me.Guessing();
				}
								
			}
		}
		
		
	}
	

	

	@EventHandler
	public void onMovement(PlayerMoveEvent event) {
		
		
		Player player =(Player) event.getPlayer();
		if(Bukkit.getWorld(main.world)!=player.getWorld())return;
		
		
		Players p=new Players(main);
		Fonctions f=new Fonctions(main);
		
		
		if(!main.getPlayers().contains(player.getUniqueId())&&f.isBetweenLocations(player.getLocation(), main.JoinMin, main.JoinMax)) {
			if(main.etape==ListeEtapes.LOBBY)p.setNewPlayer(player.getUniqueId(), null);
			else player.sendMessage("�4[Esquiss� 3D] �c Impossible de rejoindre, une partie est d�j� en cours");
		}else if(main.getPlayers().contains(player.getUniqueId())&&f.isBetweenLocations(player.getLocation(), main.QuitMin, main.QuitMax)) {
			p.RemovePlayer(player.getUniqueId(), null);
		}else if(!main.getSpecs().contains(player.getUniqueId())&&f.isBetweenLocations(player.getLocation(), main.SpecMin, main.SpecMax)) {
			player.sendMessage("�d[Esquiss� 3D] Vous avez rejoint les spectateurs");
			main.getSpecs().add(player.getUniqueId());
			if(main.getPlayers().contains(player.getUniqueId()))p.RemovePlayer(player.getUniqueId(), null);
			if(main.getNonPlayers().contains(player.getUniqueId()))main.getNonPlayers().remove(player.getUniqueId());
			if(main.etape!=ListeEtapes.LOBBY) {
				player.sendMessage("�2[Esquiss� 3D] �aUne partie est d�j� en cours, t�l�portation dans la zone de jeu...");
				player.sendMessage(" �aPour quitter le mode spectateur en cours de partie, d�connectez et reconnectez-vous au serveur");

				player.setGameMode(GameMode.SPECTATOR);
				if(main.etape!=ListeEtapes.CHOSING)f.TP2FirstSalle(player);
				else f.TP2FirstUrne(player);
			}
		}else if(main.getSpecs().contains(player.getUniqueId())&&f.isBetweenLocations(player.getLocation(), main.QuitSpecMin, main.QuitSpecMax)) {
			player.sendMessage("�c[Esquiss� 3D] Vous avez quitt� les spectateurs");
			if(player.getGameMode()==GameMode.SPECTATOR)player.setGameMode(Bukkit.getDefaultGameMode());
			main.getSpecs().remove(player.getUniqueId());
			main.getNonPlayers().add(player.getUniqueId());
		}
		
		
		
	}
		

	
		
}
