package gui;

import java.util.ResourceBundle;

import domein.DomeinController;

public class StartUp {
	public static ResourceBundle bundle;
	
	public static void main(String[] args) {
		CliController cliController = new CliController(new DomeinController());
		cliController.startApplicatie();
	}

	public static void afsluiten() {
		System.exit(0);
	}
}
