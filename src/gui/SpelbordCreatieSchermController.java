package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import domein.Actie;
import domein.BeweegRichting;
import domein.BordDimensies;
import domein.DomeinController;
import domein.VeldInterface;
import domein.Moveable;
import domein.Veld;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import vertalingen.Taal;
import vertalingen.Talen;

public class SpelbordCreatieSchermController {
	private GuiController gc;

	@FXML
	private GridPane rootPane;

	@FXML
	private GridPane speelVeld;

	@FXML
	private GridPane beweegVeld;

	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnReset;
	
	@FXML
	private Button btnCreateBoard;
	
	@FXML
	private Button addBord;
	
	@FXML
	private Label lblBordNaam;
	
	@FXML
	private TextField txfBordNaam;
	
	@FXML
	private Label lblTitle;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private ComboBox<Integer> cboX;
	private ObservableList<Integer> xList;
	
	@FXML
	private ComboBox<Integer> cboY;
	private ObservableList<Integer> yList;
	
	@FXML
	private ComboBox<Actie> cboActie;
	private ObservableList<Actie> acties;

	public SpelbordCreatieSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	private void creeerLeegBord() {
		gc.dc.creeerSpelbord(txfBordNaam.getText());
		cboX.setDisable(false);
		cboY.setDisable(false);
		cboActie.setDisable(false);
	}
	
	@FXML
	private void addBord() {
		gc.dc.voegSpelbordToe(gc.dc.getHuidigSpelbord());
	}
	
	private void bouwScherm() {
		VeldInterface[][] velden = gc.dc.geefVelden();
		// i = kolom, j = rij
		for (int i = 0; i < BordDimensies.getAantalRijen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalKolommen(); j++) {
				HBox box = new HBox();
				Image image;
				try {
					VeldInterface veld = velden[i][j];
					if (Objects.equals(veld, null)) {
						image = new Image(new FileInputStream("bin/gui/assets/images/black.jpg"));
					} else {
						switch (veld.getVeldType()) {
						case MUUR:
							image = new Image(new FileInputStream("bin/gui/assets/images/wall.jpg"));
							break;
						case VELD:
							boolean doel = veld.isDoel();
							if (doel) {
								image = new Image(new FileInputStream("bin/gui/assets/images/floor-goal.jpg"));
							} else {
								image = new Image(new FileInputStream("bin/gui/assets/images/floor.jpg"));
							}

							break;
						default:
							image = new Image(new FileInputStream("bin/gui/assets/images/black.jpg"));
							break;
						}
					}

					ImageView imageView = new ImageView(image);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					box.getChildren().add(imageView);
					speelVeld.add(box, i, j);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		updateScherm();
	}

	private void updateScherm() {
	}

	@FXML
	private void resetBord() {
		gc.dc.resetBord();
		bouwScherm();
	}

	@FXML
	private void back() {
		gc.switchScherm(Scherm.SpelCreatieScherm);
	}

	@FXML
	public void initialize() {
		btnBack.setText(Taal.vertaal("back"));
		
		xList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9);
		cboX.setItems(xList);
		cboX.setPromptText("x");
		cboX.setDisable(true);
		
		yList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9);
		cboY.setItems(yList);
		cboY.setPromptText("y");
		cboY.setDisable(true);
		
		acties = FXCollections.observableArrayList(Arrays.asList(Actie.values()));
		cboActie.setItems(acties);
		cboActie.setDisable(true);
		
		cboActie.setPromptText("Choose your action");
			cboActie.valueProperty()
			.addListener(new ChangeListener<Actie>() {
				@Override
				public void changed(ObservableValue<? extends Actie> observable, Actie oldValue, Actie newValue) {
					switch(newValue) {
						case PLAATSMUUR: gc.dc.creeerVeld(Actie.PLAATSMUUR, cboX.getValue(), cboY.getValue());
            			break;
						case PLAATSVELD: gc.dc.creeerVeld(Actie.PLAATSVELD, cboX.getValue(), cboY.getValue());
            			break;
            			case PLAATSMANNETJE: gc.dc.creeerVeld(Actie.PLAATSMANNETJE, cboX.getValue(), cboY.getValue());
            			break;
            			case PLAATSKIST: gc.dc.creeerVeld(Actie.PLAATSKIST, cboX.getValue(), cboY.getValue());
            			break;
            			case PLAATSDOEL: gc.dc.creeerVeld(Actie.PLAATSDOEL, cboX.getValue(), cboY.getValue());
            			break;
            			case CLEAR: gc.dc.creeerVeld(Actie.CLEAR, cboX.getValue(), cboY.getValue());
            			break;
            			}
				}
			});
	}

}