package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import vertalingen.Taal;

public class HoofdSchermController extends GridPane{
	
	private GuiController gc;
		
	@FXML
	private ImageView imgSega;
	
	@FXML
	private Button btnTaal;
	
	@FXML
	private Button btnRegistreer;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private Button btnExit;
	
	@FXML
	private MenuBar mnb;
	
	public HoofdSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	public void initialize() {
		btnRegistreer.setText(Taal.vertaal("register"));
		btnTaal.setText(Taal.vertaal("language_select_gui"));
		btnLogin.setText(Taal.vertaal("login"));
		btnExit.setText(Taal.vertaal("exit"));
	}
	
	@FXML
	private void registreerButtonPushed(){
		gc.switchScherm(Scherm.RegistreerScherm);
	}

	@FXML
	private void loginButtonPushed(){
		gc.switchScherm(Scherm.LoginScherm);
	}
	
	@FXML
	private void taalSelectButtonPushed(){
		gc.switchScherm(Scherm.TaalSelectScherm);
	}
	
	@FXML
	private void exitButtonPushed(){
		gc.afsluiten();
	}
}
