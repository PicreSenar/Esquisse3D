package fr.picresenar.esquisse3D.etapes;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import fr.picresenar.esquisse3D.MainEsquisse;
import fr.picresenar.esquisse3D.commands.Fonctions;
import fr.picresenar.esquisse3D.commands.parameter.Dessins;

public class Builders {
	
	
	public MainEsquisse main;

	public Builders(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	public void ConstructionSalles(boolean destroy,boolean protection){
		
		Double Xmin=main.SallesXmin;
		Double Ymin=main.SallesYmin;
		Double Zmin=main.SallesZmin;
		Double Xmax=main.SallesXmax;
		Double Ymax=main.SallesYmax;
		Double Zmax=main.SallesZmax;
		
		Integer Jmax=main.getConfig().getInt("Joueurs.max");
		
		Material material=main.MurMaterial;
		
		Integer JmaxX;
		Integer JmaxZ;
		String AxePrincipal;
		
		//Dessins d= new Dessins(main);
		Fonctions f= new Fonctions(main);
			
		if((Xmax-Xmin)>(Zmax-Zmin)) {
			AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			
		}else {
			AxePrincipal="Z";
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
		}
		
		Double X;
		Double Z;
		
		Integer PasX= (int)(Xmax-Xmin)/JmaxX;
		Integer PasZ= (int)(Zmax-Zmin)/JmaxZ;				
		
		//Effacer tout
		if(destroy) {
			f.FillBlocks(Xmin,Ymin,Zmin,Xmax,Ymax,Zmax,Material.AIR);
			f.KillEntityInArea(Xmin,Ymin,Zmin,Xmax,Ymax,Zmax);
		}
		
		//Protéger les bords avec de la bedrock et le toit avec une barrière
		if(protection) {
			f.FillBlocks(Xmin-1,Ymin,Zmin,Xmin-1,Ymax,Zmax,Material.BEDROCK);
			f.FillBlocks(Xmin,Ymin,Zmin-1,Xmax,Ymax,Zmin-1,Material.BEDROCK);
			f.FillBlocks(Xmin,Ymin-1,Zmin,Xmax,Ymin-1,Zmax,Material.BEDROCK);
			f.FillBlocks(Xmax+1,Ymin,Zmin,Xmax+1,Ymax,Zmax,Material.BEDROCK);
			f.FillBlocks(Xmin,Ymin,Zmax+1,Xmax,Ymax,Zmax+1,Material.BEDROCK);
			f.FillBlocks(Xmin,Ymax+1,Zmin,Xmax,Ymax+1,Zmax,Material.BARRIER);		
		}else {
			f.FillBlocks(Xmin-1,Ymin,Zmin,Xmin-1,Ymax,Zmax,Material.AIR);
			f.FillBlocks(Xmin,Ymin,Zmin-1,Xmax,Ymax,Zmin-1,Material.AIR);
			f.FillBlocks(Xmin,Ymin-1,Zmin,Xmax,Ymin-1,Zmax,Material.AIR);
			f.FillBlocks(Xmax+1,Ymin,Zmin,Xmax+1,Ymax,Zmax,Material.AIR);
			f.FillBlocks(Xmin,Ymin,Zmax+1,Xmax,Ymax,Zmax+1,Material.AIR);
			f.FillBlocks(Xmin,Ymax+1,Zmin,Xmax,Ymax+1,Zmax,Material.AIR);		
		}
		
		
		//Sol Salle
		f.FillBlocks(Xmin,Ymin,Zmin,Xmax,Ymin,Zmax,material);
		
		//Plafond Urnes
		f.FillBlocks(Xmin,Ymax,Zmin,Xmax,Ymax,Zmax,Material.BARRIER);
			
		//Murs X Salle
		for(X=Xmin;X<=Xmax;X=X+PasX) {
			f.FillBlocks(X,Ymin,Zmin,X,Ymax,Zmax,material);
		}
		
		//Murs Z Salle
		for(Z=Zmin;Z<=Zmax;Z=Z+PasZ) {
			f.FillBlocks(Xmin,Ymin,Z,Xmax,Ymax,Z,material);
		}
		
		
		//Enclumes Salles
		if(AxePrincipal=="Z") {
			for(X=Xmin+1;X<=Xmax;X=X+PasX) {
				for(Z=Zmin+PasZ/2;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(X,Ymin+1,Z,X,Ymin+1,Z,Material.ANVIL);
					Location anvilloc=new Location(Bukkit.getWorld(main.world),X,Ymin+1,Z);
					main.AnvilList.add(anvilloc);
				}
			}
		}else {
			for(X=Xmin+PasX/2;X<=Xmax;X=X+PasX) {
				for(Z=Zmin+1;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(X,Ymin+1,Z,X,Ymin+1,Z,Material.ANVIL);
					Location anvilloc=new Location(Bukkit.getWorld(main.world),X,Ymin+1,Z);
					main.AnvilList.add(anvilloc);
				}
			}
		}	
	}
	
	
	public void ConstructionUrnes(boolean destroy){
		
		Double Xmin=main.UrnesXmin;
		Double Ymin=main.UrnesYmin;
		Double Zmin=main.UrnesZmin;
		Double Xmax=main.UrnesXmax;
		Double Ymax=main.UrnesYmax;
		Double Zmax=main.UrnesZmax;
		
		Integer Jmax=main.Jmax;
		
		Material material=main.MurMaterial;
		
		Integer JmaxX;
		Integer JmaxZ;
		String AxePrincipal;
		
		Fonctions f= new Fonctions(main);
		
		if((Xmax-Xmin)>(Zmax-Zmin)) {
			AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			
		}else {
			AxePrincipal="Z";
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
		}
		
		Double X;
		Double Z;
		
		Integer PasX= (int)(Xmax-Xmin)/JmaxX;
		Integer PasZ= (int)(Zmax-Zmin)/JmaxZ;				
		
		
		
		//Effacer tout
		if(destroy) {
			//System.out.println("Pendant construction : " + main.destroy);
			f.FillBlocks(Xmin,Ymin,Zmin,Xmax,Ymax,Zmax,Material.AIR);
			f.KillEntityInArea(Xmin,Ymin,Zmin,Xmax,Ymax,Zmax);
		}
		
		
		//Sol Urnes
		f.FillBlocks(Xmin,Ymin,Zmin,Xmax,Ymin,Zmax,material);
		
		
		if(AxePrincipal=="X") {
			//Murs X Urnes
			for(X=Xmin;X<=Xmax;X=X+PasX) {
				f.FillBlocks(X,Ymin,Zmin,X,Ymax,Zmax,material);
			}
			//Murs Z Urnes
			f.FillBlocks(Xmin,Ymin,Zmin,Xmax,Ymax,Zmin,material);
			f.FillBlocks(Xmin,Ymin,Zmax,Xmax,Ymax,Zmax,material);
		}else {
			//Murs X Urnes
			f.FillBlocks(Xmin,Ymin,Zmin,Xmin,Ymax,Zmax,material);
			f.FillBlocks(Xmax,Ymin,Zmin,Xmax,Ymax,Zmax,material);
			//Murs Z Urnes
			for(Z=Zmin;Z<=Zmax;Z=Z+PasZ) {
			f.FillBlocks(Xmin,Ymin,Z,Xmax,Ymax,Z,material);
			}
		}
		
		//Plafond Urnes
		f.FillBlocks(Xmin,Ymax,Zmin,Xmax,Ymax,Zmax,Material.BARRIER);
		
		
		//Enclumes Urnes
		if(AxePrincipal=="X") {
			for(X=Xmin+PasX/2;X<=Xmax;X=X+PasX) {
					f.FillBlocks(X,Ymin+1,Zmin+1,X,Ymin+1,Zmin+1,Material.ANVIL);
			}
		}else {
				for(Z=Zmin+PasZ/2;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(Xmin+1,Ymin+1,Z,Xmin+1,Ymin+1,Z,Material.ANVIL);
				}
		}
		
		//Panneaux Urnes
		String TextePanneau[] = {"","","",""};
		Integer i=0;
		for(String string: main.getConfig().getStringList("Panneaux.EnclumeGuess")) {
			TextePanneau[i]=string;
			i++;
		}
		if(AxePrincipal=="X") {
			for(X=Xmin+PasX/2;X<=Xmax;X=X+PasX) {
					f.FillBlocks(X,Ymin+2,Zmin+1,X,Ymin+2,Zmin+1,Material.AIR);
					f.PutSign(X,Ymin+2,Zmin+1, BlockFace.SOUTH, TextePanneau);
				}
		}else {
				for(Z=Zmin+PasZ/2;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(Xmin+1,Ymin+2,Z,Xmin+1,Ymin+2,Z,Material.AIR);
					f.PutSign(Xmin+1,Ymin+2,Z, BlockFace.EAST, TextePanneau);
				}
		}
		

	}

	public void ConstructionPanneauxSalles(String TextePanneau[]){
	
		Double Xmin=main.SallesXmin;
		Double Ymin=main.SallesYmin;
		Double Zmin=main.SallesZmin;
		Double Xmax=main.SallesXmax;
		//Double Ymax=main.SallesYmax;
		Double Zmax=main.SallesZmax;
		
		Integer Jmax=main.getConfig().getInt("Joueurs.max");
			
		Integer JmaxX;
		Integer JmaxZ;
		String AxePrincipal;
		
		//Dessins d= new Dessins(main);
		Fonctions f= new Fonctions(main);
		
		if((Xmax-Xmin)>(Zmax-Zmin)) {
			AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			
		}else {
			AxePrincipal="Z";
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
		}
		
		Double X;
		Double Z;
		
		Integer PasX= (int)(Xmax-Xmin)/JmaxX;
		Integer PasZ= (int)(Zmax-Zmin)/JmaxZ;				
		
		//Panneaux Salles
	
		if(AxePrincipal=="Z") {
			for(X=Xmin+1;X<=Xmax;X=X+PasX) {
				for(Z=Zmin+PasZ/2;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(X,Ymin+2,Z,X,Ymin+2,Z,Material.AIR);
					f.PutSign(X,Ymin+2,Z, BlockFace.EAST, TextePanneau);
				}
			}
		}else {
			for(X=Xmin+PasX/2;X<=Xmax;X=X+PasX) {
				for(Z=Zmin+1;Z<=Zmax;Z=Z+PasZ) {
					f.FillBlocks(X,Ymin+2,Z,X,Ymin+1,Z,Material.AIR);
					f.PutSign(X,Ymin+2,Z, BlockFace.SOUTH, TextePanneau);
				}
			}
		}
	

	}

	public void GetRefSalles() {
		Integer JmaxX=1;
		Integer JmaxZ=1;
		
		Integer UrneX;
		
		Integer MinXSalle;
		Integer MinZSalle;		

		
		Integer Jmax=main.getConfig().getInt("Joueurs.max");
		
		 Double XminU=main.UrnesXmin;
//		 Double YminU=main.UrnesYmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
//		 Double YmaxU=main.UrnesYmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		 Double XminS=main.SallesXmin;
	//	 Double YminS=main.SallesYmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
	//	 Double YmaxS=main.SallesYmax;
		 Double ZmaxS=main.SallesZmax;
	
		
		 Boolean AxeXPrincipal=isAxePrincipalX(false);
		
		
			if(AxeXPrincipal) {
				JmaxX =Jmax;
				JmaxZ=Jmax/2;
				
			}else {
				JmaxX=Jmax/2;
				JmaxZ=Jmax;
			}
			
			
			Integer PasX= (int)(XmaxS-XminS)/JmaxX;
			Integer PasZ= (int)(ZmaxS-ZminS)/JmaxZ;	
			
			
			for(int i=0;i<=JmaxX;i++) {
				for(int j=0;j<=JmaxZ;j++) {
					
					MinXSalle=(int) (XminS	+(i)*PasX);
					MinZSalle=(int) (ZminS	+(j)*PasZ);

				
					
						if(!AxeXPrincipal) {
							main.RefSallesX[j][i]= MinXSalle;
							main.RefSallesZ[j][i]= MinZSalle;
						}else{
							main.RefSallesX[i][j]= MinXSalle;
							main.RefSallesZ[i][j]= MinZSalle;
						}
					
				}
			}
			
			AxeXPrincipal=isAxePrincipalX(true);

			//Ref Urnes
			
			if(AxeXPrincipal) {
				JmaxX =Jmax;
				JmaxZ=1;
				UrneX=1;
				
			}else {
				JmaxX=1;
				JmaxZ=Jmax;
				UrneX=0;
			}
			
			PasX= (int)(XmaxU-XminU)/JmaxX;
			PasZ= (int)(ZmaxU-ZminU)/JmaxZ;	
			
			for(int i=0;i<=(Jmax-1);i++) {
				MinXSalle=(int) (XminU+(UrneX*((i)*PasX)));
				MinZSalle=(int) (ZminU+((1-UrneX)*((i)*PasZ)));

				
	//			Bukkit.broadcastMessage(MinXSalle+" "+MinZSalle+" "+MaxXSalle+" "+MaxZSalle);
	//			Bukkit.broadcastMessage(location.getBlockX()+" "+location.getBlockY()+" "+location.getBlockZ());
				
				
				main.RefUrnesX[i]=MinXSalle;
				main.RefUrnesZ[i]=MinZSalle;

			}
			
			main.RefUrnesX[Jmax]=(int)(Math.round(XmaxU));
			main.RefUrnesZ[Jmax]=(int)(Math.round(ZmaxU));

	}
	
	public void ConstruireDessin() {
		Double Xmin=main.SallesXmin;
		Double Ymin=main.SallesYmin;
		Double Zmin=main.SallesZmin;
		Double Xmax=main.SallesXmax;
		//Double Ymax=main.SallesYmax;
		Double Zmax=main.SallesZmax;
		
		Integer Jmax=main.Jmax;
		
		Integer JmaxX;
		Integer JmaxZ;
		
		
		//Dessins d= new Dessins(main);
		Dessins d=new Dessins(main);
		
		if((Xmax-Xmin)>(Zmax-Zmin)) {
			
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			
		}else {
			
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
		}
		
		Double X;
		Double Z;
		
		Integer PasX= (int)(Xmax-Xmin)/JmaxX;
		Integer PasZ= (int)(Zmax-Zmin)/JmaxZ;				
		
		
		//Murs Dessinés
		for(X=Xmin;X<=Xmax-PasX;X=X+PasX) {
			for(Z=Zmin;Z<=Zmax-PasZ;Z=Z+PasZ) {
				Integer Tableau[][]=d.TitreEsquisse();
				for(Integer i=1;i<=12;i++) {
					for(Integer j=1;j<28;j++) {
						if(Tableau[i][j]==0) Bukkit.getWorld(main.world).getBlockAt((int)Math.round(X),(int)Math.round(Ymin+20-i),(int)Math.round(Z+PasZ-j-1)).setType(Material.GLOWSTONE);;
					}
				}
						
					
			}
		}

		
	}
	
	public void EffacerTout() {

				
		Fonctions f= new Fonctions(main);

		
		//Effacer (absolument) tout
			main.destroyall=true;
			f.FillBlocks(main.UrnesXmin-1,main.UrnesYmin-1,main.UrnesZmin-1,main.UrnesXmax+1,main.UrnesYmax+1,main.UrnesZmax+1,Material.AIR);
			f.FillBlocks(main.SallesXmin-1,main.SallesYmin-1,main.SallesZmin-1,main.SallesXmax+1,main.SallesYmax+1,main.SallesZmax+1,Material.AIR);	
			main.destroyall=false;
	
	}
	
	
	public Entity SpawnCochons(Boolean UrnesouSalles,Integer Joueur,Integer Dessin){

		
		 Integer Xmin;
		 Integer Ymin;
		 Integer Zmin;

		 
		 Double X;
		 Double Y;
		 Double Z;
		 
		 Entity entity;
		 Location destination;
//		 Float orientation;
		
		
		
		if(UrnesouSalles) {
			 Xmin=main.RefUrnesX[Joueur];
			 Ymin=(int) Math.round(main.UrnesYmin);
			 Zmin=main.RefUrnesZ[Joueur];
		}else {
			 Xmin=main.RefSallesX[Joueur][Dessin];
			 Ymin=(int) Math.round(main.SallesYmin);
			 Zmin=main.RefSallesZ[Joueur][Dessin];
		}
		
		//System.out.println("Coordonnées salle : "+Xmin+"/"+Ymin+"/"+Zmin);
		//System.out.println("Pas déplacement : "+PasX(UrnesouSalles)+"/"+PasZ(UrnesouSalles));
		//
		
		if(isAxePrincipalX(UrnesouSalles)) {
			X=Xmin+PasX(UrnesouSalles)*(0.5)-1;
			Y=Ymin+(0.5)*2;
			Z=Zmin+(1.5);
//			orientation=0f;
		}else {
			X=Xmin+(1.5);
			Y=Ymin+(0.5)*2;
			Z=Zmin+PasZ(UrnesouSalles)*(0.5)-1;
//			orientation=90f;
		}
		
		//System.out.println("Cochon apparu sur "+ main.world +" en :"+X+"/"+Y+"/"+Z);
		
		
	
		
		destination = new Location(Bukkit.getWorld(main.world),X,Y,Z);
		
		
		Chunk chunk=destination.getChunk();
		
		if(chunk.isLoaded()) {
			entity=Bukkit.getWorld(main.world).spawnEntity(destination, EntityType.PIG);
			return entity;
		}
		
		System.out.println("Erreur cochon non apparu : Chunk non chargé");
		return null;
		
	}	 
		

	
	public boolean isAxePrincipalX(Boolean UrnesouSalles) {
		
		 Double Xmin;
		 Double Zmin;
		 Double Xmax;
		 Double Zmax;
		 	
		if(UrnesouSalles) {
			 Xmin=main.UrnesXmin;
			 Zmin=main.UrnesZmin;
			 Xmax=main.UrnesXmax;
			 Zmax=main.UrnesZmax;
		}else {
			 Xmin=main.SallesXmin;
			 Zmin=main.SallesZmin;
			 Xmax=main.SallesXmax;
			 Zmax=main.SallesZmax;
		}
		
		if((Xmax-Xmin)>(Zmax-Zmin)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public Integer PasX(Boolean UrnesouSalles) {
		
		 Double Xmin;
		 Double Xmax;
	
		Integer JmaxX=1;
		
		if(UrnesouSalles) {
			 Xmin=main.UrnesXmin;
			 Xmax=main.UrnesXmax;
		}else {
			 Xmin=main.SallesXmin;
			 Xmax=main.SallesXmax;
		}
		
		
		if(isAxePrincipalX(UrnesouSalles)) {
			JmaxX =main.Jmax;	
		}else {
			JmaxX=main.Jmax/2;
		}
		
		Integer PasX= (int)(Xmax-Xmin)/JmaxX;
		return PasX;
		
	}

	public Integer PasZ(Boolean UrnesouSalles) {
		
		 Double Zmin;
		 Double Zmax;
	
		Integer JmaxZ=1;
		
		if(UrnesouSalles) {
			 Zmin=main.UrnesZmin;
			 Zmax=main.UrnesZmax;
		}else {
			 Zmin=main.SallesZmin;
			 Zmax=main.SallesZmax;
		}
		
		
		if(!isAxePrincipalX(UrnesouSalles)) {
			JmaxZ =main.Jmax;	
		}else {
			JmaxZ=main.Jmax/2 ;
		}
		
		Integer PasZ= (int)(Zmax-Zmin)/JmaxZ;
		return PasZ;
		
	}

}
