package domein;

import java.util.*;
import java.util.stream.Collectors;

import vertalingen.Taal;

/**
 * Stelt een spelbord voor.
 * 
 * @author g85
 */
public class Spelbord {

	private String spelbordNaam;
	private int volgorde;
	private Veld[][] velden;
	private Moveable mannetje;
	private List<Moveable> kisten;
	private boolean voltooid;
	private int aantalBewegingen;

	/**
	 * Creëert een spelbord met opgegeven naam, volgorde, mannetje, lijst van kisten
	 * en een array van velden.
	 * 
	 * @param spelbordNaam De naam van het spelbord.
	 * @param volgorde     De volgorde waarin het spelbord in het spel verschijnt.
	 * @param mannetje     Het mannetje dat in het spelbord wordt gebruikt om te
	 *                     spelen.
	 * @param kisten       De kisten die op het spelbord staan en moeten worden
	 *                     verplaatst.
	 * @param velden       De velden die het spelbord bezit.
	 */
	public Spelbord(String spelbordNaam, int volgorde, Moveable mannetje, List<Moveable> kisten, Veld[][] velden) throws IllegalArgumentException{
			setVelden(velden);
			this.mannetje = mannetje;
			this.kisten = kisten;
			setSpelbordNaam(spelbordNaam);
			setVolgorde(volgorde);
	}

	/**
	 * Creëert een spelbord met opgegeven naam en volgorde.
	 * 
	 * @param spelbordNaam De naam van het spelbord.
	 * @param volgorde     De volgorde van het spelbord in het spel.
	 */
	public Spelbord(String spelbordNaam, int volgorde) {
		setSpelbordNaam(spelbordNaam);
		this.volgorde = volgorde;
		this.velden = new Veld[BordDimensies.getAantalRijen()][BordDimensies.getAantalKolommen()];
		this.kisten = new ArrayList<>();
	}

	/**
	 * Geeft de naam van het spelbord terug.
	 * 
	 * @return de naam van het spelbord.
	 */
	public String getSpelbordNaam() {
		return spelbordNaam;
	}

	/**
	 * Stelt de naam van het spelbord in. Werpt een IllegalArgumentException indien
	 * de naam null is of leeg.
	 * 
	 * @param spelbordNaam De naam van het spelbord.
	 * @throws IllegalArgumentException indien de naam null is of leeg.
	 */
	public void setSpelbordNaam(String spelbordNaam) throws IllegalArgumentException {
		//Check dat er geen spaties zijn
		if (spelbordNaam.contains(" ") || spelbordNaam.matches("\\s+")) 
			throw new IllegalArgumentException(Taal.vertaal("exception_name_no_spaces"));
		//check null of leeg
		if (Objects.equals(spelbordNaam, null) || spelbordNaam.isBlank())
			throw new IllegalArgumentException(Taal.vertaal("game_board_name") + Taal.vertaal("exception_not_blank"));
		this.spelbordNaam = spelbordNaam;
	}

	/**
	 * Geeft de velden van het spelbord terug.
	 * 
	 * @return een array van velden.
	 */
	public Veld[][] getVelden() {
		return velden;
	}

	/**
	 * Stelt de velden van het spelbord in. Werpt een IllegalArgumentException
	 * indien de array null is.
	 * 
	 * @param velden De gegeven array van velden.
	 * @throws IllegalArgumentException indien de array null is.
	 */
	public void setVelden(Veld[][] velden) throws IllegalArgumentException {
		if (Objects.equals(velden, null))
			throw new IllegalArgumentException(Taal.vertaal("field") + Taal.vertaal("exception_not_null"));
		this.velden = velden;
	}

	/**
	 * Geeft de volgorde van het spelbord in het spel terug.
	 * 
	 * @return de volgorde van het spelbord.
	 */
	public int getVolgorde() {
		return volgorde;
	}

	/**
	 * Stelt de volgorde van het spelbord in. Werpt een IllegalArgumentException
	 * indien de volgorde null is.
	 * 
	 * @param volgorde De gegeven volgorde van het spelbord in het spel
	 * @throws IllegalArgumentException indien de volgorde null is.
	 */
	public void setVolgorde(int volgorde) throws IllegalArgumentException {
		if (Objects.equals(volgorde, null))
			throw new IllegalArgumentException(Taal.vertaal("order") + Taal.vertaal("exception_not_null"));
		this.volgorde = volgorde;
	}

	/**
	 * Beweegt het mannetje naar de gekozen beweegrichting op het spelbord.
	 * 
	 * @param richting de gekozen beweegrichting.
	 * @throws RuntimeException indien het mannetje een illegal movement maakt.
	 */
	public void beweeg(BeweegRichting richting) throws RuntimeException {
		Veld huidigePositie = mannetje.getPositie();
		int xHuidig = huidigePositie.getX();
		int yHuidig = huidigePositie.getY();
		int xTarget = xHuidig;
		int yTarget = yHuidig;
		int xAchterTarget = xHuidig;
		int yAchterTarget = yHuidig;

		// Y is de minimum de hoogste rij
		switch (richting) {
		case LINKS:
			xTarget--;
			xAchterTarget = xTarget - 1;
			break;
		case ONDER:
			yTarget++;
			yAchterTarget = yTarget + 1;
			break;
		case RECHTS:
			xTarget++;
			xAchterTarget = xTarget + 1;
			break;
		case BOVEN:
			yTarget--;
			yAchterTarget = yTarget - 1;
			break;
		}
		updateBord(huidigePositie, xHuidig, yHuidig, xTarget, yTarget, xAchterTarget, yAchterTarget);
		checkVoltooid();
		aantalBewegingen++;
	}

	/**
	 * Geeft terug of het spelbord voltooid is.
	 * 
	 * @return true indien het spelbord voltooid is.
	 */
	public boolean isVoltooid() {
		return voltooid;
	}

	/**
	 * Geeft het mannetje van het spelbord terug.
	 * 
	 * @return het mannetje van het spelbord.
	 */
	public Moveable getMannetje() {
		return mannetje;
	}

	/**
	 * Geeft het een lijst van de kisten van het spelbord terug.
	 * 
	 * @return een lijst van de kisten van het spelbord.
	 */
	public List<Moveable> getKisten() {
		return kisten;
	}

	private void checkVoltooid() {
		List<Moveable> kistenOpDoel = kisten.stream().filter(k -> k.getPositie().isDoel()).collect(Collectors.toList());
		if (kistenOpDoel.equals(kisten)) {
			voltooid = true;
		}
	}

	/**
	 * Geeft het aantal bewegingen van het mannetje terug.
	 * 
	 * @return het aantal bewegingen van het mannetje.
	 */
	public int getAantalBewegingen() {
		return aantalBewegingen;
	}

	private void updateBord(Veld huidigePositie, int xHuidig, int yHuidig, int xTarget, int yTarget, int xAchterTarget,
			int yAchterTarget) {
		Veld target = velden[xTarget][yTarget];
		if (target.getVeldType().equals(VeldType.VELD)) {
			if (hasNoMoveable(target)) {
				verplaatsMannetje(huidigePositie, target);
				// Indien het een kist is
			} else {
				Veld achterTarget = velden[xAchterTarget][yAchterTarget];
				if (!(achterTarget.getVeldType().equals(VeldType.VELD) && hasNoMoveable(achterTarget))) {
					throw new RuntimeException(Taal.vertaal("illegal_movement"));
				} else {
					Moveable kist = target.getMoveable();
					kisten.remove(kist);
					// Zet kist 1 vooruit
					verplaatsKist(kist, achterTarget);

					// Zet mannetje 1 vooruit
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
	private boolean hasMoveable(Veld target) {
		return !Objects.equals(target.getMoveable(), null);
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
		switch (actie) {
		case PLAATSMUUR:
			plaatsOndergrond(VeldType.MUUR, false, x, y);
			break;
		case PLAATSVELD:
			plaatsOndergrond(VeldType.VELD, false, x, y);
			break;
		case PLAATSMANNETJE:
			plaatsMannetje(x, y);
			break;
		case PLAATSKIST:
			plaatsKist(x, y);
			break;
		case PLAATSDOEL:
			plaatsOndergrond(VeldType.VELD, true, x, y);
			break;
		case CLEAR: {
			if(Objects.equals(getVeld(x, y), null))
				break;
				//throw new RuntimeException(Taal.vertaal("exception_erase"));
			if (hasMoveable(getVeld(x, y))) {
				Moveable moveable = getVeld(x, y).getMoveable();
				if (moveable.getType().equals(MoveableType.KIST)) {
					Moveable kistToRemove = kisten.stream()
							.filter(k -> k.getPositie().getX() == x && k.getPositie().getY() == y)
							.collect(Collectors.toList()).get(0);
					kisten.remove(kistToRemove);
				} else if (moveable.getType().equals(MoveableType.MANNETJE)) {
					this.mannetje = null;
				}
				getVeld(x, y).setMoveable(null);
			}
			setVeld(null, x, y);
		}
			break;
		}
	}
	
	private void plaatsOndergrond(VeldType veldType, boolean doel, int x, int y) {
		Veld ondergrond = new Veld(veldType, doel, x, y);
		if (!Objects.equals(getVeld(x, y), null)) {
			creeerVeld(Actie.CLEAR, x, y);
		}
		setVeld(ondergrond, x, y);
	}

	private void plaatsMannetje(int x, int y) {
		if (Objects.equals(getVeld(x, y), null) || getVeld(x, y).getVeldType() == VeldType.MUUR)
			throw new RuntimeException(Taal.vertaal("worker") + " " + Taal.vertaal("exception_empty_field"));
		if (!hasNoMoveable(this.velden[x][y]))
			throw new RuntimeException(Taal.vertaal("field") + " " + Taal.vertaal("exception_moveable"));
		if (getVeld(x, y).isDoel())
			throw new RuntimeException(Taal.vertaal("worker") + " " + Taal.vertaal("exception_start_goal"));

		if (!Objects.equals(this.mannetje, null))
			throw new RuntimeException(Taal.vertaal("exception_only_one"));

		Moveable mannetje = new Moveable(this.velden[x][y], MoveableType.MANNETJE);
		this.mannetje = mannetje;
		this.velden[x][y].setMoveable(mannetje);
		setVeld(this.velden[x][y], x, y);
	}

	private void plaatsKist(int x, int y) {
		if (Objects.equals(getVeld(x, y), null) || getVeld(x, y).getVeldType() == VeldType.MUUR)
			throw new RuntimeException(Taal.vertaal("box") + " " + Taal.vertaal("exception_empty_field"));
		if (!hasNoMoveable(this.velden[x][y]))
			throw new RuntimeException(Taal.vertaal("field") + " " + Taal.vertaal("exception_moveable"));
		if (getVeld(x, y).isDoel())
			throw new RuntimeException(Taal.vertaal("box") + " " + Taal.vertaal("exception_start_goal"));

		Moveable kist = new Moveable(this.velden[x][y], MoveableType.KIST);
		kisten.add(kist);
		this.velden[x][y].setMoveable(kist);
		setVeld(this.velden[x][y], x, y);
	}

	/**
	 * Geeft het veld op de gegeven locatie terug.
	 * 
	 * @param x De gegeven rij.
	 * @param y De gegeven kolom.
	 * @return het veld op de gegeven rij en kolom.
	 */
	public Veld getVeld(int x, int y) {
		return this.velden[x][y];
	}

	/**
	 * Geeft het aantal doelen van het spelbord terug.
	 * 
	 * @return het aantal doelen van het spelbord.
	 */
	public int getAantalDoelen() {
		int aantal = 0;
		for (int i = 0; i < BordDimensies.getAantalRijen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalKolommen(); j++) {
				if (!Objects.equals(getVeld(i, j), null) && getVeld(i, j).isDoel()) {
					aantal++;
				}
			}
		}
		return aantal;
	}

	protected void valideerSpelbord() throws RuntimeException {
		if (getKisten().size() != getAantalDoelen())
			throw new RuntimeException(Taal.vertaal("exception_goals_boxes"));
		if (getKisten().size() == 0 || getAantalDoelen() == 0) {
			throw new RuntimeException(Taal.vertaal("exception_minimum"));
		}
		if (Objects.equals(getMannetje(), null)) {
			throw new RuntimeException(Taal.vertaal("exception_worker_required"));
		}

		List<Veld> muren = getFlatListVelden().stream()
				.filter(v -> !Objects.equals(v, null) && v.getVeldType().equals(VeldType.MUUR))
				.collect(Collectors.toList());
		valideerSpelbordRand(muren);
		valideerScope(muren);
		valideerBereikbaarheid();

	}

	private void valideerSpelbordRand(List<Veld> muren) throws RuntimeException {

		if (muren.size() == 0) {
			throw new RuntimeException(Taal.vertaal("exception_walls_required"));
		}
		// Y: de minimum de hoogste rij
		int maxX = muren.stream().max(Comparator.comparingInt(Veld::getX)).map(v -> v.getX()).get();
		int minX = muren.stream().min(Comparator.comparingInt(Veld::getX)).map(v -> v.getX()).get();
		int maxY = muren.stream().max(Comparator.comparingInt(Veld::getY)).map(v -> v.getY()).get();
		int minY = muren.stream().min(Comparator.comparingInt(Veld::getY)).map(v -> v.getY()).get();

		List<Veld> nietIngeslotenMuren = new ArrayList<>();
		muren.stream().forEach(m -> {
			if (aantalOmliggendeMuren(m) < 2) {
				nietIngeslotenMuren.add(m);
			}
		});
		// Valideer dat de losstaande muren zich binnen de rand bevinden
		nietIngeslotenMuren.stream().forEach(m -> {
			int x = m.getX();
			int y = m.getY();
			if (!(x > minX && x < maxX) || !(y > minY && y < maxY))
				throw new RuntimeException(Taal.vertaal("exception_walls_closed"));
		});
	}
	
	private void valideerScope(List<Veld> muren) {
		// Check per kolom of er niets buiten de muren staat en geen null values binnen de muren
		for (int kolomNummer = 0; kolomNummer < BordDimensies.getAantalKolommen(); kolomNummer++) {
			
			Veld[] kolom = velden[kolomNummer];
			final int thiskolomNummer = kolomNummer;
			List<Veld> murenkolom = muren.stream().filter(v -> v.getX() == thiskolomNummer).collect(Collectors.toList());

			if (murenkolom.size() == 0) {
				// Als er geen muren zijn mag er niets staan
				for (int rijNummer = 0; rijNummer < BordDimensies.getAantalRijen(); rijNummer++) {
					if (!Objects.equals(kolom[rijNummer], null))
						throw new RuntimeException(Taal.vertaal("exception_no_objects_outside"));
				}

			} else {
				// Anders controleren dat er geen nulls zijn binnen de muren maar altijd erbuiten
				int linkerLimiet = murenkolom.stream().min(Comparator.comparingInt(Veld::getY)).map(v -> v.getY()).get();
				int rechterLimiet = murenkolom.stream().max(Comparator.comparingInt(Veld::getY)).map(v -> v.getY()).get();

				for (int rijNummer = 0; rijNummer < BordDimensies.getAantalRijen(); rijNummer++) {
					// Buiten de rand moet alles null zijn, maar het kan een muur zijn op de bordrand
					if (rijNummer < linkerLimiet || rijNummer > rechterLimiet) {
						if (!(Objects.equals(kolom[rijNummer], null) || kolom[rijNummer].getVeldType().equals(VeldType.MUUR)))
							throw new RuntimeException(Taal.vertaal("exception_no_objects_outside"));
					} else {
						// Binnen de rand mag niets null zijn
						if (Objects.equals(kolom[rijNummer], null))
							throw new RuntimeException(Taal.vertaal("exception_no_null_inside"));
					}
				}
			}
		}
	}

	private int aantalOmliggendeMuren(Veld veld) {
		int aantal = 0;
		List<Veld> omliggend = new ArrayList<>();
		// boven
		if (veld.getY() - 1 >= 0)
			omliggend.add(velden[veld.getX()][veld.getY() - 1]);
		// onder
		if (veld.getY() + 1 < BordDimensies.getAantalRijen())
			omliggend.add(velden[veld.getX()][veld.getY() + 1]);
		// links
		if (veld.getX() - 1 >= 0)
			omliggend.add(velden[veld.getX() - 1][veld.getY()]);
		// rechts
		if (veld.getX() + 1 < BordDimensies.getAantalKolommen())
			omliggend.add(velden[veld.getX() + 1][veld.getY()]);
		aantal = (int) omliggend.stream().filter(m -> {
			return (!Objects.equals(m, null) && m.getVeldType().equals(VeldType.MUUR));
		}).count();

		return aantal;
	}
	
	private void valideerBereikbaarheid() {
		if(aantalOmliggendeMuren(mannetje.getPositie()) == 4)
			throw new RuntimeException(Taal.vertaal("exception_worker_stuck"));
		
		getFlatListVelden()
		.stream()
		.filter(v -> !Objects.equals(v, null) && v.isDoel())
		.forEach(v -> {
			if(aantalOmliggendeMuren(v) == 4)
				throw new RuntimeException(Taal.vertaal("exception_all_goals_accessible"));
		});
		
	}

	private List<Veld> getFlatListVelden() {
		return Arrays.stream(velden).flatMap(Arrays::stream).collect(Collectors.toList());
	}
}
