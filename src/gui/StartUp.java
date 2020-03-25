package gui;

import javafx.application.Application;
import domein.DomeinController;

/**
 * StartUp-klasse van de applicatie.
 * Kan de applicatie opstarten met CLI of GUI.
 * @author g85
 */
public class StartUp {
	
	/**
	 * Main-methode van de StartUp-klasse. Boot de applicatie.
	 * 
	 * @param args De command line argumenten.
	 */
	public static void main(String[] args){
		Application.launch(GuiController.class);
		/*DomeinController dc = new DomeinController();
		CliController controller = new CliController(dc);
		controller.startApplicatie();*/
	}

}