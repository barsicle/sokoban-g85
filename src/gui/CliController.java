package gui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import domein.DomeinController;
import domein.Moveable;
import domein.Veld;
import vertalingen.Taal;
import vertalingen.Talen;

/**
 * Stelt de command line controller voor.
 * 
 * @author g85
 */
public class CliController {
	private DomeinController dc;
	private Scanner scan;
	private static final String SOKOBAN_ASCII_ART = "   _____       _         _                 \r\n"
			+ "  / ____|     | |       | |                \r\n" + " | (___   ___ | | _____ | |__   __ _ _ __  \r\n"
			+ "  \\___ \\ / _ \\| |/ / _ \\| '_ \\ / _` | '_ \\ \r\n"
			+ "  ____) | (_) |   < (_) | |_) | (_| | | | |\r\n" + " |_____/ \\___/|_|\\_\\___/|_.__/ \\__,_|_| |_|\r\n"
			+ "                                           \r\n" + "                                           ";
	private static final String LIJN_SEPARATOR_STER = "******************************************************************";
	Character[][] fields = new Character[10][10];
	Character[][] moveables = new Character[10][10];

	// UC1
	/**
	 * Cre�ert een instantie van de command line controller met een
	 * domeincontroller object.
	 * 
	 * @param dc Het meegegeven domeincontroller object.
	 */
	public CliController(DomeinController dc) {
		this.dc = dc;
		scan = new Scanner(System.in);
	}

	// UC1
	/**
	 * Start de applicatie.
	 */
	public void startApplicatie() {
		System.out.println(LIJN_SEPARATOR_STER);
		System.out.println(SOKOBAN_ASCII_ART);
		System.out.println("Gebouwd door g85!");
		System.out.println(LIJN_SEPARATOR_STER);
		taalSelect();
		loginMenu();
	}

	// UC2
	private void taalSelect() {
		// Blijf gaan zolang er geen taal geselecteerd is
		System.out.println("Gelieve uw taal te selecteren (cijfer ingeven)");
		System.out.println("Veuillez choisir votre langue (entrez le num�ro)");
		System.out.println("Please choose your language (enter number)");

		while (Taal.taalIngesteld() == false) {
			System.out.println("1. Nederlands");
			System.out.println("2. Fran�ais");
			System.out.println("3. English");
			System.out.println(LIJN_SEPARATOR_STER);

			int keuze = 0;
			try {
				keuze = scan.nextInt();
			}

			catch (InputMismatchException e) {
				System.out.println("Gelieve een cijfer in te voeren");
				System.out.println("Veuillez saisir un nombre");
				System.out.println("Please enter a number");
				System.out.println(LIJN_SEPARATOR_STER);
				scan.nextLine();
				continue;
			}

			switch (keuze) {
			case 1:
				Taal.setTaal(Talen.nl);
				break;

			case 2:
				Taal.setTaal(Talen.fr);
				break;

			case 3:
				Taal.setTaal(Talen.en);
				break;

			default:
				System.out.println("Geen geldige keuze");
				System.out.println("Choix invalide");
				System.out.println("Invalid choice");
				System.out.println(LIJN_SEPARATOR_STER);
				continue;
			}

		}

	}

	// UC1
	private void loginMenu() {
		// Re�nitialiseren scanner
		scan = new Scanner(System.in);
		int keuze = 0;
		System.out.println(Taal.vertaal("login_choose_option"));
		while (keuze <= 0 || keuze > 3) {

			try {

				keuze = scan.nextInt();

				switch (keuze) {
				case 1:
					startAanmelden();
					break;

				case 2:
					startRegistreer();
					break;

				case 3:
					afsluiten();
					break;

				default:
					System.out.println(Taal.vertaal("exception_invalid_login_choose_option"));
					break;

				}

			}

			catch (InputMismatchException e) {
				System.out.println(Taal.vertaal("exception_invalid_login_choose_option"));
				scan.nextLine();
			}

		}

	}

	// UC1
	private void hoofdMenu() {
		// Re�nitialiseren scanner
		scan = new Scanner(System.in);
		while (true) {
			if (dc.isAdmin() == false) {
				speelSpel();
			} else {
				System.out.println(Taal.vertaal("menu_choose_option_admin"));
			}

			int keuze = 0;

			try {
				keuze = scan.nextInt();
			}

			catch (InputMismatchException e) {
				System.out.println(Taal.vertaal("exception_invalid_menu_choose_option"));
				scan.nextLine();
				continue;
			}

			// TO DO
			switch (keuze) {
			case 1:
				System.out.println("Nog niet ge�mplementeerd");
				break;

			case 2:
				if (dc.isAdmin()) {
					System.out.println("Nog niet ge�mplementeerd");
				}

				else {
					afsluiten();

				}
				break;

			case 3:
				if (dc.isAdmin()) {

					// TO DO: wijzig een spel optie
					System.out.println("Nog niet ge�mplementeerd");

				}

				else {

					System.out.println(Taal.vertaal("exception_invalid_menu_choose_option"));

				}
				break;

			case 4:
				if (dc.isAdmin()) {
					afsluiten();

				}

				else {

					System.out.println(Taal.vertaal("exception_invalid_menu_choose_option"));

				}
				break;

			default:
				System.out.println(Taal.vertaal("exception_invalid_menu_choose_option"));
				continue;

			}

		}

	}

	// UC1
	private void startAanmelden() {

		// Re�nitialiseren scanner
		scan = new Scanner(System.in);

		while (true) {

			System.out.println(Taal.vertaal("sign_in_username"));
			String gebruikersnaam = scan.nextLine();

			System.out.println(Taal.vertaal("sign_in_password"));
			String wachtwoord = scan.nextLine();

			try {

				dc.meldAan(gebruikersnaam, wachtwoord);

			}

			catch (Exception e) {

				System.out.println(Taal.vertaal("exception_invalid_sign_in"));
				continue;

			}

			System.out.println(LIJN_SEPARATOR_STER);
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s %n", dc.getGebruikersnaam());
			System.out.println(LIJN_SEPARATOR_STER);

			hoofdMenu();
			break;

		}

	}

	// UC2
	private void startRegistreer() {

		// Re�nitialiseren scanner
		scan = new Scanner(System.in);

		while (true) {

			System.out.println(Taal.vertaal("register_choose_user_name"));
			String gebruikersnaam = scan.nextLine().toLowerCase();

			System.out.println(Taal.vertaal("register_choose_password"));
			String wachtwoord = scan.nextLine();

			System.out.println(Taal.vertaal("register_confirm_password"));
			String wachtwoordBevestiging = scan.nextLine();

			System.out.println(Taal.vertaal("register_choose_lastname"));
			String naam = scan.nextLine().trim();

			if (naam.equals("")) {

				naam = null;

			}

			System.out.println(Taal.vertaal("register_choose_firstname"));
			String voornaam = scan.nextLine().trim();

			if (voornaam.equals("")) {

				voornaam = null;

			}

			try {

				dc.registreer(naam, voornaam, gebruikersnaam, wachtwoord, wachtwoordBevestiging);

			}

			catch (Exception e) {

				System.out.println(Taal.vertaal("exception_invalid_registration"));
				continue;

			}

			System.out.println(LIJN_SEPARATOR_STER);
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s %n", dc.getGebruikersnaam());
			System.out.println(LIJN_SEPARATOR_STER);

			hoofdMenu();
			break;

		}

	}

	// UC3
	private void speelSpel() {

		System.out.println("Nog niet ge�mplementeerd. Hier komt je functie.");
		startKiesSpel();
		bouwScherm();
		clearScreen();
		afsluiten();

	}

	private void startKiesSpel() {

		scan = new Scanner(System.in);
		List<String> spelNamen = dc.getSpelNamen();
		int i = 1;
		int keuze = 0;

		do {

			System.out.printf("Kies een geldig spelnummer.");

			for (String spel : spelNamen) {

				System.out.printf("\n" + i + ". " + spel);
				i++;

			}
			System.out.printf("\n");
			keuze = scan.nextInt();
			i = 1;

		} while (keuze <= 0 || keuze > spelNamen.size());

		System.out.printf("Keuze gemaakt!\n");

		dc.kiesSpel(spelNamen.get(keuze));

	}

	// UC4
	private void bouwScherm() {
		Veld[][] velden = dc.geefVelden();

		// i = kolom, j = rij
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				Veld veld = velden[i][j];
				char teken = '/';
				if (Objects.equals(veld, null)) {

				} else {
					switch (veld.getVeldType()) {
					case MUUR:
						teken = '#';
						break;
					case VELD:
						boolean doel = veld.isDoel();
						if (doel) {
							teken = 'o';
						} else {
							teken = ' ';
						}

						break;
					default:
						teken = '/';
						break;
					}
				}

				fields[i][j] = teken;

			}

		}

		updateScherm();

	}

	private void updateScherm() {

		// Eerst wissen, daarna opnieuw opbouwen
		for (int i = 0; i < /* fields.length */ 10; i++) {

			for (int j = 0; j < /* fields[i].length */ 10; j++) {
				
				moveables[i][j] = null;

			}
			
		}
		// Mannetje

		Moveable mannetje = dc.getMannetje();
		Veld mannetjePositie = mannetje.getPositie();

		moveables[mannetjePositie.getY()][mannetjePositie.getX()] = 'M';

		List<Moveable> kisten = dc.getKisten();

		for (Moveable kist : kisten) {

			moveables[kist.getPositie().getY()][kist.getPositie().getX()] = 'X';

		}

		tekenScherm();

		// Check of spel voltooid is
		// checkVoltooid();

	}

	private void tekenScherm() {

		Character[][] statusBord = new Character[10][10];

		for (int i = 0; i < /* fields.length */ 10; i++) {

			for (int j = 0; j < /* fields[i].length */ 10; j++) {

				statusBord[i][j] = fields[i][j];

				if (!Objects.equals(null, moveables[i][j])) {

					statusBord[i][j] = moveables[i][j];

				}

			}

		}

		for (Character[] rij : statusBord) {
			
			String rijResult = "";
			
			for(char teken : rij) {
				
				rijResult += teken;

			}

			System.out.print(rijResult + '\n');

		}

	}

	public void clearScreen() {

		System.out.flush();

	}

	protected void afsluiten() {
		System.exit(0);
	}

}
