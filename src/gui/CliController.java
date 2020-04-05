package gui;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import domein.BeweegRichting;
import domein.DomeinController;
import domein.Moveable;
import domein.Veld;
import domein.VeldInterface;
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
	Character[][] fields = new Character[10][10];
	Character[][] moveables = new Character[10][10];
	
	Console view = System.console();

	// UC1
	/**
	 * Creï¿½ert een instantie van de command line controller met een
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
		taalSelect();
		loginMenu();
	}

	// UC2
	private void taalSelect() {
		// Blijf gaan zolang er geen taal geselecteerd is
		String taalSelectie = "\nNL: Gelieve uw taal te selecteren (cijfer ingeven)\n" +
		"FR: Veuillez choisir votre langue (entrez le numéro)\n" +
		"EN: Please choose your language (enter number)\n";
		System.out.print(taalSelectie);
		
		do {

			System.out.println("1. Nederlands");
			System.out.println("2. Français");
			System.out.println("3. English");
			System.out.print(">>> ");

			int keuze = 0;
			try {
				keuze = scan.nextInt();
			}

			catch (InputMismatchException e) {
				System.out.println("Gelieve een cijfer in te voeren");
				System.out.println("Veuillez saisir un nombre");
				System.out.println("Please enter a number");
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
				continue;
			}
			
		}

		while (Taal.taalIngesteld() == false);
		
		System.out.print("\n");
		showHeader();
		loginMenu();

	}
	
	private void showHeader() {
		
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("sokoban_ascii_art"));
		System.out.println(Taal.vertaal("build"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
	}

	// UC1
	private void loginMenu() {
		
		// Reï¿½nitialiseren scanner
		scan = new Scanner(System.in);
		int keuze = 0;
		
		int lineStarLength = Taal.vertaal("line_star").length();
		int loginTitleLength = Taal.vertaal("login_title").length();
		
		System.out.print("\n");
		for(int i = 0; i <= lineStarLength; i++) {
			
			if(i <= lineStarLength/2 - loginTitleLength/2 || i >= lineStarLength/2 + loginTitleLength/2) {
				
				System.out.print("_");
				
			}
			
			else {
				
				System.out.print(Taal.vertaal("login_title"));
				i += loginTitleLength;
				
			}
			
		}
		System.out.print("\n");
		
		System.out.print(Taal.vertaal("login_choose_option"));
		System.out.print(Taal.vertaal("scanner_input"));
		while (keuze <= 0 || keuze > 3) {

			try {

				keuze = scan.nextInt();

				switch (keuze) {
				case 1:
					startAanmelden();
					break;
					
				case 2:
					taalSelect();


				case 3:
					startRegistreer();
					break;

				case 4:
					afsluiten();
					break;

				default:
					System.out.print(Taal.vertaal("exception_invalid_login_choose_option"));
					System.out.print(Taal.vertaal("scanner_input"));
					break;

				}

			}

			catch (InputMismatchException e) {
				System.out.println(Taal.vertaal("exception_invalid_login_choose_option"));
				System.out.print(Taal.vertaal("scanner_input"));
				scan.nextLine();
			}

		}

	}

	// UC1
	private void startAanmelden() {

		// Reï¿½nitialiseren scanner
		scan = new Scanner(System.in);
		System.out.print("\n");
		int lineStarLength = Taal.vertaal("line_star").length();
		int signInTitleLength = Taal.vertaal("sign_in_title").length();
		
		for(int i = 0; i <= lineStarLength; i++) {
			
			if(i <= lineStarLength/2 - signInTitleLength/2 || i >= lineStarLength/2 + signInTitleLength/2) {
				
				System.out.print("_");
				
			}
			
			else {
				
				System.out.print(Taal.vertaal("sign_in_title"));
				i += signInTitleLength;
				
			}
			
		}
		System.out.print("\n\n");
		
		int keuze = 0;
		System.out.print(Taal.vertaal("sign_in_menu"));
		while (keuze <= 0 || keuze > 2) {
			
			
			try {
				
				System.out.print(Taal.vertaal("scanner_input"));
				keuze = scan.nextInt();
				
				switch(keuze) {
				
					case 1:
						System.out.print("\n");
						System.out.print(Taal.vertaal("sign_in_subtitle"));
						break;
					case 2:
						loginMenu();
						break;
					default:
						System.out.print(Taal.vertaal("exception_invalid_sign_in_menu_option"));
						break;

				}

			}
			
			catch(InputMismatchException e) {
				
				System.out.println(Taal.vertaal("exception_invalid_sign_in_menu_option"));
				System.out.print(Taal.vertaal("scanner_input"));
				
			}
			
		}
		
		scan = new Scanner(System.in);
		
		while (true) {

			System.out.print(Taal.vertaal("sign_in_username"));
			System.out.print(Taal.vertaal("scanner_input"));
			String gebruikersnaam = scan.nextLine();

			System.out.print(Taal.vertaal("sign_in_password"));
			System.out.print(Taal.vertaal("scanner_input"));
			String wachtwoord = scan.nextLine();

			try {

				dc.meldAan(gebruikersnaam, wachtwoord);

			}

			catch (Exception e) {

				System.out.print(Taal.vertaal("exception_invalid_sign_in"));
				continue;

			}
			
			System.out.print("\n");
			System.out.print(Taal.vertaal("line_star"));
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s!%n", dc.getGebruikersnaam());
			System.out.print(Taal.vertaal("line_star"));
			System.out.print("\n");
			
			System.out.print("\n");
			hoofdMenu();
			break;

		}

	}

	// UC2
	private void startRegistreer() {

		// Reï¿½nitialiseren scanner
		scan = new Scanner(System.in);

		System.out.print("\n");
		int lineStarLength = Taal.vertaal("line_star").length();
		int registerTitleLength = Taal.vertaal("register_title").length();
		
		for(int i = 0; i <= lineStarLength; i++) {
			
			if(i <= lineStarLength/2 - registerTitleLength/2 || i >= lineStarLength/2 + registerTitleLength/2) {
				
				System.out.print("_");
				
			}
			
			else {
				
				System.out.print(Taal.vertaal("register_title"));
				i += registerTitleLength;
				
			}
			
		}
		System.out.print("\n\n");
		
		int keuze = 0;
		System.out.print(Taal.vertaal("register_menu"));
		while (keuze <= 0 || keuze > 2) {
			
			
			try {
				
				System.out.print(Taal.vertaal("scanner_input"));
				keuze = scan.nextInt();
				
				switch(keuze) {
				
					case 1:
						System.out.print("\n");
						System.out.print(Taal.vertaal("register_subtitle"));
						break;
					case 2:
						loginMenu();
						break;
					default:
						System.out.print(Taal.vertaal("exception_invalid_register_menu_option"));
						break;

				}

			}
			
			catch(InputMismatchException e) {
				
				System.out.println(Taal.vertaal("exception_invalid_register_menu_option"));
				System.out.print(Taal.vertaal("scanner_input"));
				
			}
			
		}
		
		scan = new Scanner(System.in);		
		System.out.print("\n");
		while (true) {

			System.out.print(Taal.vertaal("register_choose_user_name"));
			System.out.print(Taal.vertaal("scanner_input"));
			String gebruikersnaam = scan.nextLine().toLowerCase();

			System.out.print(Taal.vertaal("register_choose_password"));
			System.out.print(Taal.vertaal("scanner_input"));
			String wachtwoord = scan.nextLine();

			System.out.print(Taal.vertaal("register_confirm_password"));
			System.out.print(Taal.vertaal("scanner_input"));
			String wachtwoordBevestiging = scan.nextLine();

			System.out.print(Taal.vertaal("register_choose_lastname"));
			System.out.print(Taal.vertaal("scanner_input"));
			String naam = scan.nextLine().trim();

			if (naam.equals("")) {

				naam = null;

			}

			System.out.print(Taal.vertaal("register_choose_firstname"));
			System.out.print(Taal.vertaal("scanner_input"));
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

			System.out.print("\n");
			System.out.print(Taal.vertaal("line_star"));
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s %n", dc.getGebruikersnaam());
			System.out.print(Taal.vertaal("line_star"));
			System.out.print("\n");
			
			System.out.print("\n");
			hoofdMenu();
			break;

		}

	}
	
	// UC1
	private void hoofdMenu() {
		// Reï¿½nitialiseren scanner
		scan = new Scanner(System.in);

		int lineStarLength = Taal.vertaal("line_star").length();
		int gameTitleLength = Taal.vertaal("game_title").length();
			
		for(int i = 0; i <= lineStarLength; i++) {
				
			if(i <= lineStarLength/2 - gameTitleLength/2 || i >= lineStarLength/2 + gameTitleLength/2) {
					
				System.out.print("_");
					
			}
				
			else {
					
				System.out.print(Taal.vertaal("game_title"));
				i += gameTitleLength;
					
			}
				
		}
		System.out.print("\n");
		
		System.out.print(Taal.vertaal("game_subtitle"));
			
		while (true) {
			if (dc.isAdmin() == false) {
				System.out.print(Taal.vertaal("menu_choose_option_no_admin"));
			} else {
				System.out.print(Taal.vertaal("menu_choose_option_admin"));
			}

			int keuze = 0;

			try {
				System.out.print(Taal.vertaal("scanner_input"));
				keuze = scan.nextInt();
			}

			catch (InputMismatchException e) {
				System.out.print(Taal.vertaal("exception_invalid_menu_choose_option"));
				scan.nextLine();
				continue;
			}

			// TO DO
			switch (keuze) {
			case 1:
				speelSpel();
				break;

			case 2:
				if (dc.isAdmin()) {
					System.out.print("Nog niet geï¿½mplementeerd");
				}

				else {
					afsluiten();

				}
				break;

			case 3:
				if (dc.isAdmin()) {

					// TO DO: wijzig een spel optie
					System.out.print("Nog niet geï¿½mplementeerd");

				}

				else {

					System.out.print(Taal.vertaal("exception_invalid_menu_choose_option"));

				}
				break;

			case 4:
				if (dc.isAdmin()) {
					afsluiten();

				}

				else {

					System.out.print(Taal.vertaal("exception_invalid_menu_choose_option"));

				}
				break;

			default:
				System.out.print(Taal.vertaal("exception_invalid_menu_choose_option"));
				continue;

			}

		}

	}	


	// UC3
	private void speelSpel() {

		startKiesSpel();
		bouwScherm();
		while(!dc.checkBordVoltooid()) {
			beweeg();
		}
		//clearScreen();
		
		afsluiten();

	}

	private void startKiesSpel() {

		scan = new Scanner(System.in);
		List<String> spelNamen = dc.getSpelNamen();
		int i = 1;
		int keuze = 0;
		
		System.out.printf(Taal.vertaal("title_spel_select"));

		do {
			
			System.out.print("\n");
			System.out.print(Taal.vertaal("text_spel_select"));

			for (String spel : spelNamen) {
				
				System.out.printf("\n" + i + ". " + spel);
				i++;

			}
			System.out.print(Taal.vertaal("scanner_input"));
			keuze = scan.nextInt();
			i = 1;

		} while (keuze <= 0 || keuze > spelNamen.size());

		System.out.print(Taal.vertaal("pleasure_message"));
		System.out.print("\n\n");

		dc.kiesSpel(spelNamen.get(keuze-1));

	}

	// UC4
	private void bouwScherm() {
		VeldInterface[][] velden = dc.geefVelden();

		// i = kolom, j = rij
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				VeldInterface veld = velden[i][j];
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
		
		System.out.print(Taal.vertaal("game_title_no_space"));
		updateScherm();

	}

	private void updateScherm() {
		
		System.out.print(Taal.vertaal("total_displacements") + dc.getAantalBewegingen() + "\n");

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
		checkVoltooid();


	}

	private void tekenScherm() {
		
		System.out.print("\n");

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
		
		System.out.print("\n");

	}
	

	private void checkVoltooid() {
		scan = new Scanner(System.in);
		if (dc.checkBordVoltooid()) {
			if (dc.checkSpelVoltooid()) {
				System.out.print("\n");
				System.out.println(Taal.vertaal("line_star"));
				System.out.println(Taal.vertaal("line_star"));
				System.out.println(Taal.vertaal("line_star"));
				System.out.print(Taal.vertaal("game_complete_title"));
				System.out.print("\n");
				System.out.println(Taal.vertaal("game_complete"));
				System.out.println(Taal.vertaal("line_star"));
				System.out.println(Taal.vertaal("line_star"));
				System.out.println(Taal.vertaal("line_star"));
				System.out.print("\n");
				hoofdMenu();
			} else {
				System.out.print("");	
				System.out.print("\n");
				System.out.print(Taal.vertaal("board_complete_title"));
				System.out.print("\n");
				int voltooideBorden = dc.getBordenVoltooid();
				int totaalBorden = dc.getBordenTotaal();
				int totaalVerplaatsingen = dc.getAantalBewegingen();
				String bordVoltooidContent = Taal.vertaal("board_complete") + Taal.vertaal("completed_boards") + voltooideBorden + "\r\n" + 
						Taal.vertaal("total_boards") + totaalBorden + "\n";		
				System.out.print(bordVoltooidContent);
				int keuze = 0;
				do {
					System.out.print("\n");
					System.out.print(Taal.vertaal("board_complete_menu"));
					System.out.print(Taal.vertaal("scanner_input"));
					keuze = scan.nextInt();
				} while (keuze <= 0 || keuze > 2);
				System.out.print("\n");
				if (keuze == 1) {
					// Bouw volgend bord
					bouwScherm();
				} else {
					back();
					// Voor de cancellation
				}
			}
		}
	}
	
	private void beweeg() {
		
		int keuze = 0;

		do {
			System.out.print("\n");
			System.out.print(Taal.vertaal("displacements_menu"));
			System.out.print(Taal.vertaal("scanner_input"));
			keuze = scan.nextInt();
			//System.out.print("\n");

		} while (keuze <= 0 || keuze > 6);
		
		try {			
			switch (keuze) {
			case 1:
				dc.beweeg(BeweegRichting.BOVEN);
				break;
			case 2:
				dc.beweeg(BeweegRichting.ONDER);
				break;
			case 3:
				dc.beweeg(BeweegRichting.LINKS);
				break;
			case 4:
				dc.beweeg(BeweegRichting.RECHTS);
				break;
			case 5:
				System.out.print("\n");
				back();
				break;
			case 6:
				System.out.print("\n");
				resetSpelbord();
				break;
			default:
				break;
			}
			if(keuze != 6)
				updateScherm();
		} catch (RuntimeException e) {
			System.out.print("\n");
			System.out.print(e.getMessage());
		}
	}

	private void back() {
		hoofdMenu();
	}
	
	private void resetSpelbord() {
		
		dc.resetBord();
		bouwScherm();
		
	}

	protected void afsluiten() {
		System.out.print("\n");
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.print("\n");
		System.out.print(Taal.vertaal("exit_message"));
		System.out.print("\n");
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.println(Taal.vertaal("line_star"));
		System.out.print("\n");
		System.exit(0);
	}

}
