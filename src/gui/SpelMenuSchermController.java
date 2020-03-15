package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import vertalingen.Taal;

public class SpelMenuSchermController extends GridPane{

	private GuiController gc;
	
    @FXML
    private Label lblWelkom;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnExit;
	
	
	public SpelMenuSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	public void initialize() {
		
		btnPlay.setText(Taal.vertaal("play"));
		btnExit.setText(Taal.vertaal("quit"));
		lblWelkom.setText(Taal.vertaal("sign_in_welcome") + " " + gc.dc.getGebruikersnaam());
	}

	@FXML
	private void spelButtonPushed(){
		gc.switchScherm(Scherm.SpelSelectScherm);
	}
	
	@FXML
	private void exitButtonPushed(){
		gc.afsluiten();
	}
}
