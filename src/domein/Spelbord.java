package domein;

import java.util.*;
import java.util.stream.Collectors;

import vertalingen.Taal;

public class Spelbord {

	private String spelbordNaam;
	private int volgorde;
	private Veld[][] velden;
	private Moveable mannetje;
	private List<Moveable> kisten;
	private boolean voltooid;
	private int aantalBewegingen;
	private Spel spel;

	/**
	 * Creëert een spelbord met opgegeven naam, volgordenummer, mannetje, lijst van kisten en een array van velden.
	 * 
	 * @param spelbordNaam De naam van het spelbord.
	 * @param volgorde De volgorde waarin het spelbord in het spel verschijnt.
	 * @param mannetje Het mannetje dat in het spelbord wordt gebruikt om te spelen.
	 * @param kisten De kisten die op het spelbord staan en moeten worden verplaatst.
	 * @param velden De velden die het spelbord bezit.
	 */
	public Spelbord(String spelbordNaam, int volgorde, Moveable mannetje, List<Moveable> kisten, Veld[][] velden) {
			setVelden(velden);
			this.mannetje = mannetje;
			this.kisten = kisten;
			setSpelbordNaam(spelbordNaam);
			setVolgorde(volgorde);
	}
	
	public Spelbord(String spelbordNaam, int volgorde) {
		this.spelbordNaam = spelbordNaam;
		this.volgorde = volgorde;
	}

	public String getSpelbordNaam() {
		return spelbordNaam;
	}
	public void setSpelbordNaam(String spelbordNaam) throws IllegalArgumentException {
		if (Objects.equals(spelbordNaam, null) || spelbordNaam.isBlank())
			throw new IllegalArgumentException(Taal.vertaal("game_board_name") + Taal.vertaal("exception_not_null"));
		this.spelbordNaam = spelbordNaam;
	}
	public Veld[][] getVelden() {
		return velden;
	}
	public void setVelden(Veld[][] velden) throws IllegalArgumentException {
		if (Objects.equals(velden, null))
			throw new IllegalArgumentException(Taal.vertaal("field") + Taal.vertaal("exception_not_null"));
		this.velden = velden;
	}
	
	public int getVolgorde() {
		return volgorde;
	}
	public void setVolgorde(int volgorde) throws IllegalArgumentException {
		if (Objects.equals(volgorde, null))
			throw new IllegalArgumentException(Taal.vertaal("order") + Taal.vertaal("exception_not_null"));
		this.volgorde = volgorde;
	}


	public void beweeg(BeweegRichting richting) throws RuntimeException {
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
				//Indien het een kist is
			} else {
				Veld achterTarget = velden[xAchterTarget][yAchterTarget];
				if (!(achterTarget.getVeldType().equals(VeldType.VELD) && Objects.equals(achterTarget.getMoveable(), null))) {
					throw new RuntimeException(Taal.vertaal("illegal_movement"));
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
				}
			}

		}
		if (target.getVeldType().equals(VeldType.MUUR)) {
			throw new IllegalArgumentException(Taal.vertaal("illegal_movement"));
		}
		
		checkVoltooid();
		aantalBewegingen++;
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

	public int getAantalBewegingen() {
		return aantalBewegingen;
	}

	public Spel getSpel() {
		return spel;
	}

	public void setSpel(Spel spel) {
		this.spel = spel;
	}
	
	
	
	
}
