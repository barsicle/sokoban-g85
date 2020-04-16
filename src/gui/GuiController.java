package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * De controller-klasse van de GUI. Start de applicatie en wisselt tussen de
 * verschillende schermen. Laad ook de nodige images.
 * 
 * @author g85
 */
public class GuiController extends Application {
	private static Scene scene;
	protected DomeinController dc;
	protected Image IMAGE_WALL;
	protected Image IMAGE_VELD;
	protected Image IMAGE_DOEL;
	protected Image IMAGE_KIST;
	protected Image IMAGE_MANNETJE;
	protected Image IMAGE_LEEG;
	protected Image IMAGE_ERASER;

	public GuiController() {
		dc = new DomeinController();
		loadImages();

	}

	/**
	 * Start de GUI.
	 * 
	 * @param stage De stage waarop de scenes geladen worden.
	 * @throws IOException indien er problemen zijn met Input/Output.
	 */
	@Override
	public void start(Stage stage) throws IOException {
		stage.setMinHeight(800);
		stage.setMinWidth(1600);
		stage.setMaximized(true);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaalSelectScherm.fxml"));
		fxmlLoader.setController(new TaalSelectSchermController(this));
		scene = new Scene(fxmlLoader.load());
		stage.setScene(scene);
		stage.show();
	}

	protected void switchScherm(Scherm scherm) {
		FXMLLoader fxmlLoader = null;
		switch (scherm) {
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
		case SpelCreatieScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelCreatieScherm.fxml"));
			fxmlLoader.setController(new SpelCreatieSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SpelbordCreatieScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelbordCreatieScherm.fxml"));
			fxmlLoader.setController(new SpelbordCreatieSchermController(this));
			try {
				scene.setRoot(fxmlLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:

		}
	}

	private void loadImages() {
		try {
			IMAGE_WALL = new Image(new FileInputStream("resources/images/wall.png"));
			IMAGE_VELD = new Image(new FileInputStream("resources/images/tile.png"));
			IMAGE_KIST = new Image(new FileInputStream("resources/images/box.png"));
			IMAGE_DOEL = new Image(new FileInputStream("resources/images/goal.png"));
			IMAGE_MANNETJE = new Image(new FileInputStream("resources/images/dr-mario-game.png"));
			IMAGE_LEEG = new Image(new FileInputStream("resources/images/surface.png"));
			IMAGE_ERASER = new Image(new FileInputStream("resources/images/eraser.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void afsluiten() {
		System.exit(0);
	}

}
