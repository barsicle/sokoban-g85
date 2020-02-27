package gui;

import java.util.InputMismatchException;
// import java.util.Objects;
import java.util.Scanner;

import domein.DomeinController;
import vertalingen.Taal;
import vertalingen.Talen;
/**
 * Stelt de command line controller voor.
 * @author g85
 * */
public class CliController {
	private DomeinController dc;
	private Scanner scan;
	private static final String SOKOBAN_ASCII_ART = 
			"   _____       _         _                 \r\n" + 
			"  / ____|     | |       | |                \r\n" + 
			" | (___   ___ | | _____ | |__   __ _ _ __  \r\n" + 
			"  \\___ \\ / _ \\| |/ / _ \\| '_ \\ / _` | '_ \\ \r\n" + 
			"  ____) | (_) |   < (_) | |_) | (_| | | | |\r\n" + 
			" |_____/ \\___/|_|\\_\\___/|_.__/ \\__,_|_| |_|\r\n" + 
			"                                           \r\n" + 
			"                                           ";
	private static final String LIJN_SEPARATOR_STER = "******************************************************************";
	
	/**
	 * Creëert een instantie van de command line controller met een domeincontroller object.
	 * 
	 * @param dc Het meegegeven domeincontroller object.
	 * */
	public CliController(DomeinController dc) {
		this.dc = dc;
		scan = new Scanner(System.in);
	}
	
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
	
	private void taalSelect() {
		// Blijf gaan zolang er geen taal geselecteerd is
		System.out.println("Gelieve uw taal te selecteren (cijfer ingeven)");
		System.out.println("Veuillez choisir votre langue (entrez le numéro)");
		System.out.println("Please choose your language (enter number)");
		
		while (Taal.taalIngesteld() == false) {
			System.out.println("1. Nederlands");
			System.out.println("2. Français");
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
	
	private void loginMenu() {
		
		// Reïnitialiseren scanner
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
						dc.afsluiten();
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
	
	private void hoofdMenu() {
		// Reïnitialiseren scanner
		scan = new Scanner(System.in);
		while (true) {
			if(dc.getSpeler().isAdminrechten() == false) {
				
				System.out.println(Taal.vertaal("menu_choose_option_no_admin"));
			}
			else {
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
					System.out.println("Nog niet geïmplementeerd");
					break;

				case 2:
					if(dc.getSpeler().isAdminrechten()) {
						
						// TO DO: maak een nieuw spel optie
						System.out.println("Nog niet geïmplementeerd");
						
					}
					
					else {
						
						dc.afsluiten();
						
					}					
					break;

				case 3:
					if(dc.getSpeler().isAdminrechten()) {
						
						// TO DO: wijzig een spel optie
						System.out.println("Nog niet geïmplementeerd");
						
					}
					
					else {
						
						System.out.println(Taal.vertaal("exception_invalid_menu_choose_option"));
						
					}
					break;

				case 4:
					if(dc.getSpeler().isAdminrechten()) {
						
						dc.afsluiten();
						
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
	
	private void startAanmelden() {
		
		// Reïnitialiseren scanner
		scan = new Scanner(System.in);
		
		while(true) {
			
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
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s %n", dc.getSpeler().getGebruikersnaam());
			System.out.println(LIJN_SEPARATOR_STER);
			
			hoofdMenu();
			break;
			
		}

	}
	
	private void startRegistreer() {
		
		// Reïnitialiseren scanner
		scan = new Scanner(System.in);
		
		while(true) {
			
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
			System.out.printf(Taal.vertaal("sign_in_welcome") + " %s %n", dc.getSpeler().getGebruikersnaam());
			System.out.println(LIJN_SEPARATOR_STER);
			
			hoofdMenu();
			break;
			
		}

	}

}
