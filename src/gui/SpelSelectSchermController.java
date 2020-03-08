package gui;

import java.util.List;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import vertalingen.Taal;

public class SpelSelectSchermController {
	private GuiController gc;

	@FXML
	private ObservableList<String> spellen;
	
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<String> spelSelectList;

    @FXML
    private TextArea title;

    @FXML
    private Button btnBack;
	
	public SpelSelectSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	public void initialize() {
		btnBack.setText(Taal.vertaal("back"));
		title.setText(Taal.vertaal("title_spel_select"));
		List<String> spelNamen = gc.dc.getSpelNamen();
		
		spelSelectList.setItems(FXCollections.observableArrayList(spelNamen));
		
		spelSelectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	if (!Objects.equals(newValue, null)) {
		    		gc.dc.kiesSpel(newValue);
			        gc.switchScherm(Scherm.SpelScherm);
		    	}
		    }
		});
	}
	
	@FXML
	private void back(){
		gc.switchScherm(Scherm.HoofdMenuScherm);
	}
}
