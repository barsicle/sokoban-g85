package gui;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import domein.DomeinController;

public class StartUp {
	private static DomeinController dc;
	private static Scanner scan;

	public static void main(String[] args) {
		dc = new DomeinController();
		scan = new Scanner(System.in);
		System.out.println("Welkom bij Sokoban!");
		startAanmelden();
		menuKeuze();
		System.out.println("Einde programma");
	}

	private static void menuKeuze() {
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
			menuKeuze();
		}
		switch (keuze) {
		case 1:
			System.out.println("Nog niet geïmplementeerd");
			break;

		case 2:
			if (!dc.getSpeler().isAdminrechten()) {
				System.out.println("Geen geldige keuze");
				menuKeuze();
			}
			System.out.println("Nog niet geïmplementeerd");
			break;

		case 3:
			if (!dc.getSpeler().isAdminrechten()) {
				System.out.println("Geen geldige keuze");
				menuKeuze();
			}
			System.out.println("Nog niet geïmplementeerd");
			break;

		case 4:
			afsluiten();
			break;
		default:
			System.out.println("Geen geldige keuze");
			menuKeuze();

		}
	}

	private static void startAanmelden() {
		System.out.println("Wat is uw gebruikersnaam?");
		String gebruikersnaam = scan.nextLine();
		System.out.println("Wat is uw wachtwoord?");
		String wachtwoord = scan.nextLine();
		dc.meldAan(gebruikersnaam, wachtwoord);
		if (Objects.equals(dc.getSpeler(), null)) {
			System.out.println("Aanmeldpoging mislukt");
			startAanmelden();
		} else {
			System.out.printf("Welkom %s %n", dc.getSpeler().getGebruikersnaam());
		}
	}

	private static void afsluiten() {
		System.exit(0);
	}
}
