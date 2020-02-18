package gui;

import domein.DomeinController;

public class StartUp {
	private static DomeinController dc;

	public static void main(String[] args) {
		dc = new DomeinController();
		System.out.println("Welkom bij Sokoban!");
		System.out.println("Wat is uw gebruikersnaam?");
	}
}
