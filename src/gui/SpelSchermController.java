package gui;

import static gui.ImageFactory.SoortImage.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import domein.BeweegRichting;
import domein.BordDimensies;
import domein.Moveable;
import domein.VeldInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import vertalingen.Taal;

public class SpelSchermController {
	private GuiController gc;

	@FXML
	private GridPane rootPane;

	@FXML
	private GridPane speelVeld;

	@FXML
	private GridPane beweegVeld;

	@FXML
	private TextArea title;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnReset;

	@FXML
	private Label lblNumberMoves;

	@FXML
	private Label lblMessage;

	public SpelSchermController(GuiController guiController) {
		gc = guiController;
	}

	private void bouwScherm() {
		VeldInterface[][] velden = gc.dc.geefVelden();
		// i = kolom, j = rij
		for (int i = 0; i < BordDimensies.getAantalKolommen(); i++) {
			for (int j = 0; j < BordDimensies.getAantalRijen(); j++) {
				HBox box = new HBox();
				Image image;
				VeldInterface veld = velden[i][j];
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
							image = ImageFactory.geefImage(VELD);
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
				speelVeld.add(box, i, j);

			}
		}
		updateScherm();
	}

	private void updateScherm() {
		// Zet aantal moves
		lblNumberMoves.setText(Taal.vertaal("number_of_moves") + gc.dc.getAantalBewegingen());
		// Label wissen
		lblMessage.setText("");
		// Eerst wissen, daarna opnieuw opbouwen
		beweegVeld.getChildren().clear();
		// Mannetje
		HBox box = new HBox();
		Image image = ImageFactory.geefImage(MANNETJE);
		Moveable mannetje = gc.dc.getMannetje();
		ImageView imageView = new ImageView(image);
		VeldInterface mannetjePositie = mannetje.getPositie();
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		box.getChildren().add(imageView);
		beweegVeld.add(box, mannetjePositie.getX(), mannetjePositie.getY());

		List<Moveable> kisten = gc.dc.getKisten();
		for (Moveable kist : kisten) {
			HBox kistBox = new HBox();
			Image kistImage = ImageFactory.geefImage(KIST);
			imageView = new ImageView(kistImage);
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			kistBox.getChildren().add(imageView);
			beweegVeld.add(kistBox, kist.getPositie().getX(), kist.getPositie().getY());
		}

		// Check of spel voltooid is
		checkVoltooid();
	}

	private void checkVoltooid() {
		if (gc.dc.checkBordVoltooid()) {
			if (gc.dc.checkSpelVoltooid()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(Taal.vertaal("game_complete_title"));
				alert.setHeaderText(null);
				alert.setContentText(Taal.vertaal("game_complete"));
				alert.showAndWait();
				gc.dc.resetGekozenSpel();
				gc.switchScherm(Scherm.SpelMenuScherm);
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				ButtonType buttonVolgendLevel = new ButtonType(Taal.vertaal("next_board"));
				ButtonType buttonOpgeven = new ButtonType(Taal.vertaal("quit"));
				alert.getButtonTypes().setAll(buttonVolgendLevel, buttonOpgeven);
				alert.setTitle(Taal.vertaal("board_complete_title"));
				alert.setHeaderText(null);
				int voltooideBorden = gc.dc.getBordenVoltooid();
				int totaalBorden = gc.dc.getBordenTotaal();
				String bordVoltooidContent = Taal.vertaal("board_complete") + Taal.vertaal("completed_boards")
						+ voltooideBorden + "\r\n" + Taal.vertaal("total_boards") + totaalBorden;
				alert.setContentText(bordVoltooidContent);

				Optional<ButtonType> keuze = alert.showAndWait();
				if (keuze.get() == buttonVolgendLevel) {
					// Bouw volgend bord
					bouwScherm();
				} else if (keuze.get() == buttonOpgeven) {
					back();
					// Voor de cancellation
				} else {
					back();
				}
			}
		}

	}

	@FXML
	private void resetBord() {
		gc.dc.resetBord();
		bouwScherm();
	}

	@FXML
	private void beweeg(KeyEvent event) {
		try {
			switch (event.getCode()) {
			case UP:
				gc.dc.beweeg(BeweegRichting.BOVEN);
				break;
			case DOWN:
				gc.dc.beweeg(BeweegRichting.ONDER);
				break;
			case LEFT:
				gc.dc.beweeg(BeweegRichting.LINKS);
				break;
			case RIGHT:
				gc.dc.beweeg(BeweegRichting.RECHTS);
				break;
			default:
				break;
			}

			updateScherm();
		} catch (RuntimeException e) {
			lblMessage.setText(e.getMessage());
		}

	}

	@FXML
	private void back() {
		gc.dc.resetGekozenSpel();
		gc.switchScherm(Scherm.SpelMenuScherm);
	}

	@FXML
	public void initialize() {
		btnReset.setText(Taal.vertaal("reset"));
		btnBack.setText(Taal.vertaal("back"));
		bouwScherm();
	}

}
