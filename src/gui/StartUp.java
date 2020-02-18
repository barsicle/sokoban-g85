package gui;

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
		System.out.println("Einde programma");
	}

	private static void startAanmelden() {
		System.out.println("Wat is uw gebruikersnaam?");
		String gebruikersnaam = scan.nextLine();
		System.out.println("Wat is uw wachtwoord?");
		String wachtwoord = scan.nextLine();
		dc.meldAan(gebruikersnaam, wachtwoord);
		if (Objects.equals(dc.getSpeler(), null)){
			System.out.println("Aanmeldpoging mislukt");
			startAanmelden();
		} else {
			System.out.printf("Welkom %s %n", dc.getSpeler().getGebruikersnaam());
		}
	}
}
