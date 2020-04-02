package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

import domein.Actie;
import domein.BordDimensies;
import domein.MoveableType;
import domein.VeldInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
	
	private Image IMAGE_WALL;
	private Image IMAGE_VELD;
	private Image IMAGE_DOEL;
	private Image IMAGE_KIST;
	private Image IMAGE_MANNETJE;
	private Image IMAGE_LEEG;
	private Image IMAGE_ERASER;

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
			if (Objects.equals(veld, null)) {
				image = IMAGE_LEEG;
			} else {
				switch (veld.getVeldType()) {
				case MUUR:
					image = IMAGE_WALL;
					break;
				case VELD:
					boolean doel = veld.isDoel();
					if (doel) {
						image = IMAGE_DOEL;
					} else {
						if(!Objects.equals(veld.getMoveable(), null)) {
							if(veld.getMoveable().getType().equals(MoveableType.KIST)) {
								image = IMAGE_KIST;
								break;
							} else if(veld.getMoveable().getType().equals(MoveableType.MANNETJE)) {
								image = IMAGE_MANNETJE;
								break;
							}
						} else {
							image = IMAGE_VELD;
							break;
						}
					}

					break;
				default:
					image = IMAGE_LEEG;
					break;
				}
			}

			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			box.getChildren().add(imageView);
			speelVeld.add(box, x, y);
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
		gc.dc.resetGekozenSpel();
		btnBack.setText(Taal.vertaal("back"));
		ObservableList<Actie> actionItems = FXCollections.observableArrayList(Arrays.asList(Actie.values()));
		//listViewActions = new ListView<Action>;
		listViewActions.setItems(actionItems);
		loadImages();
		listViewActions.setCellFactory(param -> new ListCell<Actie>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Actie actie, boolean empty) {
                super.updateItem(actie, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	Image image = null;
            		switch(actie) { 
        			case PLAATSMUUR:
        				image = IMAGE_WALL;
        			break;
        			case PLAATSVELD: 
        				image = IMAGE_VELD;
        			break;
        			case PLAATSMANNETJE:
        				image = IMAGE_MANNETJE;
            			break;
        			case PLAATSKIST:
        				image = IMAGE_KIST;
            			break;
        			case PLAATSDOEL:
        				image = IMAGE_DOEL;
            			break;
        			case CLEAR: 
        				image = IMAGE_ERASER;
            			break;
        		}
            		imageView.setImage(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setText(actie.toString());
                    setGraphic(imageView);
                }
            }
        });
		
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
	   
	   private void loadImages() {
		   try {
			IMAGE_WALL = new Image(new FileInputStream("bin/gui/assets/images/wall.jpg"));
			IMAGE_VELD = new Image(new FileInputStream("bin/gui/assets/images/floor.jpg"));
			IMAGE_KIST = new Image(new FileInputStream("bin/gui/assets/images/chest.jpg"));
			IMAGE_DOEL = new Image(new FileInputStream("bin/gui/assets/images/floor-goal.jpg"));
			IMAGE_MANNETJE = new Image(new FileInputStream("bin/gui/assets/images/mario.jpg"));
			IMAGE_LEEG = new Image(new FileInputStream("bin/gui/assets/images/black.jpg"));
			IMAGE_ERASER = new Image(new FileInputStream("bin/gui/assets/images/eraser.jpg")); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }

}