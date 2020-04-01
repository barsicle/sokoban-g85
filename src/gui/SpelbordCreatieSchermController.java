package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

import domein.Actie;
import domein.BordDimensies;
import domein.Kist;
import domein.Mannetje;
import domein.VeldInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import vertalingen.Taal;

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
	private Button btnRegistreerBord;
	
	@FXML
	private Label lblBordNaam;
	
	@FXML
	private TextField txfBordNaam;
	
	@FXML
	private Label lblTitle;
	
	@FXML
	private Label lblMessage;
	
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
		btnRegistreerBord.setDisable(false);
		btnReset.setDisable(false);
		bouwLeegSpelbord();
	}
	
	@FXML
	private void registreerBord() {
		try {
			gc.dc.voegSpelbordToe(gc.dc.getHuidigSpelbord());
			listViewActions.setDisable(true);
			speelVeld.setDisable(true);
			btnRegistreerBord.setDisable(true);
			back();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Ongeldige bewerking");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());

			alert.showAndWait();
		}
		
	}
	
	private void bouwLeegSpelbord() {
		VeldInterface[][] velden = gc.dc.geefVelden();
		// i = kolom, j = rij
		for (int i = 0; i < BordDimensies.getAantalRijen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalKolommen(); j++) {
				Tile box = new Tile(i, j);
				Image image;
				try {
					image = new Image(new FileInputStream("bin/gui/assets/images/black.jpg"));

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
	}

	//TO DO REFACTOR - te groot, imagefactory method?
	private void updateTile(int x, int y) {
		VeldInterface veld = gc.dc.getVeld(x, y);
		Tile box = new Tile(x, y);
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
								image = new Image(new FileInputStream("bin/gui/assets/images/chest.jpg"));
								break;
							} else if(veld.getMoveable() instanceof Mannetje) {
								image = new Image(new FileInputStream("bin/gui/assets/images/mario.jpg"));
								break;
							}
						} else {
							image = new Image(new FileInputStream("bin/gui/assets/images/floor.jpg"));
							break;
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
			speelVeld.add(box, x, y);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void resetBord() {
		gc.dc.resetBordCreatie();
		bouwLeegSpelbord();
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
		btnRegistreerBord.setDisable(true);
		btnReset.setDisable(true);
		
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