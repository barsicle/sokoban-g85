package domein;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Spelbord {

	private String spelbordNaam;
	private int volgorde;
	private Veld[][] velden;
	private Moveable mannetje;
	private List<Moveable> kisten;
	private boolean voltooid;
	
	public Spelbord(String spelbordNaam, int volgorde, Moveable mannetje, List<Moveable> kisten, Veld[][] velden) {
		this.velden = velden;
		this.mannetje = mannetje;
		this.kisten = kisten;
		this.spelbordNaam = spelbordNaam;
		this.volgorde = volgorde;
	}
	
	
	public Spelbord(String spelbordNaam, int volgorde) {
		super();
		this.spelbordNaam = spelbordNaam;
		this.volgorde = volgorde;
	}


	//TEMP DUMMY INIT
	/*
	public Spelbord() {
		voltooid = false;
		// i = kolom, j = rij!
		kisten = new ArrayList<>();
		Veld[][] velden = new Veld[10][10];

		//Basis muren
		for (int i = 2; i < 9; i++) {
			for (int j = 2; j < 9; j++) {
				velden[i][j] = new Veld(VeldType.MUUR, false, i, j);
			}
		}
		for (int i = 3; i < 8; i++) {
			for (int j = 3; j < 8; j++) {
				velden[i][j] = new Veld(VeldType.VELD, false, i, j);
			}
		}
			// Paar changes
			Veld kist1Veld = velden[4][6];
			Veld kist2Veld = velden[5][7];
			Moveable kist1 = new Kist(kist1Veld);
			Moveable kist2 = new Kist(kist2Veld);
			kisten.add(kist1);
			kisten.add(kist2);
			Veld mannetjeVeld = velden[5][5];
			mannetje = new Mannetje(mannetjeVeld);
			velden[4][6].setMoveable(kist1);
			velden[5][7].setMoveable(kist2);
			velden[7][7] = new Veld(VeldType.VELD, true, 7, 7);
			velden[3][3] = new Veld(VeldType.VELD, true, 3,3);
			velden[5][5].setMoveable(mannetje);
		this.velden = velden;
		
		for (Veld[] row : this.velden) {
			for (Veld veld : row) {
				if(!Objects.equals(veld, null))
				veld.print();
			}
		}
		
	}
*/
	
	public String getSpelbordNaam() {
		return spelbordNaam;
	}
	public void setSpelbordNaam(String spelbordNaam) {
		this.spelbordNaam = spelbordNaam;
	}
	public Veld[][] getVelden() {
		return velden;
	}
	public void setVelden(Veld[][] velden) {
		this.velden = velden;
	}
	
	public int getVolgorde() {
		return volgorde;
	}
	public void setVolgorde(int volgorde) {
		this.volgorde = volgorde;
	}


	public boolean beweeg(BeweegRichting richting) {
		boolean succes = false;
		Veld huidigePositie = mannetje.getPositie();
		int xHuidig = huidigePositie.getX();
		int yHuidig = huidigePositie.getY();
		int xTarget = xHuidig;
		int yTarget = yHuidig;
		int xAchterTarget = xHuidig;
		int yAchterTarget = yHuidig;
		switch (richting) {
		case LINKS:
			xTarget--;
			xAchterTarget = xTarget-1;
			break;
		case ONDER:
			yTarget++;
			yAchterTarget=yTarget+1;
			break;
		case RECHTS:
			xTarget++;
			xAchterTarget = xTarget+1;
			break;
		case BOVEN:
			yTarget--;
			yAchterTarget=yTarget-1;
			break;
		}
		Veld target = velden[xTarget][yTarget];
		if (target.getVeldType().equals(VeldType.VELD)) {
			if (Objects.equals(target.getMoveable(), null)){
				huidigePositie.setMoveable(null);
				mannetje.setPositie(target);
				target.setMoveable(mannetje);
				succes = true;
				//Indien het een kist is
			} else {
				Veld achterTarget = velden[xAchterTarget][yAchterTarget];
				if (!(achterTarget.getVeldType().equals(VeldType.VELD) && Objects.equals(achterTarget.getMoveable(), null))) {
					succes = false;
				} else {
					Moveable kist = target.getMoveable();
					kisten.remove(kist);
					//Zet kist 1 vooruit
					achterTarget.setMoveable(kist);
					kist.setPositie(achterTarget);
					kisten.add(kist);

					//Zet mannetje 1 vooruit
					huidigePositie.setMoveable(null);
					mannetje.setPositie(target);
					target.setMoveable(mannetje);

					succes = true;
				}

				
			}

		}
		if (target.getVeldType().equals(VeldType.MUUR)) {
			succes =  false;
		}
		checkVoltooid();
		return succes;
	}

	public boolean isVoltooid() {
		return voltooid;
	}

	public Moveable getMannetje() {
		return mannetje;
	}

	public List<Moveable> getKisten() {
		return kisten;
	}

	private void checkVoltooid(){
		List<Moveable> kistenOpDoel = kisten.stream().filter(k -> k.getPositie().isDoel()).collect(Collectors.toList());
		if (kistenOpDoel.equals(kisten)){
			voltooid = true;
		}
	}
}
