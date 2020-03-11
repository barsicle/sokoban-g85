package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiController extends Application {
	private static Scene scene;
	protected DomeinController dc;
	
	public GuiController() {
		dc = new DomeinController();
	}
	
	@Override
	public void start(Stage stage) throws IOException {
		stage.setHeight(600);
		stage.setWidth(800);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaalSelectScherm.fxml"));
		fxmlLoader.setController(new TaalSelectSchermController(this));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
	}
	
	protected void switchScherm(Scherm scherm) {
		FXMLLoader fxmlLoader = null;
		switch(scherm) {
		case Hoofdscherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("Hoofdscherm.fxml"));
			fxmlLoader.setController(new HoofdSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SpelMenuScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelMenuScherm.fxml"));
			fxmlLoader.setController(new SpelMenuSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case LoginScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("LoginScherm.fxml"));
			fxmlLoader.setController(new LoginSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case RegistreerScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("RegistreerScherm.fxml"));
			fxmlLoader.setController(new RegistreerSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SpelScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelScherm.fxml"));
			fxmlLoader.setController(new SpelSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SpelSelectScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelSelectScherm.fxml"));
			fxmlLoader.setController(new SpelSelectSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case TaalSelectScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("TaalSelectScherm.fxml"));
			fxmlLoader.setController(new TaalSelectSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			
		}
	}

	protected void afsluiten() {
		System.exit(0);
	}

}
