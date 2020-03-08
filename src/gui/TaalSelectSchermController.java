package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import vertalingen.Taal;
import vertalingen.Talen;

public class TaalSelectSchermController {
	private GuiController gc;

	@FXML
	private ComboBox<String> cboKeuze;
	private ObservableList<String> talen;
	
	@FXML
	private Button btnBack;
	
	public TaalSelectSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	public void initialize() {
		talen = FXCollections.observableArrayList("Nederlands", "Français", "English");
		cboKeuze.setItems(talen);
		
		if (Taal.taalIngesteld()) 
			cboKeuze.setPromptText(Taal.vertaal("language_select_gui"));
		else
			cboKeuze.setPromptText("Choose your language");
		
		cboKeuze.valueProperty()
        .addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	switch(newValue) {
            		case "Nederlands" : 
            			Taal.setTaal(Talen.nl);
            			break;
            		case "English" : 
            			Taal.setTaal(Talen.en);
            			break;
            		case "Français" : 
            			Taal.setTaal(Talen.fr);
            			break;
            		}
            }
        });
	}
	
	@FXML
	private void back() {
		gc.switchScherm(Scherm.Hoofdscherm);
	}
	
	@FXML
	private void selecteerTaal() {
            	if (Taal.taalIngesteld()) {
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle(Taal.vertaal("language_set"));
        			alert.setHeaderText(null);
        			alert.setContentText(String.format("%s %s.",Taal.vertaal("language_set_info") , cboKeuze.getSelectionModel().getSelectedItem()));
        			alert.showAndWait();
        			
        	        back();
        		}
            }   
	}
