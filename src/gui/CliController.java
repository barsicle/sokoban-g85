package gui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class CliController {
	private DomeinController dc;
	private Scanner scan;
	
	public CliController(DomeinController dc) {
		this.dc = dc;
		this.scan = new Scanner(System.in);
	}
	
	public void startApplicatie() {
		System.out.println("*****************");
		System.out.println("SOKOBAN - g85!");
		System.out.println("*****************");
		taalSelect();
		startAanmelden();
		hoofdMenu();
		System.out.println("Einde programma");
	}
	
	private void taalSelect() {
		// Blijf gaan zolang er geen taalbundle geselecteerd is
		while (Objects.equals(StartUp.bundle, null)) {
			System.out.println("Gelieve uw taal te selecteren (cijfer ingeven)");
			System.out.println("Veuillez choisir votre langue (entrez le numÈro)");
			System.out.println("Please choose your language (enter number)");
			System.out.println("1. Nederlands");
			System.out.println("2. FranÁais");
			System.out.println("3. English");
			int keuze = 0;
			try {
				keuze = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Gelieve een cijfer in te voeren");
				System.out.println("Veuillez saisir un nombre");
				System.out.println("Please enter a number");
				scan.nextLine();
				continue;
			}
			switch (keuze) {
			case 1:
				Locale.setDefault(new Locale("nl", "BE"));
				StartUp.bundle = ResourceBundle.getBundle("vertalingen/Vertaling");
				break;

			case 2:
				Locale.setDefault(new Locale("fr", "BE"));
				StartUp.bundle = ResourceBundle.getBundle("vertalingen/Vertaling");
				break;
				
			case 3:
				Locale.setDefault(new Locale("en", "BE"));
				StartUp.bundle = ResourceBundle.getBundle("vertalingen/Vertaling");
				break;
				
			default:
				System.out.println("Geen geldige keuze");
				System.out.println("Choix invalide");
				System.out.println("Invalid choice");
				continue;
			}
		}
	}
	
	private void hoofdMenu() {
		while (true) {
			System.out.println("Kies een optie: (cijfer ingeven)");
			System.out.println("1. Speel spel ");
			if (dc.getSpeler().isAdminrechten()) {
				System.out.println("2. Maak nieuw spel ");
				System.out.println("3. Wijzig een spel ");
			}
			System.out.println("4. Afsluiten");
			int keuze = 0;
			try {
				keuze = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Gelieve een cijfer in te voeren");
				scan.nextLine();
				continue;
			}
			switch (keuze) {
			case 1:
				System.out.println("Nog niet ge√Ømplementeerd");
				break;

			case 2:
				if (!dc.getSpeler().isAdminrechten()) {
					System.out.println("Geen geldige keuze");
					continue;
				}
				System.out.println("Nog niet ge√Ømplementeerd");
				break;

			case 3:
				if (!dc.getSpeler().isAdminrechten()) {
					System.out.println("Geen geldige keuze");
					continue;
				}
				System.out.println("Nog niet ge√Ømplementeerd");
				break;

			case 4:
				StartUp.afsluiten();
				break;
			default:
				System.out.println("Geen geldige keuze");
				continue;
			}
		}
	}
	
	private void startAanmelden() {
		while(true) {
			System.out.println("Wat is uw gebruikersnaam?");
			String gebruikersnaam = scan.nextLine();
			System.out.println("Wat is uw wachtwoord?");
			String wachtwoord = scan.nextLine();
			dc.meldAan(gebruikersnaam, wachtwoord);
			if (Objects.equals(dc.getSpeler(), null)) {
				System.out.println("Aanmeldpoging mislukt");
				continue;
			} else {
				System.out.printf("Welkom %s %n", dc.getSpeler().getGebruikersnaam());
				break;
			}
		}

	}

}
