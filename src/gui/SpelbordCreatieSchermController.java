package gui;

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
import static gui.ImageFactory.SoortImage.*;
import static gui.AlertFactory.*;

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
	private Label lblToolkit;

	@FXML
	private ListView<Actie> listViewActions;

	private Actie geselecteerdeActie = null;

	public SpelbordCreatieSchermController(GuiController guiController) {
		gc = guiController;
	}

	@FXML
	private void creeerLeegBord() {
		try {
			gc.dc.creeerSpelbord(txfBordNaam.getText());
			listViewActions.setDisable(false);
			speelVeld.setDisable(false);
			btnRegistreerBord.setDisable(false);
			btnReset.setDisable(false);
			txfBordNaam.setDisable(true);
			btnCreateBoard.setDisable(true);
			bouwLeegSpelbord();
		} catch (Exception e) {
			Alert alert = AlertFactory.createAlert(Alert.AlertType.ERROR, Taal.vertaal("exception_invalid_operation"), e.getMessage());
			alert.showAndWait();
		}
		
	}

	@FXML
	private void registreerBord() {
		try {
			gc.dc.voegSpelbordToe();
			listViewActions.setDisable(true);
			speelVeld.setDisable(true);
			btnRegistreerBord.setDisable(true);
			
			Alert alert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, Taal.vertaal("done"), Taal.vertaal("board_saved"));
			alert.showAndWait();
			back();
		} catch (Exception e) {
			Alert alert = AlertFactory.createAlert(Alert.AlertType.ERROR, Taal.vertaal("exception_invalid_operation"), e.getMessage());
			alert.showAndWait();
		}

	}

	private void bouwLeegSpelbord() {
		//i = kolom, j = rij
		for (int i = 0; i < BordDimensies.getAantalKolommen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalRijen(); j++) {
				Tile box = new Tile(i, j);
				Image image = ImageFactory.geefImage(LEEG);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(50);
				imageView.setFitWidth(50);
				box.getChildren().add(imageView);
				speelVeld.add(box, i, j);


			}
		}
	}

	private void updateTile(int x, int y) {
		VeldInterface veld = gc.dc.getVeld(x, y);
		Tile box = new Tile(x, y);
		Image image = null;
		if (Objects.equals(veld, null)) {
			image = ImageFactory.geefImage(LEEG);
		} else {
			switch (veld.getVeldType()) {
			case MUUR:
				image = ImageFactory.geefImage(WALL);
				break;
			case VELD:
				boolean doel = veld.isDoel();
				if (doel) {
					image = ImageFactory.geefImage(DOEL);
				} else {
					if (!Objects.equals(veld.getMoveable(), null)) {
						if (veld.getMoveable().getType().equals(MoveableType.KIST)) {
							image = ImageFactory.geefImage(KIST);
							break;
						} else if (veld.getMoveable().getType().equals(MoveableType.MANNETJE)) {
							image = ImageFactory.geefImage(MANNETJE);
							break;
						}
					} else {
						image = ImageFactory.geefImage(VELD);
						break;
					}
				}

				break;
			default:
				image = ImageFactory.geefImage(LEEG);
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
		btnBack.setText(Taal.vertaal("back"));
		lblToolkit.setText(Taal.vertaal("toolkit"));
		lblTitle.setText(Taal.vertaal("create_title"));
		lblBordNaam.setText(Taal.vertaal("enter_board_name"));
		btnCreateBoard.setText(Taal.vertaal("create_empty_board"));
		btnReset.setText(Taal.vertaal("reset"));
		btnRegistreerBord.setText(Taal.vertaal("register_board"));
		
		ObservableList<Actie> actionItems = FXCollections.observableArrayList(Arrays.asList(Actie.values()));
		listViewActions.setItems(actionItems);
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
					switch (actie) {
					case PLAATSMUUR:
						setText(" " + Taal.vertaal("place_wall"));
						image = ImageFactory.geefImage(WALL);
						break;
					case PLAATSVELD:
						setText(" " + Taal.vertaal("place_field"));
						image = ImageFactory.geefImage(VELD);
						break;
					case PLAATSMANNETJE:
						setText(" " + Taal.vertaal("place_worker"));
						image = ImageFactory.geefImage(MANNETJE);
						break;
					case PLAATSKIST:
						setText(" " + Taal.vertaal("place_box"));
						image = ImageFactory.geefImage(KIST);
						break;
					case PLAATSDOEL:
						setText(" " + Taal.vertaal("place_goal"));
						image = ImageFactory.geefImage(DOEL);
						break;
					case CLEAR:
						setText(" " + Taal.vertaal("clear"));
						image = ImageFactory.geefImage(ERASER);
						break;
					}
					imageView.setImage(image);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
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

		// Onzichtbaar tot leeg bord aangemaakt is!
		listViewActions.setDisable(true);
		speelVeld.setDisable(true);
		btnRegistreerBord.setDisable(true);
		btnReset.setDisable(true);

	}

	class Tile extends Pane {
		public Tile(int x, int y) {
			setOnMouseClicked(e -> {
				if (geselecteerdeActie == null) {
					Alert alert = AlertFactory.createAlert(Alert.AlertType.ERROR, Taal.vertaal("exception_invalid_operation"), Taal.vertaal("exception_select_operation"));
					alert.showAndWait();
				} else {
					try {
						gc.dc.creeerVeld(geselecteerdeActie, x, y);
						updateTile(x, y);
					} catch (Exception e1) {
						Alert alert = AlertFactory.createAlert(Alert.AlertType.ERROR, Taal.vertaal("exception_invalid_operation"), e1.getMessage());
						alert.showAndWait();
					}

				}
			});
		}
	}



}