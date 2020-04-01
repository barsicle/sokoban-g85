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
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnModify;
	
	public SpelMenuSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	public void initialize() {
		
		btnPlay.setText(Taal.vertaal("play"));
		btnExit.setText(Taal.vertaal("quit"));
		btnCreate.setText(Taal.vertaal("create_game"));
		btnModify.setText(Taal.vertaal("modify_game"));
		lblWelkom.setText(Taal.vertaal("sign_in_welcome") + " " + gc.dc.getGebruikersnaam());
		
		if (!gc.dc.isAdmin()) {
			btnCreate.setVisible(false);
			btnModify.setVisible(false);
		}
	}

	@FXML
	private void spelButtonPushed(){
		gc.switchScherm(Scherm.SpelSelectScherm);
	}
	
	@FXML
	private void spelCreateButtonPushed() {
		gc.switchScherm(Scherm.SpelCreatieScherm);
	}
	
	@FXML
	private void exitButtonPushed(){
		gc.afsluiten();
	}
	
}
