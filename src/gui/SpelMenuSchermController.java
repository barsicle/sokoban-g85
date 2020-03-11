package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import vertalingen.Taal;

public class SpelMenuSchermController extends GridPane{

	private GuiController gc;
	
    @FXML
    private TextField txtWelkom;
	
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
		txtWelkom.setText(Taal.vertaal("sign_in_welcome")+ " "+ gc.dc.getGebruikersnaam());
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
