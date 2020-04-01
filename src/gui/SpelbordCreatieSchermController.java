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
import domein.Kist;
import domein.Mannetje;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import vertalingen.Taal;
import vertalingen.Talen;

public class SpelbordCreatieSchermController {
	private GuiController gc;

	@FXML
	private GridPane rootPane;

	@FXML
	private GridPane speelVeld;

	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnReset;
	
	@FXML
	private Button btnCreateBoard;
	
	@FXML
	private Button btnAddBord;
	
	@FXML
	private Label lblBordNaam;
	
	@FXML
	private TextField txfBordNaam;
	
	@FXML
	private Label lblTitle;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private ComboBox<Actie> cboActie;
	
	@FXML
	private ListView<Actie> listViewActions;
	
	private Actie geselecteerdeActie = null;

	public SpelbordCreatieSchermController(GuiController guiController) {
		gc = guiController;
	}
	
	@FXML
	private void creeerLeegBord() {
		gc.dc.creeerSpelbord(txfBordNaam.getText());
		listViewActions.setDisable(false);
		speelVeld.setDisable(false);
		bouwScherm();
	}
	
	@FXML
	private void addBord() {
		try {
			gc.dc.voegSpelbordToe(gc.dc.getHuidigSpelbord());
			listViewActions.setDisable(true);
			speelVeld.setDisable(true);
			btnAddBord.setDisable(true);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Ongeldige bewerking");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());

			alert.showAndWait();
		}
		
	}
	
	private void bouwScherm() {
		VeldInterface[][] velden = gc.dc.geefVelden();
		// i = kolom, j = rij
		for (int i = 0; i < BordDimensies.getAantalRijen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalKolommen(); j++) {
				Tile box = new Tile(i, j);
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
		//setClickable();
		//updateScherm();
	}

	//TO DO REFACTOR - te groot, imagefactory method?
	private void updateTile(int x, int y) {
		VeldInterface veld = gc.dc.geefVelden()[x][y];
		Tile box = new Tile(veld.getX(), veld.getY());
		Image image = null;
		try {
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
						if(!Objects.equals(veld.getMoveable(), null)) {
							if(veld.getMoveable() instanceof Kist) {
								new Image(new FileInputStream("bin/gui/assets/images/chest.jpg"));
							} else if(veld.getMoveable() instanceof Mannetje) {
								new Image(new FileInputStream("bin/gui/assets/images/mario.jpg"));
							}
						} else {
							image = new Image(new FileInputStream("bin/gui/assets/images/floor.jpg"));
						}
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
			speelVeld.add(box, veld.getX(), veld.getY());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		ObservableList<Actie> actionItems = FXCollections.observableArrayList(Arrays.asList(Actie.values()));
		//listViewActions = new ListView<Action>;
		listViewActions.setItems(actionItems);
		
		listViewActions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Actie>() {
				@Override
				public void changed(ObservableValue<? extends Actie> observable, Actie oldValue, Actie newValue) {
					geselecteerdeActie = newValue;
				}
			});
		
		//Onzichtbaar tot leeg bord aangemaakt is!
		listViewActions.setDisable(true);
		speelVeld.setDisable(true);
		//btnAddBord.setDisable(true);
		
	}
	
	   class Tile extends Pane {
	        private int positionX;
	        private int positionY;

	        public Tile(int x, int y) {
	            positionX = x;
	            positionY = y;
	            setOnMouseClicked(e -> {
	    			if (geselecteerdeActie == null) {
	    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    				alert.setTitle("Ongeldige bewerking");
	    				alert.setHeaderText(null);
	    				alert.setContentText("Gelieve eerst een actie te selecteren in de toolbox");

	    				alert.showAndWait();
	    			} else {
	    				try {
							gc.dc.creeerVeld(geselecteerdeActie, x, y);
							updateTile(x, y);
						} catch (Exception e1) {
		    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    				alert.setTitle("Ongeldige bewerking");
		    				alert.setHeaderText(null);
		    				alert.setContentText(e1.getMessage());

		    				alert.showAndWait();
						}
	    				
	    			}
	    		});
	        }
	    }

}