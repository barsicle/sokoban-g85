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

public class RegistreerSchermController extends GridPane{
	@FXML
	private Label lblNaam;
	@FXML
	private Label lblVoornaam;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private Label lblWachtwoordBevestiging;
	@FXML
	private Button btnRegistreer;
	@FXML
	private TextField txfNaam;
	@FXML
	private TextField txfVoornaam;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private PasswordField pwfWachtwoord;
	@FXML
	private PasswordField pwfWachtwoordBevestiging;
	@FXML
	private Button btnBack;
	
	private GuiController gc;
	
	public RegistreerSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	public void initialize() {
		
		lblNaam.setText(Taal.vertaal("name") + ": ");
		lblVoornaam.setText(Taal.vertaal("first_name")+ ": ");
		lblGebruikersnaam.setText(Taal.vertaal("username") + ": ");
		lblWachtwoord.setText(Taal.vertaal("password") + ": ");
		lblWachtwoordBevestiging.setText(Taal.vertaal("password_confirmation") + ": ");
		
		txfNaam.setPromptText(Taal.vertaal("register_choose_lastname"));
		txfVoornaam.setPromptText(Taal.vertaal("register_choose_firstname"));
		txfGebruikersnaam.setPromptText(Taal.vertaal("register_choose_user_name"));
		pwfWachtwoord.setPromptText(Taal.vertaal("register_choose_password"));
		pwfWachtwoordBevestiging.setPromptText(Taal.vertaal("register_confirm_password"));
		
		btnBack.setText(Taal.vertaal("cancel"));
		btnRegistreer.setText(Taal.vertaal("register"));
	}
	
	@FXML
	private void backButtonPushed() {
		gc.switchScherm(Scherm.Hoofdscherm);
	}
	
	@FXML
	private void registreerButtonPushed() {
		
		String naam = txfNaam.getText();
		String voornaam = txfVoornaam.getText();
		String gebruikersnaam = txfGebruikersnaam.getText();
		String wachtwoord = pwfWachtwoord.getText();
		String wachtwoordBevestiging = pwfWachtwoordBevestiging.getText();
		
		try {
			gc.dc.registreer(naam, voornaam, gebruikersnaam, wachtwoord, wachtwoordBevestiging);
			gc.switchScherm(Scherm.SpelMenuScherm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
	}
}