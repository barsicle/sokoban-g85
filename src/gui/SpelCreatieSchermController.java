package gui;

import java.util.Date;
import java.util.Optional;

import domein.Spel;
import domein.SpelInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import vertalingen.Taal;

public class SpelCreatieSchermController extends GridPane {
	
	private GuiController gc;
	

    @FXML
    private TextArea title;

    @FXML
    private Button btnBack;
    
    @FXML
    private Label lblGameName;
    
    @FXML
    private Label lblBordNaam;

    @FXML
    private TextField gameNameField;

    @FXML
    private Button btnSaveGame;
    
    @FXML
    private Button btnAddBoard;

    @FXML
    private Button btnCreateGame;
    
    @FXML
    private Label lblMessage;

    @FXML
    private void addBoard() {
    	gc.switchScherm(Scherm.SpelbordCreatieScherm);
    }

    @FXML
    private void back() {
    	gc.switchScherm(Scherm.SpelMenuScherm);
    }


    @FXML
    private void createGame() {
    	try {
    		gc.dc.creeerSpel(gameNameField.getText());
    		
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    		alert.setTitle(Taal.vertaal("done"));
    		alert.setHeaderText(null);
    		alert.setContentText(Taal.vertaal("game_created"));
    		gameNameField.setDisable(true);
    		btnCreateGame.setDisable(true);
    		alert.showAndWait();
    		
    		btnAddBoard.setDisable(false);
    	
    	} catch (Exception e) {
    		lblMessage.setText(e.getMessage());
    	}
    }
    
    @FXML
    private void saveGame() {
    	try {
    	SpelInterface spel = gc.dc.registreerSpel();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(Taal.vertaal("done"));
		alert.setHeaderText(null);
		alert.setContentText(Taal.vertaal("game_saved")+spel.getSpelNaam()+"\n"+Taal.vertaal("number_of_boards") +spel.getBordenTotaal());
		alert.showAndWait();
    	gc.switchScherm(Scherm.SpelMenuScherm);
    	} catch (Exception e) {
    		lblMessage.setText(e.getMessage());
    	}
    }
    
	public SpelCreatieSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	public void initialize() {
		btnBack.setText(Taal.vertaal("back"));
		btnCreateGame.setText(Taal.vertaal("create_game"));
		btnSaveGame.setText(Taal.vertaal("save_game"));
		btnSaveGame.setDisable(true);
		btnAddBoard.setText(Taal.vertaal("add_board"));
		btnAddBoard.setDisable(true);
		lblGameName.setText(Taal.vertaal("enter_game_name"));
		
		
		//Haal saved state op en enable de features op basis hier van
		SpelInterface spel = gc.dc.getSpel();
		if(spel != null) {
			btnAddBoard.setDisable(false);
    		gameNameField.setDisable(true);
    		btnCreateGame.setDisable(true);
    		if(spel.getBordenTotaal() > 0) {
    			btnSaveGame.setDisable(false);
    		}
		}

	}

	/*private void resetMessageLabel() {
		lblMessage.setText("");
	}*/
}
