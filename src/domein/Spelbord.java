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
	private boolean hasMannetje;

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
		this.velden = new Veld[10][10];
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
		updateBord(huidigePositie, xHuidig, yHuidig, xTarget, yTarget, xAchterTarget, yAchterTarget);
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
	
	private void updateBord(Veld huidigePositie, int xHuidig, int yHuidig, int xTarget, int yTarget, int xAchterTarget, int yAchterTarget) {
		Veld target = velden[xTarget][yTarget];
		if (target.getVeldType().equals(VeldType.VELD)) {
			if (hasNoMoveable(target)){
				verplaatsMannetje(huidigePositie, target);
				//Indien het een kist is
			} else {
				Veld achterTarget = velden[xAchterTarget][yAchterTarget];
				if (!(achterTarget.getVeldType().equals(VeldType.VELD) && hasNoMoveable(achterTarget))) {
					throw new RuntimeException(Taal.vertaal("illegal_movement"));
				} else {
					Moveable kist = target.getMoveable();
					kisten.remove(kist);
					//Zet kist 1 vooruit
					verplaatsKist(kist, achterTarget);

					//Zet mannetje 1 vooruit
					verplaatsMannetje(huidigePositie, target);
				}
			}

		}
		if (target.getVeldType().equals(VeldType.MUUR)) {
			throw new IllegalArgumentException(Taal.vertaal("illegal_movement"));
		}
	}
	
	private boolean hasNoMoveable(Veld target) {
		return Objects.equals(target.getMoveable(), null);
	}
	
	private void verplaatsMannetje(Veld huidigePositie, Veld target) {
		huidigePositie.setMoveable(null);
		mannetje.setPositie(target);
		target.setMoveable(mannetje);
	}
	
	private void verplaatsKist(Moveable kist, Veld achterTarget) {
		achterTarget.setMoveable(kist);
		kist.setPositie(achterTarget);
		kisten.add(kist);
	}
	
	private void setVeld(Veld veld, int x, int y) {
		this.velden[x][y] = veld;
	}
	
	protected void creeerVeld(Actie actie, int x, int y) {
		Veld muur = new Veld(VeldType.MUUR, false, x, y);
		Veld nieuwVeld = new Veld(VeldType.VELD, false, x, y);
		Veld doel = new Veld(VeldType.VELD, true, x, y);
		
		switch(actie) { 
			case PLAATSMUUR: plaatsMuur(muur, x, y);
			break;
			case PLAATSVELD: plaatsVeld(nieuwVeld, x, y);
			break;
			case PLAATSMANNETJE: {
				plaatsMannetje(x, y);
				hasMannetje = true;
			}
			break;
			case PLAATSKIST: plaatsKist(x, y);
			break;
			case PLAATSDOEL: plaatsDoel(doel, x, y);
			break;
			case CLEAR: setVeld(null, x, y);
			break;
		}
	}
	
	private void plaatsMuur(Veld muur, int x, int y) {
		if (!Objects.equals(getVeld(x,y), null))
			throw new RuntimeException("Geen leeg veld.");
			setVeld(muur, x, y);
	}
	
	private void plaatsVeld(Veld veld, int x, int y) {
		if (!Objects.equals(getVeld(x,y), null))
			throw new RuntimeException("Geen leeg veld.");
		setVeld(veld, x, y);
	}
	
	private void plaatsDoel(Veld doel, int x, int y) {
		if (!Objects.equals(getVeld(x,y), null))
			throw new RuntimeException("Geen leeg veld.");
		setVeld(doel, x, y);
	}
	
	private void plaatsMannetje(int x, int y) {
		if (Objects.equals(getVeld(x,y), null) || getVeld(x,y).getVeldType() == VeldType.MUUR )
			throw new RuntimeException("Mannetje kan niet op muur of leeg veld geplaatst worden.");
		if(!hasNoMoveable(this.velden[x][y]))
			throw new RuntimeException("Veld heeft al moveable");
		if(getVeld(x,y).isDoel())
			throw new RuntimeException("Mannetje mag niet starten op doel.");
		if(hasMannetje)
			throw new RuntimeException("There can be only one.");
			this.velden[x][y].setMoveable(new Mannetje(this.velden[x][y]));
			setVeld(this.velden[x][y], x, y);
	}
	
	private void plaatsKist(int x, int y) {
		if (Objects.equals(getVeld(x,y), null) || getVeld(x,y).getVeldType() == VeldType.MUUR )
			throw new RuntimeException("Kist kan niet op muur of leeg veld geplaatst worden.");
		if(!hasNoMoveable(this.velden[x][y]))
			throw new RuntimeException("Veld heeft al moveable");
		if(getVeld(x,y).isDoel())
			throw new RuntimeException("Kist mag niet starten op doel.");
			this.velden[x][y].setMoveable(new Kist(this.velden[x][y]));
			setVeld(this.velden[x][y], x, y);
	}

	public VeldInterface getVeld(int x, int y) {
		return this.velden[x][y];

	}
	
	public int getAantalDoelen() {
		int aantal = 0;
		for (int i = 0; i < BordDimensies.getAantalRijen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalKolommen(); j++) {
				if (getVeld(i, j).isDoel())
					aantal++;
			}
		}
		return aantal;
  }
	public VeldInterface getVeld(int x, int y) {
		return this.velden[x][y];
	}
}
