package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import vertalingen.Taal;

public class LoginSchermController extends GridPane{

	@FXML
	private Label lblGebruikersNaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private PasswordField pwfWachtwoord;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnBack;
	
	private GuiController gc;
	
	public LoginSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	public void initialize() {
		lblGebruikersNaam.setText(Taal.vertaal("username") + ": ");
		lblWachtwoord.setText(Taal.vertaal("password") + ": ");
		
		txfGebruikersnaam.setPromptText(Taal.vertaal("sign_in_username"));
		pwfWachtwoord.setPromptText(Taal.vertaal("sign_in_password"));
		
		btnBack.setText(Taal.vertaal("cancel"));
		btnLogin.setText(Taal.vertaal("login"));
	}
	
	@FXML
	private void back() {
		gc.switchScherm(Scherm.Hoofdscherm);
	}
	
	@FXML
	private void login() {
		String gebruikersnaam = txfGebruikersnaam.getText();
		String wachtwoord = pwfWachtwoord.getText();
		
		try {
			gc.dc.meldAan(gebruikersnaam, wachtwoord);
			gc.switchScherm(Scherm.HoofdMenuScherm);
		}
		catch(RuntimeException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
	}
}
