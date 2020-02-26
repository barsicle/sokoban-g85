package gui;

import domein.DomeinController;

public class StartUp {
	
	public static void main(String[] args){
		CliController cliController = new CliController(new DomeinController());
		cliController.startApplicatie();
	}


}
