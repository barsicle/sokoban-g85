package gui;

import java.util.Date;

import domein.Spel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SpelCreatieSchermController extends GridPane {
	
	private GuiController gc;
	

    @FXML
    private TextArea title;

    @FXML
    private Button btnBack;

    @FXML
    private TextField gameNameField;

    @FXML
    private Button btnAddBoard;

    @FXML
    private Button btnSaveGame;

    @FXML
    private Button btnCreateGame;

    @FXML
    private void addBoard() {
    	try {
    	//TO DO DELETE DUMMY
    	Date date = new Date();
    	String dummyName = "Dummy"+date.getTime();
    	gc.dc.creeerSpelbord(dummyName);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Done");
		alert.setHeaderText(null);
		alert.setContentText("Board added \nTotal number of boards: "+gc.dc.getBordenTotaal());
		alert.showAndWait();
		
    	} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			e.printStackTrace();
			alert.setContentText(e.getMessage());
			alert.showAndWait();
    	}
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
    		alert.setTitle("Done");
    		alert.setHeaderText(null);
    		alert.setContentText("Game created");
    		gameNameField.setDisable(true);
    		btnCreateGame.setDisable(true);
    		alert.showAndWait();
    		
    	
    	} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			e.printStackTrace();
			alert.setContentText(e.getMessage());
			alert.showAndWait();
    	}
    }
    
    @FXML
    private void saveGame() {
    	try {
    	Spel spel = gc.dc.registreerSpel();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Done");
		alert.setHeaderText(null);
		alert.setContentText("Game saved: "+spel.getSpelNaam()+"\nNumber of boards: "+spel.getBordenTotaal());
		alert.showAndWait();
    	gc.switchScherm(Scherm.SpelMenuScherm);
    	} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			e.printStackTrace();
			alert.setContentText(e.getMessage());
			alert.showAndWait();
    	}
    }
    
	public SpelCreatieSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	public void initialize() {
	}

}
