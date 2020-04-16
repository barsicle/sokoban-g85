package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import vertalingen.Taal;

public class LoginSchermController{

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
	@FXML
	private Label lblMessage;
    @FXML
    private BorderPane rootPane;
	
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
		
		rootPane.setOnKeyReleased(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                login();
	            }
	        }
	    });
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
			gc.switchScherm(Scherm.SpelMenuScherm);
		}
		catch(RuntimeException e) {
			e.printStackTrace();
			lblMessage.setText(e.getMessage());
		}
		
	}
}
