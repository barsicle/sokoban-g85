package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import domein.BeweegRichting;
import domein.Moveable;
import domein.Veld;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	public SpelSchermController(GuiController guiController) {
		gc = guiController;
	}

	private void bouwScherm() {
		Veld [][] velden = gc.dc.geefVelden();
		//i = kolom, j = rij
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				HBox box = new HBox();
				Image image;
				try {
					Veld veld = velden[i][j];
					if (Objects.equals(veld, null)) {
						image = new Image(new FileInputStream("bin/gui/assets/images/black.jpg"));
					} else {
						switch (veld.getVeldType()) {
						case MUUR:
							image = new Image(new FileInputStream("bin/gui/assets/images/wall.jpg"));
							break;
						case VELD:
							boolean doel = veld.isDoel();
							if(doel){
								image = new Image(new FileInputStream("bin/gui/assets/images/floor-goal.jpg"));
							} else{
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
					speelVeld.add(box,i,j);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		updateScherm();
	}

	private void updateScherm(){
		//Eerst wissen, daarna opnieuw opbouwen
		beweegVeld.getChildren().clear();
		try {
			//Mannetje
			HBox box = new HBox();
			Image image = new Image(new FileInputStream("bin/gui/assets/images/mario.jpg"));
			Moveable mannetje = gc.dc.getMannetje();
			ImageView imageView = new ImageView(image);
			Veld mannetjePositie = mannetje.getPositie();
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			box.getChildren().add(imageView);
			beweegVeld.add(box,mannetjePositie.getX(),mannetjePositie.getY());
			
			
			List<Moveable> kisten = gc.dc.getKisten();
			for (Moveable kist : kisten) {
				HBox kistBox = new HBox();
				Image kistImage = new Image(new FileInputStream("bin/gui/assets/images/chest.jpg"));
				imageView = new ImageView(kistImage);
				imageView.setFitHeight(50);
				imageView.setFitWidth(50);
				kistBox.getChildren().add(imageView);
				beweegVeld.add(kistBox,kist.getPositie().getX(),kist.getPositie().getY());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Check of spel voltooid is
		checkVoltooid();
	}

	private void checkVoltooid(){
		if(gc.dc.checkBordVoltooid()){
			if(gc.dc.checkSpelVoltooid()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Spel voltooid!");
				alert.setHeaderText(null);
				alert.setContentText(Taal.vertaal("game_complete"));

				alert.showAndWait();
				gc.switchScherm(Scherm.Hoofdscherm);
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				ButtonType buttonVolgendLevel = new ButtonType(Taal.vertaal("next_board"));
				ButtonType buttonOpgeven = new ButtonType(Taal.vertaal("quit"));
				alert.getButtonTypes().setAll(buttonVolgendLevel, buttonOpgeven);
				alert.setTitle("Bord voltooid");
				alert.setHeaderText(null);
				int voltooideBorden = gc.dc.getBordenVoltooid();
				int totaalBorden = gc.dc.getBordenTotaal();
				String bordVoltooidContent = Taal.vertaal("board_complete") + 
						Taal.vertaal("completed_boards")+voltooideBorden+"\r\n" + 
						Taal.vertaal("total_boards")+totaalBorden;
				alert.setContentText(bordVoltooidContent);

				Optional<ButtonType> keuze = alert.showAndWait();
				if (keuze.get() == buttonVolgendLevel){
					//Bouw volgend bord
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
	private void beweeg(KeyEvent event) {
		boolean succes = false;
		switch (event.getCode()) {
		case UP:
			succes = gc.dc.beweeg(BeweegRichting.BOVEN);
			break;
		case DOWN:
			succes = gc.dc.beweeg(BeweegRichting.ONDER);
			break;
		case LEFT:
			succes = gc.dc.beweeg(BeweegRichting.LINKS);
			break;
		case RIGHT:
			succes = gc.dc.beweeg(BeweegRichting.RECHTS);
			break;
		default:
			break;
		}
		
		if (succes) {
			updateScherm();
		}
		
	}
	
	@FXML
	private void back(){
		gc.switchScherm(Scherm.HoofdMenuScherm);
	}

	@FXML
	public void initialize() {
        btnBack.setText(Taal.vertaal("back"));
		bouwScherm();
	}

}
